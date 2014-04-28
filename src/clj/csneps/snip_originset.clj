;;; This file includes several functions for calcluating origin sets. 
;;; Since IGs reason in all contexts simultaneously, it's often necessary to
;;; create origin sets which are the combinatoric combination of other sets,
;;; or to apply difference operations to many sets. These functions are meant
;;; to abstract that away from the implementation of IGs.

(in-ns 'csneps.snip)

(defn os-union 
  "When two sets of sets are unioned, the result is a set of sets 
   which has size |supports1|*|supports2|, and represents every 
   combination of the two."
  [supports1 supports2]
  (set (for [s1 supports1
             s2 supports2]
         (union s1 s2))))

(defn os-remove-hyp
  "For all sets in supports1 which contain hyp,
   remove hyp, and return the resulting set of sets."
  [supports1 hyp]
  (let [sup-with-hyp (filter #(get % hyp) supports1)]
    (set (map #(disj % hyp) sup-with-hyp))))

(defn os-remove-hyps
  "For all sets in supports1 which are supersets of 
   hyps. remove the hyps elements, and return the 
   resulting set of sets."
  [supports1 hyps]
  (let [sup-with-hyps (filter #(subset? hyps %) supports1)]
    (set (map #(difference % hyps) sup-with-hyps))))

(defn os-equal-sets
  "Return the set of sets in supports1 also in supports2 
   (i.e., the intersection of the two)"
  [supports1 supports2]
  (intersection supports1 supports2))