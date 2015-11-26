(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))

(defn submit-code
  ""
  [input error result]
  (POST "/api/evaluate"
        {:params        {:code @input}
         :handler       #(reset! result %)
         :error-handler #(reset! error %)}))

(defn editor-page
  "fun stuff"
  []
  (let [input (atom nil)
        error (atom nil)
        result (atom "yo")]
    (fn []
      [:div
       [:div.row>div.col-md-12>button
        {:on-click #(submit-code input error result)} "PRESS ME"]
       [:div.row>div.col-md-12
        [:p @input]

        [:textarea.form-control
         {:value     @input
          :on-change #(reset! input
                              (-> % .-target .-value))}]
        [:div.row>div.col-md-12
         [:p.form-control @result]]]])))