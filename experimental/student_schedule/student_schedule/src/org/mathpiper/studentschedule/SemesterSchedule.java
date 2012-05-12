package org.mathpiper.studentschedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SemesterSchedule {
    
    private Map<String, Course> courses = new HashMap<String, Course>();
    
    
    private int lineNumber = 1;
    
   
    
    public int getLineNumber() {
        return lineNumber;
    }


    
    public void loadSchedule(File file) throws Exception {
	if (!file.exists()) {
	    throw new IOException(file.getName() + " does not exist.");

	}

	if (!(file.isFile() && file.canRead())) {
	    throw new IOException(file.getName() + " cannot be read from.");
	}

	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(file);
	    char character = ' ';
	    
	    StringBuilder row = new StringBuilder();
	    
	    //String NL = System.getProperty("line.separator");
	    
	    Course currentCourse = null;
	    
	    String currentCourseNumber = "";
	  
	    while (fis.available() > 0) {
		
		int delimiterCount = 0;
		
		
		while(delimiterCount < 9)
		{
		   character = (char) fis.read();
		   
		   if(character > 127)
		   {
		       character = ' ';
		   }
		   
		   if(character == '\n')
		   {
		       lineNumber++;
		   }
		   
		   row.append(character);
		   
		   if(character == '|')
		   {
		       delimiterCount++;
		   }
		}
		
		while(character != '\n')
		{
		    character = (char) fis.read();
		    
		    if (character > 127) {
			character = ' ';
		    }
		    
	            if(character == '\n')
	            {
			lineNumber++;
	            }
		    
		    row.append(character);
		}
		
		String rowString = row.toString();
		
		Section section = createSection(rowString);
		
		
		
		if(! currentCourseNumber.equals(section.getCourseNumber()))
		{
		    currentCourseNumber = section.getCourseNumber();
		    
		    currentCourse = new Course(currentCourseNumber);
		    
		    courses.put(currentCourseNumber, currentCourse);
		}
		
		currentCourse.addSection(section);
		
		
		
		
		row.delete(0, row.length());

	    }
	    
	} finally  {
	    
	    if(fis != null)
	    {
		fis.close();
	    }
	}
    }//end method
    
    
    private Section createSection(String row) throws Exception
    {
	Section section = new Section();
	
	String[] fields = row.split("\\|");
	
	
	String compoundField = fields[0];
	
	String[] courseNameAndSection = compoundField.split("-");
	
	String courseNumber = courseNameAndSection[0].trim();
	
	section.setCourseNumber(courseNumber);
	
	String courseSection = courseNameAndSection[1].trim();
	
	section.setCourseSection(courseSection);
	
	String courseDescription = fields[1].trim();
	
	section.setCourseDescription(courseDescription);
	
	String instructors = fields[3].trim();
	
	section.setInstructors(instructors);
	
	compoundField = fields[4];
	
	String[] enrolledAndCapacity = compoundField.split("/");
	
	String enrolled = enrolledAndCapacity[0].trim();
	
	section.setEnrolled(Integer.parseInt(enrolled));
	
	String capacity = enrolledAndCapacity[1].trim();
	
	section.setCapacity(Integer.parseInt(capacity));
	
	//System.out.println(courseNumber + ", " + courseSection + ", " + enrolled + ", " + capacity);
	
	String openClosedString = fields[5];
	
	if(openClosedString.equalsIgnoreCase("open"))
	{
	    section.setIsOpen(true);
	}
	{
	    section.setIsOpen(false);
	}
	
	
	
	String compoundDaysAndTimesAndLocations = fields[6].trim();
	
	String[] daysAndTimesAndLocations = compoundDaysAndTimesAndLocations.split("\n");
	
	
	if(courseNumber.equalsIgnoreCase("MATH1300"))
	{
	    int xx = 1;

	}
	
	
	for(String daysAndTimesAndLocationComposite : daysAndTimesAndLocations )
	{
	    //System.out.println(daysAndTimesAndLocationComposite);
	    
	    
	    //If a day has not been assigned to a class, prepend an 'X' to the string.
	    if(daysAndTimesAndLocationComposite.charAt(0) >= 48 && daysAndTimesAndLocationComposite.charAt(0) <= 57)
	    {
		daysAndTimesAndLocationComposite = "X " + daysAndTimesAndLocationComposite;
	    }
	    
	    String[] daysAndTimesAndLocation = daysAndTimesAndLocationComposite.split(";");
	    
	    String daysAndTimesComposite = daysAndTimesAndLocation[0];
	    
	    String[] daysAndTimesWithoutBothMeridians = daysAndTimesComposite.split("[ -]+");
	    
	    String[] daysAndTimesWithBothMeridians = new String[5];
	    
	    daysAndTimesWithBothMeridians[0] = daysAndTimesWithoutBothMeridians[0];
	    
	    //Handle case where class spans AM and PM.
	    if(daysAndTimesWithoutBothMeridians[1].endsWith("M"))
	    {
		String timePart = daysAndTimesWithoutBothMeridians[1].substring(0,daysAndTimesWithoutBothMeridians[1].length() - 2);
		
		String meridianPart = daysAndTimesWithoutBothMeridians[1].substring(daysAndTimesWithoutBothMeridians[1].length() - 2, daysAndTimesWithoutBothMeridians[1].length());
		
		daysAndTimesWithBothMeridians[1] = timePart;
		
		daysAndTimesWithBothMeridians[2] = meridianPart;
		
		daysAndTimesWithBothMeridians[3] = daysAndTimesWithoutBothMeridians[2];
		
		daysAndTimesWithBothMeridians[4] = daysAndTimesWithoutBothMeridians[3];
	    }
	    else
	    {	
		daysAndTimesWithBothMeridians[1] = daysAndTimesWithoutBothMeridians[1];
		
		daysAndTimesWithBothMeridians[2] = new String(daysAndTimesWithoutBothMeridians[3]);
	
		daysAndTimesWithBothMeridians[3] = daysAndTimesWithoutBothMeridians[2];
		
		daysAndTimesWithBothMeridians[4] = daysAndTimesWithoutBothMeridians[3];
	    }
	    
	    
	    String days = daysAndTimesWithBothMeridians[0];
	    String startTimeString = daysAndTimesWithBothMeridians[1];
	    String startTimeMeridian = daysAndTimesWithBothMeridians[2];
	    String endTimeString = daysAndTimesWithBothMeridians[3];
	    String endTimeMeridian = daysAndTimesWithBothMeridians[4];
	    
	    DaysAndTimes daysAndTimes = new DaysAndTimes(days, startTimeString, startTimeMeridian, endTimeString, endTimeMeridian);
	    
	    //System.out.println(daysAndTimes);
	    
	    section.addDaysAndTimes(daysAndTimes);
	    
	    
	}//end for.
	
	//System.out.println(section.toString());
	
	//System.out.println("---------");
	
	return section;
	
    }//end method.
    
    
    
    public String toMathPiper()
    {
	
	
	StringBuilder mp = new StringBuilder();
	
	mp.append("zz := {\n");
	
	
	for(Course course : courses.values())
	{
	    mp.append("{ \"" + course.getCourseNumber() + "\", {");
	    
	    for(Section section : course.getSections())
	    {
		mp.append("{");
		mp.append("\"" + section.getCourseSection() + "\",");
		
		//List<DaysAndTimes> dayAndTimes = section.getDaysAndTimesList();
		mp.append("{");
		for(DaysAndTimes daysAndTimes : section.getDaysAndTimesList())
		{
		    mp.append("{");
		    
		    mp.append(daysAndTimes.getDaysPattern() + ",");
		    
		    mp.append(daysAndTimes.getStartSlot() + ",");
		    
		    mp.append(daysAndTimes.getSlotsLength());
		    
		    mp.append("},");
		}
		mp.append("},");
		mp.append("},");
	    }//end for.
	    
	    mp.append("} },\n");
	    
	}
	
	mp.append("};");
	
	return mp.toString();
    }//end method.
    
    
    
