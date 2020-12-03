(ns aoc2020.day2
  (:gen-class)
  (:require [clojure.string :as str]))

(defn xor [a b]
  ;; did you know logical (a xor b) is the same as !=? don't believe me? do the
  ;; truth table
  (not= a b))

(def PASSWORD_POLICY_REGEX #"^(\d+)-(\d+) (.): (.*)$")

(defn password-policy [line]
  (let [[min-chars max-chars matching-char password] (->> line
                                                          (re-matcher PASSWORD_POLICY_REGEX)
                                                          re-find
                                                          rest)]
    {:min-chars (Integer/parseInt min-chars)
     :max-chars (Integer/parseInt max-chars)
     :matching-char (->> matching-char .getBytes first char)
     :password password}))

(defn valid? [{:keys [min-chars max-chars matching-char password]}]
  (let [occurrences (-> password
                        frequencies
                        (get matching-char 0))]
    (and
     (>= occurrences min-chars)
     (<= occurrences max-chars))))

(defn valid2? [{:keys [min-chars max-chars matching-char password]}]
  (let [first-idx (dec min-chars)
        second-idx (dec max-chars)
        ]
    (xor
     (= first-idx (str/index-of password matching-char first-idx))
     (= second-idx (str/index-of password matching-char second-idx))
     )))


(defn part1 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map password-policy)
         (filter valid?)
         count
         )))

(defn part2 [path]
  (with-open [reader (clojure.java.io/reader path)]
    (->> reader
         line-seq
         (map password-policy)
         (filter valid2?)
         count
         )))
