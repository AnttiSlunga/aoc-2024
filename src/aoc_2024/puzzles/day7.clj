(ns aoc-2024.puzzles.day7
  (:require [aoc-2024.core :as core]
            [clojure.math.combinatorics :as combo]))

(defn match [wanted-result equation operator result]
  (if (empty? operator)
    (if (= wanted-result result) wanted-result)
    (let [op (first operator)
          result (case op
                   "+" (+ result (first equation))
                   "*" (* result (first equation))
                   "||" (Long/parseLong (str result (first equation))))]
      (recur wanted-result (rest equation) (rest operator) result))))


(defn valid? [[result equation]]
  (->> (pmap
         #(match result (rest equation) % (first equation))
         (combo/selections ["+" "*"] (dec (count equation))))
       (remove nil?)
       first))

;; 7885693428401
(defn part1 []
  (let [input (->> (core/get-input "day7")
                   (mapv #(let [value (clojure.string/split % #":")]
                            [(Long/parseLong (first value))
                             (mapv
                               (fn [v] (Long/parseLong v))
                               (-> (second value)
                                       clojure.string/trim
                                       (clojure.string/split #" ")))])))]
    (->> input
         (pmap #(valid? %))
         (remove nil?)
         (apply +))))

(defn valid2? [[result equation]]
  (->> (pmap
         #(match result (rest equation) % (first equation))
         (combo/selections ["+" "*" "||"] (dec (count equation))))
       (remove nil?)
       first))

;;348360680516005
(defn part2 []
  (let [input (->> (core/get-input "day7")
                   (mapv #(let [value (clojure.string/split % #":")]
                            [(Long/parseLong (first value))
                             (mapv
                               (fn [v] (Long/parseLong v))
                               (-> (second value)
                                   clojure.string/trim
                                   (clojure.string/split #" ")))])))]
    (->> input
         (pmap #(valid2? %))
         (remove nil?)
         (apply +))))