(ns org.mathpiper.studentschedule.completescheduleengine.quality_engine)

;The quality_engine nuamspace contains all the functions needed to evaluate a given schedules or schedules for their quality 
;compared to ideal or optimum parameters.

;Note: all functions that have 'ratio' in their names return numbers from 0 to 1 that are ment to represent the quality of the schedule
;that has been inputed compared to the optimum.

(use 'org.mathpiper.studentschedule.ssu_spring_2013_semester_schedule_map)



(defn days-in 
"     The days-in function takes a vector in the form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

The function returns the total number of days that the timecode is on.

"
  [day-code]
 (apply + (map #(if (not= (bit-and day-code %) 0) 1 0)  [2r1000000 2r0100000 2r0010000 2r0001000 2r0000100 2r0000010 2r0000001])))


;overlap takes two timecodes and finds their overlap
(defn overlap 
 "     The overlap function takes two vectors in the form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

The function returns an integer number of the five minute time blocks during the whole week that the two timecodes have in commen.

" 
  
  [[daycode1 starttime1 duration1] [daycode2 starttime2 duration2]]
  (let [endtime1 (+ starttime1 duration1) 
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
(defn overlapingratio 
"
     The overlapingratio function takes two vectors of vectors in the form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

The function returns the number of overlaping five minute time-blocks that are shared between timecodes1 and timecodes2 diveded by 
the total number of five minute time-blocks in timecodes2.

"
  [timecodes1 timecodes2]
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
(defn distancefromoptimum
"
     The distancefromoptimum function takes the arguments timecode and optimumtimecode are vectors in the form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

The third argument is in the form:
[measuremethod1 measuremethod2]

where measuremethod1 and measuremethod2 are numbers from zero to one.
measuremethod1 tells the function where in timecode the first measure point should be, where 0 would mean measure from the start time, 0.5 would mean
measure from the middle, 1 would mean measure from the end and so on.
measuremethod2 tells the function where in optimumtimecode the second measure point should be, where 0 would mean measure from the
start time, 0.5 would mean measure from the middle, 1 would mean measure from the end and so on.

The function returns the distance from the first measure point to the second measure point.

"
  
  [timecode optimumtimecode [measuremethod1 measuremethod2]]
  ( if (and (>= measuremethod1 0) (<=  measuremethod1 1) (>= measuremethod2 0) (<=  measuremethod2 1)) 
        
          (let  [[daycode1 starttime1 duration1] timecode
                 [daycode2 starttime2 duration2] optimumtimecode
                 ] (if (not (= (bit-and daycode1 daycode2) 0)) (- (+ starttime1 (* duration1 measuremethod1)) (+ starttime2 (* duration2 measuremethod2)) 0.0) 0.0) ) ))




(defn linearweight 
"     The function llnearweight is ment to be used as a weighting function for the distancefromoptimumratio  function. The function takes
diff as non-negitive integer which is ment to represent the difference bettween the chosen time and the actual time, where both times
are in five minute blocks. Note that the option parameter is not used in this function. The function returns a number from 0 to 1, 1 if 
diff is 0 and 0 if diff is 288. The function's return value linealy drops off from 1 to 0 as diff increases from 0 to 288. 

"
  [diff option] (- 1 (/ (Math/abs diff) 288)))




(defn distancefromoptimumratio
"     The function distancefromoptimumratio takes two timecodes. The first is the proposed timecode, and the second is the desired
timecode.

The parameter measuremethod is a vector with two numbers from zero to one. The first number tells the function where in timecode the 
first measure point should be, where 0 would mean measure from the start time, 0.5 would meanmeasure from the middle, and 1 would mean
measure from the end and so on. The second number tells the function where in optimumtimecode the second measure point should be,
where 0 would mean measure from thestart time, 0.5 would mean measure from the middle, 1 would mean measure from the end and so on.

The weightfn parameter is a weight function that is 1 when distancefromoptimum, if distancefromoptimum were given timecode and
optimumtimecode from distancefromoptimumratio, is 0 and 0 when distancefromoptimum is +/-288. The weight
function must be decreasing in the +/- 288 directions.

The option parameter is an optional input for the weighting function.

The function distancefromoptimumratio returns a number from 0 to 1. If the diffrence bettween the first measure point and the second 
measure point is 0 the function will return 1, if the diffrence is 288 the function will return 0. if the diffrence is bettween 0 and 288
output will depend on weightfn and option but is will be bettween 0 and 1.
"
  
  [timecode optimumtimecode measuremethod weightfn option]
  (weightfn (distancefromoptimum timecode optimumtimecode measuremethod) option)  )



(defn distance-from-optimum-linear-ratio 
"     The function distancefromoptimumratio takes two timecodes in the following form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

     The first timecode is the proposed timecode, and the second is the desired timecode. The function measures the distance bettween the
timecodes from the middle of the timecodes. The function measures the diffrence in five-minute time blocks. The function returns a
number from 0 to 1. If the diffrence bettween the first measure point and the second measure point is 0 the function will return 1, if
the diffrence is 288 the function will return 0. if the diffrence is bettween 0 and 288, the function will linearly drop from 1 to 0.


"
  
  [timecode optimumtimecode]
  (distancefromoptimumratio timecode optimumtimecode [0.5 0.5] linearweight nil))




(defn spread-from-optimum-ratio
"     The spread-from-optimum-ratio function takes an optimum timecode and a vector of timecodes, optimum-timecode and time-codes
respectfully. Where the timecodes are in the form:
[daycode start-time duration]

where daycode, start-time and duration are positive integers. 
daycode is in the form of a binary number of sevan bits, where each bit detimremines if the timecode is on that day.
start-time is the number of five-minute blocks the start time is from midnight.
duration is the number of five-minute blocks that are in the period of the timecode.

     The function uses distance-from-optimum-linear-ratio on each of the timecodes in time-codes, using optimum-timecode as the optimum
timecode input for distance-from-optimum-linear-ratio. The function spread-from-optimum-ratio will then take a weighted average of the 
outputs from istance-from-optimum-linear-ratio, using total time during the week (in five-minute time blocks) for each timecode's weight.
The function returns this number multiplied by the weight parameter, which is a positive floating point number.
"  
  [optimum-timecode time-codes weight]
  (* 1.0 weight (/ (apply + (map #(* ( distance-from-optimum-linear-ratio % optimum-timecode) (* (nth % 2) (days-in (nth % 0)))) time-codes))
                   (apply + (map #(* (nth % 2) (days-in (nth % 0))) time-codes))))
  
  
  )

(defn time-of-day-ratio-corrected
"     The function time-of-day-ratio-corrected takes the schedule parameter is a vector that holds each course and section number
 in the form of a map like:
[{:course-number :ENGL1101 :section-number :01} {:course-number :MATH1300 :section-number :51}]

The timeofday parameter can take the following three legal values, in the form of keys:

:morning 
:afternoon
:evening.

Each key is associated with a timecode for which the function will base its quality number. The timecodes that are associated with the
keys are as follows:

:morning    [127 0 144]
:afternoon  [127 144 60]
:evening    [127 204 84]

To get the final output number the function uses the overlapingratio and spread-from-optimum-ratio functions on all
timecodes in the schedule to produce a number from 0 to 1 that reflects the quality, how closely the schedule's timecodes are to the
optimum timecode chosen using the timeofday parameter. The weight parameter is multiplied by the output of the
spread-from-optimum-ratio function to determine how much of an effect that function's output has on the overall number.

The course-map parameter is in the standard course map format. 
"  
  
  [schedule timeofday weight course-map]
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

(defn minimize-days
  "     The function minimize-days takes the schedule parameter is a vector that holds each course and section number
 in the form of a map like:
[{:course-number :ENGL1101 :section-number :01} {:course-number :MATH1300 :section-number :51}]

The course-map parameter is in the standard course map format. 

The function returns a number from 0 to 1. The functions finds total number of days that the schedule is has timecodes on and divides by
7. The function then subtracts this number from 1 and putputs the result.

"
  
  
  [schedule course-map]
  (let [timecodes (apply concat (for [{course-number :course-number section-number :section-number} schedule] (get-in course-map [course-number :sections section-number :days-and-times]) ))
        class-on-days  (reduce (fn [daycode-1 daycode-2] (bit-or daycode-1 daycode-2)) (map (fn [[daycode start-time duraiton]] daycode ) timecodes))
        number-of-days (apply + (map #(if (not= (bit-and % class-on-days) 0) 1 0) '(2r1000000 2r0100000 2r0010000 2r0001000 2r0000100 2r0000010 2r0000001)))
        ] 
    #_(println  class-on-days)
    (- 1 (/ number-of-days 7))
    
    )
 
  )



(defn choose-days
"     The function choose-days takes the schedule parameter as a vector that holds each course and section number
 in the form of many maps like:
[{:course-number :ENGL1101 :section-number :01} {:course-number :MATH1300 :section-number :51}]

The daycode parameter is in the form of a binary number of sevan bits, where each bit is a day, if the function will count those
given days as more desirable.

The course-map parameter is in the standard course map format. 

The function returns a number from 0 to 1. It finds the number of days in schedule that do not fall on the days in daycode, divides this
number by 7 and substracts that number from 1 then outputs the result.


"  
  
  [schedule chosen-daycode course-map]
  (let [timecodes (apply concat (for [{course-number :course-number section-number :section-number} schedule] (get-in course-map [course-number :sections section-number :days-and-times]) ))
        class-on-days  (reduce (fn [daycode-1 daycode-2] (bit-or daycode-1 daycode-2)) (map (fn [[daycode start-time duraiton]] daycode ) timecodes))
        bad-days (bit-and class-on-days (bit-not chosen-daycode))
        number-of-bad-days (apply + (map #(if (not= (bit-and % bad-days) 0) 1 0) '(2r1000000 2r0100000 2r0010000 2r0001000 2r0000100 2r0000010 2r0000001)))
        ]
    
    (- 1 (/ number-of-bad-days 7))
  )
  )



(defn complete-quality
"The function complete-quality takes the schedule parameter as a vector that holds each course and section number
 in the form of many maps like:
[{:course-number :ENGL1101 :section-number :01} {:course-number :MATH1300 :section-number :51}]

The course-map parameter is in the standard course map format.

The function uses the quality-fn-and-vals to find the the quality number of the given schedule. The quality-fn-and-vals parameter is a
vector of vectors. Each vector in quality-fn-and-vals contains a string, which is the name of a quality function, and a vector, which
contains parameters for that function. The parameters do not include the first parameter, the schedule, or the last parameter, the
course map. These parameters will be added to the function call using the parameters passed to complete-quality. An example of a legal
quality-fn-and-vals parameter would be:

[[\"time-of-day-ratio-corrected\" [:morning 1]] [\"choose-days\" [2r1010100]] [\"minimize-days\" []]] 

The order in which the functions in quality-fn-and-vals are placed determines the weighting for each function. Each function is
evalulated using the given parameters. The outputs are then avraged using a weights that decrese hyperbolicly as follows: The first 
output from the first function in quality-fn-and-vals will have weight 1/1, the second 1/2, the third 1/3, and, in general, the nth 
function output will have weight 1/n. This average is returned. 

"  

  [schedule quality-fn-and-vals course-map] 
  (let [range-denom (range (count quality-fn-and-vals)) 
        denoms (map #(/ 1.0 (inc %)) range-denom)
        output (apply + (map #(* (apply (ns-resolve 'org.mathpiper.studentschedule.completescheduleengine.quality_engine (symbol (first %))) (concat [schedule] (second %) [course-map]) ) %2)  quality-fn-and-vals denoms))
        ]
    
    (/ output (apply + denoms))
    )
  
  )




#_(def course-list [[:ETCO1120] [:ETEM1110] [:MATH1300] [:ENGL1101] [:ARTH1101 :ENGL2275 :MUSI1201 :MUSI2211 :PHIL3300 :THAR1000]])


#_(complete-quality (first (legal-schedules course-list zz2))
                    [[time-of-day-ratio-corrected [:morning 1]]] zz2)

#_(time-of-day-ratio-corrected (first (legal-schedules course-list zz2)) :morning 1 zz2 )


(defn selected-by-quality [schedules quality-fn-and-vals allowence course-map]

  (let [list-of-pairs  (map #( list (complete-quality % quality-fn-and-vals course-map) %)   schedules)
        best  (reduce max (map first list-of-pairs)) 
        list-of-best (filter #(>= (first %) (- best allowence)) list-of-pairs)
        ]
    (for [pair list-of-best] (second pair) )

)
  
  
)


(defn sort-by-quality [schedules quality-fn-and-vals course-map]
  (sort (fn [schedule-1 schedule-2] 
              (< (complete-quality schedule-1 quality-fn-and-vals course-map)
                   (complete-quality schedule-2 quality-fn-and-vals course-map))  ) schedules)
  
  )




#_(


(defn sort-by-time [schedules time-of-day weight course-map]
  (reduce (fn [schedule-1 schedule-2] 
             (if (> (time-of-day-ratio-corrected schedule-1 time-of-day weight course-map)
                   (time-of-day-ratio-corrected schedule-2 time-of-day weight course-map)) schedule-1 schedule-2  )) schedules)
  
  )


(defn sort-by-time-2 [schedules time-of-day weight allowence course-map]

  (let [best (reduce max
          (map (fn [schedule]
                 (time-of-day-ratio-corrected schedule time-of-day weight course-map))
               schedules))]



    (filter #(>=  (time-of-day-ratio-corrected % time-of-day weight course-map)
    (- best allowence))
    
    schedules)

)
  
  
)




(defn sort-by-time-3 [schedules time-of-day weight allowence course-map]

  (let [list-of-pairs  (map #(list (time-of-day-ratio-corrected % time-of-day weight course-map) %)   schedules)
        best  (reduce max (map first list-of-pairs)) 
        list-of-best (filter #(>= (first %) (- best allowence)) list-of-pairs)
        ]
    (for [pair list-of-best] (second pair) )

)
  
  
)

)
#_(defn sort-by-time-4 [schedules time-of-day weight allowence course-map]

  (let [list-of-pairs (pmap #(list (time-of-day-ratio-corrected % time-of-day weight course-map) %)   schedules)
        
        best 
        list-of-best (filter #(>= (first %) (- best allowence)) list-of-pairs)
        ]
    (for [pair list-of-best] (second pair) )

)  
)