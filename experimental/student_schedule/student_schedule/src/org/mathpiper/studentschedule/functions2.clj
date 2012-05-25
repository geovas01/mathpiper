
;(use 'clojure.math.combinatorics)


(defn formated-days [course]
  (let [{course-number :course-number section-number :section-number} course
        days-and-times (get-in zz2 [course-number :sections section-number :days-and-times])
        all-days-bin (reduce #(bit-or %1 %2) (map (fn [day-and-time] (nth day-and-time 0) )  days-and-times) )]
    (str (if (not= (bit-and 2r1000000 all-days-bin) 0) "M" "-")
         (if (not= (bit-and 2r0100000 all-days-bin) 0) "T" "-")
         (if (not= (bit-and 2r0010000 all-days-bin) 0) "W" "-")
         (if (not= (bit-and 2r0001000 all-days-bin) 0) "R" "-")
         (if (not= (bit-and 2r0000100 all-days-bin) 0) "F" "-")
     
         )
    
    
    ))

;;Add associate a color with each course.
#_(defn addColorsToCourses [schedule]
  (map  #(assoc schedule )   (cycle [["#99CCFF" "Blue(Light)"]
 ["#F6F6CC" "Beige"]
 ["#9FCC9F" "Green(Pale)"]
 ["#C9C9F3" "Quartz"]
 ["#FF6F60" "Coral"]
 ["#CCCC60" "Goldenrod"]])))


(defn addColorsToCourses [schedule]
  ( vec(map  #(assoc (nth schedule %2) :color %1 )   (cycle [["#99CCFF" "Blue(Light)"]
 ["#F6F6CC" "Beige"]
 ["#9FCC9F" "Green(Pale)"]
 ["#C9C9F3" "Quartz"]
 ["#FF6F60" "Coral"]
 ["#CCCC60" "Goldenrod"]])
        
        (range (count schedule))) ))
  
  


(defn time-in-blocks-to-time
  [timeinblock]
  (let [hour (int  (Math/floor (/ timeinblock 12)) )
        minute (mod (* timeinblock 5) 60)
       ]
  (cond
    (= hour 0) "12:00AM"
    (>= hour 13) (str (- hour 12) ":" (if (< minute 10) (str "0" minute) minute) "PM" )
    (<  hour 13) (str (if (= hour 0) (str "12" ) hour) ":" (if (< minute 10) (str "0" minute) minute) (if (= hour 12) "PM" "AM")  )
    )  )
)




