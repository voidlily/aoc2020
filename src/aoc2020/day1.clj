(ns aoc2020.day1
  (:gen-class))

(defn eq-2020 [nums]
  (= 2020 (reduce + nums)))

(defn find-2020 [nums]
  (first (for [x nums
               y nums
               :when (eq-2020 [x y])]
           [x y])))

(defn find-2020-triplets [nums]
  (first (for [x nums
               y nums
               z nums
               :when (eq-2020 [x y z])]
           [x y z])))

(defn day1 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map #(Integer/parseInt %))
         find-2020
         (reduce *))))

(defn part2 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map #(Integer/parseInt %))
         find-2020-triplets
         (reduce *))))
