cd ../caampr-backend
sam.cmd package --template-file cloudformation/template.yml --s3-bucket caampr-site --output-template-file build/tmp/packaged-template.yml
sam.cmd deploy --template-file build/tmp/packaged-template.yml --stack-name caampr-stack --capabilities CAPABILITY_NAMED_IAM
