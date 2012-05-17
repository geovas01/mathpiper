

;;Add associate a color with each course.
(defn addColorsToCourses [schedule]
  (map conj schedule (cycle [["#99CCFF" "Blue(Light)"]
 ["#F6F6CC" "Beige"]
 ["#9FCC9F" "Green(Pale)"]
 ["#C9C9F3" "Quartz"]
 ["#FF6F60" "Coral"]
 ["#CCCC60" "Goldenrod"]])))
  


(defn time-in-blocks-to-time
  [timeinblock]
  (let [hour (int  (Math/floor (/ timeinblock 12)) )
        minute (mod (* timeinblock 5) 60)
       ]
  (cond
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
(defn unbundleDaysAndTimes [schedule]
  (vec (for [[courseName courseSection daysAndTimes courseColor] schedule, dayAndTime daysAndTimes]
  [courseName courseSection dayAndTime courseColor])))
             
             


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







  
 



;;Create one HTML schedule table.
;(spit "../student_schedule.html" (createHtmlScheduleTable (nth legalSchedules 0)))
;
(defn createHtmlScheduleTable [schedule] 
  
  (let [days (addOpenTimeBlocks schedule) accumulator [] 
 ]
  (str
      
"
<table border=1 width=90%>
<tr>
<td BGCOLOR=#EEEEEE align=center nowrap><b>SSU</b></td>
<th BGCOLOR=#EEEEEE width=18%>Monday</th>
<th BGCOLOR=#EEEEEE width=18%>Tuesday</th>
<th BGCOLOR=#EEEEEE width=18%>Wednesday</th>
<th BGCOLOR=#EEEEEE width=18%>Thursday</th>
<th BGCOLOR=#EEEEEE width=18%>Friday</th>
</tr>
"  
      
    (apply str (apply concat (for [row (range 96 288)]
      (apply concat
             
        
        (conj accumulator (str "<tr> " (if (= (mod row 12) 0) (str "<th rowspan=12 BGCOLOR=#EEEEEE nowrap>" (time-in-blocks-to-time row) "</th>"))))
        
        
    (apply str (apply concat (for [ day days, [courseNumber sectionNumber [daysCode startTime duration] backgroundColor] day]
      (apply concat
             
                
        
        (if (= startTime row)
          (do
                  (conj accumulator (str "<td align=center " 
                                         (str "BGCOLOR=\"" (first backgroundColor) "\"") " rowspan=" duration " >" 
                                         (if (= courseNumber :Open) (str "<font color=black>" (name courseNumber) " </font>") 
                                           (name courseNumber)) " " (if (= sectionNumber :x) "" 
                                                                      (name sectionNumber)) " <br />" (time-in-blocks-to-time startTime) "-" (time-in-blocks-to-time (+ startTime duration))  "</td>"))

                  
            )
          )
  
     )
        
  )))    
        
         (conj accumulator (str "</tr>" "\n" ))
        
     )
        
  )))
    

    


 
"</table>
"   
    
    (count legalSchedules)(
    )
    
    
    
    
    )
  
  ))








;;Create multiple HTML schedule tables on one web page.
;(spit "../student_schedule.html" (createHtmlScheduleTables (take 10 legalSchedules)))
;
(defn createHtmlScheduleTables [schedules]
(str 
"<html>
<body>"
  
   

   (apply str (for  [index (range (count schedules)) ]
     (str 
       
     "<hr /> <h2 align=\"center\"> Schedule " (inc index) "</h2> " 
       
    (createHtmlScheduleTable (nth schedules index))

"<br /> <br /> <br />")
))
    
"</body>
</html>" ))




