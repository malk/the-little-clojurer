(ns the-little-clojurer.core)

;;; The Little 'Clojurer'

;;; Preface

(def atom?
  (fn [a]
    (not (seq? a))))

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

;;; The law of rest - defined only for non empty list, the cdr of a non emty
;;; list is a list

(first (rest '((b) (x y) ((c)))))
(rest (rest '((b) (x y) ((c)))))
;; (rest (first '(a (b (c)) d)))
(cons 'peanut '(butter and jelly))

;;; p8
(cons '(banana and) '(peanut butter and jelly))
(cons '((help) this) '(is very ((hard) to learn)))
(cons '(a b (c)) '())
(cons 'a '())
;; (cons '((a b c)) 'b)
;; (cons 'a 'b)

;;; p9

;;; the law of cons - two arguments, the second must be a list, the result
;;; is a list

(cons 'a (first '((b) c d)))
(cons 'a (rest '((b) c d)))

(def null?
  (fn [a]
    (or
      (nil? a)
      (= () a))))

(null? '())
(null? '(a b c))

;;; p10
(null? 'spaguetti)

;;; law of null? - defined only for lists

(atom? 'Harry)
(atom? '(Harry had a heap of apples))

;;; p11

(atom? (first '(Harry had a heap of apples)))
(atom? (rest '(Harry had a heap of apples)))
(atom? (rest '(Harry)))
(atom? (first (rest '(swing low sweet cherry oat))))
(atom? (first (rest '(swing (low sweet) cherry oat))))
(= 'Harry 'Harry)
(= 'Harry 'Harry)
(= 'Margarine 'Butter)

;;; p12

(= '() '(strawberry))
(= 6 7)

;;; the law of = : it takes two arguments, each a non numeric atom

(= (first '(Mary had a little lamb chop)) 'Mary)
(= (rest '(soured milk)) 'milk)

;;; p13

(= (first '(beans beans we need jelly beans)) (first (rest '(beans beans we need jelly beans))))

;;; Chapter 2: Do it, Do it Again, and Again, and Again...

;;; p16
(def lat? (fn [l]
          (cond
           (null? l) true
           (atom? (first l)) (lat? (rest l))
           :else false
           )))

(lat? '(bacon and eggs))

;;; p15 (yes I've put p16 first, so we can have the definition before the usage when evaluating this file, will do so onwards
(lat? '(Jack Sprat could eat no chicken fat))
(lat? '((Jack) Sprat could eat no chicken fat))
(lat? '(Jack (Sprat could) eat no chicken fat))
(lat? '())

;;; p19
(lat? '(bacon (and eggs)))

;;; p21
(or (null? '()) (atom? '(d e f g)))
(or (null? '(a b c)) (null? '()))

;;; p22
(def member? (fn [a lat]
             (cond
              (null? lat) false
              :else (or (= a (first lat))
                       (member? a (rest lat)))
              )))

(member? 'tea '(coffee tea or milk))
(member? 'poached '(fried eggs and scrambled eggs))
(member? 'meat '(mashed potatoes and meat gravy))
(member? 'liver '(bagel and lox))


;;; p23

;;; Commandement 1: (preliminary) always ask null? as the first question of
;;; any function

;;; Chapter 3. Cons the magnificent

;;; p34
(def rember
  (fn [a lat]
    (cond
     (null? lat) '()
     :else (cond
            (= a (first lat)) (rest lat)
            :else (rember a (rest lat))
            )
     )))


;;; p33
(rember 'mint '(lamb chops and mint jelly))
(rember 'mint '(lamb chops and mint flavored mint jelly))
(rember 'toast '(bacon letuce and tomato))
(rember 'cup '(coffee cup tea cup and hick cup))

;;; p37

;;; the second commandment : use cons to build lists
(def rember
  (fn [a lat]
    (cond
     (null? lat) '()
     :else (cond
            (= a (first lat)) (rest lat)
            :else (cons (first lat) (rember a (rest lat)))
            )
     )))

(rember 'and '(bacon letuce and tomato))

;;; p41
(def rember
  (fn [a lat]
    (cond
     (null? lat) '()
     (= a (first lat)) (rest lat)
     :else (cons (first lat) (rember a (rest lat)))
     )))

;;; p42
(rember 'sauce '(soy sauce and tomato sauce))

;;; p44

(def firsts
  (fn [l]
    (cond
     (null? l) '()
     :else (cons (first (first l)) (firsts (rest l)))
     )))

;;; p43
(firsts '((apple peach pumpkin)
          (plum pear cherry)
          (grape raisin pea)
          (bean carrot eggplant)
          ))

(firsts '((a b) (c d) (e f)))

(firsts '())

(firsts '(
          (five plums)
          (four)
          (eleven green oranges)
          ))

(firsts '(
          ((five plums) four)
          (eleven green oranges)
          ((no) more)
          ))


;; Copyright 2013 Romeu “Malk’Zameth” MOURA
;; This file is part of the-little-clojurer. the-little-clojurer is free
;; software: you can redistribute it and/or modify it under the terms of the
;; GNU General Public License as published by the Free Software Foundation,
;; either version 3 of the License, or (at your option) any later version.
;; the-little-clojurer is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
;; Public License for more details. You should have received a copy of the
;; GNU General Public License along with the-little-clojurer. If not, see
;; <http://www.gnu.org/licenses/>.
