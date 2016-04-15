(ns kata.pages.submit-problem
  (:require [ajax.core :refer [GET POST]]
            [reagent.core :as reagent]))

(defn save-input! [form-data id]
  (fn [event]
    (swap! form-data assoc id (.. event -target -value))))

(defn fields-present? [form-data fields]
  (not-any? empty? (map @form-data fields)))

(defn submit-problem [form-data result]
  (if-not (fields-present? form-data [:title :submitter :description])
    (js/alert "Required field(s) are missing.")
    (POST "/api/add-example"
      {:params {:problem @form-data}
       :handler #(reset! result %)})))

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

(defn text-field
  ([form-data field-name]
   (text-field form-data field-name (name field-name)))
  ([form-data field-name label]
   [:div.form-group
    {:class (if (empty? (field-name @form-data)) "has-error")}
    [:input.form-control
     {:type :text
      :placeholder label
      :value (field-name @form-data)
      :on-change (save-input! form-data field-name)}]]))

(defn textarea
  ([form-data field-name]
   (textarea form-data field-name (name field-name)))
  ([form-data field-name label]
   [:div.form-group
    {:class (if (empty? (field-name @form-data)) "has-error")}
    [:textarea.form-control
     {:placeholder label
      :value (field-name @form-data)
      :on-change (save-input! form-data field-name)}]]))

(defn submit-problem-form []
  (let [form-data (reagent/atom {})
        result (reagent/atom {})
        eval-result (reagent/atom {})]
    (fn []
      [:div.row>div.col-md-12
       [:h2 "Submit a problem"]
       (text-field form-data :title)
       (text-field form-data :submitter "your name")
       (textarea form-data :description)
       [:div.form-group
        [:div.row>div.col-md-12
         [editor form-data]]]
       [:div.row
        [:div.form-group
         [:div.col-md-1>label "Result"]
         [:div.col-md-11
          [eval-view eval-result]]]]
       [:div.form-group
        [:button.btn.btn-primary
         {:on-click #(evaluate-code form-data eval-result)}
         "evaluate"]
        [:button.btn.btn-primary
         {:on-click #(submit-problem form-data result)}
         "submit"]]
       (when-let [result @result]
         [:h2 result])])))

(defn submit-problem-page []
  [:div
   [submit-problem-form]])
