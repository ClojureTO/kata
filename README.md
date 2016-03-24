# kata


## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Before you create the database, you need to create `profiles.clj` under your
project root, and add your database connection strings:

    {:profiles/dev  {:env {:database-url "jdbc:h2:./kata_dev.db"}}
     :profiles/test {:env {:database-url "jdbc:h2:./kata_test.db"}}}

When running for the first time, you'll need to create a database by running the following command:

    lein run migrate

The application has two parts that have to be run independently.
The Clojure side of the application is run using:

    lein run

To start the ClojureScript portion is run with Figwheel:

    lein figwheel
    
You will need two separate terminal to run these.
Once both start the application will be available at `http://localhost:3000`.

The application will also start a remote REPL that you can connect your editor to.
The default REPL port is `7000`. When connecting the editor select remote REPL, then
put in `localhost` for the server and `7000` for the port.

If you think the application got in a bad state then stop both `lein` commands and
run `lein clean`. This will remove all the generated assets and next time you start
the application everything will be compiled from scratch.

## Deploy to heroku

1. Ask nicely for permission to push
2. Add heroku git remote:

```bash
heroku login # log in to heroku
cd [kata repository]
heroku git:remote -a kata-clj
```
To make a new dpeloyment, do

```bash
git push heroku master
```

## Troubleshoots

When you run `lein figwheel` and you get something like:

> Caused by: java.io.FileNotFoundException: Could not locate cljs/analyzer__init.class or cljs/analyzer.clj on classpath:

try upgrade leiningen with `lein upgrade`.

## License

Copyright © 2015 ClojureTO
