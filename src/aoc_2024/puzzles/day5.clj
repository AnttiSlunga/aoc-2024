(ns aoc-2024.puzzles.day5
  (:require [aoc-2024.core :as core]))

(defn check [page original-page rules result valid?]
  (if (or (empty? page) (false? valid?))
    result
    (let [rules-for-number (->> (filter #(= (first %) (first page)) rules)
                                (mapv #(last %)))
          rule-idx (->> rules-for-number
                        (mapv #(.indexOf original-page %))
                        (remove neg?))
          num-idx (.indexOf original-page (first page))
          valid? (every? #(< num-idx %) rule-idx)]
      (recur (rest page) original-page rules (if valid? (conj result original-page) result) valid?))))

(defn part1 []
  (let [rules (->> (core/get-input "day5_rules")
                   (mapv #(let [value (clojure.string/split % #"\|")]
                            [(first value) (second value)])))
        pages (->> (core/get-input "day5_pages")
                   (mapv #(clojure.string/split % #","))
                   ;(mapv (fn [vv] (mapv #(Integer/parseInt %) vv)))
                   )]
    (->> pages
         (map (fn [page]
                (let [correct (check page page rules [] nil)]
                  (if (= (count correct) (count page)) page))))
         (remove nil?)
         (map #(% (quot (count %) 2)))
         (map #(Integer/parseInt %))
         (apply +))))
