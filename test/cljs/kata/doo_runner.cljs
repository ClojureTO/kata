(ns kata.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [kata.core-test]))

(doo-tests 'kata.core-test)

