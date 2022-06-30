# ATA-Learn-And-Be-Curious-Project

Follow the instructions in the course for completing the group LBC project.

Deploy the DynamoDB tables using the following Cloudformation commands:

# RSVP table (check stack name & file://)
```
aws cloudformation create-stack --stack-name DynamoDBIndexes-RSVP --template-body file://RSVPTable.yaml --capabilities CAPABILITY_IAM
aws cloudformation wait stack-create-complete --stack-name DynamoDBIndexes-RSVP

```

Run the application with

```
./gradlew bootRunDev
```

Run Integration tests with
```
./gradlew :IntegrationTests:test
```