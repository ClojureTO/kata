(ns kata.db.core
  (:require
    [conman.core :as conman]
    [mount.core :refer [defstate]]
    [kata.config :refer [env]]))

; Example of profile :database-url jdbc:postgresql://localhost/kata_dev?user=username&password=securepassword
(defstate ^:dynamic *db*
          :start (if (env :dev)
                   (conman/connect!
                     {:datasource
                      (doto (org.h2.jdbcx.JdbcDataSource.)
                        (.setURL (env :database-url))
                        (.setUser "")
                        (.setPassword ""))})
                   (conman/connect!
                     {:jdbc-url (env :database-url)}))
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

