(ns aoc-2024.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input [day]
  (-> (slurp (io/resource day))
      (str/split #"\n")))

(defn get-table-input [day]
  (->> (-> (slurp (io/resource day))
           (clojure.string/split #"\n"))
       (mapv #(clojure.string/split % #"\r"))))

(defn from [input row column]
  (try
    (-> (nth input row)
        first
        (clojure.string/split #"")
        (nth column))
    (catch Exception e
      nil)))
