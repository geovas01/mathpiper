package org.mathpiper.studentschedule;

import java.util.ArrayList;
import java.util.List;


public class Section{

    private String courseNumber;
    private String courseSection;
    private String courseDescription;
    private String instructors;
    private int enrolled;
    private int capacity;
    private boolean isOpen;
    private List<DaysAndTimes> daysAndTimesList = new ArrayList();
    
    
    
    public Section()
    {
	super();
    }
    
    
    
    public void addDaysAndTimes(DaysAndTimes daysAndTimes)
    {
	daysAndTimesList.add(daysAndTimes);
    }
    
    
    
    public String getCourseNumber() {
        return courseNumber;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getCourseSection() {
        return courseSection;
    }
    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }
    public String getCourseDescription() {
        return courseDescription;
    }
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    public String getInstructors() {
        return instructors;
    }
    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }
    public int getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setIsOpen(boolean open) {
        this.isOpen = open;
    }
    public List<DaysAndTimes> getDaysAndTimesList() {
        return daysAndTimesList;
    }
    public void setDaysAndTimesList(List daysAndTimesList) {
        this.daysAndTimesList = daysAndTimesList;
    }
    
    
    @Override
    public String toString()
    {
	StringBuilder sb = new StringBuilder();
	
	sb.append(this.courseNumber);
	sb.append(" ");
	
	sb.append(this.courseSection);
	sb.append(" ");
	
	sb.append(this.courseDescription);
	sb.append(" ");
	
	sb.append(this.instructors);
	sb.append(" ");
	
	sb.append(this.enrolled);
	sb.append(" ");
	
	sb.append(this.capacity);
	sb.append(" ");
	
	for(DaysAndTimes s : daysAndTimesList)
	{
	    sb.append(s.toString());
	    sb.append(" ");
	}
	
	
	return sb.toString();
	
	
    }
    

}