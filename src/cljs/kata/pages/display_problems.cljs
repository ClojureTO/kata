(ns kata.pages.display-problems
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]
            [kata.components.table :refer [problem-table]]))

(def app-state (r/atom {:sort-val :title :ascending true}))
(def table-contents (r/atom nil))

(defn update-sort-value [new-val]
  (if (= new-val (:sort-val @app-state))
    (swap! app-state update-in [:ascending] not)
    (swap! app-state assoc :ascending true))
  (swap! app-state assoc :sort-val new-val))

(defn sorted-contents [table-contents]
  (let [sorted-contents (sort-by (:sort-val @app-state) table-contents)]
    (if (:ascending @app-state)
      sorted-contents
      (rseq sorted-contents))))

(defn filter-tags
  "filter by stuff"
  [table-contents tag value]
  (filter #(= value (tag %)) table-contents))

(defn apply-filters
  ""
  [filters table-contents]
  (reduce
    (fn [table filter]
      (filter table))
    table-contents @filters)
  ;)
  (reduce
    (fn [table filter]
      (filter table))
    table-contents @filters))

(defn add-tag [filters]
  [:div
   [:label "add-tag"]
   [:button.btn.btn-primary
    {:on-click #(swap! filters conj
                       (fn [table]
                         (filter-tags table :difficulty "Easy")))}
    "Difficulty"]])

(defn display-problem-list []
  (let [filters (r/atom [])
        filtered-table (r/atom [])]
    (r/create-class
      {:component-did-mount
       #(GET "/api/examples"
             {:handler (fn [param] (reset! table-contents param))
              :response-format :json
              :keywords? true})
       :reagent-render (fn []
                         [:div.container
                          [:div.row>div.col-md-12>h1 "Problem List"]
                          #_[add-tag filters]
                          [:div.row>div.col-md-12
                           [problem-table (apply-filters filters @table-contents)]]])})))
