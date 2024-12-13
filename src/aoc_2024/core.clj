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

(defn get-map-input [day]
  (->> (-> (slurp (io/resource day))
           (clojure.string/split #"\n"))
       (mapv
         #(->> (clojure.string/split % #"\r")
               (mapv (fn [r] (map (fn [v] v) (clojure.string/split r #""))))
               flatten))))

(defn get-map-like-input [day]
  (->> (-> (slurp (io/resource day))
           (clojure.string/split #"\n"))
       (mapv
         #(->> (clojure.string/split % #"\r")
              (mapv (fn [r] (map (fn [v] (Integer/parseInt v)) (clojure.string/split r #""))))
              flatten))))

(defn from [input row column]
  (try
    (-> (nth input row)
        first
        (clojure.string/split #"")
        (nth column))
    (catch Exception e
      nil)))

(defn find-all [max-columns max-row row column input target coords]
  (if (and (= row max-row) (= column max-columns))
    (if (= target (reduce nth input [row column])) (conj coords [row column]) coords)
    (if (= column max-columns)
      (recur max-columns max-row (inc row) 0 input target (if (= target (reduce nth input [row column])) (conj coords [row column]) coords))
      (recur max-columns max-row row (inc column) input target (if (= target (reduce nth input [row column])) (conj coords [row column]) coords)))))

(defn search [input target]
  (find-all (dec (count (first input))) (dec (count input)) 0 0 input target []))

(defn urdl [[row column]]
  [[(dec row) column]
   [row (inc column)]
   [(inc row) column]
   [row (dec column)]])

(defn urdl-values-w-coords [[_ row column] input]
  (let [max-row (count input)
        max-column (count (first input))]
    (->> (urdl [row column])
         (mapv (fn [[r c]] (if (and (>= r 0) (>= c 0) (< r max-row) (< c max-column)) [(reduce nth input [r c]) r c])))
         (remove nil?))))
