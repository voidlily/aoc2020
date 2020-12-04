(ns aoc2020.day4
  (:gen-class)
  (:require [clojure.string :as str]))

(defn split-by-blank-lines [x]
  (let [splitted-str (str/split x #"\n\n+")]
    (map #(str/replace % #"\n" " ") splitted-str)))

(defn parse-line [line]
  (let [kv-pattern #"(?<key>.*?):(?<value>.*?)(?: |$)"]
    (->> line
         (re-seq kv-pattern)
         (map rest)
         (map (fn [[a b]] [(keyword a) b]))
         (into (sorted-map)))))

(defn valid-line? [parsed-line]
  (and
   (contains? parsed-line :byr)
   (contains? parsed-line :iyr)
   (contains? parsed-line :eyr)
   (contains? parsed-line :hgt)
   (contains? parsed-line :hcl)
   (contains? parsed-line :ecl)
   (contains? parsed-line :pid)))

(def CM_REGEX #"(?<hgt>\d+)+cm")
(def IN_REGEX #"(?<hgt>\d+)+in")

(defn valid-height? [height-str]
  (cond
    (re-seq CM_REGEX height-str) (let [height (->> (re-seq CM_REGEX height-str)
                                                   first
                                                   second
                                                   Integer/parseInt)]
                                   (<= 150 height 193))
    (re-seq IN_REGEX height-str) (let [height (->> (re-seq IN_REGEX height-str)
                                                   first
                                                   second
                                                   Integer/parseInt)]
                                   (<= 59 height 76))
    :else false))

(defn valid-hcl? [hcl-str]
  (re-matches #"#[0-9a-f]{6}" hcl-str))

(defn valid-ecl? [ecl-str]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl-str))

(defn valid-pid? [pid-str]
  (re-matches #"\d{9}" pid-str))

(defn fully-valid-line? [parsed-line]
  (and
   (valid-line? parsed-line)
   (let [parsed-byr (Integer/parseInt (:byr parsed-line))]
     (<= 1920 parsed-byr 2002))
   (let [parsed-iyr (Integer/parseInt (:iyr parsed-line))]
     (<= 2010 parsed-iyr 2020))
   (let [parsed-eyr (Integer/parseInt (:eyr parsed-line))]
     (<= 2020 parsed-eyr 2030))
   (valid-height? (:hgt parsed-line))
   (valid-hcl? (:hcl parsed-line))
   (valid-ecl? (:ecl parsed-line))
   (valid-pid? (:pid parsed-line))))

(defn part1 [path]
  (->> path
       slurp
       split-by-blank-lines
       (map parse-line)
       (filter valid-line?)
       count))

(defn part2 [path]
  (->> path
       slurp
       split-by-blank-lines
       (map parse-line)
       (filter fully-valid-line?)
       count))
