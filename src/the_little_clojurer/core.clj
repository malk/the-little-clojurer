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

;;; p45

;;; Third commandment : when building a list, describe the first typical
;;; element and then cons it into the natural recursion

;;; p46
(firsts '((a b) (c d) (e f)))

;;; p48
(def insertR
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons old (cons new (rest lat)))
     :else (cons (first lat) (insertR new old (rest lat)))
     )))
(insertR 'e 'd '(a b c d f g h))

;;; p47
(insertR 'topping 'fudge '(ice cream with fudge for dessert))
(insertR 'japaleno 'and '(tacos tamales and salsa))

;;; p51
(def insertL
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons new lat)
     :else (cons (first lat) (insertL new old (rest lat)))
     )))

(def subst
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons new (rest lat))
     :else (cons (first lat) (subst new old (rest lat)))
     )))

;;; p52
(def subst2
  (fn [new o1 o2 lat]
    (cond
     (null? lat) '()
     (or (= o1 (first lat)) (= o2 (first lat))) (cons new (rest lat))
     :else (cons (first lat) (subst2 new o1 o2 (rest lat)))
     )))

(def multirember
  (fn [a lat]
    (cond
     (null? lat) '()
     (= a (first lat)) (multirember a (rest lat))
     :else (cons (first lat) (multirember a (rest lat)))
     )))

;;; p56
(def multiinsertR
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons old (cons new (multiinsertR new old (rest lat))))
     :else (cons (first lat) (multiinsertR new old (rest lat)))
     )))

;;; p57
(def multiinsertL
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons new (cons old (multiinsertL new old (rest lat))))
     :else (cons (first lat) (multiinsertL new old (rest lat)))
     )))

(def multisubst
  (fn [new old lat]
    (cond
     (null? lat) '()
     (= old (first lat)) (cons new (multisubst new old (rest lat)))
     :else (cons (first lat) (multisubst new old (rest lat)))
     )))

;;; Chapter 4. Number Games

(atom? 14)
(atom? 14)
(number? -3)
(number? 3.14159)
(def add1
  (fn [n]
    (+ n 1)))
(add1 67)
(def sub1
  (fn [n]
    (- n 1)))
(sub1 5)
(sub1 0)
(zero? 0)
(zero? 1492)
(def o+
  (fn [n m]
    (cond
     (zero? n) m
     :else (o+ (sub1 n) (add1 m))
     )))
(o+ 46 12)
;;; p61
(def o-
  (fn [n m]
    (cond
     (zero? m) n
     :else (o- (sub1 n) (sub1 m))
     )))
(o- 14 3)
(o- 17 9)
(o- 18 25)

(def tup?
  (fn [t]
    (cond
     (null? t) true
     (number? (first t)) (tup? (rest t))
     :else false
     )))

