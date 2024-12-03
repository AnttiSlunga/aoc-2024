(defproject aoc-2024 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :repl-options {:init-ns aoc-2024.core
                 :init (do
                        (require '[aoc-2024.puzzles.day1 :as d1])
                        (require '[aoc-2024.puzzles.day2 :as d2])
                        (require '[aoc-2024.puzzles.day3 :as d3])
                        (in-ns 'aoc-2024.puzzles.day2))})
