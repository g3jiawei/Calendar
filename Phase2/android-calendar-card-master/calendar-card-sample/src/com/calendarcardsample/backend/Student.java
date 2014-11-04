package com.calendarcardsample.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Calendar;

public class Student implements Serializable {
	
	private String username;
	private HashSet<Course> personalCourses = null;
	
	public Student() {
		this.username = null;
		this.personalCourses = new HashSet<Course>();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int setUsername(String name) {
		this.username = name;
		return 0;
	}
	
	public HashSet<Course> getPersonalEvents() {
		return this.personalCourses;
	}
	
	public boolean haveCourse(int courseID) {
		
		for (Course cur : this.getPersonalEvents()) {
			if (cur.getCourseID() == courseID) {
				return true;
			}
		}
		return false;
	}
	
	public Course getCourse(int courseID) {
		
		for (Course cur : this.getPersonalEvents()) {
			if (cur.getCourseID() == courseID) {
				return cur;
			}
		}
		return null;
	}

	/**
	 * Add the new course into the course set
	 */
	public void addCourse(int courseID, String code, String title)  {
		Course newCourse = new Course(courseID, code, title);
		this.personalCourses.add(newCourse);
		
	}
	
	/**
	 * Add the new course into the course set
	 */
	public void deleteCourse(int courseID) {
		this.personalCourses.remove(this.getCourse(courseID));
	}
	
	public void saveData(File file) {
		//add code here
		
	}
	
	public void loadData(File file) {
		
	}

}
