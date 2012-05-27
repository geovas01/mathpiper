(ns org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)

(defn open-section? [course-section course-map]
  (let [{course-number :course-number section-number :section-number} course-section
        open-boolean (get-in course-map [course-number :sections section-number :open?])
        ]
    open-boolean)
  )


(defn get-open-sections [course-number course-map] 
  (let [ {sections :sections} (course-number course-map)
        section-numbers (filter #(open-section? {:course-number course-number :section-number %} course-map) (keys sections))]
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


(defn tabulate-schedules 
  ([course-list course-map]
  (let [courses-possible (combine course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (combine (map #(get-open-sections % course-map)  possible-course-list)) ) courses-possible)) ]     
     schedules) )
  
([course-list picked-courses course-map]
    
    (let [courses-possible (combine course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (combine (map #_(println %) #(if (not= (course-in-list? % picked-courses ) [])
                                                   (course-in-list? % picked-courses  )
                                                   (get-open-sections % course-map)
                                                   )
                                             
                                             possible-course-list
                                             )) )
                               courses-possible))
        ]     
     schedules)
  
    )
)



(defn overlap? [{course-number-1 :course-number section-number-1 :section-number} {course-number-2 :course-number section-number-2 :section-number} course-map]
  (let [time-codes-1 (get-in course-map [course-number-1 :sections section-number-1 :days-and-times])
        time-codes-2 (get-in course-map [course-number-2 :sections section-number-2 :days-and-times])
       
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



(defn legal-schedule? [schedule course-map]
(let [boolean-list
      (for [course-1 schedule course-2 schedule]
        
        (if (and (= (:course-number course-1) (:course-number course-2)) (= (:section-number course-1) (:section-number course-2)))
          true (not  (overlap? course-1 course-2 course-map))
          )
       )]
        (every? (fn [bool]  bool) boolean-list)
        
) 
)


(defn legal-schedules 
  
  
  ([course-list course-map]
  (vec (filter #(legal-schedule? % course-map) (tabulate-schedules course-list course-map)))
  )
  
  ([course-list picked-courses course-map]
  (vec (filter #(legal-schedule? % course-map) (tabulate-schedules course-list picked-courses course-map)))
  )
  
  )


#_(legal-schedules [[:MATH1300] [:ENGL1101]] #_[{:course-number :MATH1010 :section-number :01}
                                              {:course-number :ENGL1101 :section-number :01}] zz2)

