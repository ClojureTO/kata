(ns kata.db.core
  (:require
    [environ.core :refer [env]]
    [conman.core :as conman]
    [mount.core :refer [defstate]]))

(defonce ^:dynamic conn (atom nil))

(conman/bind-connection conn "sql/queries.sql")

(defstate ^:dynamic *db*
          :start (conman/connect!
                   {:datasource
                    (doto (org.h2.jdbcx.JdbcDataSource.)
                      (.setURL (env :database-url))
                      (.setUser "")
                      (.setPassword ""))})
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")