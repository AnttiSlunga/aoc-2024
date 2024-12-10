(ns aoc-2024.puzzles.day10
  (:require [aoc-2024.core :as core]))

(defn find-trail [input height paths]
  (if (= 9 height)
    (count (last paths))
    (let [nearbys (->> (last paths)
                       (mapv #(core/urdl-values-w-coords % input))
                       (map (fn [v] (filter #(= (inc height) (first %)) v)))
                       flatten
                       (partition 3)
                       ;set ;; remove for part2
                       )]
      (recur input (inc height) (conj paths nearbys)))))

(defn part1 []
  (let [input (core/get-map-like-input "day10")
        trail-heads (core/search input 0)]
    (->> trail-heads
         (map
           #(find-trail input 0 [[[0 (first %) (last %)]]]))
         (apply +))
    ))
