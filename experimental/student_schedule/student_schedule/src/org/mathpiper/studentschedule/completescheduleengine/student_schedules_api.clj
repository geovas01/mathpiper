(ns org.mathpiper.studentschedule.completescheduleengine.student_schedules_api)

(use 'org.mathpiper.studentschedule.completescheduleengine.html_creation_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.quality_engine)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)


;returns at random return-number of schedules made using course-lists
;and legal-scheddules.

(defn find-schedules [string]
  (let [
        {course-lists :course-lists quality-fn-and-vals :quality-fn-and-vals return-number :return-number custom-courses :custom-courses} (load-string string)
        custom-course-map (merge zz2 custom-courses)
        all-courses (apply concat course-lists)
        courses-not-in-map (filter (fn [course] (not (course  zz2))) all-courses)
        legal-schedules-output  (legal-schedules course-lists  custom-course-map)
        ]
    (cond (not= courses-not-in-map '())
            (apply str (concat ["The following course numbers either are not offered this semester or do not exist \n"] (map #(str "   " (name %) " \n " ) courses-not-in-map) ) )
            
            (and (not= quality-fn-and-vals []))
     (createHtmlScheduleTables (take return-number  (sort-by-quality legal-schedules-output quality-fn-and-vals 0.0 custom-course-map))
       custom-course-map)
     
     
     )
    
    
    )
  )

(defn  formated-courses-in-map []
  (apply str (map #(str (name %) ",") (keys zz2)))
  
  )

(formated-courses-in-map)

 ; test string:

 ;
#_(def ali "{:course-lists [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]
  :return-number 6 :quality-fn-and-vals [[time-of-day-ratio-corrected [:afternoon 1]]] :custom-courses {}}")

 
 
#_(time (spit "../student_schedule.html" (find-schedules ali) ))

#_(def course-list [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

#_(time (legal-schedules course-list zz2))

#_(nth (find-schedules ali) 10)

#_(for [index (range return-number) ]
        (nth  legal-schedules-output (rand-int (count  legal-schedules-output)) ) )


#_(time (do  (def best (reduce max
          (map (fn [schedule]
                 (time-of-day-ratio-corrected schedule :morning 1 zz2))
               (legal-schedules course-list zz2))))



    (filter #(=  (time-of-day-ratio-corrected % :morning 1 zz2)
    best)
    
    (legal-schedules course-list zz2))


))

#_(time (sort-by-time-2 (legal-schedules course-list zz2) :morning 1 zz2))

#_(time (def coll-test (doall (map #(list (time-of-day-ratio-corrected % :morning 1 zz2) %)   (legal-schedules course-list zz2)))))



#_(def course-list [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])


#_(complete-quality (first (legal-schedules course-list zz2))
                    [[time-of-day-ratio-corrected [:morning 1]]] zz2)

