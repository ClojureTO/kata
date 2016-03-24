(ns kata.test.db.core
  (:require [kata.db.core :as db]
            [kata.db.migrations :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(use-fixtures
  :once
  (fn [f]
    (migrations/migrate ["migrate"])
    (db/connect!)
    (f)))

(deftest test-users
  (jdbc/with-db-transaction [t-conn @db/conn]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-user!
               {:id         "1"
                :first_name "Sam"
                :last_name  "Smith"
                :email      "sam.smith@example.com"
                :pass       "pass"} {:connection t-conn})))
    (is (= [{:id         "1"
             :first_name "Sam"
             :last_name  "Smith"
             :email      "sam.smith@example.com"
             :pass       "pass"
             :admin      nil
             :last_login nil
             :is_active  nil}]
           (db/get-user {:id "1"} {:connection t-conn})))))
