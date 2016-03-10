(ns kata.pages.problem-editor
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]
            [cljs.pprint :refer [pprint]]
            [re-com.dropdown :as dropdown]))

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

(defn editor-did-mount [input]
  (fn [this]
    (let [cm (.fromTextArea js/CodeMirror
                            (reagent/dom-node this)
                            #js {:mode        "clojure"
                                 :lineNumbers true})]
      (.on cm "change" #(reset! input (.getValue %))))))

(defn editor [input]
  (reagent/create-class
    {:render              (fn [] [:textarea
                                  {:default-value ""
                                   :auto-complete "off"}])
     :component-did-mount (editor-did-mount input)}))

(defn editor-page-with-dropdown []
  (let [items ["foo" "bar" "baz"]
        selected (atom nil)]
    (fn []
      [:div.row>div.col-md-12
       [:h2 @selected]
       [dropdown/single-dropdown
        :placeholder "Countries"
        :model selected
        :filter-box? true
        :on-change #(reset! selected %)
        :choices
        [{:id "AU" :label "Australia" :group "Group 1"}
         {:id "US" :label "United States" :group "Group 1"}
         {:id "GB" :label "United Kingdom" :group "Group 1"}
         {:id "AF" :label "Afghanistan" :group "Group 2"}]]])
    ))

(defn editor-page
  "fun stuff"
  []
  (let [input (atom nil)
        error (atom nil)
        result (atom {:foo :bar})]
    (fn []
      [:div
       [:div.row>div.col-md-12>button.btn.btn-primary
        {:on-click #(submit-code input error result)} "PRESS ME"]
       [:div.row>div.col-md-12
        [editor input]
        [:div.row>div.col-md-12
         [result-view result]]]])))
