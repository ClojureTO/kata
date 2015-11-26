(ns kata.config
  (:require [taoensso.timbre :as timbre]))

(def defaults
  {:init
   (fn []
     (timbre/info "\n-=[kata started successfully]=-"))
   :middleware identity})
