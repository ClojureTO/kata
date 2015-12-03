(defproject kata "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "0.9.5"]
                 [markdown-clj "0.9.82"]
                 [environ "1.0.1"]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "0.3.3"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.webjars/bootstrap "3.3.5"]
                 [org.webjars/jquery "2.1.4"]
                 [com.taoensso/tower "3.0.2"]
                 [com.taoensso/timbre "4.1.4"]
                 [com.fzakaria/slf4j-timbre "0.2.1"]
                 [compojure "1.4.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring "1.4.0" :exclusions [ring/ring-jetty-adapter]]
                 [mount "0.1.4" :exclusions [ch.qos.logback/logback-classic]]
                 [migratus "0.8.7"]
                 [conman "0.2.7"]
                 [com.h2database/h2 "1.4.188"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [reagent "0.5.1"]
                 [reagent-forms "0.5.13"]
                 [reagent-utils "0.1.5"]
                 [secretary "1.2.3"]
                 [org.clojure/core.async "0.2.374"]
                 [cljs-ajax "0.5.1"]
                 [clojail "1.0.6"]
                 [metosin/compojure-api "0.24.0"]
                 [metosin/ring-swagger-ui "2.1.3-2"]
                 [org.immutant/web "2.1.1" :exclusions [ch.qos.logback/logback-classic]]]

  :min-lein-version "2.0.0"
  :uberjar-name "kata.jar"
  ;; needed for evaluating code
  :jvm-opts ["-server" "-Djava.security.policy=sandbox.policy"]
  :resource-paths ["resources" "target/cljsbuild"]

  :main kata.core
  :migratus {:store :database}

  :plugins [[lein-environ "1.0.1"]
            [migratus-lein "0.2.0"]
            [lein-cljsbuild "1.1.1"]]
  :clean-targets ^{:protect false} [:target-path [:cljsbuild :builds :app :compiler :output-dir] [:cljsbuild :builds :app :compiler :output-to]]
  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src-cljs"]
     :compiler
                   {:output-to "target/cljsbuild/public/js/app.js"
                    :output-dir "target/cljsbuild/public/js/out"
                    :externs ["resources/externs.js"
                              "react/externs/react.js"]
                    :pretty-print true}}}}

  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
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
             :source-paths ["env/prod/clj"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "0.8.2"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.0"]
                                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]
                                 [lein-figwheel "0.5.0-2"]
                                 [mvxcvi/puget "1.0.0"]]
                  :plugins [[lein-figwheel "0.5.0-2"]]
                  :cljsbuild
                                {:builds
                                 {:app
                                  {:source-paths ["env/dev/cljs"]
                                   :compiler
                                                 {:main "kata.app" :asset-path "/js/out" :source-map true}}}}

                  :figwheel
                                {:http-server-root "public"
                                 :server-port 3449
                                 :nrepl-port 7002
                                 :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"]
                                 :css-dirs ["resources/public/css"]
                                 :ring-handler kata.handler/app}

                  :source-paths ["env/dev/clj"]
                  :repl-options {:init-ns kata.core}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  ;;when :nrepl-port is set the application starts the nREPL server on load
                  :env {:dev        true
                        :port       3000
                        :nrepl-port 7000
                        :log-level  :trace}}
   :project/test {:env {:test       true
                        :port       3001
                        :nrepl-port 7001
                        :log-level  :trace}}
   :profiles/dev {}
   :profiles/test {}})