; (defn leastDays [schedule]
; scheduleWithColors 
;   
;   (every?
;   (fn [class] (every? 
;                 (fn [timecode]
;                   (= (timecode 0) day) ) (class 2addColorsToCourses)) ) schedule))
; 
; 
; (filter #(query  %) legalSchedules)
; 
; 
; 
; 
; ;;Count the number of bits that are set in an interger.
; (defn bit-count1 [n]
;   (count
;     (take-while
;       #(not (zero? %))
;       (iterate #(bit-and % (dec %)) n))))
; addColorsToCourses
; 
; ;;Count the number of bits that are set in an integer.
; (defn bit-count2 [v]
;   (loop [v v
;          c 0]
;     (if (zero? v)
;         c
;         (recur (bit-and v (dec v)) (inc c)))))
; 
; 
; 
; ;;Count how many days a course is spread over.
; (defn courseDayCount [[_ _ daysAndTimes]]
;   
;   (apply max (map (fn [dayTime] (bit-count2 (dayTime 0))) daysAndTimes))
;   
;   )
; 
; 
; 
; ;;Sort a legal schedules list by the number of days it is over.
; (defn leastDays [schedule]
;  
;        (sort (fn [[course1Number course1Section course1DaysAndTimes] [course2Number course2Section course2DaysAndTimes]] (< (courseDayCount course1DaysAndTimes) (courseDayCount course2DaysAndTimes))) schedule)
;        
;        )
;        
;        
; 
; 

;Change section format so each section has only one time code.
;
; Unidiomatic implementation.
;(def scheduleA (nth legalSchedules 0))
;
;(def schedulesTimeSlots [])
;  
;(doseq [section scheduleA]
;  
;    (doseq [sectionTime (section 2)]
;    
;        (def schedulesTimeSlots (conj  schedulesTimeSlots [(section 0) (section 1) sectionTime]))
;    )
;)
;
;
;Alternate implementation.
;(vec (mapcat 
;        (fn [[courseName sectionNumber daysAndTimes]] 
;          (map (fn [dayAndTime] [courseName sectionNumber dayAndTime]) daysAndTimes ) )
;        aa))
;
#_(defn unbundleDaysAndTimes [schedule]
  (vec (for [[courseName courseSection daysAndTimes courseColor] schedule, dayAndTime daysAndTimes]
  [courseName courseSection dayAndTime courseColor])))
             
             
(defn unbundleDaysAndTimes [schedule-colored]
  (vec (for [{courseName :course-number courseSection :section-number courseColor :color} schedule-colored,
             dayAndTime (get-in zz2 [courseName :sections courseSection :days-and-times])
            ]
         
         [courseName courseSection dayAndTime courseColor]))
  
 
  
  #_(println  schedule-colored))


 #_(get-in zz2 [:OTAT2210 :sections :01 :days-and-times])

#_(unbundleDaysAndTimes (addColorsToCourses sample-sched))

;Orders previously formatted single timecode sections into days of a week.
;
;Unidiomatic implementation.
;(def week [])
;(doseq [daybit [64 32 16 8 4 2 1]]
;    (def day [])
;    
;    (doseq [section schedulesTimeSlots]
;      (if (not= (bit-and ((section 2) 0) daybit) 0) 
;        (do
;            (def day (conj day section))
;            )
;      ))
;
;     (def day (vec (sort (fn [x y] (< ((x 2) 1) ((y 2) 1))) day) ))
;    
;     (def week (conj week day))
;  )
;    
;)
(defn orderIntoDays [schedule]
  (let [unbundled (unbundleDaysAndTimes (addColorsToCourses schedule))]
        
    (vec (for [daybit [64 32 16 8 4 2 1]]
        
      (vec (sort 
             (fn [[course1Name course1Section day1Time] [course2Name course2Section day2Time]] 
               (< (day1Time 1) (day2Time 1))) 
             (filter (fn [[courseName courseSection dayTime]]                                  
                       (not= (bit-and (dayTime 0) daybit) 0) ) unbundled)) )

        ))))

  
  
  



;;Add open time blocks.
;
; (def weekComplete [])
; 
; (doseq [day week]
; (do   
; 
;   ;(vec (cons courseNumber section))
;     (def dayEnd (conj day [:end :x [0,289,0]] ))
;     (def dayOpen [])
;     
;     (def indexTime 0)
;     (def index 0)
;     (while (< indexTime 288)
;     (do   
;         (def nextTime (((dayEnd index) 2) 1))
;         (def timeInc (((dayEnd index) 2 ) 2))
; 
;         (if (not= indexTime nextTime) (def dayOpen (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)]])))
;        
;         (def indexTime (+ nextTime timeInc))
;  
;         (def index (inc index))
;     ))
; 
;     (def dayComplete (vec (sort (fn [x,y] (< ((x 2) 1) ((y 2) 1))) (vec (concat day dayOpen)))))
; 
;     (def weekComplete (conj weekComplete dayComplete))
; 
; ))
; 
; 
; 
;weekComplete
;64 32 16  8  4 2 1
; M  T  W  R  F

;80 - MW
;40 - TR
;32 - T

(defn addOpenTimeBlocks [schedule]
  

  (let [week (orderIntoDays schedule)]
    
    (vec (for [day week]
      (do
        
        (let [dayEnd2 (conj day [:end :x [0,288,0]]) dayOpenFinal 
                     
          ;(println (count dayEnd2) dayEnd2 (dayEnd2 1))
          
          ;(. (System/out) (println (str (count dayEnd2) dayEnd2 (dayEnd2 1))))

      
      (loop [index 0
             indexTime 0
             dayOpen []]
        
;(. (System/out) (println (str (count dayEnd2) dayEnd2 " DDDD " dayOpen)))
        
        (let [
              nextTime (((dayEnd2 index) 2 ) 1)
              timeInc (((dayEnd2 index) 2 ) 2)
              ]
          
        
        ;(if (= index 1)
        ;  dayOpen
        ;  (recur (inc index) 3 dayOpen))
          
        ;(. (System/out) (println (str (count dayEnd2) dayEnd2 )))
        
        (if (>= (+ nextTime timeInc) 288)
          (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)] ["#FFFFFF" "White"]])
          (recur (inc index) (+ nextTime timeInc) 
             (if (not= indexTime nextTime) (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)] ["#FFFFFF" "White"]]) ["HHH"]);todo;tk;this if function needs to be studied further.
            )            
         )
        )
        )
      ]
       ;(. (System/out) (println (str " FFFF " day  " PPPPP " dayOpenFinal)))
      (vec (sort (fn [x,y] (< ((x 2) 1) ((y 2) 1))) (vec (concat day dayOpenFinal))))
       
      ;dayOpenFinal
       
      );let
        

       
       )
        
      
    
      ))))







  
 


