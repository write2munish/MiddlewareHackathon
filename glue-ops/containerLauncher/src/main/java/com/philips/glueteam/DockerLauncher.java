package com.philips.glueteam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.ecs.AmazonECSClient;
import com.amazonaws.services.ecs.model.Cluster;
import com.amazonaws.services.ecs.model.DescribeClustersRequest;
import com.amazonaws.services.ecs.model.ListClustersResult;
import com.amazonaws.services.ecs.model.RunTaskRequest;
import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

/**
 * Created by mikkel on 16-06-2015.
 */
public class DockerLauncher {
    public DockerLauncher() {
    }

    public String myHandler(DockerLauncherInput input, Context context) {
    	
    	System.out.println("Hello, World");
    	System.out.println(input.getCrontabTablename());
    	System.out.println(input);
    	System.out.println("----");
    	
        String environment = context.getFunctionName().split("-")[0];

        Region region = Region.getRegion(Regions.EU_WEST_1);

        AWSCredentialsProvider credentials = new DefaultAWSCredentialsProviderChain();
        AmazonECSClient ecsClient = region.createClient(AmazonECSClient.class, credentials, new ClientConfiguration());

        String clusterName = null;

        ListClustersResult describeClustersResult = ecsClient.listClusters();
        for (Cluster cluster : ecsClient.describeClusters(new DescribeClustersRequest().withClusters(describeClustersResult.getClusterArns())).getClusters()) {
            if (cluster.getClusterName().startsWith(environment + "-")) clusterName = cluster.getClusterName();
        }

        if (clusterName == null) {
            context.getLogger().log("Cluster not found with correct prefix: '" + environment + "-'");
            return "Error";
        }

        Map<String, String> taskNameToFullName = new LinkedHashMap<>();
        ecsClient.listTaskDefinitionFamilies().getFamilies().stream()
                .filter(family -> family.startsWith(environment + "-"))
                .forEach(family -> taskNameToFullName.put(family.split("-")[1], family));


        switch (input.getEventType()) {
            case "heartbeat":
                AmazonDynamoDBClient dbClient = region.createClient(AmazonDynamoDBClient.class, credentials, new ClientConfiguration());
                String tableName = environment + "_scheduler";
                CronParser cronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
                DynamoDB db = new DynamoDB(dbClient);


                Table t = db.getTable(tableName);
                for (Item item : t.scan()) {
                    String taskName = item.getString("taskname");
                    Set<String> cronList = item.getStringSet("schedule");
                    long lastExecution = item.getLong("lastExecution");

                    for (String cron : cronList) {
                        ExecutionTime executionTime = ExecutionTime.forCron(cronParser.parse(cron));
                        DateTime lastExecutionFromCron = executionTime.lastExecution(DateTime.now());
                        if (lastExecutionFromCron.isAfter(lastExecution)) {
                            try {
                                ecsClient.runTask(new RunTaskRequest().withCluster(clusterName).withCount(1).withTaskDefinition(taskNameToFullName.get(taskName) + ":1"));
                                Item updatedItem = item.withLong("lastExecution", lastExecutionFromCron.getMillis());
                                t.putItem(updatedItem);
                            } catch (Exception e1) {
                                context.getLogger().log("Failed to run task: " + taskName + ". Error message is: " + e1.getMessage());
                                context.getLogger().log(exceptionToString(e1));
                            }
                            break;
                        }
                    }
                }
                break;
            case "launchNow":
                try {
                    ecsClient.runTask(new RunTaskRequest().withCluster(clusterName).withCount(1).withTaskDefinition(taskNameToFullName.get(input.getTaskName()) + ":1"));
                } catch (Exception e) {
                    context.getLogger().log("Failed to run task: " + input.getTaskName() + ". Error message is: " + e.getMessage());
                    context.getLogger().log(exceptionToString(e));
                    return "Error";
                }

                break;
        }

        return "Ok";
    }

    private String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        pw.close();
        return sw.toString();
    }

    public static void main(String[] args) {
        new DockerLauncher().myHandler(new DockerLauncherInput("heartbeat", null), new SimulateContext());
        new DockerLauncher().myHandler(new DockerLauncherInput("launchNow", "TaskD"), new SimulateContext());
    }

    private static class SimulateContext implements Context {
        @Override
        public String getAwsRequestId() {
            return null;
        }

        @Override
        public String getLogGroupName() {
            return null;
        }

        @Override
        public String getLogStreamName() {
            return null;
        }

        @Override
        public String getFunctionName() {
            return "StepP-dummy";
        }

        @Override
        public CognitoIdentity getIdentity() {
            return null;
        }

        @Override
        public ClientContext getClientContext() {
            return null;
        }

        @Override
        public int getRemainingTimeInMillis() {
            return 0;
        }

        @Override
        public int getMemoryLimitInMB() {
            return 0;
        }

        @Override
        public LambdaLogger getLogger() {
            return string -> System.out.println("logged: " + string);
        }
    }
}
