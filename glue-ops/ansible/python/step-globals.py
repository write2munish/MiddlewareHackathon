from troposphere import Base64, GetAtt, GetAZs, If, Select, Join, Output, Parameter, Ref, Template, Tags, Function, Code
from troposphere import sns, route53, rds, ec2, sqs, ecs, dynamodb

#
# TODO: Manually moved Function and Code to __init__.py as per https://github.com/cloudtools/troposphere/issues/268
#

__author__ = 'henning'

def GenerateGlobalLayer():
    t = Template()

    t.add_description("""\
    Global Layer
    """)

    stackname_param = t.add_parameter(Parameter(
        "StackName",
        Description="Environment Name (default: StepGlobals)",
        Type="String",
        Default="StepGlobals",
    ))

    crontab_table = t.add_resource(dynamodb.Table(
        "scheduleTable",
        AttributeDefinitions=[
            dynamodb.AttributeDefinition("taskname", "S"),
        ],
        KeySchema=[
            dynamodb.Key("taskname", "HASH")
        ],
        ProvisionedThroughput=dynamodb.ProvisionedThroughput(
            1,
            1
        )
    ))

    t.add_output([
        Output(
            "crontabtablename",
            Description="Crontab Table Name",
            Value=Ref(crontab_table),
        )
    ])

    return t

if __name__ == "__main__":

    t = GenerateGlobalLayer()
    with open('../cf/stepglobals.json', 'w') as f:
        f.write(str(t.to_json()))
    print "OK!"
