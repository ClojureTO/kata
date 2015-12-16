(ns kata.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [kata.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (timbre/info "\n-=[kata started successfully using the development profile]=-"))
   :middleware wrap-dev})
