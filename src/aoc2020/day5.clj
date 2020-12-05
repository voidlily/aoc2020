(ns aoc2020.day5
  (:gen-class)
  (:require [clojure.string :as str]))

(defn row [boarding-pass]
  (let [row-part (subs boarding-pass 0 7)
        row-binary (-> row-part
                       (str/replace \B \1)
                       (str/replace \F \0))]
    (Integer/parseInt row-binary 2)))

(defn column [boarding-pass]
  (let [column-part (subs boarding-pass 7)
        column-binary (-> column-part
                          (str/replace \R \1)
                          (str/replace \L \0))]
    (Integer/parseInt column-binary 2)))

(defn seat-id [boarding-pass]
  (let [row-id (row boarding-pass)
        col-id (column boarding-pass)]
    (+
     (* row-id 8)
     col-id)))

(defn part1 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map seat-id)
         (apply max))))

(defn surrounding-seats? [[a b]]
  (let [seat-distance (Math/abs (- a b))]
    (= seat-distance 2)))

(defn find-my-seat [sorted-seat-ids]
  (let [seat-pairs (partition 2 1 sorted-seat-ids)
        surrounding-seats (filter surrounding-seats? seat-pairs)]
    (->> surrounding-seats
         ;; should only be one match
         first
         ;; match is an ordered pair such as (40 42), first->inc will get the
         ;; middle one
         first
         inc)))

(defn part2 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map seat-id)
         sort
         find-my-seat)))
