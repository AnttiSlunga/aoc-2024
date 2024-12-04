(ns aoc-2024.puzzles.day4
  (:require [aoc-2024.core :as core]))

(defn from [input row column]
  (try
    (-> (nth input row)
        first
        (clojure.string/split #"")
        (nth column))
    (catch Exception e
      nil)))

(defn adjacents [[start end] row-id]
  (let [start (max 0 (dec start))
        end (min 140 (inc end))]
    (concat
      (if (>= row-id 1)
        (mapv
          (fn [v] [(dec row-id) v])
          (range start end)))
      (if (>= start 1)
        [[row-id start]])
      [[row-id (dec end)]]
      (mapv
        (fn [v] [(inc row-id) v])
        (range start end)))))

(defn xm [input x position]
  (if (= "M" (from input (first position) (second position))) [x position]))

(defn as [input [ul ur dl dr]]
  (let [line1 (str (from input (first ul) (second ul)) "A" (from input (first dr) (second dr)))
        line2 (str (from input (first ur) (second ur)) "A" (from input (first dl) (second dl)))]
    (if (and (or (= line1 "MAS") (= line1 "SAM")) (or (= line2 "MAS") (= line2 "SAM"))) 1)))

(defn a [[[x-row x-col] [m-row m-col]] input]
  (let [[a-pos s-pos] (cond
                    (and (= x-row m-row) (< x-col m-col)) [[x-row (inc m-col)] [x-row (inc (inc m-col))]]
                    (and (= x-row m-row) (> x-col m-col)) [[x-row (dec m-col)] [x-row (dec (dec m-col))]]
                    (and (< x-row m-row) (= x-col m-col)) [[(inc m-row) x-col] [(inc (inc m-row)) x-col]]
                    (and (> x-row m-row) (= x-col m-col)) [[(dec m-row) x-col] [(dec (dec m-row)) x-col]]
                    (and (= (inc x-row) m-row) (= (inc x-col) m-col)) [[(inc m-row) (inc m-col)] [(inc (inc m-row)) (inc (inc m-col))]]
                    (and (= (inc x-row) m-row) (= (dec x-col) m-col)) [[(inc m-row) (dec m-col)] [(inc (inc m-row)) (dec (dec m-col))]]
                    (and (= (dec x-row) m-row) (= (inc x-col) m-col)) [[(dec m-row) (inc m-col)] [(dec (dec m-row)) (inc (inc m-col))]]
                    (and (= (dec x-row) m-row) (= (dec x-col) m-col)) [[(dec m-row) (dec m-col)] [(dec (dec m-row)) (dec (dec m-col))]])]
    (if (and (= "A" (from input (first a-pos) (second a-pos))) (= "S" (from input (first s-pos) (second s-pos)))) 1)))

(defn part1 []
  (let [input (core/get-table-input "day4")]
    (->> input
         (map-indexed
           (fn [row-id row]
             (let [matcher (re-matcher #"X" (first row))
                   smatcher (re-matcher #"X" (first row))]
               (loop [parts []]
                 (if (not (.find smatcher))
                   parts
                   (let [match (re-find matcher)
                         start (.start matcher)
                         end (.end matcher)
                         xm (->>
                              (mapv
                                #(xm input [row-id start] %)
                                (adjacents [start end] row-id))
                              (remove nil?))
                         xmas (->> xm
                                   (mapv #(a % input))
                                   (remove nil?))]
                     (recur (conj parts xmas))))))))
         flatten
         (apply +))))

(defn part2 []
  (let [input (core/get-table-input "day4")]
    (->> input
         (map-indexed
           (fn [row-id row]
             (let [matcher (re-matcher #"A" (first row))
                   smatcher (re-matcher #"A" (first row))]
               (loop [parts []]
                 (if (not (.find smatcher))
                   parts
                   (let [match (re-find matcher)
                         start (.start matcher)
                         end (.end matcher)
                         add (->> (adjacents [start end] row-id)
                                  (remove (fn [[r c]] (or (= r row-id) (= c start)))))
                         as (as input add)]
                     (recur (conj parts as))))))))
         flatten
         (remove nil?)
         (apply +))))
