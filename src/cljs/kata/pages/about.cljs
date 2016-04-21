(ns kata.pages.about
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]))

(defn about-page []
  [:div.container
   [:h1 "About Kata"]])