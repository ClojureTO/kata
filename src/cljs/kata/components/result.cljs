(ns kata.components.result
  (:require [reagent.core :as reagent]))

(defn render-code [this]
  (->> this reagent/dom-node (.highlightBlock js/hljs)))

(defn result-view [output]
  (reagent/create-class
    {:render (fn []
               [:pre>code.clj
                @output])
     :component-did-update render-code
     :component-did-mount render-code}))