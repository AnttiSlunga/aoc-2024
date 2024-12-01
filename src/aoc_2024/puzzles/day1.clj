(ns aoc-2024.puzzles.day1
  (:require [aoc-2024.core :as core]
            [clojure.string :as str]))

(defn to-list [input f s]
  (if (empty? input)
    [f s]
    (do
      (let [[a b] (first input)]
        (recur (rest input) (conj f (Integer/parseInt a)) (conj s (Integer/parseInt b)))))))

(defn countti [input result]
  (if (empty? (first input))
    result
    (do
      (let [left (first input)
            right (second input)]
        (recur [(rest left) (rest right)] (conj result (abs (- (first left) (first right)))))))))

(defn contains [input number result]
  (if (empty? input)
    (* result number)
    (do
      (let [value (first input)]
        (recur (rest input) number (if (= value number) (inc result) result))))))

(defn part1 []
  (println "Resolving puzzle 1 part 1")
  (let [locations (->> (core/get-input "day1")
                       (mapv #(clojure.string/split % #"   ")))
        [a b] (to-list locations [] [])]
    (apply + (countti [(sort a) (sort b)] []))))


(defn part2 []
  (let [locations (->> (core/get-input "day1")
                       (mapv #(clojure.string/split % #"   ")))
        [a b] (to-list locations [] [])]
    (apply + (map #(contains b % 0) a))))
