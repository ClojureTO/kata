(ns kata.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]))

(def conn
  {:classname   "org.h2.Driver"
   :connection-uri (:database-url env)
   :make-pool?     true
   :naming         {:keys   clojure.string/lower-case
                    :fields clojure.string/upper-case}})

(defqueries "sql/queries.sql" {:connection conn})
