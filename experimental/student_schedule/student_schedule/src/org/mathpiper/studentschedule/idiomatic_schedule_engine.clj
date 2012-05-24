(use 'clojure.math.combinatorics)

(defn get-sections [course-number] 
  (let [ {sections :sections} (course-number zz2)
        section-numbers (keys sections)]
    (for [section-number section-numbers]
      {:course-number course-number :section-number section-number}
    )    
  )
)


#_((defn tabulat-schedules [course-list]
  (let [courses-possible (apply cartesian-product course-list)
        schedules (reduce concat 
                          (map (fn
                                  [possible-course-list ]
                                  (map #(apply cartesian-product (get-sections %) ) possible-course-list) ) courses-possible))]     
    (vec schedules))
  )

(def maci (apply cartesian-product [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]))

(:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275)



(map #(apply cartesian-product (get-sections %) ) [:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275])  

(map (fn [x] println x ) (:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275))  )

(defn combine [seq]
    (list 'for 
      (vec (reduce concat (map (fn [x y] (list (symbol (str "var" y)) x )) seq (range  (count seq)))))
      (vec  (for [var (map (fn [x] (symbol (str "var" x))) (range  (count seq)))] var) ))
)
(def ab (combine aa))

(defmacro countmac [s] (combine s))

(defmacro course-combinations [list-of-courses] (combine list-of-courses))



(macroexpand-1 '(countmac aa))

(defmacro illl [x] `(do  `(count x)) )

(def aa [["a" "b" "c"] [1 2 3 4] [:red :green :blue]])


#_(combine aa)

