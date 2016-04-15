(ns kata.sandbox
  (:require [clojail.testers :refer [secure-tester-without-def blanket]]
            [clojail.core :refer [sandbox]]
            [clojure.stacktrace :refer [root-cause]])
  (:import java.io.StringWriter
    java.util.concurrent.TimeoutException))
;; stolen from https://github.com/Raynes/tryclojure/blob/master/src/tryclojure/models/eval.clj

(defn eval-form [form sbox]
  (with-open [out (StringWriter.)]
    (let [result (sbox form {#'*out* out})]
      {:expr form
       :result [out result]})))

(defn eval-string [expr sbox]
  (let [form (binding [*read-eval* false] (read-string expr))]
    (eval-form form sbox)))

(def try-clojure-tester
  (conj secure-tester-without-def (blanket "tryclojure" "noir")))

(defn make-sandbox []
  (sandbox try-clojure-tester
           :timeout 2000
           :init '(do (require '[clojure.repl :refer [doc source]])
                      (future (Thread/sleep 600000)
                              (-> *ns* .getName remove-ns)))))

(defn sandboxed-eval [expr]
  (try
    (eval-string expr (make-sandbox))
    (catch TimeoutException _
      {:error true :message "Execution Timed Out!"})
    (catch Exception e
      {:error true :message (str (root-cause e))})))
