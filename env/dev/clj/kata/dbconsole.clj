(ns kata.dbconsole
  (:require [taoensso.timbre :as timbre])
  (:import org.h2.tools.Server))

(defonce admin (Server/createWebServer (into-array ["-ifExists" "-webPort" "3001"])))

(defn start-admin []
  (let [port (.getPort admin)]
    (if (.isRunning admin false)
      (timbre/error "H2 web console already running on port" port)
      (do (.start admin)
          (timbre/info "H2 web console started on port" port)))))


(defn stop-admin []
  (if (.isRunning admin false)
    (do (.stop admin)
        (timbre/info "H2 web console stopped"))))
