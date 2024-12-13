(defproject aoc-2024 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/math.combinatorics "0.3.0"]]
  :repl-options {:init-ns aoc-2024.core
                 :init (do
                        (require '[aoc-2024.puzzles.day1 :as d1])
                        (require '[aoc-2024.puzzles.day2 :as d2])
                        (require '[aoc-2024.puzzles.day3 :as d3])
                        (require '[aoc-2024.puzzles.day4 :as d4])
                        (require '[aoc-2024.puzzles.day5 :as d5])
                        (require '[aoc-2024.puzzles.day6 :as d6])
                        (require '[aoc-2024.puzzles.day7 :as d7])
                        (require '[aoc-2024.puzzles.day9 :as d9])
                        (require '[aoc-2024.puzzles.day10 :as d10])
                        (require '[aoc-2024.puzzles.day11 :as d11])
                        (require '[aoc-2024.puzzles.day12 :as d12])
                        (in-ns 'aoc-2024.puzzles.day2))})
