package org.mathpiper.studentschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course{
    
    private String courseNumber = "";
    
    private List<Section> sections = new ArrayList<Section>();
    
    private String name;
    
    private int creditHours;
    
    
    public Course(String courseNumber, String courseName, int creditHours)
    {
	super();
	
	this.courseNumber = courseNumber;
	
	this.name = courseName;
	
	this.creditHours = creditHours;
    }
    
    
    public void addSection(Section section)
    {
	sections.add(section);
    }


    public List<Section> getSections() {
        return sections;
    }




    public String getCourseNumber() {
        return courseNumber;
    }


    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getCreditHours() {
        return creditHours;
    }


    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    
    
    
    

}