#_(unbundleDaysAndTimes (nth legalSchedules 0))
;;Create one HTML schedule table.
#_(spit "../student_schedule.html" (createHtmlScheduleTable (nth (legal-schedules course-list) 600) ))
;
(defn createHtmlScheduleTable [schedule] 
  
  (let [days (addOpenTimeBlocks schedule) accumulator [] 
        earliest (reduce min (map (fn [class] (let [[courseName courseSection dayAndTime courseColor]  class, [dayCode startTime duration] dayAndTime] startTime)) (unbundleDaysAndTimes schedule)))
        latest   (reduce max (map (fn [class] (let [[courseName courseSection dayAndTime courseColor]  class, [dayCode startTime duration] dayAndTime] (+ startTime duration))) (unbundleDaysAndTimes schedule))) 
        earliestCorrected (- earliest (mod earliest 12) )
        latestCorrected   (+ latest (- 12 (mod latest 12)) )
       ]
     #_(println (time-in-blocks-to-time earliest) " " (time-in-blocks-to-time latest) )
     #_(println (time-in-blocks-to-time earliestCorrected) " " (time-in-blocks-to-time latestCorrected) )
  (str
      
"
<table border=1 cellpadding="4" width=90%>
<tr>
<td BGCOLOR=#EEEEEE align=center nowrap><b>Time</b></td>
<th BGCOLOR=#EEEEEE width=18%>Monday</th>
<th BGCOLOR=#EEEEEE width=18%>Tuesday</th>
<th BGCOLOR=#EEEEEE width=18%>Wednesday</th>
<th BGCOLOR=#EEEEEE width=18%>Thursday</th>
<th BGCOLOR=#EEEEEE width=18%>Friday</th>
</tr>
"  
      
    (apply str (apply concat (for [row (range earliestCorrected latestCorrected)]
      (apply concat
             
        
        (conj accumulator (str "<tr> " (if (= (mod row 12) 0) (str "<th rowspan=12 BGCOLOR=#EEEEEE nowrap>" (time-in-blocks-to-time row) "</th>"))))
        
        
    (apply str (apply concat (for [ day (take 5 days), [courseNumber sectionNumber [daysCode startTime duration] backgroundColor] day]
      (apply concat
             
        (if (and (= row earliestCorrected) (< startTime earliestCorrected) (> (+ startTime duration) earliestCorrected)) #_(println day " " startTime " " (+ startTime duration))
          (conj accumulator (str "<td align=center " 
                                         (str "BGCOLOR=\"" (first backgroundColor) "\"") " rowspan=" (-  (+ duration startTime) earliestCorrected) " >" 
                                          (if (= courseNumber :Open) "" 
                                           (name courseNumber)) (if (= courseNumber :Open) "" "-") (if (= sectionNumber :x) "" 
                                                                      (str (name sectionNumber) " <br />") ) (time-in-blocks-to-time earliestCorrected) "-" (time-in-blocks-to-time (+ startTime duration))  "</td>"))

                  
          
          
          )       
        
        (if (= startTime row)
          (do
                  (conj accumulator (str "<td align=center " 
                                         (str "BGCOLOR=\"" (first backgroundColor) "\"") " rowspan=" (if (> duration latestCorrected) (- latestCorrected startTime) duration) " >" 
                                         (if (= courseNumber :Open) "" 
                                          (name courseNumber)) (if (= courseNumber :Open) "" "-") (if (= sectionNumber :x) "" 
                                                                      (str (name sectionNumber) " <br />") )  (time-in-blocks-to-time startTime) "-" (time-in-blocks-to-time (+ startTime (if (> (+ startTime duration) latestCorrected) (- latestCorrected startTime) duration)))  "</td>"))

                  
            )
          )
  
     )
        
  )))    
        
         (conj accumulator (str "</tr>" "\n" ))
        
     )
        
  )))
    

    


 
"</table>
"   
    

    
    
    
    )
  
 ))








