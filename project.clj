(defproject kata "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [selmer "1.0.4"]
                 [markdown-clj "0.9.87"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "1.0.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.2"]
                 [org.webjars/font-awesome "4.5.0"]
                 [org.webjars.bower/tether "1.1.1"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.0"]
                 [mount "0.1.10"]
                 [cprop "0.1.7"]
                 [org.clojure/tools.cli "0.3.3"]
                 [luminus-nrepl "0.1.4"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.1.9"]
                 [luminus-migrations "0.1.0"]
                 [conman "0.4.9"]
                 [com.h2database/h2 "1.4.191"]
                 [org.clojure/clojurescript "1.8.40" :scope "provided"]
                 [reagent "0.5.1"]
                 [reagent-forms "0.5.22"]
                 [reagent-utils "0.1.7"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.5.4"]
                 [metosin/compojure-api "1.0.2"]
                 [luminus-log4j "0.1.3"]
                 [clojail "1.0.6"]
                 [clj-oauth "1.5.4"]
                 ;[re-com "0.8.0"]
                 ]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env" "-Djava.security.policy=sandbox.policy"]
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]

  :main kata.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.1"]
            [migratus-lein "0.2.6"]
            [lein-cljsbuild "1.1.1"]]
  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src/cljc" "src/cljs"]
     :compiler
     {:output-to "target/cljsbuild/public/js/app.js"
      :output-dir "target/cljsbuild/public/js/out"
      :externs ["resources/externs.js"
                "react/externs/react.js"]
      :pretty-print true}}}}

  :target-path "target/%s/"
  :profiles
  {:uberjar {:omit-source true

              :prep-tasks ["compile" ["cljsbuild" "once"]]
              :cljsbuild
              {:builds
               {:app
                {:source-paths ["env/prod/cljs"]
                 :compiler
                 {:optimizations :advanced
                  :pretty-print false
                  :closure-warnings
                  {:externs-validation :off :non-standard-jsdoc :off}}}}}

             :aot :all
             :uberjar-name "kata.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "1.1.1"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.8.0"]
                                 [lein-figwheel "0.5.2"]
                                 [lein-doo "0.1.6"]
                                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.14.0"]
                                 [lein-figwheel "0.5.2"]
                                 [lein-doo "0.1.6"]
                                 [org.clojure/clojurescript "1.8.40"]]

                   :cljsbuild
                   {:builds
                    {:app
                     {:source-paths ["env/dev/cljs"]
                      :compiler
                      {:main "kata.app"
                       :asset-path "/js/out"
                       :optimizations :none
                       :source-map true}}
                     :test
                     {:source-paths ["src/cljc" "src/cljs" "test/cljs"]
                      :compiler
                      {:output-to "target/test.js"
                       :main "kata.doo-runner"
                       :optimizations :whitespace
                       :pretty-print true}}}}

                  :figwheel
                  {:http-server-root "public"
                   :nrepl-port 7002
                   :css-dirs ["resources/public/css"]}
                  :doo {:build "test"}
                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user
                                 :nrepl-middleware
                                 [cemerick.piggieback/wrap-cljs-repl]}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev {}
   :profiles/test {}})
