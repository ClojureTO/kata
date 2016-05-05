(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [cljs.pprint :refer [pprint]]
            [kata.components.editor :refer [editor]]))

(defn render-code [this]
  (->> this reagent/dom-node (.highlightBlock js/hljs)))

(defn result-view [output]
  (reagent/create-class
    {:render               (fn []
                             [:pre>code.clj
                              @output])
     :component-did-update render-code
     :component-did-mount  render-code}))

(defn submit-code
  ""
  [input error result]
  (POST "/api/evaluate"
        {:params        {:expr @input}
         :handler       #(reset! result %)
         :error-handler #(reset! error %)}))

(defn editor-page
  "fun stuff"
  []
  (let [input (atom nil)
        error (atom nil)
        cm (atom nil)
        result (atom {:foo :bar})]
    (fn []
      [:div
       [:div.row>div.col-md-12>button.btn.btn-primary
        {:on-click #(submit-code input error result)} "PRESS ME"]
       [:div.row>div.col-md-12
        [editor cm input #(reset! input (.getValue %))]
        [:div.row>div.col-md-12
         [result-view result]]]])))