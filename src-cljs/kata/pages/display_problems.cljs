(ns kata.pages.display-problems
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]
            [kata.components.table :refer [problem-table]]))

(def app-state (r/atom {:sort-val :title :ascending true}))

(def table-contents
  [{:id 1 :title "Maps" :submitted "May 8 2015" :difficulty "Easy" :submitted-by "E66" :times-solved "10" :solved false}
   {:id 2 :title "Vectors" :submitted "Jan 15 2016" :difficulty "Easy" :submitted-by "Jonny3" :times-solved "100" :solved true}
   {:id 3 :title "Make a new function" :submitted "Sep 4 2015" :difficulty "Very Easy" :submitted-by "Bob" :times-solved "18" :solved false}
   {:id 4 :title "Recursion" :submitted "Dec 25 2015" :difficulty "Medium" :submitted-by "G1na" :times-solved "56" :solved false}
   {:id 5 :title "DOM Manipulation" :submitted "Nov 5 2015" :difficulty "Expert" :submitted-by "Patsy" :times-solved "18" :solved true}
   {:id 6 :title "Middleware" :submitted "Feb 23 2016" :difficulty "Hard" :submitted-by "yogsthos" :times-solved "0" :solved false}
   {:id 7 :title "Were Squirrel Log" :submitted "Feb 4 2016" :difficulty "Very Hard" :submitted-by "jonny" :times-solved "999" :solved true}])

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
       [add-tag filters]
       [:div.row>div.col-md-12.problem-table
        [problem-table (apply-filters filters table-contents)]]])))

