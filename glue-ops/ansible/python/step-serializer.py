from troposphere import Base64, GetAtt, GetAZs, If, Select, Join, Output, Parameter, Ref, Template, Tags, Function, Code
from troposphere import sns, route53, rds, ec2, sqs, ecs, dynamodb

#
# TODO: Manually moved Function and Code to __init__.py as per https://github.com/cloudtools/troposphere/issues/268
#

__author__ = 'henning'

def GenerateSerializerLayer():
    t = Template()

    t.add_description("""\
    Serializer Layer
    """)

    serializername_param = t.add_parameter(Parameter(
        "SerializerName",
        Description="Serializer Name (default: SampleSerializer)",
        Type="String",
        Default="SampleSerializer",
    ))

    stackname_param = t.add_parameter(Parameter(
        "StackName",
        Description="Environment Name (default: hackathon)",
        Type="String",
        Default="hackathon",
    ))

    clusterid_param = t.add_parameter(Parameter(
        "ClusterId",
        Type="String",
        Description="ClusterId to run the serializer on",
    ))

    docker_id_param = t.add_parameter(Parameter(
        "DockerId",
        Description="DockerId (default: centos:latest)",
        Type="String",
        Default="centos:latest"
    ))

    execution_role_param = t.add_parameter(Parameter(
        "ExecutionRole",
        Description="Lambda Execution Role",
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

    dirtylist_param = t.add_parameter(Parameter(
        "DirtyList",
        Description="DirtyList Table Name",
        Type="String"
    ))

    runlog_param = t.add_parameter(Parameter(
        "RunLog",
        Description="RunLog Table Name",
        Type="String"
    ))

    canonical_prefix_param = t.add_parameter(Parameter(
        "CanonicalPrefix",
        Description="Canonical Tables Prefix",
        Type="String",
        Default="CANON_"
    ))

    serializer_table = t.add_resource(dynamodb.Table(
        "sampleSerializerTable",
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

    task_definition = t.add_resource(ecs.TaskDefinition(
        'TaskDefinition',
        ContainerDefinitions=[
            ecs.ContainerDefinition(
                Name=Join('', [Ref(stackname_param), "Task"]),
                Image=Ref(docker_id_param),
                Environment=[
                    ecs.Environment(Name="LAYER", Value=Ref(stackname_param)),
                    ecs.Environment(Name="SERIALIZER", Value=Ref(serializername_param)),
                    ecs.Environment(Name="SERIALIZER_TABLE", Value=Ref(serializer_table)),
                    ecs.Environment(Name="CANONICAL_PREFIX", Value=Ref(canonical_prefix_param)),
                    ecs.Environment(Name="DIRTYLIST", Value=Ref(dirtylist_param)),
                    ecs.Environment(Name="RUNLOG", Value=Ref(runlog_param))
                ],
                Memory=512,
            )
        ],
        Volumes=[],
    ))

    t.add_output([
        Output(
            "taskdefinitionid",
            Description="Task Definition Id",
            Value=Ref(task_definition),
        )
    ])

    return t

if __name__ == "__main__":

    t = GenerateSerializerLayer()
    with open('../cf/stepserializer.json', 'w') as f:
        f.write(str(t.to_json()))
    print "OK!"