;;Create multiple HTML schedule tables on one web page.
;(spit "../student_schedule.html" (createHtmlScheduleTables (take 10 legalSchedules)))
;
#_(def course-list [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

#_(spit "../student_schedule.html"(createHtmlScheduleTables (for [e (range 50) ] (nth (legal-schedules course-list) (rand-int (count (legal-schedules course-list))) ) )) )


#_[{:course-number :ETCO1120, :section-number :51} {:course-number :ETEM1110, :section-number :01} {:course-number :MATH1300, :section-number :01} {:course-number :ENGL1101, :section-number :12} {:course-number :ARTH1101, :section-number :52}]

#_(spit "../student_schedule.html" (createHtmlScheduleTables  [ [{:course-number :ETCO1120, :section-number :51} {:course-number :ETEM1110, :section-number :01} {:course-number :MATH1300, :section-number :01} {:course-number :ENGL1101, :section-number :12} {:course-number :ARTH1101, :section-number :52}] ] ) )


(defn createHtmlScheduleTables [schedules]

  
  
(str 
"<html>
<head>
<title>Student schedule</title>
</head>
<body>"
  
   

   (apply str (for  [index (range (count schedules)) ]
     
       
                
     (str 
       
       "<hr /> <h2 align=\"center\"> Schedule " (inc index) "</h2> \n" 
       
"
<table border=1  cellpadding=3 align=\"center\">
<tr>
<td BGCOLOR=#EEEEEE align=center nowrap><b>#</b></td>
<th BGCOLOR=#EEEEEE >Course</th>
<th BGCOLOR=#EEEEEE >Section</th>
<th BGCOLOR=#EEEEEE >Name</th>
<th BGCOLOR=#EEEEEE >Faculty</th>
<th BGCOLOR=#EEEEEE >Capacity</th>
<th BGCOLOR=#EEEEEE >Credits</th>
<th BGCOLOR=#EEEEEE >Days</th>
</tr>
"  
             
               (apply str (for [[index2 {courseName :course-number courseSection :section-number backgroundColor :color}]
                                (map #(do [%1 %2]) (range (count (nth schedules index))) (addColorsToCourses (nth schedules index))) 
                                  
                                ] 
                       
                   
                  
                 (str "<tr> <td>" (inc index2) "</td>" "<td " (str "BGCOLOR=\"" (first backgroundColor) "\"") ">" (name courseName) "</td> <td align=center>" (name courseSection) "</td>
                  <td>"  (get-in zz2 [courseName :name]) "</td>
                  <td>"  (first (get-in zz2 [courseName :sections courseSection :faculty])) "</td>
                  <td>" "-" "</td>
                  <td>" "-" "</td>
                  <td>"  (formated-days {:course-number courseName  :section-number courseSection}) "</td>


                 </tr> ")
                 )
               )
               
       "</table> <br />"
       
                   
       
       
    (createHtmlScheduleTable (nth schedules index))

"<br /> <br /> <br />")
))
    
"</body>
</html>" ))




