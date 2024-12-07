(ns aoc-2024.puzzles.day6
  (:require [aoc-2024.core :as core]))

(def max-rows 129)                                            ;;129
(def max-columns 129)                                         ;;129

(defn outside? [[row column]]
  (if (or (or (< row 0) (> row max-rows))
          (or (< column 0) (> column max-columns)))
    true
    false))

(defn free-space? [input [row column]]
  (if (not= (core/from input row column) "#")
    true
    false))

(defn new-direction [direction]
  (case direction
    "U" "R"
    "R" "D"
    "D" "L"
    "L" "U"))

(defn move [current-pos direction moves input count]
  (if (or (outside? current-pos))
    moves
    (let [next-move (case direction
                      "U" [(dec (first current-pos)) (last current-pos)]
                      "D" [(inc (first current-pos)) (last current-pos)]
                      "L" [(first current-pos) (dec (last current-pos))]
                      "R" [(first current-pos) (inc (last current-pos))])]
      (recur
        (if (free-space? input next-move) next-move current-pos)
        (if (free-space? input next-move) direction (new-direction direction))
        (if (free-space? input next-move) (conj moves next-move) moves)
        input
        (inc count)))))

(defn part1 []
  (let [input (core/get-table-input "day6")
        start-pos [45 42]]                                  ;;45 42
    (->> (move start-pos "U" [] input 0)
         set
         count)))

(defn add-obstacle [input [row column]]
  (->> input
       (map-indexed
         (fn [idx r]
           (if (= idx row)
             [(str (subs (first r) 0 column)
                   "#"
                   (subs (first r) (inc column) 10))]
             r)))))

(defn check-loops [current-pos direction moves input count loop? obj]
  (if (or loop? (outside? current-pos))
    (flatten count)
    (let [next-move (case direction
                      "U" [(dec (first current-pos)) (last current-pos)]
                      "D" [(inc (first current-pos)) (last current-pos)]
                      "L" [(first current-pos) (dec (last current-pos))]
                      "R" [(first current-pos) (inc (last current-pos))])
          loop? (not (empty? (->> moves
                                  (filter #(= [next-move direction] %)))))]
      (recur
        (if (free-space? input next-move) next-move current-pos)
        (if (free-space? input next-move) direction (new-direction direction))
        (if (free-space? input next-move) (conj moves [next-move direction]) moves)
        input
        (if loop? (conj count obj) count )
        loop?
        obj))))

;; 132 too low
(defn part2 []
  (let [input (core/get-table-input "day6")
        start-pos [45 42]
        path (->> (move start-pos "U" [] input 0)
                  set)]
    (->> path
         (map #(check-loops start-pos "U" [] (add-obstacle input %) [] false %))
         (remove empty?)
         set
         count)))