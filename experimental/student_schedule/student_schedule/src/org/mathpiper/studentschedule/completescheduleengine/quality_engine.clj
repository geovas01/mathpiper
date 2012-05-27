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



;gives signed number which is the diffrence bettween the opitmum time and the start time, middle time, end time or any
;time that is within the duration of the timecode
(defn distancefromoptimum [timecode optimumtimecode [measuremethod1 measuremethod2]]
  ( if (and (>= measuremethod1 0) (<=  measuremethod1 1) (>= measuremethod2 0) (<=  measuremethod2 1)) 
        
          (let  [[daycode1 starttime1 duration1] timecode
                 [daycode2 starttime2 duration2] optimumtimecode
                 ] (if (not (= (bit-and daycode1 daycode2) 0)) (- (+ starttime1 (* duration1 measuremethod1)) (+ starttime2 (* duration2 measuremethod2)) 0.0) 0.0) ) ))




(defn linearweight [diff option] (- 1 (/ (Math/abs diff) 288)))



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

(defn time-of-day-ratio-corrected [schedule timeofday weight course-map]
  (let [ timeofdaycode (cond (= :morning timeofday) [127 0 144]
         (= :afternoon timeofday) [127 144 60]
         (= :evening timeofday) [127 204 84]
         )
        scheduletimecodes (reduce concat (map (fn
                                                [{course-number :course-number section-number :section-number}]
                                                (get-in course-map [course-number :sections section-number :days-and-times]) ) schedule))
        overlaprate (overlapingratio [timeofdaycode] scheduletimecodes)
        spread (spread-from-optimum-ratio timeofdaycode scheduletimecodes weight)
        ]
    (/ (+ overlaprate spread) ( + 1 weight))
    
    
    )
)



(defn sort-by-time [schedules time-of-day weight course-map]
  (reduce (fn [schedule-1 schedule-2] 
            (if (> (time-of-day-ratio-corrected schedule-1 time-of-day weight course-map)
                   (time-of-day-ratio-corrected schedule-2 time-of-day weight course-map)) schedule-1 schedule-2  )) schedules)
  
  )