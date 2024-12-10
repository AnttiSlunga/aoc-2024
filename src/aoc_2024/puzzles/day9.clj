(ns aoc-2024.puzzles.day9
  (:require [aoc-2024.core :as core]
            [clojure.string :as str]
            [clojure.string :as string]))

(def testi "2333133121414131402")
(def simple-test "12345")

(def types {:file :free :free :file})

(defn disk-map [id input map type]
  (if (empty? input)
    (flatten map)
    (let [value (case type
                  :file (repeat (Integer/parseInt (str (first input))) (str id))
                  :free (repeat (Integer/parseInt (str (first input))) "."))]
      (recur
        (if (= type :free) (inc id) id)
        (rest input)
        (conj map value)
        (type types)))))

;; 943696266 too low ;; 5000 kpl 58704.478042 msecs
;; 90365034097 too low, 1007992.006709 msecs
(defn way-too-slow [files round]
  (if (not (.find (re-matcher #"\." files)))
    files
    (let [backward-files (->> files reverse (apply str))
          last-digit (str (first (str/replace backward-files #"\." "")))
          ld-pos (.indexOf backward-files last-digit)
          files (apply str (drop-last (inc ld-pos) files))
          files (string/replace-first (str files) #"\." last-digit)]
      (recur files (inc round)))))

(defn move-files [files front tail result]
  (if (< tail front)
    result
    (let [add (if (= "." (nth files front))
                (nth files tail)
                (nth files front))]
      (if (= "." (nth files tail))
        (recur files front (dec tail) result)
        (recur files (inc front) (if (= "." (nth files front)) (dec tail) tail) (conj result add))))))

;; 6384282079460
(defn part1 []
  (let [ip (first (core/get-input "day9"))
        dmap (disk-map 0 ip [] :file)]
    (->> (move-files dmap 0 (dec (count dmap)) [])
         (map-indexed
             (fn [idx value]
               (* idx (Long/parseLong (str value)))))
         (apply +))))
