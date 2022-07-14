# My Special Day

# RSVP table (check stack name & file://)
```
aws cloudformation create-stack --stack-name rsvp-invitees --template-body file://updatedTable.yaml --capabilities CAPABILITY_IAM
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