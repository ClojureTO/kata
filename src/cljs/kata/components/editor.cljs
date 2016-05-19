(ns kata.components.editor
  (:require [reagent.core :as reagent]
            [ajax.core :refer [POST]]))

(defn submit-code
  ""
  [input error result]
  (POST "/api/evaluate"
        {:params        {:expr input}
         :handler       #(reset! result %)
         :error-handler #(reset! error %)}))

(defn editor-did-mount [cm save-fn]
  (fn [this]
    (.on (reset! cm
                 (.fromTextArea js/CodeMirror
                                (reagent/dom-node this)
                                #js {:mode        "clojure"
                                     :lineNumbers true})) "change" save-fn)))

(defn editor [cm input save-fn]
  (reagent/create-class
    {:reagent-render      (fn [] [:textarea.form-control])
     :component-did-mount (editor-did-mount
                            cm
                            save-fn)}))