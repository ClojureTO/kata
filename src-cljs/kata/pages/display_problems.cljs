(ns kata.pages.display-problems
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]
            [kata.components.table :refer [problem-table]]))

(def app-state (r/atom {:sort-val :title :ascending true}))
(def table-contents (r/atom  (GET "/api/examples")))

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
  (println "applying filters..."
           (reduce
             (fn [table filter]
               (filter table))
             table-contents @filters))
  (reduce
    (fn [table filter]
      (filter table))
    table-contents @filters))

#_(defn table [table-contents]
    [:table.table-striped.table
     [:thead
      [:tr
       [:th {:on-click #(update-sort-value :title)} "Title"]
       [:th {:on-click #(update-sort-value :submitted)} "Submitted"]
       [:th {:on-click #(update-sort-value :difficulty)} "Difficulty"]
       [:th {:on-click #(update-sort-value :submitted-by)} "Submitted By"]
       [:th {:on-click #(update-sort-value :times-solved)} "Times Solved"]
       [:th {:on-click #(update-sort-value :solved)} "Solved"]]]
     [:tbody
      (for [title (sorted-contents table-contents)]
        ^{:key (:id title)}
        [:tr
         [:td (:title title)]
         [:td (:submitted title)]
         [:td (:difficulty title)]
         [:td (:submitted-by title)]
         [:td (:times-solved title)]
         [:td (:solved title)]])]])

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
    (fn []
      [:div.container
       [:div.row>div.col-md-12>h1 "Problem List"]
       #_[add-tag filters]
       [:div.row>div.col-md-12.problem-table
        [problem-table (apply-filters filters table-contents)]]])))
