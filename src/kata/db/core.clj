(ns kata.db.core
  (:require
    [clojure.java.jdbc :as jdbc]
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]
    [conman.core :as conman]))

(defonce ^:dynamic conn (atom nil))

(conman/bind-connection conn "sql/queries.sql")

(def pool-spec
  {:adapter    :postgresql
   :init-size  1
   :min-idle   1
   :max-idle   4
   :max-active 32})

; Example of profile :database-url jdbc:postgresql://localhost/kata_dev?user=username&password=securepassword
(defn connect! []
  (conman/connect!
    conn
    (assoc
      pool-spec
      :jdbc-url (env :database-url))))

(defn disconnect! []
  (conman/disconnect! conn))