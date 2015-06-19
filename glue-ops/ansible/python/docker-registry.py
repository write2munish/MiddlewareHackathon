from troposphere import Base64, GetAtt, GetAZs, If, Select, Join, Output, Parameter, Ref, Template, Tags
from troposphere import sns, route53, rds, ec2, sqs

__author__ = 'henning'

# Based on:
#
#  http://codepen.io/tsabat/blog/s3-backed-docker-private-registry-on-aws
#
#
def GenerateDockerRegistryLayer():
    t = Template()

    t.add_description("""\
    DockerRegistry Layer
    """)

    stackname_param = t.add_parameter(Parameter(
        "StackName",
        Description="Environment Name (default: test)",
        Type="String",
        Default="test",
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

    registry_ami_id_param = t.add_parameter(Parameter(
        "RegistryAmiId",
        Description="Registry server AMI ID",
        Type="String",
        Default="ami-a10897d6"
    ))

    iam_role_param = t.add_parameter(Parameter(
        "IamRole",
        Description="IAM Role name",
        Type="String",
    ))

    s3bucket_param = t.add_parameter(Parameter(
        "BucketName",
        Description="S3 Bucket Name (default: )",
        Type="String",
        Default="",
    ))

    # --------- Docker registry

    registry_sg = t.add_resource(ec2.SecurityGroup(
        'RegistrySG',
        GroupDescription='Security group for Registry host',
        VpcId=Ref(vpcid_param),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "RegistrySG"])
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
        ]
    ))

    registry_eip = t.add_resource(ec2.EIP(
        'RegistryEIP',
        Domain='vpc',
    ))

    registry_eth0 = t.add_resource(ec2.NetworkInterface(
        "RegistryEth0",
        Description=Join("", [Ref(stackname_param), "Registry Eth0"]),
        GroupSet=[Ref(registry_sg), ],
        SourceDestCheck=True,
        SubnetId=Select(0, Ref(subnets)),
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "Registry Interface 0"]),
            Interface="eth0",
        )
    ))

    registry_host = t.add_resource(ec2.Instance(
        'RegistryHost',
        ImageId=Ref(registry_ami_id_param),
        InstanceType='t2.micro',
        KeyName=Ref(keypair_param),
        IamInstanceProfile=Ref(iam_role_param),
        NetworkInterfaces=[
            ec2.NetworkInterfaceProperty(
                NetworkInterfaceId=Ref(registry_eth0),
                DeviceIndex="0",
            ),
        ],
        Tags=Tags(
            Name=Join("", [Ref(stackname_param), "Registry"]),
            Id=Join("", [Ref(stackname_param), "Registry"])
        ),
        UserData=Base64(Join('', [
            '#!/bin/bash\n',
            'yum update -y aws-cfn-bootstrap\n',
            'mkdir -p /root/build/redis /root/build/registry\n',
            'touch /root/build/redis/Dockerfile\n',
            'touch /root/build/redis/redis.conf\n',
            'touch /root/build/registry/Dockerfile\n',
        ])),
    ))

    registry_eip_assoc = t.add_resource(ec2.EIPAssociation(
        "RegistryEIPAssoc",
        NetworkInterfaceId=Ref(registry_eth0),
        AllocationId=GetAtt("RegistryEIP", "AllocationId"),
        PrivateIpAddress=GetAtt("RegistryEth0", "PrimaryPrivateIpAddress"),
    ))

    return t

if __name__ == "__main__":

    t = GenerateDockerRegistryLayer()
    with open('../cf/dockerregistry.json', 'w') as f:
        f.write(str(t.to_json()))
    print "OK!"
