(ns kata.endpoint.main-view
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]))

(def main-page
  (io/resource "kata/endpoint/index.html"))

(defn main-endpoint [config]
  (routes
   (GET "/" [] main-page)))
