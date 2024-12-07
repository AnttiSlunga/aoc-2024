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

;; 5955
(defn part1 []
  (let [rules (->> (core/get-input "day5_rules")
                   (mapv #(let [value (clojure.string/split % #"\|")]
                            [(first value) (second value)])))
        pages (->> (core/get-input "day5_pages")
                   (mapv #(clojure.string/split % #",")))]
    (->> pages
         (map (fn [page]
                (let [correct (check page page rules [] nil)]
                  (if (= (count correct) (count page)) page))))
         (remove nil?)
         (map #(% (quot (count %) 2)))
         (map #(Integer/parseInt %))
         (apply +))))

(defn valid? [page rules]
  (= (count (check page page rules [] nil)) (count page)))

(defn correct [rules page page-togo count]
  (if (valid? page rules)
    page
    (let [rules-for-number (->> (filter #(= (first %) (first page-togo)) rules)
                                (mapv #(last %)))
          num-idx (.indexOf page (first page-togo))
          rules-to-apply (->> rules-for-number
                              (mapv (fn [v] [(.indexOf page v) v]))
                              (remove #(neg? (first %)))
                              (remove #(> (first %) num-idx))
                              (map #(last %)))
          new-page (if (empty? rules-to-apply)
                     page
                     (first (map #(into (into [] (remove #{%} page)) [%]) rules-to-apply)))]
      (recur rules new-page (if (empty? (rest page-togo)) new-page (rest page-togo)) (inc count)))))

;; 4030
(defn part2 []
  (let [rules (->> (core/get-input "day5_rules")
                   (mapv #(let [value (clojure.string/split % #"\|")]
                            [(first value) (second value)])))
        pages (->> (core/get-input "day5_pages")
                   (mapv #(clojure.string/split % #",")))]
    (->> pages
         (map (fn [page]
                (let [correct (check page page rules [] nil)]
                  (if (not= (count correct) (count page)) page))))
         (remove nil?)
         (map #(correct rules % % 0))
         (map #(% (quot (count %) 2)))
         (map #(Integer/parseInt %))
         (apply +))))