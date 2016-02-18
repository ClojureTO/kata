(ns kata.pages.submit-problem
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]))

(defn save-input! [form-data id]
  (fn [event]
    (swap! form-data assoc id (.. event -target -value))))

(defn submit-problem [form-data result]
  (POST "/api/add-example"
        {:params {:problem @form-data}
         :handler #(reset! result %)}))

(defn submit-problem-form []
  (let [form-data (r/atom {})
        result (r/atom {})]
    (fn []
      [:div.row>div.col-md-12
       [:h2 "Submit a problem"]
       [:input.form-control
        {:type :text
         :placeholder "title"
         :value (:title @form-data)
         :on-change (save-input! form-data :title)}]
       [:textarea.form-control
        {:placeholder "description"
         :value (:description @form-data)
         :on-change (save-input! form-data :description)}]
       [:textarea.form-control
        {:placeholder "code"
         :value (:code @form-data)
         :on-change (save-input! form-data :code)}]
       [:button.btn.btn-primary
        {:on-click #(submit-problem form-data result)}
        "submit"]
       (when-let [result @result]
         [:h2 result])])))

(defn submit-problem-page []
  [:div
   [submit-problem-form]])
