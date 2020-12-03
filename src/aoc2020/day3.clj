(ns aoc2020.day3
  (:gen-class)
  (:require [clojure.string :as str]))

(defn get-rem [x idx]
  (let [x-len (count x)
        actual-idx (rem idx x-len)]
    (get x actual-idx)))

(defn is-tree [row idx]
  (let [cell (get-rem row idx)]
    (= cell \#)))

(defn find-tree [across idx row]
  (let [row-idx (* idx across)]
    (is-tree row row-idx)))

;; for each row i, check for a tree in 3*i
(defn num-trees [across down rows]
  (->> rows
       (take-nth down)
       (map-indexed #(find-tree across %1 %2))
       (filter identity)
       count))

(defn part1 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (num-trees 3 1))))

(defn part2 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (let [rows (->> reader
                   line-seq
                   )]
      (*
       (num-trees 1 1 rows)
       (num-trees 3 1 rows)
       (num-trees 5 1 rows)
       (num-trees 7 1 rows)
       (num-trees 1 2 rows)))))
