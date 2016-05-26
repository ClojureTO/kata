(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [cljs.pprint :refer [pprint]]
            [kata.components.editor :refer [editor submit-code]]
            [kata.components.result :refer [result-view]]
            [reagent.session :as session]))

(defn editor-page
  "fun stuff"
  [& [init-input]]
  (let [input (reagent/atom init-input)
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

(defn problem-page
  []
  (let [problem (reagent/atom nil)
        input (reagent/atom nil)
        error (reagent/atom nil)
        result (reagent/atom {})
        cm (atom nil)]
    (reagent/create-class
      {:reagent-render      (fn []
                              [:div
                               [:button.btn.btn-primary
                                {:on-click #(submit-code @input error result)} "Submit"]
                               [editor cm input #(reset! input (.getValue %))]
                               [result-view result]])
       :component-did-mount #(GET (str "/api/examples/" (session/get :problem-id))
                                  {:handler         (fn [resp]
                                                      (reset! problem resp)
                                                      (.setValue (.getDoc @cm) (:code resp)))
                                   :response-format :json
                                   :keywords?       true})})))