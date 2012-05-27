(ns org.mathpiper.studentschedule.completescheduleengine.student_schedules_api)

(use 'org.mathpiper.studentschedule.completescheduleengine.html_creation_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)


;returns at random return-number of schedules made using course-lists
;and legal-scheddules.

(defn find-schedules [string]
  (let [
        {course-lists :course-lists time-of-day :time-of-day return-number :return-number custom-courses :custom-courses} (load-string string)
        custom-course-map (merge zz2 custom-courses)
        legal-schedules-output (legal-schedules course-lists custom-course-map)
        ]
    
    (createHtmlScheduleTables (sort-by-time legal-schedules-output time-of-day 1  custom-course-map)
       custom-course-map)
    
    )
  )

 ; test string:

 ;
#_(def ali "{:course-lists [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]
 :time-of-day :morning :return-number 11}")

#_(spit "../student_schedule.html" (find-schedules ali) )

#_(for [index (range return-number) ]
        (nth  legal-schedules-output (rand-int (count  legal-schedules-output)) ) )