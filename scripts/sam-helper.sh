#!/usr/bin/env bash

# usage:
# ./sam-helper.sh [package|deploy] [username]
# package|deploy required
# -- package is to package up the project, upload the jar to S3 and create an output CFN template
# -- deploy is to create/update the CFN stack and deploy all resources
# username optional
# -- if provided, will suffix the stack name

cd ../caampr-backend

bucket=caampr-lambda-code
input_template=cloudformation/template.yml
output_template=build/tmp/packaged-template.yml
stack_name=caampr-stack
overrides="ParameterKey=DevSuffix,ParameterValue=$2"

# If we have provided a suffix, update the stack name to reflect that
if [ -n $2 ]
then
 stack_name=$stack_name-$2
fi

echo "using template $input_template to produce $output_template for bucket $bucket"

if [ $1 = "package" ]
then
  echo 'packaging'
  sam.cmd package \
    --template-file $input_template \
    --s3-bucket $bucket \
    --output-template-file $output_template
elif [ $1 = "deploy" ]
then
  echo 'deploying'
  sam.cmd deploy \
    --template-file $output_template \
    --stack-name $stack_name \
    --capabilities CAPABILITY_NAMED_IAM \
    --parameter-overrides $overrides
fi