(tup? '(2 11 3 79 47 6))
(tup? '(8 55 5 555))
(tup? '(1 2 8 apple 4 3))
(tup? '(3 (7 4) 13 9))
;;; p62
(tup? '())


(def addtup
  (fn [t]
    (cond
     (null? t) 0
     :else (o+ (first t) (addtup (rest t)))
     )))

(addtup '(3 5 2 8))
(addtup '(15 6 7 12 3))

;;; p64

(def ox
  (fn [n m]
    (cond
     (zero? m) 0
     :else (o+ n (ox n (sub1 m)))
     )))

(ox 5 3)
(ox 13 4)

;;; p66
(ox 12 3)

;;; p67
(def tup+
  (fn [t1 t2]
    (cond
     (null? t1) t2
     (null? t2) t1
     :else (cons (o+ (first t1) (first t2)) (tup+ (rest t1) (rest t2))))))

(tup+ '(3 6 9 11 4) '(8 5 2 0 7))

;;; p68

(tup+ '(2 3) '(4 6))

;;; p69

(tup+ '(3 7) '(4 6))

;;; p70
(tup+ '(3 7) '(4 6 8 1))
(tup+ '(3 7 8 1) '(4 6))

;;; p71
(def o>
  (fn [n m]
    (cond
     (zero? n) false
     (zero? m) true
     :else (o> (sub1 n) (sub1 m))
     )))

(o> 12 133)
(o> 120 11)
(o> 3 3)

;;; p73
(def o<
  (fn [n m]
    (cond
     (zero? m) false
     (zero? n) true
     :else (o< (sub1 n) (sub1 m))
     )))

(o< 4 6)
(o< 8 3)
(o< 6 6)

;;; p74
(def o=
  (fn [n m]
    (not (or (o< n m) (o> n m)))
    ))

(def oxx
  (fn [n m]
    (cond
     (zero? m) 1
     :else (ox n (oxx n (sub1 m)))
     )))

(oxx 1 1)
(oxx 2 3)
(oxx 5 3)

(def quotient
  (fn [n m]
    (cond
     (o< n m) 0
     :else (add1 (quotient (o- n m) m))
     )))

;;; p75

(quotient 15 4)

;;; p76

(def length
  (fn [l]
    (cond
     (null? l) 0
     :else (add1 (length (rest l)))
     )))

(length '(hotdogs with mustard sauerkraut and pickles))
(length '(ham and cheese on rye))

(def pick
  (fn [n l]
    (cond
     (zero? (sub1 n)) (first l)
     :else (pick (sub1 n) (rest l))
     )))

(pick 4 '(lasagna spaguetti ravioli macaroni meatball))

;;; p77

(def rempick
  (fn [n l]
    (cond
     (zero? (sub1 n)) (rest l)
     :else (cons (first l) (rempick (sub1 n) (rest l)))
     )))

(rempick 3 '(hotdogs with hot mustard))

(number? 'tomato)
(number? 76)

(def no-nums
  (fn [l]
    (cond
     (null? l) '()
     (number? (first l)) (no-nums (rest l))
     :else (cons (first l) (no-nums (rest l)))
     )))

(no-nums '(5 pears 6 prunes 9 dates))

;;; p78
(def all-nums
  (fn [l]
    (cond
     (null? l) '()
     (number? (first l)) (cons (first l) (all-nums (rest l)))
     :else (all-nums (rest l))
     )))

(def eqan?
  (fn [a1 a2]
    (cond
     (and (number? a1) (number? a2)) (o= a1 a2)
     (or (number? a1) (number? a2)) false
     :else (= a1 a2)
     )))

(def occur
  (fn [a l]
    (cond
     (null? l) 0
     (eqan? a (first l)) (add1 (occur a (rest l)))
     :else (occur a (rest l))
     )))

(occur 'a '(a b c d a d f aa dd))

;;; p79

(def one?
  (fn [n]
    (= n 1)
    ))

(one? 1)

(def rempick
  (fn [n l]
    (cond
     (one? n) (rest l)
     :else (cons (first l) (rempick (sub1 n) (rest l)))
     )))

(rempick 3 '(lemon meringue salty pie))

;;; Chapter 5. *Oh My Gawd*: It's Full of Stars

;;; p81

(def rember*
  (fn [a l]
    (cond
     (null? l) '()
     (atom? (first l)) (cond
                        (eqan? a (first l)) (rember* a (rest l))
                        :else (cons (first l) (rember* a (rest l)))
                        )
     :else (cons (rember* a (first l)) (rember* a (rest l)))
     )))

(rember* 'cup '((coffee) cup ((tea) cup) (and (hick)) cup))
(rember* 'sauce '(((tomato sauce)) ((bean) sauce) (and ((flying)) sauce)))

(lat? '(((tomato sauce)) ((bean) sauce) (and ((flying)) sauce)))

;;; p82

(def insertR*
  (fn [n o l]
    (cond
     (null? l) '()
     (atom? (first l)) (cond
                        (eqan? o (first l)) (cons o (cons n (insertR* n o (rest l))))
                        :else (cons (first l) (insertR* n o (rest l)))
                        )
     :else (cons (insertR* n o (first l)) (insertR* n o (rest l)))
     )))

(insertR* 'roast 'chuck '((how much (wood)) could ((a (wood) chuck)) (((chuck))) (if (a) ((wood chuck))) could chuck wood))

;;; p85
(def occur*
  (fn [a l]
    (cond
     (null? l) 0
     (atom? (first l)) (cond
                        (eqan? a (first l)) (add1 (occur* a (rest l)))
                        :else (occur* a (rest l))
                        )
     :else (o+ (occur* a (first l)) (occur* a (rest l)))
     )))

(def subst*
  (fn [n o l]
    (cond
     (null? l) '()
     (atom? (first l)) (cond
                        (eqan? o (first l)) (cons n (subst* n o (rest l)))
                        :else (cons (first l) (subst* n o (rest l)))
                        )
     :else (cons (subst* n o (first l)) (subst* n o (rest l)))
     )))

(def insertL*
  (fn [n o l]
    (cond
     (null? l) '()
     (atom? (first l)) (cond
                        (eqan? o (first l)) (cons n (cons o (insertL* n o (rest l))))
                        :else (cons (first l) (insertL* n o (rest l)))
                        )
     :else (cons (insertL* n o (first l)) (insertL* n o (rest l)))
     )))

(def member*
  (fn [a l]
    (cond
     (null? l) false
     (atom? (first l)) (or
                        (eqan? a (first l))
                        (member* a (rest l))
                        )
     :else (or (member* a (first l)) (member* a (rest l)))
     )))

;;; p88

(def leftmost
  (fn [l]
    (cond
     (atom? (first l)) (first l)
     :else (leftmost (first l))
     )))

;;; p91
(def eqlist?
  (fn [l1 l2]
    (cond
     (and (null? l1) (null? l2)) true
     (or (null? l1) (null? l2)) false
     (and (atom? l1) (atom? l2)) (eqan? l1 l2)
     (or (atom? l1) (atom? l2)) false
     :else (and (eqlist? (first l1) (first l2)) (eqlist? (rest l1) (rest l2)))
     )))

(eqlist? '(strawberry ice cream) '(strawberry ice cream))
(eqlist? '((strawberry) ice cream) '((strawberry) (ice) cream))

(def equal?
  (fn [s1 s2]
    (cond
     (and (atom? s1) (atom? s2)) (eqan? s1 s2)
     (or (atom? s1) (atom? s2)) false
     :else (eqlist? s1 s2)
     )))

(def eqlist?
  (fn [l1 l2]
    (cond
     (and (null? l1) (null? l2)) true
     (or (null? l1) (null? l2)) false
     :else (and (equal? (first l1) (first l2)) (eqlist? (rest l1) (rest l2)))
     )))

(def rember
  (fn [s l]
    (cond
     (null? l) '()
     (equal? (first l) s) (rest l)
     :else (cons (first l) (rember s (rest l)))
     )))

;;; Chapter 6. Shadows

;;; p101

(def numbered?
  (fn [aexp]
    (cond
     (atom? aexp) (number? aexp)
     (equal? (first (rest aexp)) '+) (and (numbered? (first aexp)) (numbered? (first (rest (rest aexp)))))
     (equal? (first (rest aexp)) 'x) (and (numbered? (first aexp)) (numbered? (first (rest (rest aexp)))))
     (equal? (first (rest aexp)) 'xx) (and (numbered? (first aexp)) (numbered? (first (rest (rest aexp)))))
     :else false
     )))

(numbered? 1)
(numbered? '(3 + (4 xx 5)))
(numbered? '(2 x sausage))


(def numbered?
  (fn [aexp]
    (cond
     (atom? aexp) (number? aexp)
     :else (and (numbered? (first aexp)) (numbered? (first (rest (rest aexp)))))
     )))

;;; p102

(def value
  (fn [nexp]
    (cond
     (atom? nexp) nexp
     (equal? (first (rest nexp)) '+) (o+ (value (first nexp)) (value (first (rest (rest nexp)))))
     (equal? (first (rest nexp)) 'x) (ox (value (first nexp)) (value (first (rest (rest nexp)))))
     (equal? (first (rest nexp)) 'xx) (oxx (value (first nexp)) (value (first (rest (rest nexp)))))
     )))

(value '(1 + (3 xx 4)))
(value 'cookie)
(value 13)
(value '(1 + 3))

;;; p105

(def first-sub-exp
  (fn [aexp]
    (first (rest aexp))
    ))

;;; p106
(def second-sub-exp
  (fn [aexp]
    (first (rest (rest aexp)))
    ))

(def operator
  (fn [aexp]
    (first aexp)
    ))

(def value
  (fn [nexp]
    (cond
     (atom? nexp) nexp
     (equal? (operator nexp) '+) (o+ (value (first-sub-exp nexp)) (value (second-sub-exp nexp)))
     (equal? (operator nexp) 'x) (ox (value (first-sub-exp nexp)) (value (second-sub-exp nexp)))
     (equal? (operator nexp) 'xx) (oxx (value (first-sub-exp nexp)) (value (second-sub-exp nexp)))
     )))

(value '(+ 1 (xx 3 4)))
(value 'cookie)
(value 13)
(value '(+ 1 3))

;;; p108

(def sero?
  (fn [n]
    (null? n)
    ))

(def edd1
  (fn [n]
    (cons '() n)
    ))

(def zub1
  (fn [n]
    (rest n)
    ))


(zub1 (edd1 (edd1 (edd1 '()))))

(def z+
  (fn [n m]
    (cond
     (sero? m) n
     :else (z+ (edd1 n) (zub1 m))
     )))

;;; when you chose a representation different for the existing ones you risk
;;; using functions that have other representations in "mind" onto your
;;; representations: beware of shadows

;;; Chapter 7. friends and relations
;;; p111

(def a-set?
  (fn [l]
    (cond
     (null? l) true
     (member? (first l) (rest l)) false
     :else (a-set? (rest l))
     )))


(a-set? '(apple peaches apple plum))
(a-set? '(applesxs peaches pears plum))
(a-set? '())
(a-set? '(apple 3 pear 4 9 apple 3 4))

;;; p112
(def makeset
  (fn [l]
    (cond
     (null? l) '()
     (member? (first l) (rest l)) (makeset (rest l))
     :else (cons (first l) (makeset (rest l)))
     )))

(makeset '(apple peach pear peach plum apple lemon peach))

(def makeset
  (fn [l]
    (cond
     (null? l) '()
     :else (cons (first l) (makeset (multirember (first l) (rest l))))
     )))

(makeset '(apple peach pear peach plum apple lemon peach))
(makeset '(apple 3 pear 4 9 apple 3 4))

(def subset?
  (fn [set1 set2]
    (cond
     (null? set1) true
     :else (and (member? (first set1) set2) (subset? (rest set1) set2))
     )))


(subset? '(5 chicken wings) '(5 hamburgers 2 pieces fried chicken light duckling wings))
(subset? '(4 pounds of horseradish) '(four pounds of chicken 5 hamburgers 2 pieces fried chicken light duckling wings))

;;; p114

(def eqset?
  (fn [set1 set2]
    (and (subset? set1 set2) (subset? set2 set1))
    ))


(eqset? '(6 large chicken with wings) '(6 chicken with large wings))



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
