# Learn and Be Curious Project - My Special Day (July 10, 2022)

Requirements:

    - The project contains an API and a front end.
    - Unit and integration tests are present and passing.
    - Have at least 70% unit test coverage on any Service classes (Both line and branch)
    - The project was successfully deployed.
    - The project contains at least 4 API endpoints.
    - Branches were used to develop individual features, as recorded in your repository's closed pull requests

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