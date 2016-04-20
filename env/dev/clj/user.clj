(ns user
  (:require [mount.core :as mount]
            [kata.figwheel :refer [start-fw stop-fw cljs]]
            kata.core))

(defn start []
  (mount/start-without #'kata.core/repl-server))

(defn stop []
  (mount/stop-except #'kata.core/repl-server))

(defn restart []
  (stop)
  (start))


