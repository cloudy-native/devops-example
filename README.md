# Teaching Harvard Extension DevOps

An end-to-end DevOps example. 

It's a small fake microservice that delivers fake recommendations for fake members of a shopping site.

You can run the (Java) microservice three ways
- Docker
- AWS Lambda (Serverless)
- Spring Boot standalone

## Prerequisites

- Maven 
- Docker
- Java IDE

### Optional

- Amazon Web Services account and credentials
- AWS CLI

# Build

```
mvn clean install
```

# Run Standalone

Import the Maven project into an IDE. Run the main class `org.harrison.devops.Application`. A Spring Boot application should start on port `8080`.

Test it's working by opening http://localhost:8080/member/5/brands in a browser. You should see something line 
```
[{"brandId":"brand-29xc09","score":0.29238153},{"brandId":"brand-9jy8u8","score":0.10777217}]
```

# Run Locally in Docker

The build created a Docker image `username/devops-example-application:0.0.1`. You can run it using

```
docker run -p 8080:8080 username/devops-example-application:0.0.1
```

The `-p 8080:8080` bit tells Docker to map port `8080` from the Docker container to the same port on the host.

> If you get a weird Docker error and you think you've installed Docker just fine, it's probably because you left the standalone version running on the same port.

You can test it by opening the above URL in a browser.

# Run Serverless on Amazon Web Services

We're using AWS Cloudformation. Or actually a variant called SAM for https://github.com/awslabs/serverless-application-model .

Compile and build using

```
mvn clean install
```

Then

```
cd devops-example-serverless
```

Now _package_ the application and upload to AWS S3.

```
aws cloudformation package --template-file sam.yaml --output-template-file sam-output.yaml --s3-bucket your-bucket
```

where `your-bucket` is a unique bucket you created. This step creates a file `sam-output.yaml`, which we use in the next step.

Now _deploy_ using `sam-output.yaml`.

```
aws cloudformation deploy --template-file sam-output.yaml --stack-name your-stack --capabilities CAPABILITY_IAM
```

If this completes successfully, your code is deployed to a stack called `your-stack`. A bunch of resources were created from the `sam.yaml` file. You'll need to find out what they are to construct the URL to your Lambda.

Say

```
aws cloudformation describe-stack-resources --stack-name your-stack --output table
```

and you should get the output something like

```
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
|                                                                                                                                               DescribeStackResources                                                                                                                                                |
+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
||                                                                                                                                                  StackResources                                                                                                                                                   ||
|+--------------------------------------------------+--------------------------------------------------------------------+------------------+------------------------------+-----------------------------------------------------------------------------------------------+------------+----------------------------+|
||                 LogicalResourceId                |                        PhysicalResourceId                          | ResourceStatus   |        ResourceType          |                                            StackId                                            | StackName  |         Timestamp          ||
|+--------------------------------------------------+--------------------------------------------------------------------+------------------+------------------------------+-----------------------------------------------------------------------------------------------+------------+----------------------------+|
||  PersonalizationFunction                         |  d1-PersonalizationFunction-TXT8TQGDOJT3                           |  UPDATE_COMPLETE |  AWS::Lambda::Function       |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-26T00:17:00.899Z  ||
||  PersonalizationFunctionGetResourcePermissionProd|  d1-PersonalizationFunctionGetResourcePermissionProd-HZ0LP3EH5RUG  |  CREATE_COMPLETE |  AWS::Lambda::Permission     |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:56:22.327Z  ||
||  PersonalizationFunctionGetResourcePermissionTest|  d1-PersonalizationFunctionGetResourcePermissionTest-1P7H93WC7NUK7 |  CREATE_COMPLETE |  AWS::Lambda::Permission     |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:56:22.589Z  ||
||  PersonalizationFunctionRole                     |  d1-PersonalizationFunctionRole-1C6007WPS5F5L                      |  CREATE_COMPLETE |  AWS::IAM::Role              |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:55:58.451Z  ||
||  ServerlessRestApi                               |  gsxknxxuy6                                                        |  CREATE_COMPLETE |  AWS::ApiGateway::RestApi    |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:56:08.717Z  ||
||  ServerlessRestApiDeploymentcdf57dea48           |  x6c0bb                                                            |  CREATE_COMPLETE |  AWS::ApiGateway::Deployment |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:56:12.235Z  ||
||  ServerlessRestApiProdStage                      |  Prod                                                              |  CREATE_COMPLETE |  AWS::ApiGateway::Stage      |  arn:aws:cloudformation:us-east-1:502469999500:stack/d1/9fd80c80-590e-11e7-b840-50fae583dcd2  |  d1        |  2017-06-24T18:56:16.540Z  ||
|+--------------------------------------------------+--------------------------------------------------------------------+------------------+------------------------------+-----------------------------------------------------------------------------------------------+------------+----------------------------+|
```

The most interested resource is the `ServerlessRestApi`. Use its value (`gsxknxxuy6` above, but yours will be different) in a URL to test the lambda as follows.

```
curl https://gsxknxxuy6.execute-api.us-east-1.amazonaws.com/Prod/member/4/brands
```

