# Scalatra-cars

Implementation of a sample RESTful api using Scalatra, Quill, Mysql and Docker. 

## Quickstart
Prequisites: You have `docker` and `docker-compose` installed. 

```sh
$ docker-compose up
```

## Manual Setup

* Scala (2.11.11) and sbt (0.13.15) should be installed
* Setup a Mysql Database, e.g. via docker. 
* Create a user 'carapi'.
* Apply db.sql to the database. (quill has no dbmigrations...)
* Configure the connection in application.conf or use ENV vars:
  * DB_HOST
  * DB_PORT
  * DB_USER (should be carapi_user or root)
  * DB_PASSWORD (for the user trying to connect)


```sh
$ ./sbt
> jetty:start
```

## Swagger
* Autogenerated [API DOCS](http://localhost:8080/api-docs/swagger.json)
* You might use [swagger-ui](http://swagger.io/swagger-ui/) to explore the api.

Note: Fuel and the Instant(Date) types are broken and not correct.

## Tests
* Specs2 is used as testing framework. There are unit tests for the api layer including validation.
* There should be integration tests for the db layer (TODO).
* Don't be confused by  ``[info] No tests were executed.``, this is because Scalatest is somewhere on the classpath. All tests execute fine.