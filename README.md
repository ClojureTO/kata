# kata

## Description
Kata is a project being worked on by members of ClojureTO with the goal of providing an environment for all levels of developers to learn and practice clojure as well as share knowledge and experience. 

Kata, a Japanese word, are detailed choreographed patterns of martial art movements practised either solo or in pairs. A code kata is an exercise in programming which helps a programmer hone their skills through practice and repetition. Popular sites like 4clojure.com, exercism.io, codewars.com, etc, provide environments for this type of learning to take place. Few sites for clojure problem solving exist and those that do (4clojure) are outdated. They're still great resources, but the codebase has changed a lot since they were created and so we're starting from the ground up, building our own code kata site with modern code and integrations, and a modern look.

To describe it like a tech start-up, **it's like 4clojure, stack overflow, and reddit, with social media integrations and gamification.**

For our first working prototype, we're planning to have a submission page for users (through a twitter login) to submit problems which will then be tweeted by the kata twitter account. People can reply to these tweets with their answers and chat about the problem. This will hopefully give us a leg up to gain some followers of the project as we develop the site. 

Once this is operational, our database should start gaining a base of problems to work with. We will move to having these problems solvable on our site and tracking which problems users solve, displaying answers in a thread-like manner similar to reddit. We're planning to implement voting systems, tags and categories, skill level, and some sort of scoring system.

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
