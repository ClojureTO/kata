(ns kata.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [kata.sandbox :refer [sandboxed-eval]]
            [schema.core :as s]
            [kata.db.core :as db]))

(s/defschema
  Problem
  {(s/optional-key :id) s/Num
   :title String
   :description String
   :code String
   :submitter String
   (s/optional-key :difficulty) s/Num
   (s/optional-key :solved) s/Bool
   (s/optional-key :times_solved) s/Num
   (s/optional-key :submitted) s/Inst})

(defapi service-routes
  ;JSON docs available at the /swagger.json route
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Kata API"
                           :description "Kata Services"}}}}
  (context "/api" []
            ;;TODO should use buffered input stream to prevent running out of memory for huge inputs
            (POST "/add-example" []
                   :return String
                   :body-params [problem :- Problem]
                   (print problem)
                   (db/add-example! problem)
                   (ok "success"))

            (GET "/examples" []
                  :description "list of example problems"
                  :return [Problem]
                  (ok (db/get-examples)))

            (POST "/evaluate" []
              :tags ["eval"]
              :return String
              :body-params [expr :- String]
              (ok (str (pr-str (second (:result (sandboxed-eval expr)))))))))
