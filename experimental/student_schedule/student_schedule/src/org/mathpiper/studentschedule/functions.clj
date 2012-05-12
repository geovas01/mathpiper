


(defn query [day schedule ]
(every?
  (fn [class] (every? 
                (fn [timecode]
                  (= (timecode 0) day) ) (class 2)) ) schedule))
(filter #( query  %) legalSchedules)