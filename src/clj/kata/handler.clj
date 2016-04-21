(ns kata.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [kata.layout :refer [error-page]]
            [kata.routes.home :refer [home-routes]]
            [kata.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [kata.middleware :as middleware]))

(def app-routes
  (routes
    #'service-routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
