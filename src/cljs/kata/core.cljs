(ns kata.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [kata.pages.problem-editor :as editor]
            [kata.pages.submit-problem :as submit-problem]
            [kata.pages.display-problems :as display-problems]
            [kata.pages.about :as about])
  (:import goog.History))

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href uri
     :on-click #(reset! collapsed? true)} title]])

(defn navbar []
  (let [collapsed? (atom true)]
    (fn []
      [:nav.navbar.navbar-light.bg-faded
       [:button.navbar-toggler.hidden-sm-up
        {:on-click #(swap! collapsed? not)} "☰"]
       [:div.collapse.navbar-toggleable-xs
        (when-not @collapsed? {:class "in"})
        [:a.navbar-brand {:href "#/"} "kata"]
        [:ul.nav.navbar-nav
         [nav-link "#/" "Home" :home collapsed?]
         [nav-link "#/add-problem" "Add Problem" :add-problem collapsed?]
         [nav-link "#/about" "About" :about collapsed?]]]])))

(defn home-page []
  [:div.container
   [:div.row
    [editor/editor-page]
    [display-problems/display-problem-list]]])

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [about/about-page]]]])

(defn add-problem []
  [:div.container
   [:div.row
    [:div.col-md-12
     [submit-problem/submit-problem-page]]]])

(def pages
  {:home #'home-page
   :about-page #'about-page
   :add-problem #'add-problem
   })

(defn page []
  [(pages (session/get :page))])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :page :home))

(secretary/defroute "/problem/:id" [id]
                    (session/put! :page :home))

(secretary/defroute "/add-problem" []
  (session/put! :page :add-problem))

(secretary/defroute "/about" []
  (session/put! :page :about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          EventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET (str js/context "/docs") {:handler #(session/put! :docs %)}))

(defn mount-components []
  (reagent/render [#'navbar] (.getElementById js/document "navbar"))
  (reagent/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
