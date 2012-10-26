(ns org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

;The namespace schedule_engine holds all the functions used to exhaustivly find all
;schedules that do not have conflicts.

(use 'org.mathpiper.studentschedule.ssu_spring_2013_semester_schedule_map)

(use 'clojure.math.combinatorics)


(defn open-section? 
"     The function open-section? takes the argument course-section in the form:
{:course-number course-number :section-number section-number},
where course-number is a keyword like :MATH1010 and where section-number is in the form of a numbered keyword like :05. The function
then finds if the course section is open by using course-map, and then it returns true if it is open or false if it is not. 
"
  [course-section course-map]
  (let [{course-number :course-number section-number :section-number} course-section
        open-boolean (get-in course-map [course-number :sections section-number :open?])
        ]
    open-boolean)
  )


(defn get-open-sections
"     The function get-open-sections takes the argument course-number as a keyword, like :MATH1300, and returns a vector containing maps
of the form: 
{:course-number course-number :section-number section-number},
where course-number is the given course number and where section-number is in a form like :05, for each open section number.

"
  [course-number course-map] 
  (let [ {sections :sections} (course-number course-map)
        section-numbers (filter #(open-section? {:course-number course-number :section-number %} course-map) (keys sections))]
    (vec (for [section-number section-numbers]
      {:course-number course-number :section-number section-number})
    )    
  ))


;combine is a function that tends to overflow the stack.
#_(defn combine [seq-of-seqs]
 (loop [remainder seq-of-seqs currant-state-of-seq [[]] ]
  (let [next-seq (first remainder)
        rest-remainder (rest remainder)
        processed-seq (reduce concat (map (fn [unfinished-seq] (map #(conj unfinished-seq %) next-seq) ) currant-state-of-seq))
        ] (if (= rest-remainder '()) processed-seq (recur rest-remainder  processed-seq)) ))
  
  )


(defn course-in-list?
"     The function course-in-list? takes the course-number-in argument in the form of a keyword like :MATH1010 and takes course-listing
 as a vector containing maps of the form: 
 {:course-number course-number :section-number section-number}.

     The function returns a vector that contains the subset of course-listing with maps that have the value of course-number-in paired
 with :course-number.


"
  
  [ course-number-in course-listing]
  
 (vec (filter (fn [{course-number :course-number section-number :section-nimber}] (= course-number course-number-in) ) course-listing))
 )


(defn tabulate-schedules
  
"     The function tabulate-schedules takes the course-list argument as a vector of vectors that contains course-numbers in the form of
 keys like the following: 
[[:ETEC2101]] [[:ETCO1120] [:PSYC1101] [:ETEM1110] [:MATH1010] [:ENGL1105] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]].

The course-map argument is in the standerd format as a map of maps that hold data on courses.

An optional third argument may be added between course-list and course-map. This argument, picked courses, is in a form like:
[{:course-number :MATH1010 :section-number :01} {:course-number :ENGL1101 :section-number :01}].

     The tabulate-schedules function will return a vector of vectors that are in the same form as picked-courses. Each vector in the output 
vector represents a possible schedule. Each schedule is a vector that holds each course and section number in the form of a map like:
{:course-number :ENGL1101 :section-number :01}.
Each schedule has one section of one course from each vector of course-list. If picked-courses is passed to tabulate-schedules, the 
function will only use those sections from each course that are in picked-courses. However, if there are no sections of a given course
in picked-courses, then tabulate-schedules will use all sections of that course. Note that the schedules produced by this function
may contain conflicts.

"
  
  ([course-list course-map]
  (let [courses-possible (apply cartesian-product course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (apply cartesian-product (map #(get-open-sections % course-map)  possible-course-list)) ) courses-possible)) ]     
     schedules) )
  
([course-list picked-courses course-map]
    
    (let [courses-possible (apply cartesian-product course-list)
        schedules (reduce concat 
                          (map (fn [possible-course-list]
                                  (apply cartesian-product (map #_(println %) #(if (not= (course-in-list? % picked-courses ) [])
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



(defn overlap?
"     The overlap? function takes two maps that each contain the a section of a given course. The function then uses course-map to find if
the two sections have conflicting times. It then returns true if they do conflict and false if they don't.

"
  
  [{course-number-1 :course-number section-number-1 :section-number} {course-number-2 :course-number section-number-2 :section-number} course-map]
  (let [time-codes-1 (get-in course-map [course-number-1 :sections section-number-1 :days-and-times])
        time-codes-2 (get-in course-map [course-number-2 :sections section-number-2 :days-and-times])
       
     boolean-list (for [time-code-1 time-codes-1 time-code-2 time-codes-2]
           (let [[day-code-1 start-time-1 duration-1] time-code-1
                 [day-code-2 start-time-2 duration-2] time-code-2
                 end-time-1 (+ start-time-1 duration-1)
                 end-time-2 (+ start-time-2 duration-2)]
                 
             (if (and 
                  (not= (bit-and day-code-1 day-code-2 ) 0) ;To overlap, the two timecodes must have one or more days in commen.
                  (> end-time-1 start-time-2)
                  (> end-time-2 start-time-1))
                  true false)    )) ]
    
    (some (fn [bool] bool) boolean-list)
  
    )     
)



(defn legal-schedule?
  
"     The function legal-schedule? takes a schedule in the form of a vector of maps like:
[{:course-number :MATH1010 :section-number :01} {:course-number :ENGL1101 :section-number :01}].
The function then determines if there exists any conflicts bettween any of the sections in the schedule
and returns true if there is and false if there is not.
"
  
  [schedule course-map]
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
"     The function legal-schedules takes the course-list argument as a vector of vectors that contains course-numbers in the form of keys
like the following: 
[[:ETEC2101]] [[:ETCO1120] [:PSYC1101] [:ETEM1110] [:MATH1010] [:ENGL1105] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]].

The course-map argument is in the standerd format of course-maps as a map of maps that hold data on courses.

An optional third argument may be added between course-list and course-map. This argument, picked courses, is in the form:
[{:course-number :MATH1010 :section-number :01} {:course-number :ENGL1101 :section-number :01}].

     The legal-schedules function will return a vector of vectors that are in the same form as picked-courses. Each vector in the output 
vector represents a possible schedule. Each schedule is a vector that holds each course and section number in the form of a map like:
{:course-number :ENGL1101 :section-number :01}.
Each schedule has one section of one course from each vector of course-list. If picked-courses is passed to legal-schedules, the 
function will only use those sections from each course that are in picked-courses. However, if there are no sections of a given course
in picked-courses, then legal-schedules will use all sections of that course. The function legal-schedules will only return those
possilbe schedules that have no time conflicts.
"  
  
  ([course-list course-map]
  (filter #(legal-schedule? % course-map) (tabulate-schedules course-list course-map)))
  
  
  ([course-list picked-courses course-map]
   (filter #(legal-schedule? % course-map) (tabulate-schedules course-list picked-courses course-map)))
  
  
  )


#_(legal-schedules [[:MATH1300] [:ENGL1101]] #_[{:course-number :MATH1010 :section-number :01}
                                              {:course-number :ENGL1101 :section-number :01}] zz2)

