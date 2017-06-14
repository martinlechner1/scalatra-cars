# CarApi #

## Build & Run ##

## Setup Database

Setup a Mysql Database, e.g. via docker. 

Configure the connection in application.conf.
 
Apply db.sql to the database.

```sh
$ cd CarApi
$ ./sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.
