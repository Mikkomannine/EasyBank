# Continuous Integration and Deployment

____

## Continuous Integration

____

Continuous Integration (CI) is a development practice that requires developers to integrate code into a shared repository several times a day. Each check-in is then verified by an automated build, allowing teams to detect problems early. By integrating regularly, you can detect errors quickly, and locate them more easily. In Easybank we used Jenkins for CI. Jenkins is an open-source automation server that helps to automate the non-human part of the software development process, with continuous integration and facilitating technical aspects of continuous delivery. We have set up Jenkins pipeline to generate jacoco test reports for code coverage.  

____

## Continuous Deployment

____

Continuous Deployment is a software release process that uses automated testing to validate if changes are correct and stable for immediate production deployment. In Easybank, we used Docker and Jenkins for continuous deployment. Docker is a tool designed to make it easier to create, deploy, and run applications by using containers. Jenkins is used to automate the deployment process. The deployment process is triggered automatically when a new build is successfully created. The application is then deployed to a Docker container and made available for testing. Unfortunetly, the deployment process is still in progress.