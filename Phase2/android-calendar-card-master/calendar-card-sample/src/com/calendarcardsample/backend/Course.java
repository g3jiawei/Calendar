package com.calendarcardsample.backend;

import java.io.Serializable;

public class Course implements Serializable {


	private static final long serialVersionUID = 6030099844342423834L;
	/**
	 * 
	 */
	// private int courseID;
	private String code;
	private String title;

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Course(String code, String title) {
		// this.courseID = courseID;
		this.code = code;
		this.title = title;
	}

	// add some getters and setters here
	public String getCode() {
		return this.code;
	}

	public void setCode(String newCode) {
		this.code = newCode;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public static void addCourse(String code, String title) {
		Course course = new Course(code, title);
		saveCourse(course);
	}

	public void removeCourse(Course course) {
		Student.courseAssignments.remove(course);
		Student.courseTests.remove(course);
	}

	public static void saveCourse(Course course) {
		Student.courseTests.put(course, null);
		Student.courseAssignments.put(course, null);
	}
    
}
