(ns the-little-clojurer.core)

(def atom?
  (fn [a]
    (not (seq? a))))

;;; seq? - clojure's "is this a list"

(def sexp?
  (fn [n]
     (or (coll? n)
        (atom? n)
        )))

;;; first - clojure's "car"

;;; rest - clojure's "cdr"
;;; 1. Toys

;;; 3
(atom? 'atom)
(atom? 'turkey)
(atom? 1492)
(atom? 'u)
(atom? 'turkey)
(atom? '*abc$)
(atom? 'turkey)
(atom? '(atom))
(atom? '(atom turkey or))

;;; 4
;;; (coll? '(atom turkey) 'or)
(seq? '((atom turkey) or))
(sexp? 'xyz)
(sexp? '(x y z))
(sexp? '((x y) z))
(seq? '(how are you doing so far))
(count '(how are you doing so far))
(seq? '(((how) are) ((you) (doing so)) far))
(count '(((how) are) ((you) (doing so)) far))

;;; 5
(seq? '())
(atom? '())
(seq? '(() () () ()))
(first '(a b c))
(first '((a b c) x y z))
;; (first 'hotdog)
(first '())

;;; The law of first : defined only for non empty seqs

;;; 6
(first '(((hotdogs)) (and) (pickle) relish))
(first '(((hotdogs)) (and) (pickle) relish))
(first (first '(((hotdogs)) (and) (pickle) relish)))
(rest '(a b c))
(rest '((a b c) x y z))
(rest '(hamburger))
(rest '((x) t r))
;; (rest 'hotdogs)

(def null?
  (fn [a]
    (or
      (nil? a)
      (= () a))))

