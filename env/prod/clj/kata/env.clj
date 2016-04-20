(ns kata.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[kata started successfully]=-"))
   :middleware identity})
