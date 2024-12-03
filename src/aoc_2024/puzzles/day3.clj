(ns aoc-2024.puzzles.day3
  (:require [clojure.java.io :as io]))

(defn laske [inputs results]
       (if (empty? inputs)
         results
         (let [val (first inputs)
               numbers (subs val 4 (dec (count val)))
               sum (->> (clojure.string/split numbers #",")
                        (mapv #(Integer/parseInt %))
                        (apply *))]
           (recur (rest inputs) (conj results sum)))))

;;182780583
(defn part1 []
  (let [input (slurp (io/resource "day3"))
        matcher (re-matcher #"mul\(\d{1,3},\d{1,3}\)" input)
        matcher2 (re-matcher #"mul\(\d{1,3},\d{1,3}\)" input)
        foundit (loop [founds []]
                  (if (not (.find matcher2))
                    founds
                    (let [mul (re-find matcher)]
                      (recur (conj founds mul)))))]
    (apply + (laske foundit []))))


(defn clean-donts [text]
  (if (not (.find (re-matcher #"don't\(\)" text)))
    text
    (let [start (clojure.string/index-of text "don't()")
          new-text (subs text start (count text))
          end (clojure.string/index-of new-text "do()")]
      (recur
        (str (subs text 0 start) (subs new-text end (count new-text)))))))

;;90772405
(defn part2 []
  (let [input (slurp (io/resource "day3"))
        cleaned (clean-donts input)
        matcher (re-matcher #"mul\(\d{1,3},\d{1,3}\)" cleaned)
        matcher2 (re-matcher #"mul\(\d{1,3},\d{1,3}\)" cleaned)
        foundit (loop [founds []]
                  (if (not (.find matcher2))
                    founds
                    (let [mul (re-find matcher)]
                      (recur (conj founds mul)))))]
    (apply + (laske foundit []))))