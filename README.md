# Transfer-Money-API
**Author: Ehab Elagizy**

2 APIs to retrieve balance and create transactions. The project is developed in Java Spring Boot.

## Prerequisites
- MongoDB installed
- MongoDB Compass (Recommended for viewing stored data)
- IntelliJ IDEA (Recommended for compiling and running the project)
- Postman (Recommended for testing API endpoints)

## Test the project
- Clone the project: https://github.com/Elagizy/Transfer-Money-API.git
- Open with IntelliJ IDEA or your favorite IDE.
- Compile and run the project.
- Test API endpoints with Postman, verify that http://localhost:8080/ is running.
- API service should be running if you received an error as below:
```
{
    "timestamp": "2019-07-24T05:55:42.034+0000",
    "status": 404,
    "error": "Not Found",
    "message": "No message available",
    "path": "/"
}
```
- Test the following endpoints using Swagger link specifications:  https://app.swaggerhub.com/apis/Elagizy/Transfer-Money-API/1.0.0
```
{
    http://localhost:8080/retrieve
    http://localhost:8080/create
}
```
- Verify the changes on the database **MoneyAPI** on both collections: **balance** and **transactions**
- Verify project functionality using the unit tests.
## Summary
- 2 API endpoints within the same project.
- Using Spring Boot (2.1.6.RELEASE), Spring Data to connect to MongoDB
- Using MongoDB to store balance and transactions
- Swagger API Specification: https://app.swaggerhub.com/apis/Elagizy/Transfer-Money-API/1.0.0
- APIs are following REST standards.
- The project is using Gradle for build automation.
- No 3rd-party libraries needed.
- MongoDB can be configured from **application.properties** file