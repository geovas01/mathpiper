(ns org.mathpiper.studentschedule.completescheduleengine.html_creation_engine)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)

(use 'org.mathpiper.studentschedule.completescheduleengine.schedule_engine)



(defn formated-days [course course-map]
  (let [{course-number :course-number section-number :section-number} course
        days-and-times (get-in course-map [course-number :sections section-number :days-and-times])
        all-days-bin (reduce #(bit-or %1 %2) (map (fn [day-and-time] (nth day-and-time 0) )  days-and-times) )]
    (str (if (not= (bit-and 2r1000000 all-days-bin) 0) "M" "-")
         (if (not= (bit-and 2r0100000 all-days-bin) 0) "T" "-")
         (if (not= (bit-and 2r0010000 all-days-bin) 0) "W" "-")
         (if (not= (bit-and 2r0001000 all-days-bin) 0) "R" "-")
         (if (not= (bit-and 2r0000100 all-days-bin) 0) "F" "-")
         
         ) 
    ))



(defn formated-daycode [day-code]
  
    (str (if (not= (bit-and 2r1000000 day-code) 0) "M" "-")
         (if (not= (bit-and 2r0100000 day-code) 0) "T" "-")
         (if (not= (bit-and 2r0010000 day-code) 0) "W" "-")
         (if (not= (bit-and 2r0001000 day-code) 0) "R" "-")
         (if (not= (bit-and 2r0000100 day-code) 0) "F" "-")
         
         ) 
    )



(defn addColorsToCourses [schedule]
  ( vec(map  #(assoc (nth schedule %2) :color %1 )   (cycle [
 ["#99CCFF" "Blue(Light)"]
 ["#F6F6CC" "Beige"]
 ["#FF6F60" "Coral"]
 ["#C9C9F3" "Quartz"]
 ["#9FCC9F" "Green(Pale)"]
 ["#CCCC60" "Goldenrod"]])
        
        (range (count schedule))) ))
  
  


(defn time-in-blocks-to-time
  [timeinblock]
  (let [hour (int  (Math/floor (/ timeinblock 12)) )
        minute (mod (* timeinblock 5) 60)
       ]
  (cond
    (= hour 0) "12:00PM"
    (>= hour 13) (str (- hour 12) ":" (if (< minute 10) (str "0" minute) minute) "PM" )
    (<  hour 13) (str (if (= hour 0) (str "12" ) hour) ":" (if (< minute 10) (str "0" minute) minute) (if (= hour 12) "PM" "AM")  )
    )  )
)



(defn unbundleDaysAndTimes [schedule-colored course-map]
  (vec (for [{courseName :course-number courseSection :section-number courseColor :color} schedule-colored,
             dayAndTime (get-in course-map [courseName :sections courseSection :days-and-times])
            ]
         
         [courseName courseSection dayAndTime courseColor])))
  
  
  
  (defn orderIntoDays [schedule course-map]
  (let [unbundled (unbundleDaysAndTimes (addColorsToCourses schedule) course-map)]
        
    (vec (for [daybit [64 32 16 8 4 2 1]]
        
      (vec (sort 
             (fn [[course1Name course1Section day1Time] [course2Name course2Section day2Time]] 
               (< (day1Time 1) (day2Time 1))) 
             (filter (fn [[courseName courseSection dayTime]]                                  
                       (not= (bit-and (dayTime 0) daybit) 0) ) unbundled)) )

        ))))

  
  
  
  (defn addOpenTimeBlocks [schedule course-map]
  

    (let [week (orderIntoDays schedule course-map)]
    
    (vec (for [day week]
      (do
        
        (let [dayEnd2 (conj day [:end :x [0,288,0]]) dayOpenFinal       
      (loop [index 0
             indexTime 0
             dayOpen []]        
        (let [
              nextTime (((dayEnd2 index) 2 ) 1)
              timeInc (((dayEnd2 index) 2 ) 2)
              ]
          
        
        (if (>= (+ nextTime timeInc) 288)
          (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)] ["#FFFFFF" "White"]])
          (recur (inc index) (+ nextTime timeInc) 
             (if (not= indexTime nextTime) (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)] ["#FFFFFF" "White"]]) ["HHH"]);todo;tk;this if function needs to be studied further.
            )            
         )
        )
        )
      ]
       
      (vec (sort (fn [x,y] (< ((x 2) 1) ((y 2) 1))) (vec (concat day dayOpenFinal))))      
      )       
       )    
      ))))
  
  
  
