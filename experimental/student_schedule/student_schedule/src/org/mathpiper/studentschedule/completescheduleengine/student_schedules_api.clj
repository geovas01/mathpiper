(ns org.mathpiper.studentschedule.completescheduleengine.student_schedules_api)

(use 'org.mathpiper.studentschedule.completescheduleengine.html_creation_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

;returns at random return-number of schedules made using course-lists
;and legal-scheddules.

(defn find-schedules [string]
  (let [
        {course-lists :course-lists time-of-day :time-of-day return-number :return-number} (load-string string)
        legal-schedules-output (legal-schedules course-lists)
        ]
    
    (createHtmlScheduleTables
      (for [index (range return-number) ]
        (nth  legal-schedules-output (rand-int (count  legal-schedules-output)) ) ))
    
    )
  )

 ; test string:

 ;
#_(def ali "{:course-lists [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]
 :time-of-day :morning :return-number 11}")

#_(spit "../student_schedule.html" (find-schedules ali) )
