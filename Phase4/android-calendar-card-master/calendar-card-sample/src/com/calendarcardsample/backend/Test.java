package com.calendarcardsample.backend;

import java.io.Serializable;
import java.util.Set;

public class Test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private int testID;
	private String code;
	private String name;
	private String date;
	private String from;
	private String to;
	private String location;

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Test(String code, String name, String date, String from, String to, String location) {
		// this.courseID = courseID;
		this.code = code;
		this.name = name;
		this.date = date;
		this.from = from;
		this.to = to;
		this.location = location;
	}

	// add some getters and setters here
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String newCode) {
		this.code = newCode;
	}

	public String getName() {
		return this.name;
	}
	
	public String getDate() {
		return this.date;
	}

	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setDate(String newDate) {
		this.date = newDate;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String newFrom) {
		this.from = newFrom;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String newTo) {
		this.to = newTo;
	}

	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}
	
	public static void addTest(String code, String name, String date, String from, String to, String location) {
		Test test = new Test(code, name, date, from, to, location);
		Set<Course> courses = Student.courseTests.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				Student.courseTests.get(course).add(test);
				break;
			}
		}
	}

	public void removeTest(Test test) {
		Set<Course> courses = Student.courseTests.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				Student.courseTests.get(course).remove(test);
				break;
			}
		}
	}

}
