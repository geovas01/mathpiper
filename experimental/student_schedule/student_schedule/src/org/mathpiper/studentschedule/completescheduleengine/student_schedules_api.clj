(ns org.mathpiper.studentschedule.completescheduleengine.student_schedules_api)

;The namespace student_schedule_api holds all the functions that talk to the client along with some test code.

(use 'org.mathpiper.studentschedule.completescheduleengine.html_creation_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.schedule_engine)

(use 'org.mathpiper.studentschedule.completescheduleengine.quality_engine)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)

(import 'org.mathpiper.studentschedule.gwt.shared.ArgumentException)




(defn find-schedules
  "     The function find-schedueles takes a single string as input in the following form:

\"{:selected-courses selected-courses :course-lists course-lists
 :quality-fn-and-vals quality-fn-and-vals :return-number return-number :custom-courses custom-courses}\".

Where selected-courses is a vector that contains all the sections that have been selected,
course-lists is a vector of vectors that each contain course-numbers to be used to find schedules,
quality-fn-and-vals contains all the quality functions and their values to be used in finding  the best schedules,
return-number is a positive integer, and
custom-courses is a map that is a custom add-on to the normal course map and is in that format.

     The function find-schedules outputs HTML without html or body tags.
The output is in the form of schedules that are seprated by | so that 
the client can print only one schedule at a time.



"
  
  [string]
  (let [
        clean-string (apply str (filter #(and (not= % \( ) (not= % \) )) string)) ;This line prevents clojure code from being passed to the rest of the function.
        {selected-courses :selected-courses course-lists :course-lists quality-fn-and-vals :quality-fn-and-vals return-number :return-number custom-courses :custom-courses} (load-string clean-string)
        unpacked-selected-courses (for [course selected-courses  section-number (:section-numbers course )]
                                    {:course-number (:course-number course) :section-number section-number})
        custom-course-map (merge zz2 custom-courses)
        all-courses (apply concat course-lists)
        courses-not-in-map (filter (fn [course] (not (course  zz2))) all-courses)
        legal-schedules-output  (doall (legal-schedules course-lists unpacked-selected-courses custom-course-map))
        ]
    ;This cond checks to see if there is any bad input before going through the time comsuming quality system.
    (cond 
      
      (= all-courses '()) (throw (ArgumentException. "You must enter at least one course to make a schedule."))

      (not= courses-not-in-map '()) (throw (ArgumentException. (apply str (concat ["The following course numbers are not offered this semester, are mistyped, or do not exist:\n"] (map #(str "   " (name %) " \n " ) courses-not-in-map) )) ))
      
      (not (apply distinct? all-courses)) (throw (ArgumentException. "Each course can only be entered once."))
      
      (= legal-schedules-output '()) 
            (throw (ArgumentException. "There are either no schedules that do not have conflicts or there are no open sections in the courses you have selected. "))   
            
      (and  (not= quality-fn-and-vals []))
            (createHtmlScheduleTables (sort-by-quality (take return-number (selected-by-quality legal-schedules-output quality-fn-and-vals 0.01 custom-course-map))  quality-fn-and-vals custom-course-map)
             custom-course-map)
            
       :default (createHtmlScheduleTables (for [_ (range return-number)]  (nth legal-schedules-output (rand-int (count legal-schedules-output)) )) custom-course-map)
     
     
     )    
    
    )
  
  )




(defn course-list
  "     Returns a string with a  comma seperated list, withour brackets, of all the open course names."
  []
  (let [open-courses (filter
                       (fn [course]
                         (not (every? (fn [course-section] (not(open-section? course-section zz2))) (map
                                   (fn [section] (do {:course-number course :section-number section}) ) 
                                   (keys (get-in zz2 [course :sections]))))) ) (keys zz2))
        
        ]
  
  (apply str (name (first open-courses)) (map #(str "," (name %) ) (rest open-courses)))
  )
  )




(defn show-sections
  "     The function show-sections takes a string that holds a map in the following format:

\"{:course-lists course-lists}\"

Where course-lists is a vector of vectors with course numbers of the courses who's section info will be returned. For example:
[[:ETCO1120] [:PSYC1101] [:ETEM1110] [:MATH1010] [:ENGL1105] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]]

The function show-sections returns HTML tables in a string that contains all infomation for the sections of the courses in course-list.
"
  [string]
  (let [clean-string (apply str (filter #(and (not= % \( ) (not= % \) )) string))
        {course-lists :course-lists} (load-string clean-string )
        all-courses (apply concat course-lists)
        courses-not-in-map (filter (fn [course] (not (course  zz2))) all-courses)
        ]
  (cond
    (not= courses-not-in-map '())
            
            (throw (ArgumentException. (apply str (concat ["The following course numbers are not offered this semester, are mistyped, or do not exist:\n"] (map #(str "   " (name %) " \n " ) courses-not-in-map) )) ))

    
   :default (create-html-sections-table (apply concat course-lists) zz2)
  )
  )
  )

(defn get-sections
  "     The function get-sections takes a course number as input
and returns a string that contains all the section numbers of
that course seprated by commas."
  [course-number-string]
  (let [clean-string (apply str (filter #(and (not= % \( ) (not= % \) )) course-number-string))
        course-number (load-string clean-string)
        open-section-keys (filter #(open-section? {:course-number course-number :section-number %} zz2) (keys (get-in zz2 [course-number :sections])))
        ordered-keys (sort #(< (Integer/parseInt (name %1)) (Integer/parseInt (name %2))) open-section-keys)
        ] 

(cond    (not (course-number zz2)) (throw (ArgumentException. (str "The following course number either is not offered this semester or does not exist: " (name  course-number) )) )
      (= open-section-keys '()) (throw (ArgumentException. (str "The following course number has no open sections " (name  course-number) )) )
      :default (apply str (name (first ordered-keys)) (map #(str "," (name %) ) (rest ordered-keys)))

)
    ))

(defn test-html-tables [string] (str "<html> <body> " (find-schedules string) "</html> </body> ")
  )

#_(course-list)

 ; test string:

 ;
#_(def ali "{:selected-courses [] #_[{:course-number :MATH1010 :section-numbers [:01 :02]}
 {:course-number :ARTH1101 :section-numbers [:51]}]
 :course-lists #_[] #_[[:ARTS2311]] #_[[:ETEC2101]] [[:ETCO1120] #_[:PSYC1101] [:ETEM1110] [:MATH1010] [:ENGL1105] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 #_:PHIL3300 :THAR1000]]
  :return-number 6 :quality-fn-and-vals [#_[\"time-of-day-ratio-corrected\" [:morning 1]] [\"choose-days\" [2r1010100]] #_[\"minimize-days\" []] ] :custom-courses {}}")

#_(spit "../student_schedule.html" (test-html-tables ali))
 
#_(legal-schedules [[:MATH1010]] [{:course-number :MATH1010 :section-number :01}] zz2)
 
#_(time (spit "../student_schedule.html" (show-sections ali) ))

#_(time (spit "../student_schedule.html" (find-schedules ali) ))

#_(find-schedules ali)

#_(def course-list [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

#_(get-sections ":SIGN1112")

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

