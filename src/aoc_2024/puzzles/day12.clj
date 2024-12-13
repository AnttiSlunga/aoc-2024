(ns aoc-2024.puzzles.day12
  (:require [aoc-2024.core :as core]))

(defn calc-perimeter [connected]
  (reduce (fn [perimeter coord]
            (+ perimeter
               (count (filter #(not (contains? connected %)) (core/urdl coord)))))
          0
          connected))

(defn find-distinct-plots [all-plant-locations]
  (let [plant-locations-set (set all-plant-locations)]
    (loop [remaining plant-locations-set
           groups []]
      (if (empty? remaining)
        groups
        (let [current (first remaining)
              connected (loop [visited #{current}
                               queue (conj clojure.lang.PersistentQueue/EMPTY current)]
                          (if (empty? queue)
                            visited
                            (let [coord (peek queue)
                                  neighbors (filter plant-locations-set (core/urdl coord))
                                  new-neighbors (remove visited neighbors)]
                              (recur (into visited new-neighbors)
                                     (into (pop queue) new-neighbors)))))
              perimeter (calc-perimeter connected)]
          (recur (reduce disj remaining connected)
                 (conj groups [(count connected) perimeter])))))))

(defn find-plots [input plots plants]
  (if (empty? plants)
    plots
    (let [plant (first plants)
          all-plant-locations (core/search input plant)
          distinct-plots (find-distinct-plots all-plant-locations)]
      (recur input (conj plots distinct-plots) (rest plants)))))

;; 1449902
(defn part1 []
  (let [input (core/get-map-input "day12")
        plants (->> input flatten set)]
    (->> plants
         (find-plots input [])
         flatten
         (partition 2)
         (map #(apply * %))
         (apply +))))
