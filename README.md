# kata

FIXME

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

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

## License

Copyright © 2015 ClojureTO
