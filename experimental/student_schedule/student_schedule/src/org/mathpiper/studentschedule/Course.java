package org.mathpiper.studentschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course{
    
    private String courseNumber = "";
    
    private List<Section> sections = new ArrayList<Section>();
    
    private String name;
    
    
    public Course(String courseNumber, String courseName)
    {
	super();
	
	this.courseNumber = courseNumber;
	
	this.name = courseName;
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
    
    

}