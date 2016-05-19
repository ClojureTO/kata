(ns kata.pages.submit-problem
  (:require [ajax.core :refer [GET POST]]
            [reagent.core :as reagent]
            [kata.components.editor :refer [editor submit-code]]
            [kata.components.result :refer [result-view]]))

(defn save-input! [form-data id]
  (fn [event]
    (swap! form-data assoc id (.. event -target -value))))

(defn fields-present? [form-data fields]
  (not-any? empty? (map @form-data fields)))

(defn submit-problem [cm form-data result]
  (if-not (fields-present? form-data [:title :submitter :description :code])
    (js/alert "Required field(s) are missing.")
    (POST "/api/add-example"
      {:params {:problem @form-data}
       :handler #((reset! result %)
                  (reset! form-data nil)
                  (.setValue cm "")
                  (.clearHistory cm)
                  (.refresh cm)
                  )})))

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
        eval-result (reagent/atom {})
        error (reagent/atom nil)
        cm (atom nil)]
    (fn []
      [:div.row>div.col-md-12
       [:h2 "Submit a problem"]
       (text-field form-data :title)
       (text-field form-data :submitter "your name")
       (textarea form-data :description)
       [:div.form-group
        [:div.row>div.col-md-12
         [editor cm form-data #(swap! form-data assoc :code (.getValue %))]]]
       [:div.row
        [:div.form-group
         [:div.col-md-1>label "Result"]
         [:div.col-md-11
          [result-view eval-result]]]]
       [:div.form-group
        [:button.btn.btn-primary
         {:on-click #(submit-code (:code @form-data) error eval-result)}
         "evaluate"]
        [:button.btn.btn-primary
         {:on-click #(submit-problem @cm form-data result)}
         "submit"]]
       (when-let [result @result]
         [:h2 result])])))

(defn submit-problem-page []
  [:div
   [submit-problem-form]])
