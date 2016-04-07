(ns kata.pages.submit-problem
  (:require [ajax.core :refer [GET POST]]
            [reagent.core :as reagent]))

(defn save-input! [form-data id]
  (fn [event]
    (swap! form-data assoc id (.. event -target -value))))

(defn submit-problem [form-data result]
  (POST "/api/add-example"
        {:params {:problem @form-data}
         :handler #(reset! result %)}))

(defn render-code [this]
  (->> this reagent/dom-node (.highlightBlock js/hljs)))

(defn editor-did-mount [save-fn]
  (fn [this]
    (let [cm (.fromTextArea  js/CodeMirror
                             (reagent/dom-node this)
                             #js {:mode "clojure"
                                  :lineNumbers true})]
      (.on cm "change" save-fn))))

(defn editor [input]
  (reagent/create-class
    {:render (fn [] [:textarea.form-control
                     {:placeholder "code"
                      :value (:code @input)
                      :on-change (save-input! input :code)
                      :auto-complete "off"}])
     :component-did-mount (editor-did-mount
                            #(swap! input assoc :code (.getValue %)))}))

(defn eval-view [output]
  (reagent/create-class
    {:render (fn []
               [:pre>code.clj
                @output])
     :component-did-update render-code
     :component-did-mount render-code
     }))

(defn evaluate-code [input result]
  ""
  [input result]
  (POST "/api/evaluate"
        {:params  {:expr (:code @input)}
         :handler #(reset! result %)
         ;:error-handler #(reset! error %)
         }))

(defn submit-problem-form []
  (let [form-data (reagent/atom {})
        result (reagent/atom {})
        eval-result (reagent/atom {})]
    (fn []
      [:div.row>div.col-md-12
       [:h2 "Submit a problem"]
       [:input.form-control
        {:type :text
         :placeholder "title"
         :value (:title @form-data)
         :on-change (save-input! form-data :title)}]
       [:input.form-control
        {:type :text
         :placeholder "name"
         :value (:submitter @form-data)
         :on-change (save-input! form-data :submitter)}]
       [:textarea.form-control
        {:placeholder "description"
         :value (:description @form-data)
         :on-change (save-input! form-data :description)}]
       [:div.row>div.col-md-12
          [editor form-data]]
       [:div.row
        [:div.col-md-1>label "Result"]
        [:div.col-md-11
        [eval-view eval-result]]]
       [:button.btn.btn-primary
        {:on-click #(evaluate-code form-data eval-result)}
        "evaluate"]
       [:button.btn.btn-primary
        {:on-click #(submit-problem form-data result)}
        "submit"]
       (when-let [result @result]
         [:h2 result])])))

(defn submit-problem-page []
  [:div
   [submit-problem-form]])
