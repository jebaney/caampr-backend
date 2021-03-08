# caampr-backend

## Wut?
This repo contains the backend API handlers to drive the frontend UI.

## Building and Deploying

### Prereqs
* [gradle installed](https://gradle.org/install/)
* [aws cli installed](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html)
* [sam installed](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
* IAM access to AWS account with local credentials pulled down

### Build
* clone this repo
* build via gradle from project root dir
  * `cd caampr-backend`
  * `gradle build`
* Or if you have the gradle intellij plugin installed 
  * run the gradle plugin `build` task
    * Open gradle toolbox
    * caampr -> Tasks -> build -> `build`
  
### Deploy to AWS
* build and deploy via cli
  * `cd caampr-backend`
  * `gradle clean`
  * `gradle build`
  * `gradle buildAWSLambdaZip`
* At this point, the lambda is all packaged up in the `caampr-backend/build/distributions` directory as a zip file
  * Via my sciprt
    * `cd ../scripts`
    * `./sam-helper.sh package <username>`
    * `./sam-helper.sh deploy <username>`
  * Via sam cli directly
    * look at what the sam-helper.sh script is doing any copy the commands ;-)
  
## Testing
* Via the AWS Console
  * Log in to console
  * Go to Lambda -> Functions -> Caampr-<api><username>
    * example function: `Caampr-GetGearListjbaney`
  * Click the 'test' tab
  * Chose from an existing template
  * template: "Amazon API Gateway AWS Proxy"
    * This creates a sample request payload that mimics a request coming from api gateway
  * when the lambda is doing something for real, update the template payload to include any headers/body as needed
  

## Intellij 

### Plugins
* lombok
* gradle
* aws toolkit

### Issues
* dagger from gradle and intellij didn't place nice together.
* just use the dagger task to generate the dagger classes
* intellij builds will fail, but gradle builds will be fine
* install the gradle plugin for intellij