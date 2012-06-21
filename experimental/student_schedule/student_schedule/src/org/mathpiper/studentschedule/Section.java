package org.mathpiper.studentschedule;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private String courseNumber;
    private String courseSection;
    private String courseName;// todo:tk:it would be more efficient to only have
			      // this field in Course.
    private String instructors;
    private int enrolled;
    private int capacity;
    private boolean isOpen;
    private List<DaysAndTimes> daysAndTimesList = new ArrayList<DaysAndTimes>();
    private int creditHours;

    public Section() {
	super();
    }

    public void addDaysAndTimes(DaysAndTimes daysAndTimes) {
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

    public String getCourseName() {
	return courseName;
    }

    public void setCourseName(String courseName) {
	this.courseName = courseName;
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

    public int getCreditHours() {
	return creditHours;
    }

    public void setCreditHours(int creditHours) {
	this.creditHours = creditHours;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	sb.append(this.courseNumber);
	sb.append(" ");

	sb.append(this.courseSection);
	sb.append(" ");

	sb.append(this.courseName);
	sb.append(" ");

	sb.append(this.instructors);
	sb.append(" ");

	sb.append(this.enrolled);
	sb.append(" ");

	sb.append(this.capacity);
	sb.append(" ");

	for (DaysAndTimes s : daysAndTimesList) {
	    sb.append(s.toString());
	    sb.append(" ");
	}

	return sb.toString();

    }

}