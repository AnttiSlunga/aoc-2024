(ns aoc-2024.puzzles.day11)

(def test-stones [[125 1] [17 1]])
(def real-stones [[4329 1] [385 1] [0 1] [1444386 1] [600463 1] [19 1] [1 1] [56615 1]])
(def real-stones-map {4329 1 385 1 0 1 1444386 1 600463 1 19 1 1 1 56615 1})

(defn split-stone [stone]
  (let [midpoint (/ (count (str stone)) 2)]
    [(Integer/parseInt (apply str (first (split-at midpoint (str stone)))))
     (Integer/parseInt (apply str (second (split-at midpoint (str stone)))))]))

(defn handle-stone [stone]
  (cond
    (= 0 stone) 1
    (even? (count (str stone))) (split-stone stone)
    :else (* stone 2024)))

(defn blink [stones new-stones]
  (if (empty? stones)
    new-stones
    (let [stone (first (first stones))
          new-stone (handle-stone stone)]
      (recur
        (rest stones)
        (if (vector? new-stone)
          (conj new-stones [(first new-stone) 1] [(second new-stone) 1])
          (conj new-stones [new-stone 1]))))))

;; 218079
(defn part1 []
  (->> (loop [stones real-stones
              blinks 0]
         (if (= blinks 25)
           stones
           (recur (blink stones []) (inc blinks))))
       count))