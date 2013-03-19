(ns the-little-clojurer.core)

(def atom?
  (fn [a]
    (not (seq? a))))

(def null?
  (fn [a]
    (or
      (nil? a)
      (= () a))))

