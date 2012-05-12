package org.mathpiper.studentschedule;

public class DaysAndTimes {
    
    public static int dayBitPositions[] = new int[] { 64, 32, 16, 8, 4, 2, 1 };
    
    public static String dayIdentifiers[] = new String[] { "M", "T", "W", "R", "F", "S", "U" };

    // MWF = 1010100.
    private int daysPattern = 0;

    // Units are 5 minute time blocks starting at 12:00 AM.
    // The first slot is slot 0.
    private int startSlot;

    // Units are 5 minute time blocks
    private int slotsLength;
    
    
    private int hashCode;
    

    public DaysAndTimes(String days, String startTimeString, String startTimeMeridianString, String endTimeString, String endTimeMeridianString) {
	
	daysPattern = daysToDaysPattern(days);

	setStartTimeAndDuration(startTimeString, startTimeMeridianString,
		endTimeString, endTimeMeridianString);
	
	
	hashCode = combine(combine(daysPattern, startSlot), slotsLength);

    }

    public int daysToDaysPattern(String days) {
	int daysPattern = 0;

	if (days.contains("M")) {
	    daysPattern = daysPattern | Integer.parseInt("1000000", 2);
	}
	if (days.contains("T")) {
	    daysPattern = daysPattern | Integer.parseInt("0100000", 2);
	}
	if (days.contains("W")) {
	    daysPattern = daysPattern | Integer.parseInt("0010000", 2);
	}
	if (days.contains("R")) {
	    daysPattern = daysPattern | Integer.parseInt("0001000", 2);
	}
	if (days.contains("F")) {
	    daysPattern = daysPattern | Integer.parseInt("0000100", 2);
	}

	return daysPattern;

    }// end method.

    
    
    public void setStartTimeAndDuration(String startTimeString,  String startTimeMeridianString,
	    String endTimeString, String endTimeMeridianString) {
	
	
	//Start time.
	String[] startTimeHourAndMinute = startTimeString.split(":");

	int startTimeHour = Integer.parseInt(startTimeHourAndMinute[0]);

	int startTimeMinute = Integer.parseInt(startTimeHourAndMinute[1]);

	startSlot = startTimeHour * 12 /* slots per hour */ + startTimeMinute / 5;

	if (startTimeMeridianString.contains("PM") && startTimeHour != 12) {
	    startSlot = startSlot + 144 /* slots in 1/2 day */;
	}
	
	
	//End time.
	String[] endTimeHourAndMinute = endTimeString.split(":");

	int endTimeHour = Integer.parseInt(endTimeHourAndMinute[0]);

	int endTimeMinute = Integer.parseInt(endTimeHourAndMinute[1]);

	int endSlot = endTimeHour * 12 /* slots per hour */ + endTimeMinute / 5;

	if (endTimeMeridianString.contains("PM") && endTimeHour != 12) {
	    endSlot = endSlot + 144 /* slots in 1/2 day */;
	}
	
	
	slotsLength = endSlot - startSlot;
	

    }

    public boolean daysOverlap(DaysAndTimes other) {
	return ((daysPattern & other.daysPattern) != 0);
    }

    public boolean hoursOverlap(DaysAndTimes other) {
	return (startSlot + slotsLength > other.startSlot)
		&& (other.startSlot + other.slotsLength > startSlot);
    }

    public int getDaysPattern() {
	return daysPattern;
    }
    
    
    public int getStartSlot() {
        return startSlot;
    }

    public int getSlotsLength() {
        return slotsLength;
    }
    
    private static int combine(int a, int b) {
        int ret = 0;
        for (int i = 0; i < 15; i++)
            ret = ret | ((a & (1 << i)) << i) | ((b & (1 << i)) << (i + 1));
        return ret;
    }
    
    
//=============== Printable versions of days and times.
    public String daysString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++)
            if ((daysPattern & dayBitPositions[i]) != 0)
                sb.append(dayIdentifiers[i]);
        return sb.toString();
    }


    public String startTimeString() {
        int min = startSlot * 5;
        int h = min / 60;
        int m = min % 60;
        return (h > 12 ? h - 12 : h) + ":" + (m < 10 ? "0" : "") + m + (h >= 12 ? "p" : "a");
    }


    public String endTimeString() {
        int min = (startSlot + slotsLength) * 5;
        int m = min % 60;
        int h = min / 60;
        return (h > 12 ? h - 12 : h) + ":" + (m < 10 ? "0" : "") + m + (h >= 12 ? "p" : "a");
    }



    // ============================

    @Override
    public boolean equals(Object o) {
	if (o == null || !(o instanceof DaysAndTimes))
	    return false;
	DaysAndTimes other = (DaysAndTimes) o;
	if (startSlot != other.startSlot)
	    return false;
	if (slotsLength != other.slotsLength)
	    return false;
	if (daysPattern != other.daysPattern)
	    return false;
	return true;
    }//end method
    
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    
    
    


    @Override
    public String toString()
    {
	return daysString() + " " + startTimeString() + " - " + endTimeString();
    }
    
    

}
