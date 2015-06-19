from troposphere import Base64, GetAtt, GetAZs, If, Select, Join, Output, Parameter, Ref, Template, Tags
from troposphere import sns, route53, rds, ec2, sqs, ecs, dynamodb

__author__ = 'henning'

def GenerateStepJenkinsLayer():
    t = Template()

    t.add_description("""\
    Jenkins for Step Hackathon Layer
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

    jenkins_ami_id_param = t.add_parameter(Parameter(
        "JenkinsAmiId",
        Description="Jenkins server AMI ID (default: ami-f3641a84)",
        Type="String",
        Default="ami-f3641a84"
    ))

    operations_subdomain_hosted_zone_param = t.add_parameter(Parameter(
        "DashsoftHostedZoneParam",
        Description="HostedZone (default: hackathon.operations.dk)",
        Type="String",
        Default="hackathon.operations.dk"
    ))

    iam_role_param = t.add_parameter(Parameter(
        "IamRole",
        Description="IAM Role name",
        Type="String",
    ))

    # --------- Jenkins instance

    jenkins_sg = t.add_resource(ec2.SecurityGroup(
        'JenkinsSG',
        GroupDescription='Security group for Jenkins host',
        VpcId=Ref(vpcid_param),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "SG"])
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
                FromPort="80",
                ToPort="80",
                CidrIp="0.0.0.0/0",
            ),
            ec2.SecurityGroupRule(
                IpProtocol="tcp",
                FromPort="443",
                ToPort="443",
                CidrIp="0.0.0.0/0",
            ),
        ]
    ))

    jenkins_eip = t.add_resource(ec2.EIP(
        'JenkinsEIP',
        Domain='vpc',
    ))

    jenkins_eth0 = t.add_resource(ec2.NetworkInterface(
        "JenkinsEth0",
        Description=Join("", [Ref(stackname_param), " Eth0"]),
        GroupSet=[Ref(jenkins_sg), ],
        SourceDestCheck=True,
        SubnetId=Select(0, Ref(subnets)),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), " Interface 0"]),
            Interface="eth0",
        )
    ))

    jenkins_host = t.add_resource(ec2.Instance(
        'JenkinsHost',
        ImageId=Ref(jenkins_ami_id_param),
        InstanceType='m3.medium',
        KeyName=Ref(keypair_param),
        IamInstanceProfile=Ref(iam_role_param),
        NetworkInterfaces=[
            ec2.NetworkInterfaceProperty(
                NetworkInterfaceId=Ref(jenkins_eth0),
                DeviceIndex="0",
            ),
        ],
        Tags=Tags(
            Name=Ref(stackname_param),
            Id=Ref(stackname_param)
        ),
        UserData=Base64(Join('', [
            '#!/bin/bash\n',
        ])),
    ))

    jenkins_eip_assoc = t.add_resource(ec2.EIPAssociation(
        "JenkinsEIPAssoc",
        NetworkInterfaceId=Ref(jenkins_eth0),
        AllocationId=GetAtt("JenkinsEIP", "AllocationId"),
        PrivateIpAddress=GetAtt("JenkinsEth0", "PrimaryPrivateIpAddress"),
    ))

    jenkins_host_cname = t.add_resource(route53.RecordSetType(
        "JenkinsHostCname",
        HostedZoneName=Join("", [Ref(operations_subdomain_hosted_zone_param), "."]),
        Comment=Join("", ["Jenkins host CNAME for ", Ref(stackname_param)]),
        Name=Join("", ["jenkins.", Ref(operations_subdomain_hosted_zone_param), "."]),
        Type="A",
        TTL="60",
        ResourceRecords=[GetAtt("JenkinsHost", "PublicIp")],
        DependsOn="JenkinsEIPAssoc"
    ))

    return t

if __name__ == "__main__":

    t = GenerateStepJenkinsLayer()
    with open('../cf/stepjenkins.json', 'w') as f:
        f.write(str(t.to_json()))
    print "OK!"