//(def zz {"BUAC1010" [{"01",[[80,168,20],],},{"02",[[80,120,20],],},{"51",[[16,222,40],],},]
//}) 
    
    public String toClojure()
    {
	
	StringBuilder mp = new StringBuilder();
	
	mp.append("(def zz {\n");
	
	
	for(Course course : courses.values())
	{
	    mp.append(":" + course.getCourseNumber() + " [");
	    
	    for(Section section : course.getSections())
	    {
		mp.append("[");
		mp.append(":" + section.getCourseSection() + " ");
		
		//List<DaysAndTimes> dayAndTimes = section.getDaysAndTimesList();
		mp.append("[");
		for(DaysAndTimes daysAndTimes : section.getDaysAndTimesList())
		{
		    mp.append("[");
		    
		    mp.append(daysAndTimes.getDaysPattern() + " ");
		    
		    mp.append(daysAndTimes.getStartSlot() + " ");
		    
		    mp.append(daysAndTimes.getSlotsLength());
		    
		    mp.append("] ");
		}
		mp.append("] ");
		mp.append("] ");
	    }//end for.
	    
	    mp.append("] \n");
	    
	}
	
	mp.append("} )");
	
	return mp.toString();
    }//end method.
    
    
    
    
    public static void main(String[] args)
    {
	SemesterSchedule schedule = new SemesterSchedule();
	
	File scheduleDSV = new File("ssu_course_schedule_fall_2012.dsv");
	
	try {
	    schedule.loadSchedule(scheduleDSV);
	    
	    
	    System.out.println(schedule.toClojure());
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    
	    System.out.println(schedule.getLineNumber());
	    e.printStackTrace();
	}
    }

}
