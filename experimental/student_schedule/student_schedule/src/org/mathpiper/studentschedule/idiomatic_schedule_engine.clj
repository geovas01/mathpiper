#_(time (def sched (tabulat-schedules [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]))
)

#_(time (tabulat-schedules [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]))

#_(combine (map #(get-sections % ) [:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275]))

#_(get-sections :MATH1300)


#_(comment        
  
  (map combine (:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275))  

(declare seq-of-seqs)

(defmacro combine [seq-of-seqs]
  
     (list 'for 
      (vec (reduce concat (map (fn [x y] (list (symbol (str "var" y)) x )) (eval seq-of-seqs) (range   (count (eval seq-of-seqs) ) ))))
      (vec  (for [var (map (fn [x] (symbol (str "var" x))) (range   (count (eval seq-of-seqs))))] var) ))
)

(defn stuff [a] (combine ))

(combine aa) 
       )
#_(use 'clojure.math.combinatorics)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)



(defn open-section? [course-section]
  (let [{course-number :course-number section-number :section-number} course-section
        open-boolean (get-in zz2 [course-number :sections section-number :open?])
        ]
    open-boolean)
  )

;test code for open-section?

#_(for [course-number (keys zz2)
        section-number (keys (get-in zz2 [course-number :sections]))
        
        ]
    (if (not (open-section? {:course-number course-number :section-number section-number})) (println course-number section-number) ))





(defn get-sections [course-number] 
  (let [ {sections :sections} (course-number zz2)
        section-numbers (keys sections)]
    (vec (for [section-number section-numbers]
      {:course-number course-number :section-number section-number})
    )    
  )
)

(defn get-open-sections [course-number] 
  (let [ {sections :sections} (course-number zz2)
        section-numbers (filter #(open-section? {:course-number course-number :section-number %}) (keys sections))]
    (vec (for [section-number section-numbers]
      {:course-number course-number :section-number section-number})
    )    
  ))
 


(defn combine [seq-of-seqs]
 (loop [remainder seq-of-seqs currant-state-of-seq [[]] ]
  (let [next-seq (first remainder)
        rest-remainder (rest remainder)
        processed-seq (reduce concat (map (fn [unfinished-seq] (map #(conj unfinished-seq %) next-seq) ) currant-state-of-seq))
        ] (if (= rest-remainder '()) processed-seq (recur rest-remainder  processed-seq)) ))
  
  )




(defn course-in-list? [ course-number-in course-listing]
  
 (vec (filter (fn [{course-number :course-number section-number :section-nimber}] (= course-number course-number-in) ) course-listing))
 )


#_(tabulate-schedules [[:MATH1010] [:ENGL1101]] [{:course-number :MATH1010 :section-number :01}
                                              {:course-number :ENGL1101 :section-number :01}])

#_(tabulate-schedules [[:MATH1010] [:ENGL1101]] [])





(defn tabulate-schedules 
  ([course-list]
  (let [courses-possible (combine course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (combine (map #(get-open-sections %)  possible-course-list)) ) courses-possible)) ]     
     schedules) )
  
([course-list picked-courses]
    
    (let [courses-possible (combine course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (combine (map #_(println %) #(if (not= (course-in-list? % picked-courses ) [])
                                                   (course-in-list? % picked-courses  )
                                                   (get-open-sections %)
                                                   )
                                             
                                             possible-course-list
                                             )) )
                               courses-possible))
        ]     
     schedules)
  
    )
)

(defn overlap? [{course-number-1 :course-number section-number-1 :section-number} {course-number-2 :course-number section-number-2 :section-number}]
  (let [time-codes-1 (get-in zz2 [course-number-1 :sections section-number-1 :days-and-times])
        time-codes-2 (get-in zz2 [course-number-2 :sections section-number-2 :days-and-times])
       
     boolean-list (for [time-code-1 time-codes-1 time-code-2 time-codes-2]
           (let [[day-code-1 start-time-1 duration-1] time-code-1
                 [day-code-2 start-time-2 duration-2] time-code-2
                 end-time-1 (+ start-time-1 duration-1)
                 end-time-2 (+ start-time-2 duration-2)]
                 
             (if (and 
                  (not= (bit-and day-code-1 day-code-2 ) 0)
                  (> end-time-1 start-time-2)
                  (> end-time-2 start-time-1))
                  true false)    )) ]
    
    (some (fn [bool] bool) boolean-list)
  
    )     
)



(defn legal-schedule? [schedule]
(let [boolean-list
      (for [course-1 schedule course-2 schedule]
        
        (if (and (= (:course-number course-1) (:course-number course-2)) (= (:section-number course-1) (:section-number course-2)))
          true (not  (overlap? course-1 course-2))
          )
       )]
        (every? (fn [bool]  bool) boolean-list)
        
) 
)


(defn legal-schedules 
  
  
  ([course-list]
  (vec (filter legal-schedule? (tabulate-schedules course-list)))
  )
  
  #_([course-list picked-courses]
  (vec (filter legal-schedule? (tabulate-schedules course-list picked-courses)))
  )
  
  )


#_(:ETCO1120 :ETEM1110 :MATH1300 :ENGL1101 :ENGL2275)



#_(def sample-sched [{:course-number :ETCO1120, :section-number :51} {:course-number :ETEM1110, :section-number :01} {:course-number :MATH1010, :section-number :05} {:course-number :ENGL1101, :section-number :05} {:course-number :ARTH1101, :section-number :09}])


#_(time (def scheds (tabulate-schedules [[:ETCO1120] [:ETEM1110] [:MATH1010] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])))

#_(time (def legs (legal-schedules [[:ETCO1120] [:ETEM1110] [:MATH1010] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]))
)







