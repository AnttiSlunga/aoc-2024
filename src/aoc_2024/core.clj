(ns aoc-2024.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input [day]
  (-> (slurp (io/resource day))
      (str/split #"\n")))
