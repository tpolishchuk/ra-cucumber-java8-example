### What is the project about?

Just a simple example how to test API with [REST Assured](http://rest-assured.io/) using [Cucumber Java 8](https://docs.cucumber.io/installation/java/#maven) as BDD tool

## Quick start

* Create DB structure with src/main/resources/sql/init_db.sql

* Run a demo application
```
mvn spring-boot:run
```

* Run tests
```
mvn integration-test
```

* Observe report stored in target/cucumber-reports/index.html
