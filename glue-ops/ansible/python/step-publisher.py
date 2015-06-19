from troposphere import Base64, GetAtt, GetAZs, If, Select, Join, Output, Parameter, Ref, Template, Tags, Code, Function
from troposphere import sns, route53, rds, ec2, sqs, ecs, dynamodb

#
# TODO: Manually moved Function and Code to __init__.py as per https://github.com/cloudtools/troposphere/issues/268
#

__author__ = 'henning'

def GenerateStepPublisherLayer():
    t = Template()

    t.add_description("""\
    StepScheduler Layer
    """)

    stackname_param = t.add_parameter(Parameter(
        "StackName",
        Description="Environment Name (default: hackathon)",
        Type="String",
        Default="hackathon",
    ))

    vpcid_param = t.add_parameter(Parameter(
        "VpcId",
        Type="String",
        Description="VpcId of your existing Virtual Private Cloud (VPC)",
        Default="vpc-fab00e9f"
    ))

    subnets = t.add_parameter(Parameter(
        "Subnets",
        Type="CommaDelimitedList",
        Description=(
            "The list SubnetIds, for public subnets in the "
            "region and in your Virtual Private Cloud (VPC) - minimum one"),
        Default="subnet-b68f3bef,subnet-9a6208ff,subnet-bfdd4fc8"
    ))

    keypair_param = t.add_parameter(Parameter(
        "KeyPair",
        Description="Name of an existing EC2 KeyPair to enable SSH "
                    "access to the instance",
        Type="String",
        Default="glueteam"
    ))

    scheduler_ami_id_param = t.add_parameter(Parameter(
        "SchedulerAmiId",
        Description="Scheduler server AMI ID (default: ami-a10897d6)",
        Type="String",
        Default="ami-a10897d6"
    ))

    cluster_ami_id_param = t.add_parameter(Parameter(
        "ClusterAmiId",
        Description="Cluster server AMI ID (default: ami-3db4ca4a)",
        Type="String",
        Default="ami-3db4ca4a"
    ))

    iam_role_param = t.add_parameter(Parameter(
        "IamRole",
        Description="IAM Role name",
        Type="String",
    ))

    hashkeyname_param = t.add_parameter(Parameter(
        "HaskKeyElementName",
        Description="HashType PrimaryKey Name (default: id)",
        Type="String",
        AllowedPattern="[a-zA-Z0-9]*",
        MinLength="1",
        MaxLength="2048",
        ConstraintDescription="must contain only alphanumberic characters",
        Default="id"
    ))

    crontab_tablename_param = t.add_parameter(Parameter(
        "CrontabTablename",
        Description="Crontab Table Name",
        Type="String",
    ))

    containerlauncher_param = t.add_parameter(Parameter(
        "Containerlauncher",
        Description="Container Launcher zip file (default: containerLauncher-1.0.zip)",
        Type="String",
        Default="containerLauncher-1.0.zip"
    ))

    zipfileversion_param = t.add_parameter(Parameter(
        "ZipfileVersion",
        Description="Container Launcher zip file version",
        Type="String",
    ))

    # --------- Lambda Container Launcher

    lambda_function = t.add_resource(Function(
        "containerLauncher",
        Code=Code(
            S3Bucket="hackathon-glueteam-lambda",
            S3Key=Ref(containerlauncher_param),
            S3ObjectVersion=Ref(zipfileversion_param),
        ),
        Description=Join('', [Ref(stackname_param), " container Launcher"]),
        MemorySize=256,
        Handler="com.philips.glueteam.DockerLauncher::myHandler",
        Runtime="java8",
        Timeout=60,
        Role=Join('', ["arn:aws:iam::", Ref("AWS::AccountId"), ":role/", Ref(iam_role_param)]),
    ))

    townclock_topic = t.add_resource(sns.Topic(
        "TownClock",
        Subscription=[
            sns.Subscription(
                Endpoint=GetAtt("containerLauncher", "Arn"),
                Protocol="lambda"
            ),
        ],
    ))

    # --------- Scheduler instance

    scheduler_sg = t.add_resource(ec2.SecurityGroup(
        'SchedulerSG',
        GroupDescription='Security group for Scheduler host',
        VpcId=Ref(vpcid_param),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "SchedulerSG"])
        ),
        SecurityGroupIngress=[
            ec2.SecurityGroupRule(
                IpProtocol="tcp",
                FromPort="22",
                ToPort="22",
                CidrIp="0.0.0.0/0",
            ),
            ec2.SecurityGroupRule(
                IpProtocol="tcp",
                FromPort="8080",
                ToPort="8080",
                CidrIp="0.0.0.0/0",
            ),
        ]
    ))

    cluster = t.add_resource(ecs.Cluster(
        "ECSCluster",
    ))

    scheduler_host = t.add_resource(ec2.Instance(
        'SchedulerHost',
        ImageId=Ref(scheduler_ami_id_param),
        InstanceType='t2.micro',
        KeyName=Ref(keypair_param),
        IamInstanceProfile=Ref(iam_role_param),
        NetworkInterfaces=[
            ec2.NetworkInterfaceProperty(
                AssociatePublicIpAddress=True,
                SubnetId=Select(0, Ref(subnets)),
                DeleteOnTermination=True,
                GroupSet=[Ref(scheduler_sg),],
                DeviceIndex=0,
            ),
        ],
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "Scheduler"]),
            Id=Join("", [Ref(stackname_param), "Scheduler"])
        ),
        UserData=Base64(Join('', [
            '#!/bin/bash\n',
            'yum update -y aws-cfn-bootstrap\n',
            'sns_topic_arn="', Ref(townclock_topic), '"\n',
            'region="', Ref("AWS::Region"), '"\n',
            'crontab_tablename="', Ref(crontab_tablename_param), '"\n',
            'ecs_clustername="', Ref(cluster), '"\n',
            'publish_source=https://raw.githubusercontent.com/hngkr/hackathon/master/ansible/files/unreliable-town-clock-publish\n',
            'publish=/usr/local/bin/unreliable-town-clock-publish\n',
            'curl -s --location --retry 10 -o $publish $publish_source\n',
            'chmod +x $publish\n',
            'cat <<EOF >/etc/cron.d/unreliable-town-clock\n',
            '*/2 * * * * ec2-user $publish "$sns_topic_arn" "$region" "$crontab_tablename" "$ecs_clustername"\n',
            'EOF\n',
        ])),
    ))

    cluster_sg = t.add_resource(ec2.SecurityGroup(
        'ClusterSG',
        GroupDescription='Security group for Cluster host',
        VpcId=Ref(vpcid_param),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "ClusterSG"])
        ),
        SecurityGroupIngress=[
            ec2.SecurityGroupRule(
                IpProtocol="tcp",
                FromPort="22",
                ToPort="22",
                CidrIp="0.0.0.0/0",
            ),
        ]
    ))

    cluster_host = t.add_resource(ec2.Instance(
        'ClusterHost',
        ImageId=Ref(cluster_ami_id_param),
        InstanceType='t2.micro',
        KeyName=Ref(keypair_param),
        # TODO: Should have multiple separate iam roles for townclock / clusterhost
        IamInstanceProfile=Ref(iam_role_param),
        NetworkInterfaces=[
            ec2.NetworkInterfaceProperty(
                AssociatePublicIpAddress=True,
                SubnetId=Select(0, Ref(subnets)),
                DeleteOnTermination=True,
                GroupSet=[Ref(cluster_sg),],
                DeviceIndex=0,
            ),
        ],
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "ClusterNode"]),
            Id=Join("", [Ref(stackname_param), "ClusterNode"])
        ),
        UserData=Base64(Join('', [
            '#!/bin/bash\n',
            'mkdir /etc/ecs\n',
            'cat <<EOF >/etc/ecs/ecs.config\n',
            'ECS_CLUSTER=', Ref(cluster), '\n',
            'EOF\n',
       ])),
    ))

    # --------- Expected DynamoDB Tables

    dirtylist_table = t.add_resource(dynamodb.Table(
        "DirtyList",
        AttributeDefinitions=[
            dynamodb.AttributeDefinition(Ref(hashkeyname_param), "S"),
        ],
        KeySchema=[
            dynamodb.Key(Ref(hashkeyname_param), "HASH")
        ],
        ProvisionedThroughput=dynamodb.ProvisionedThroughput(
            1,
            1
        )
    ))

    runlog_table = t.add_resource(dynamodb.Table(
        "RunLog",
        AttributeDefinitions=[
            dynamodb.AttributeDefinition(Ref(hashkeyname_param), "S"),
        ],
        KeySchema=[
            dynamodb.Key(Ref(hashkeyname_param), "HASH")
        ],
        ProvisionedThroughput=dynamodb.ProvisionedThroughput(
            1,
            1
        )
    ))

    # --------- Outputs
    t.add_output([
        Output(
            "clusterid",
            Description="Cluster Id",
            Value=Ref(cluster),
        )
    ])

    t.add_output([
        Output(
            "dirtylist",
            Description="DirtyList Tablename",
            Value=Ref(dirtylist_table),
        )
    ])

    t.add_output([
        Output(
            "runlog",
            Description="Runlog Tablename",
            Value=Ref(runlog_table),
        )
    ])

    t.add_output([
        Output(
            "lambdafunctionname",
            Description="Lambda Function Name",
            Value=Ref(lambda_function),
        )
    ])

    t.add_output([
        Output(
            "clocktowertopicarn",
            Description="Clock Tower Topic Arn",
            Value=Ref(townclock_topic),
        )
    ])

    return t

if __name__ == "__main__":

    t = GenerateStepPublisherLayer()
    with open('../cf/steppublisher.json', 'w') as f:
        f.write(str(t.to_json()))
    print "OK!"
