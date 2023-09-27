# Jenkins AMI Builder
---------------------------------------------------------------------------------------------

### Summary
---------------------------------------------------------------------------------------------

_This project creates custom AMI to host Jenkins using Packer_
- Uses Ubuntu 22.04 LTS as base image and creates custom AMI using Packer
- These AMIs have Jenkins agent installed
- GitHub Actions is used to create CI pipeline ti validate Packer files in every PR and run Packer in every merge in main branch

### Tools and Technologies
-----------------------

| Infrastructure        |   VPC, Route53, EC2, ElasticIP            |
|-----------------------|-------------------------------------------|
| Custom AMI            |   Packer                                  |


### To run code:
-----------------------

**Prerequisite:** [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-welcome.html), [Packer](https://www.packer.io/)


**Useful Commands**

1. Setup AWS credentials:
    ```
    aws configure
    ```
2. Set profile to environment variables:
    -   Linux/Mac
    ```
    export AWS_PROFILE= <profilename>
    ```
    -   Windows
    ```
    setx AWS_PROFILE <profilename>
    ```
3. Initialize Packer:
    ```
    packer init ./packer/ami.pkr.hcl
    ```
4. Validate Packer file:
    ```
    packer validate ./packer/ami.pkr.hcl
    ```
5. Build AMI:
    ```
    packer build ./packer/ami.pkr.hcl
    ```