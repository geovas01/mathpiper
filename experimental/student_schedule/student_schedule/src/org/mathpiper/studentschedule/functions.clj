


(defn query [day schedule ]
(every?
  (fn [class] (every? 
                (fn [timecode]
                  (= (timecode 0) day) ) (class 2)) ) schedule))
(filter #( query 2r0101000 %) legalSchedules)

;(defn query2 [day schedule ]
;(some?
;  (fn [class] (every? 
;                (fn [timecode]
;                  (= (timecode 0) day) ) (class 2)) ) schedule))
;

(filter #( query2 2r0101000) legalSchedules)

(defn overlap [timecode1 timecode2]
  (let [[daycode1 starttime1 duration1] timecode1
        [daycode2 starttime2 duration2] timecode2
        endtime1 (+ starttime1 duration1) 
        endtime2 (+ starttime2 duration2)]
    (if (and (not (= (bit-and daycode1 daycode2) 0)) (> endtime2 starttime1) (>  endtime1 starttime2))
      (let 
        [
        highesttime (reduce max [starttime1 starttime2 endtime1 endtime2])
        lowesttime (reduce min [starttime1 starttime2 endtime1 endtime2])
        range (- highesttime lowesttime)
        startdiff (Math/abs (- starttime1 starttime2))
        enddiff (Math/abs (- endtime1 endtime2))
        overlaps (- range (+ startdiff enddiff))]       
       overlaps)
      0))
)


;;possibleCourses

(defn countSectionPatterns [possible]
  (apply + (map (fn [courselist]
         (apply * (map 
           #(count (% fall2012Schedule)) courselist) )) possible)) 
)

(defn overlapingratio [timecodes1 timecodes2]
  (/ (apply + (map 
   (fn [timecode1] (apply + (map (fn
                                   [timecode2] (overlap timecode1 timecode2)) timecodes2))) 
    timecodes1))
     
     (apply + 0.0 (map 
                (fn
                  [timecode2] (let [ [daycode2 starttime2 duration2] timecode2 ] duration2) ) timecodes2))
     ) 
)


(defn timeofdayratio [schedule timeofday]
  (let [ timeofdaycode (cond (= :morning timeofday) [127 0 144]
         (= :afternoon timeofday) [127 144 60]
         (= :evening timeofday) [127 204 84]
         )
        scheduletimecodes (reduce concat (map #(% 2) schedule))
        ]
   (overlapingratio [timeofdaycode] scheduletimecodes) )
)

(defn sortbytime [schedules timeofday]
  (sort (fn [schedule1 schedule2]
          (> (timeofdayratio schedule1 timeofday) (timeofdayratio schedule2 timeofday))
          ) schedules))

;;(def zzz (sortbytime legalSchedules :morning))
;;(def zzz2(sortbytime legalSchedules :afternoon))
;;(def zzz3(sortbytime legalSchedules :evening))
;;(/ (apply + (map #(timeofdayratio % :morning) legalSchedules)) (count legalSchedules))
;;(/ (apply + (map #(timeofdayratio % :afternoon) legalSchedules)) (count legalSchedules))
;;(/ (apply + (map #(timeofdayratio % :evening) legalSchedules)) (count legalSchedules))


