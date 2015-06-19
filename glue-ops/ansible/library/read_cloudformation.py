#!/usr/bin/python
# This file is part of Ansible
#
# Ansible is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Ansible is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Ansible.  If not, see <http://www.gnu.org/licenses/>.

DOCUMENTATION = '''
---
module: read_cloudformation
short_description: Gets output variables from an existing AWS CloudFormation stack
description:
     - Gets output variables from an existing AWS CloudFormation stack.
version_added: "1.8"
options:
  stack_name:
    description:
      - name of the cloudformation stack
    required: true
    default: null
    aliases: []
  region:
    description:
      - The AWS region to use. If not specified then the value of the AWS_REGION or EC2_REGION environment variable, if any, is used.
    required: true
    default: null
    aliases: ['aws_region', 'ec2_region']
    version_added: "1.5"
author: Henning Kristensen
'''

EXAMPLES = '''
# Basic task example
- name: launch ansible cloudformation example
  read_cloudformation:
    stack_name: "ansible-cloudformation"
    region: "us-east-1"
  register: ansible_cloudformation_ress
'''

import json
import time

try:
    import boto
    import boto.cloudformation.connection
    HAS_BOTO = True
except ImportError:
    HAS_BOTO = False


def boto_exception(err):
    '''generic error message handler'''
    if hasattr(err, 'error_message'):
        error = err.error_message
    elif hasattr(err, 'message'):
        error = err.message
    else:
        error = '%s: %s' % (Exception, err)

    return error


def boto_version_required(version_tuple):
    parts = boto.Version.split('.')
    boto_version = []
    try:
        for part in parts:
            boto_version.append(int(part))
    except:
        boto_version.append(-1)
    return tuple(boto_version) >= tuple(version_tuple)


def stack_operation(cfn, stack_name, operation):
    '''gets the status of a stack while it is created/updated/deleted'''
    existed = []
    result = {}
    operation_complete = False
    while operation_complete == False:
        try:
            stack = invoke_with_throttling_retries(cfn.describe_stacks, stack_name)[0]
            existed.append('yes')
        except:
            if 'yes' in existed:
                result = dict(changed=True,
                              output='Stack Deleted',
                              events=map(str, list(stack.describe_events())))
            else:
                result = dict(changed= True, output='Stack Not Found')
            break
        if '%s_COMPLETE' % operation == stack.stack_status:
            result = dict(changed=True,
                          events = map(str, list(stack.describe_events())),
                          output = 'Stack %s complete' % operation)
            break
        if  'ROLLBACK_COMPLETE' == stack.stack_status or '%s_ROLLBACK_COMPLETE' % operation == stack.stack_status:
            result = dict(changed=True, failed=True,
                          events = map(str, list(stack.describe_events())),
                          output = 'Problem with %s. Rollback complete' % operation)
            break
        elif '%s_FAILED' % operation == stack.stack_status:
            result = dict(changed=True, failed=True,
                          events = map(str, list(stack.describe_events())),
                          output = 'Stack %s failed' % operation)
            break
        else:
            time.sleep(5)
    return result

IGNORE_CODE = 'Throttling'
MAX_RETRIES=3
def invoke_with_throttling_retries(function_ref, *argv):
    retries=0
    while True:
        try:
            retval=function_ref(*argv)
            return retval
        except boto.exception.BotoServerError, e:
            if e.code != IGNORE_CODE or retries==MAX_RETRIES:
                raise e
        time.sleep(5 * (2**retries))
        retries += 1

def main():
    argument_spec = ec2_argument_spec()
    argument_spec.update(dict(
            stack_name=dict(required=True),
        )
    )

    module = AnsibleModule(
        argument_spec=argument_spec,
    )
    if not HAS_BOTO:
        module.fail_json(msg='boto required for this module')

    stack_name = module.params['stack_name']
    region, ec2_url, aws_connect_kwargs = get_aws_connection_info(module)

    stack_outputs = {}
    stack_parameters = {}
    stack_tags = {}

    try:
        cfn = boto.cloudformation.connect_to_region(
                  region,
                  **aws_connect_kwargs
              )
    except boto.exception.NoAuthHandlerFound, e:
        module.fail_json(msg=str(e))

    result = {}
    stacks_matching_name = invoke_with_throttling_retries(cfn.describe_stacks, stack_name)
    if len(stacks_matching_name) > 0:
        stack = stacks_matching_name[0]
        for param in stack.parameters:
            stack_parameters[param.key] = param.value
        result['stack_parameters'] = stack_parameters

        for output in stack.outputs:
            stack_outputs[output.key] = output.value
        result['stack_outputs'] = stack_outputs

        result['stack_tags'] = stack.tags

    module.exit_json(**result)

# import module snippets
from ansible.module_utils.basic import *
from ansible.module_utils.ec2 import *

main()