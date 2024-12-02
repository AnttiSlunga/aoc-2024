(ns aoc-2024.puzzles.day2
  (:require [aoc-2024.core :as core]))

(defn inc? [arr in?]
  (if (or (= 1 (count arr)) (false? in?))
    in?
    (recur (rest arr) (if (and (< (first arr) (second arr)) (<= (- (second arr) (first arr)) 3)) true false))))

(defn dec? [arr in?]
  (if (or (= 1 (count arr)) (false? in?))
    in?
    (recur (rest arr) (if (and (> (first arr) (second arr)) (<= (- (first arr) (second arr)) 3)) true false))))

(defn safe? [report]
  (let [report (->> (clojure.string/split (first report) #" ")
                    (mapv #(Integer/parseInt %)))
        safe? (or (inc? report nil) (dec? report nil))
        _ (println report " -- >" safe?)]
    safe?))

(defn check [reports safe-levels]
  (if (empty? reports)
    safe-levels
    (do
      (let [report (first reports)]
        (recur (rest reports)  (if (safe? report) (inc safe-levels) safe-levels))))))

(defn part1 []
  (let [reports (->> (core/get-input "day2")
                     (mapv #(clojure.string/split % #"   ")))]
    (check reports 0)))


(defn positions
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))

(defn problem-bampener [results report]
  (if (every? false? results)
    false
    (let [false-indexes (positions false? results)
          dec-bampered (->> (range (count report))
                            (map #(dec? (concat (subvec report 0 %)
                                                     (subvec report (inc %))) nil)))
          inc-bampered (->> (range (count report))
                            (map #(inc? (concat (subvec report 0 %)
                                                (subvec report (inc %))) nil)))]
      (or (some true? dec-bampered) (some true? inc-bampered)))))

(defn p2inc? [arr in?]
  (if (= 1 (count arr))
    in?
    (recur
      (rest arr)
      (if (and (< (first arr) (second arr)) (<= (- (second arr) (first arr)) 3)) (conj in? true) (conj in? false)))))

(defn p2dec? [arr in?]
  (if (= 1 (count arr))
    in?
    (recur (rest arr) (if (and (> (first arr) (second arr)) (<= (- (first arr) (second arr)) 3)) (conj in? true) (conj in? false)))))

(defn p2safe? [report]
  (let [report (->> (clojure.string/split (first report) #" ")
                    (mapv #(Integer/parseInt %)))
        incr? (p2inc? report [])
        decr? (p2dec? report [])
        safe? (if (or (every? true? incr?) (every? true? decr?))
                    true
                    (or (problem-bampener incr? report) (problem-bampener decr? report)))
        _ (println report " -- >" safe?)]
    safe?))

(defn p2check [reports safe-levels]
  (if (empty? reports)
    safe-levels
    (do
      (let [report (first reports)]
        (recur (rest reports)  (if (p2safe? report) (inc safe-levels) safe-levels))))))

(defn part2 []
  (let [reports (->> (core/get-input "day2")
                     (mapv #(clojure.string/split % #"   ")))]
    (p2check reports 0)))