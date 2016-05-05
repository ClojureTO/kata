(ns kata.components.editor
  (:require [reagent.core :as reagent]))

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