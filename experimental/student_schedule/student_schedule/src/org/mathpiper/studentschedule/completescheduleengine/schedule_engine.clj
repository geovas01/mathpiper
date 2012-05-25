(defn get-sections [course-number] 
  (let [ {sections :sections} (course-number zz2)
        section-numbers (keys sections)]
    (vec (for [section-number section-numbers]
      {:course-number course-number :section-number section-number})
    )    
  )
)



(defn combine [seq-of-seqs]
 (loop [remainder seq-of-seqs currant-state-of-seq [[]] ]
  (let [next-seq (first remainder)
        rest-remainder (rest remainder)
        processed-seq (reduce concat (map (fn [unfinished-seq] (map #(conj unfinished-seq %) next-seq) ) currant-state-of-seq))
        ] (if (= rest-remainder '()) processed-seq (recur rest-remainder  processed-seq)) ))
  
  )




(defn tabulate-schedules [course-list]
  (let [courses-possible (combine course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (combine (map #(get-sections %)  possible-course-list)) ) courses-possible)) ]     
     schedules)
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


(defn legal-schedules [course-list]
  (vec (filter legal-schedule? (tabulate-schedules course-list)))
  )

