(ns the-little-clojurer.core)

;;; The Little 'Clojurer'

;;; Preface

(def atom?
  (fn [a]
    (not (seq? a))))

(def null?
  (fn [a]
    (or
      (nil? a)
      (= () a))))

;;; Chapter 1. Toys

;;; p3
(atom? 'atom)
(atom? 'turkey)
(atom? 1492)
(atom? 'u)
(atom? 'turkey)
(atom? '*abc$)
(atom? 'turkey)
(atom? '(atom))
(atom? '(atom turkey or))


;;; p4

;;; seq? - clojure's "is this a list"

;;; (seq? '(atom turkey) 'or)
(seq? '((atom turkey) or))

(def sexp?
  (fn [n]
     (or (seq? n)
        (atom? n)
        )))

(sexp? 'xyz)
(sexp? '(x y z))
(sexp? '((x y) z))
(seq? '(how are you doing so far))
(count '(how are you doing so far))
(seq? '(((how) are) ((you) (doing so)) far))
(count '(((how) are) ((you) (doing so)) far))

;;; p5
(seq? '())
(atom? '())
(seq? '(() () () ()))

;;; first - clojure's "car"

(first '(a b c))
(first '((a b c) x y z))
;; (first 'hotdog)
(first '())

;;; The law of first : defined only for non empty seqs

;;; p6
(first '(((hotdogs)) (and) (pickle) relish))
(first '(((hotdogs)) (and) (pickle) relish))
(first (first '(((hotdogs)) (and) (pickle) relish)))

;;; rest - clojure's "cdr"

(rest '(a b c))
(rest '((a b c) x y z))
(rest '(hamburger))
(rest '((x) t r))
;; (rest 'hotdogs)

;;; p7
(rest '())

