package org.mathpiper.studentschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course{
    
    private List<Section> sections = new ArrayList<Section>();
    
    private String courseNumber = "";
    
    
    public Course(String courseNumber)
    {
	super();
	
	this.courseNumber = courseNumber;
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
    
    

}