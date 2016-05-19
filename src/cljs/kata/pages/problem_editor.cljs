(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [cljs.pprint :refer [pprint]]
            [kata.components.editor :refer [editor submit-code]]
            [kata.components.result :refer [result-view]]))

(defn editor-page
  "fun stuff"
  []
  (let [input (reagent/atom nil)
        error (reagent/atom nil)
        result (reagent/atom {})
        cm (atom nil)]
    (fn []
      [:div
       [:div.row>div.col-md-12>button.btn.btn-primary
        {:on-click #(submit-code @input error result)} "PRESS ME"]
       [:div.row>div.col-md-12
        [editor cm input #(reset! input (.getValue %))]
        [:div.row>div.col-md-12
         [result-view result]]]])))