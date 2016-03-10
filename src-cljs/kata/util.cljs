(ns kata.util
  (:require [clojure.string :as s]))

(defn lower-case [string]
  (when string
    (s/lower-case string)))

(defn str-contains? [string substr]
  (when (and string substr)
    (> (.indexOf (lower-case string) (lower-case substr)) -1)))
