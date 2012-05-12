
;;Determine possible course combinations. [:ENGL1101]
(def courses [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

(def possibleCourses [[]])
(def possibleCoursesRevised [])


(doseq [courseList  courses]
  (do
    
    (doseq [patt possibleCourses]

        (doseq [course courseList]
              
            (def possibleCoursesRevised (conj possibleCoursesRevised (conj patt course)))
        ) 
    )
    
    (def possibleCourses possibleCoursesRevised)
    
    (def possibleCoursesRevised [])
    
    )
)



;;Determine l0.
(def l0 [])
(def l1 [[]])
(def l2 [])

(doseq [courseList possibleCourses]


    (doseq [courseNumber courseList]
     (do 
        (doseq [pat l1]
         
            (if (zz courseNumber)
            
                (doseq [section (zz courseNumber)]
                   (def l2 (conj l2 (conj pat (vec (cons courseNumber section)))))
                )
            )
        )
        (def l1 l2)
        (def l2 [])
        )
    )
    
    (def l0 (concat l0 l1))
    (def l1 [[]])
)




;;Legal schedules.
(def timeSlots [])
(def legalSchedules [])
(def illegalSchedules [])

(doseq [schedule l0]

(do 
    (doseq [section schedule]
    
        (doseq [timeSlot (section 2)]
        
            (def timeSlots (conj timeSlots timeSlot))   
        )
    )
    

    (def index1 0)
    
    (while (< index1 (- (count timeSlots) 1))
    (do
      
        (def index2 (+ index1 1))
        
        (while (< index2 (count timeSlots ))
          
        (do
            (if (and 
                  (not= (bit-and ((timeSlots index1) 0) ((timeSlots index2) 0) ) 0)
                  (> (+ ((timeSlots index1) 1) ((timeSlots index1) 2)) ((timeSlots index2) 1))
                  (> (+ ((timeSlots index2) 1) ((timeSlots index2) 2)) ((timeSlots index1) 1)))
            (do
             
                (def illegalSchedules (conj illegalSchedules schedule))
                
                ;Break out of loops.
                (def index2 (+ (count timeSlots) 1));
                (def index1 (count timeSlots))
            ))
            
            (def index2 (inc index2))
           
        ))

        
        (def index1 (inc index1))
    ))
    
    
    
    (def timeSlots [])
    
    )
    )


(def legalSchedules (remove (set illegalSchedules) l0))


;This code causes a stack overflow.
;(def legalSchedules l0)
;(doseq [illegal illegalSchedules]
;    (def legalSchedules (remove #(= illegal %) legalSchedules )))




;;Prepares student schedule.

;Change section format so each section has only one time code.
(def scheduleA (nth legalSchedules 0))

(def schedulesTimeSlots [])
  
(doseq [section scheduleA]
  
    (doseq [sectionTime (section 2)]
    
        (def schedulesTimeSlots (conj  schedulesTimeSlots [(section 0) (section 1) sectionTime]))
    )
)



;Orders previously formatted single timecode sections into days of a week.
(def week [])
(doseq [daybit [64 32 16 8 4 2 1]]
(do 
    (def day [])
    
    (doseq [section schedulesTimeSlots]
      (if (not= (bit-and ((section 2) 0) daybit) 0) 
        (do
            (def day (conj day section))
            )
      ))
    
    

     (def day (vec (sort (fn [x y] (< ((x 2) 1) ((y 2) 1))) day) ))
    
     (def week (conj week day))
  )
    
)
    

;TableForm(week);





;;Add open time blocks.
(def weekComplete [])

(doseq [day week]
(do   

  ;(vec (cons courseNumber section))
    (def dayEnd (conj day [:end :x [0,289,0]] ))
  
  
  
    (def dayOpen [])
    
    (def indexTime 0)
    (def index 0)
    (while (< indexTime 288)
    (do   
        (def nextTime (((dayEnd index) 2) 1))
        (def timeInc (((dayEnd index) 2 ) 2))
        
      

        (if (not= indexTime nextTime) (def dayOpen (conj dayOpen [:Open :x [0 indexTime (- nextTime indexTime)]])))
       
        (def indexTime (+ nextTime timeInc))
 
        (def index (inc index))
    ))

    (def dayComplete (vec (sort (fn [x,y] (< ((x 2) 1) ((y 2) 1))) (vec (concat day dayOpen)))))

    (def weekComplete (conj weekComplete dayComplete))

))
;weekComplete




;;Generate html.

(def htmlRows "")


(def row 0)

(while (< row 288)
(do
    (def htmlTR (str "<tr> <th nowrap>" row "</th>"))
    
    (def htmlTD "")
    (doseq [day weekComplete]
    
   ;(println "B " row)  
        (doseq [section day]
        (do
          ;(println "C " row) 
            (def courseNumber (section 0))
            (def sectionNumber (section 1))
            
            (def daysAndTime (section 2))
            

                (def daysCode (daysAndTime 0))

                ;(println "D " daysAndTime (daysAndTime 1) row) 
                
                (if (= (daysAndTime 1) row)
                (do
                   
                  (def htmlTD (str htmlTD "<td align=center rowspan=" (daysAndTime 2) " >" (name courseNumber) " " (name sectionNumber) "</td>"))
                   ;(println "E " row htmlTD)
                  )
                )

 
            
        
        )))
            
   ;(println "G " row) 
    
    (def htmlTR (str htmlTR htmlTD "</tr>" "\n" ))
    
    (def htmlRows (str htmlRows htmlTR))

    (def row (inc row))
))


(def htmlTop "
<html>
<body>
<table border=1 width=100%>
<tr>
<td nowrap></td>
<th width=16%>Monday</th>
<th width=16%>Tuesday</th>
<th width=16%>Wednesday</th>
<th width=16%>Thursday</th>
<th width=16%>Friday</th>
</tr>
")



(def html (str htmlTop htmlRows 
"</table>
</body>
</html>"))

"Done"


(spit "/home/tkosan/tmp/student_schedule.html" html)

