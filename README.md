# Transactions Manager

## Overview

This application exposes APIs for handling `Transactions` between `Accounts` for `Users`

## Guidelines

This application does not start automatically the database, it is needed to do it externally.
Database uses [MariaDB](https://downloads.mariadb.org/mariadb/10.6.3/) by default in localhost:3306 (database 'main'), you can change this in [application.properties](src/main/resources/application.properties).

1. Clone this project
2. Execute Maven Wrapper
```
Windows
   mvnw.cmd spring-boot:run
```
```
Linux
   ./mvnw spring-boot:run
```
3. Use [Swagger UI](http://localhost:8080/swagger-ui/) to test
```
   http://localhost:8080/swagger-ui/
```

## Using APIs

###User
To create, provide a name in the body request. \
To research, provide User ID.

###Account
To create, provide a User ID in the body request. \
To research, use the Account ID.

###Transaction
To create, provide Account(s) ID(s) with the value of transaction in the body request. \
To research or rollback, use the Transaction ID.