(defn createHtmlScheduleTable [schedule course-map] 
  
  (let [days (addOpenTimeBlocks schedule course-map) accumulator [] 
        earliest (reduce min (map (fn [class] (let [[courseName courseSection dayAndTime courseColor]  class, [dayCode startTime duration] dayAndTime] startTime)) (unbundleDaysAndTimes schedule course-map)))
        latest   (reduce max (map (fn [class] (let [[courseName courseSection dayAndTime courseColor]  class, [dayCode startTime duration] dayAndTime] (+ startTime duration))) (unbundleDaysAndTimes schedule course-map))) 
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



  
  
  
(defn createHtmlScheduleTables [schedules course-map]

  
  
(str 
;"<html>
;<head>
;<title>Student schedule</title>
;</head>
;<body>"
  
   

   (apply str (for  [index (range (count schedules)) ]
     
       
                
     (str 
       
       "<h2 align=\"center\"> Schedule " (inc index) "</h2> \n" 
       
"
<table border=1  cellpadding=3 align=\"center\">
<tr>
<td BGCOLOR=#EEEEEE align=center nowrap><b>#</b></td>
<th BGCOLOR=#EEEEEE >Course</th>
<th BGCOLOR=#EEEEEE >Section</th>
<th BGCOLOR=#EEEEEE >Name</th>
<th BGCOLOR=#EEEEEE >Faculty</th>
<th BGCOLOR=#EEEEEE >Credits</th>
<th BGCOLOR=#EEEEEE >Days</th>
</tr>
"  
             
               (apply str (for [[index2 {courseName :course-number courseSection :section-number backgroundColor :color}]
                                (map #(do [%1 %2]) (range (count (nth schedules index))) (addColorsToCourses (nth schedules index))) 
                                  
                                ] 
                       
                   
                  
                 (str "<tr> <td>" (inc index2) "</td>" "<td " (str "BGCOLOR=\"" (first backgroundColor) "\"") ">" (name courseName) "</td> <td align=center>" (name courseSection) "</td>
                  <td>"  (get-in course-map [courseName :name]) "</td>
                  <td>"  (first (get-in course-map [courseName :sections courseSection :faculty])) "</td>
                  <td align=\"center\" >"  (get-in course-map [courseName :credit-hours]) "</td>
                  <td>"  (formated-days {:course-number courseName  :section-number courseSection} course-map) "</td>


                 </tr> ")
                 )
               )
               
       "</table> <br />"
       
                   
       
       
    (createHtmlScheduleTable (nth schedules index) course-map)

;"<br /> <br /> <br />"
"|")
))
    
;"</body>
;</html>" 
))





(defn create-html-sections-table [course-list course-map]

  
  
(str 
"<html>
<head>
<title>Section listings</title>
</head>
<body>"

             
               (apply str (for [course-number course-list]
                            (str
                               "
                               <table border=1  cellpadding=3 align=\"top\">
                               <tr>
                               <th BGCOLOR=#EEEEEE >Course</th>
                               <th BGCOLOR=#EEEEEE >Name</th>
                               <th BGCOLOR=#EEEEEE >Section</th>
                               <th BGCOLOR=#EEEEEE >Faculty</th>
                               <th BGCOLOR=#EEEEEE >Capacity</th>
                               <th BGCOLOR=#EEEEEE >Credits</th>
                               <th BGCOLOR=#EEEEEE >Times</th> 
                               <th BGCOLOR=#EEEEEE >Days</th>
                               </tr>
                               "  
                              
                              
                              
                              
                              "<tr>  <td align=\"top\" rowspan=" (count (keys (get-in course-map [course-number :sections])))  ">" (name course-number) "</td>" 
                               "<td align=\"top\" rowspan=" (count (keys (get-in course-map [course-number :sections])))  "> "  (get-in course-map [course-number :name]) "</td>"
                                 (apply str (for [section-number 
                                                  (sort #(< (Integer/parseInt (name %1)) (Integer/parseInt (name %2))) (keys (get-in zz2 [course-number :sections])))] 
                   
                  
                 (str (if (not= section-number (first (sort #(< (Integer/parseInt (name %1)) (Integer/parseInt (name %2))) (keys (get-in zz2 [course-number :sections]))) ))
                        "<tr>")  " <td align=center>" (name section-number) "</td>
                  
                  <td>"  (first (get-in course-map [course-number :sections section-number :faculty])) "</td>
                  <td>" "-" "</td>
                  <td>" "-" "</td>
                  <td>" (apply str (for [[day-code start-time duration] (get-in course-map [course-number :sections section-number :days-and-times])]
                                     (str (time-in-blocks-to-time start-time) "-" (time-in-blocks-to-time (+ start-time duration)) " " (formated-daycode day-code) "\n") )) "</td>
                  <td>"  (formated-days {:course-number course-number  :section-number section-number} course-map) "</td>


                 </tr> ")
                 ))
                   
                  "</table>  <br />"
                  #_"<br /> <br /> <br />"
                                 
                                 
                                 )
                 )
               )
   
"</body>
</html>" ))




#_(def course-lists #_[[:ETCO1120] [:ETEM1110]] [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

#_(spit "../student_schedule.html" (create-html-sections-table (apply concat course-lists) zz2))

#_(create-html-sections-table [:MATH1010 :ENGL1101] zz2)