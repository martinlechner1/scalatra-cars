# CarAdvertApi #

## Quickstart

### Setup Database

* Setup a Mysql Database, e.g. via docker. 
* Configure the connection in application.properties.
* Apply db.sql to the database.

### Build and Run

```sh
$ cd CarApi
$ ./sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

## Swagger
[API DOCS](http://localhost:8080/api-docs/swagger.json)

Note: Fuel and the Instant types are broken and not displayed correctly.