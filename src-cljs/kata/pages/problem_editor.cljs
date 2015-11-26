(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]
            [cljs.pprint :refer [pprint]]))

(defn render-code [this]
  (->> this reagent/dom-node (.highlightBlock js/hljs)))

(defn result-view [output]
  (reagent/create-class
    {:render (fn []
               [:pre>code.clj
                @output])
     :component-did-update render-code
     :component-did-mount render-code}))

(defn submit-code
  ""
  [input error result]
  (POST "/api/evaluate"
        {:params        {:expr @input}
         :handler       #(reset! result %)
         :error-handler #(reset! error %)}))

(defn editor-did-mount [input]
  (fn [this]
    (let [cm (.fromTextArea  js/CodeMirror
                             (reagent/dom-node this)
                             #js {:mode "clojure"
                                  :lineNumbers true})]
      (.on cm "change" #(reset! input (.getValue %))))))

(defn editor [input]
  (reagent/create-class
    {:render (fn [] [:textarea
                     {:default-value ""
                      :auto-complete "off"}])
     :component-did-mount (editor-did-mount input)}))

(defn editor-page
  "fun stuff"
  []
  (let [input (atom nil)
        error (atom nil)
        result (atom {:foo :bar})]
    (fn []
      [:div
       [:div.row>div.col-md-12>button
        {:on-click #(submit-code input error result)} "PRESS ME"]
       [:div.row>div.col-md-12
        [editor input]
        [:div.row>div.col-md-12
         [result-view result]]]])))
