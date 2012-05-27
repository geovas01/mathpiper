


#_(defn query [day schedule ]
(every?
  (fn [class] (every? 
                (fn [timecode]
                  (= (timecode 0) day) ) (class 2)) ) schedule))
#_(filter #( query 2r0101000 %) legalSchedules)

;(defn query2 [day schedule ]
;(some?
;  (fn [class] (every? 
;                (fn [timecode]
;                  (= (timecode 0) day) ) (class 2)) ) schedule))
;

;(filter #( query2 2r0101000) legalSchedules)

(use 'org.mathpiper.studentschedule.ssu_fall_2012_semester_schedule_map)

(defn days-in [day-code]
 (apply + (map #(if (not= (bit-and day-code %) 0) 1 0)  [2r1000000 2r0100000 2r0010000 2r0001000 2r0000100 2r0000010 2r0000001])))


;overlap takes two timecodes and finds their overlap
(defn overlap [timecode1 timecode2]
  (let [[daycode1 starttime1 duration1] timecode1
        [daycode2 starttime2 duration2] timecode2
        endtime1 (+ starttime1 duration1) 
        endtime2 (+ starttime2 duration2)]
    (if (and (> endtime2 starttime1) (>  endtime1 starttime2))
      (let 
        [
         
        highesttime (reduce max [starttime1 starttime2 endtime1 endtime2])
        lowesttime (reduce min [starttime1 starttime2 endtime1 endtime2])
        range (- highesttime lowesttime)
        startdiff (Math/abs (- starttime1 starttime2))
        enddiff (Math/abs (- endtime1 endtime2))
        overlaps (- range (+ startdiff enddiff))]       
       (* (days-in (bit-and daycode1 daycode2)) overlaps))
      0)) 
)


;;possibleCourses

;countSectionPatterns counts the number of schedules that con be made from
;the given courses
#_(defn countSectionPatterns [possible]
  (apply + (map (fn [courselist]
         (apply * (map 
           #(count (% fall2012Schedule)) courselist) )) possible)) 
)



;overlapingratio takes two sequences of timecodes and finds the ratio of the
;their overlap and the sum of the durations of the secound sequence of timecodes.
(defn overlapingratio [timecodes1 timecodes2]
  (/ (apply + (map 
   (fn [timecode1] (apply + (map (fn
                                   [timecode2] (overlap timecode1 timecode2)) timecodes2))) 
    timecodes1))
     
     (apply + 0.0 (map 
                (fn
                  [timecode2] (let [ [daycode2 starttime2 duration2] timecode2 ] (* (days-in daycode2) duration2)) ) timecodes2))
     ) 
)



;finds the ratio of classes that fall into timeofday (morning afternoon or eveninig)
;and intorpolates ???.
#_(defn timeofdayratio [schedule timeofday]
  (let [ timeofdaycode (cond (= :morning timeofday) [127 0 144]
         (= :afternoon timeofday) [127 144 60]
         (= :evening timeofday) [127 204 84]
         )
        scheduletimecodes (reduce concat (map #(% 2) schedule))
        ]
   (overlapingratio [timeofdaycode] scheduletimecodes) )
)






;takes times in the form of number of five minute blocks from midnight and turns them into 
;normal time/
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

;gives signed number which is the diffrence bettween the opitmum time and the start time, middle time, end time or any
;time that is within the duration of the timecode
(defn distancefromoptimum [timecode optimumtimecode [measuremethod1 measuremethod2]]
  ( if (and (>= measuremethod1 0) (<=  measuremethod1 1) (>= measuremethod2 0) (<=  measuremethod2 1)) 
        
          (let  [[daycode1 starttime1 duration1] timecode
                 [daycode2 starttime2 duration2] optimumtimecode
                 ] (if (not (= (bit-and daycode1 daycode2) 0)) (- (+ starttime1 (* duration1 measuremethod1)) (+ starttime2 (* duration2 measuremethod2)) 0.0) 0.0) ) ))

;weight function for distancefromoptimumratio
(defn linearweight [diff option] (- 1 (/ (Math/abs diff) 288)))

#_(defn quadweight [diff option] (let [
                                     a option
                                     b (- (+ (* a 288) (/ 1 288)))
                                     c 1
                                     output (+ (* a (Math/pow diff 2)) (* b diff) c)
                                     ] output))

;takes a weight function that is 1 when distancefromoptimum is 0 and 0 when distancefromoptimum is +/-288. The weight
;function must be decreasing in the +/- 288 directions.
(defn distancefromoptimumratio [timecode optimumtimecode measuremethod weightfn option]
  (weightfn (distancefromoptimum timecode optimumtimecode measuremethod) option)  )



(defn distance-from-optimum-linear-ratio [timecode optimumtimecode]
  (distancefromoptimumratio timecode optimumtimecode [0.5 0.5] linearweight nil))

(defn spread-from-optimum-ratio [optimum-timecode time-codes weight]
  (* 1.0 weight (/ (apply + (map #(* ( distance-from-optimum-linear-ratio % optimum-timecode) (* (nth % 2) (days-in (nth % 0)))) time-codes))
                   (apply + (map #(* (nth % 2) (days-in (nth % 0))) time-codes))))
  
  
  )


#_(defn distancefromoptimumquadratio [timecode optimumtime]
  (distancefromoptimumratio timecode optimumtime 0 linearweight nil))
  
; test for time-in-blocks-to-time
#_(for [y  (for [x (range 289)]
  (time-in-blocks-to-time x)) ]
  (println y ))
  
  
  

;; this code is not efficent
;;(defn sortbytime [schedules timeofday]
;;  (sort (fn [schedule1 schedule2]
;;          (> (timeofdayratio schedule1 timeofday) (timeofdayratio schedule2 timeofday))
;;          ) schedules))

;;(def zzz (sortbytime legalSchedules :morning))
;;(def zzz2(sortbytime legalSchedules :afternoon))
;;(def zzz3(sortbytime legalSchedules :evening))
;;(/ (apply + (map #(timeofdayratio % :morning) legalSchedules)) (count legalSchedules))
;;(/ (apply + (map #(timeofdayratio % :afternoon) legalSchedules)) (count legalSchedules))
;;(/ (apply + (map #(timeofdayratio % :evening) legalSchedules)) (count legalSchedules))










(defn time-of-day-ratio-corrected [schedule timeofday weight]
  (let [ timeofdaycode (cond (= :morning timeofday) [127 0 144]
         (= :afternoon timeofday) [127 144 60]
         (= :evening timeofday) [127 204 84]
         )
        scheduletimecodes (reduce concat (map (fn
                                                [{course-number :course-number section-number :section-number}]
                                                (get-in zz2 [course-number :sections section-number :days-and-times]) ) schedule))
        overlaprate (overlapingratio [timeofdaycode] scheduletimecodes)
        spread (spread-from-optimum-ratio timeofdaycode scheduletimecodes weight)
        ]
    (/ (+ overlaprate spread) ( + 1 weight))
    
    
    )
)


#_(for [schedule (legal-schedules course-lists)] 
  (time-of-day-ratio-corrected schedule :evening 0.3))

#_((time-of-day-ratio-corrected (nth (legal-schedules course-lists) 0) :evening 0.3))

#_(do (def x 3000) (time-of-day-ratio-corrected (nth legalSchedules x) :evening)
    (spit "../student_schedule.html" (createHtmlScheduleTable (nth legalSchedules x)))
)


#_(defn sortbytime2 [schedules timeofday]
  (sort (fn [schedule1 schedule2]
          (> (time-of-day-ratio-corrected schedule1 timeofday) (time-of-day-ratio-corrected schedule2 timeofday))
          ) schedules))


(defn sortbytime3 [schedules timeofday weight]
  (reduce (fn [schedule1 schedule2] 
            (if (> (time-of-day-ratio-corrected schedule1 timeofday weight) (time-of-day-ratio-corrected schedule2 timeofday weight)) schedule1 schedule2  )) schedules)
  
  )


;test for sortbytime3

#_(def course-lists [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])
#_(spit "../student_schedule.html" (createHtmlScheduleTables   (map #(sortbytime3 (legal-schedules course-lists) :evening (/ % 10.0)) (range 0 20))))

#_(spit "../student_schedule.html" (createHtmlScheduleTables   [[] (sortbytime3 (legal-schedules course-lists) :evening 1.0)] ))

#_(sortbytime3 (legal-schedules course-lists) :afternoon 1)

#_(def course-lists2 [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:THAR1000] #_[:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])

#_(def test-sched [{:course-number :ETCO1120, :section-number :52}
                   {:course-number :ETEM1110, :section-number :51}
                   #_{:course-number :MATH1300, :section-number :04}
                   {:course-number :ENGL1101, :section-number :53} 
                   {:course-number :THAR1000, :section-number :04}]

    
    
    
    #_[{:course-number :ETCO1120, :section-number :52}
                   {:course-number :ETEM1110, :section-number :51}
                   #_{:course-number :MATH1300, :section-number :04} 
                   {:course-number :ENGL1101, :section-number :53}
                   #_{:course-number :MUSI1201, :section-number :02}])

#_(spit "../student_schedule.html" (createHtmlScheduleTables (legal-schedules course-lists2 test-sched)  ))


#_(def test-sched2 [{:course-number :ETCO1120, :section-number :52}
                    {:course-number :ETEM1110, :section-number :51}
                    {:course-number :MATH1300, :section-number :04}
                    {:course-number :ENGL1101, :section-number :18} 
                    {:course-number :THAR1000, :section-number :04}]

    
    
    #_[{:course-number :ETCO1120, :section-number :52}
                    {:course-number :ETEM1110, :section-number :01}
                    {:course-number :MATH1300, :section-number :04}
                    {:course-number :ENGL1101, :section-number :21} 
                    {:course-number :THAR1000, :section-number :04}]

    
    
    #_[{:course-number :ETCO1120, :section-number :52}
                    {:course-number :ETEM1110, :section-number :51}
                    {:course-number :MATH1300, :section-number :04}
                    {:course-number :ENGL1101, :section-number :21}
                    {:course-number :THAR1000, :section-number :04}]

)

#_(spit "../student_schedule.html" (createHtmlScheduleTables (legal-schedules course-lists2 test-sched2)  ))
#_(spit "../student_schedule.html" (createHtmlScheduleTables  [
                                                               (sortbytime3 (legal-schedules course-lists) :morning 1)
] ))


#_(time-of-day-ratio-corrected [{:course-number :ETCO1120, :section-number :52}
                     {:course-number :ETEM1110, :section-number :51}
                     {:course-number :MATH1300, :section-number :04}
                     {:course-number :ENGL1101, :section-number :21}
                     {:course-number :THAR1000, :section-number :04}]  :afternoon 1)

;;(def zzz12 (sortbytime2 legalSchedules :morning))
;;(def zzz22 (sortbytime2 legalSchedules :afternoon))
#_(def zzz32 (sortbytime2 legalSchedules :evening))
#_(spit "../student_schedule.html" (createHtmlScheduleTable (nth zzz32 0)))

#_(do (def zzz33 (sortbytime3 legalSchedules :evening 0.3))
    (spit "../student_schedule.html" (createHtmlScheduleTable zzz33))
)
