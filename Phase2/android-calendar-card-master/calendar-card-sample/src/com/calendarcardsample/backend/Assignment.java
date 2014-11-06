package com.calendarcardsample.backend;

import java.io.Serializable;
import java.util.Set;

public class Assignment implements Serializable{
	
	
	private static final long serialVersionUID = -4415663512701202820L;
	/**
	 * 
	 */
	// private int assignmentID;
	private String code;
	private String date;
	private String time;

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Assignment(String code, String date, String time) {
		// this.courseID = courseID;
		this.code = code;
		this.date = date;
		this.time = time;
	}

	// add some getters and setters here
	public String getCode() {
		return this.code;
	}

	public void setCode(String newCode) {
		this.code = newCode;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String newDate) {
		this.date = newDate;
	}

	public String getTime() {
		return this.time;
	}

	public void setTo(String newTime) {
		this.time = newTime;
	}

	public static void addAssignment(String code, String date, String time) {
		Assignment assignment = new Assignment(code, date, time);
		Set<Course> courses = Student.courseAssignments.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				Student.courseAssignments.get(course).add(assignment);
				break;
			}
		}
	}

	public void removeAssignment(Assignment assignment) {
		Set<Course> courses = Student.courseAssignments.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				Student.courseAssignments.get(course).remove(assignment);
				break;
			}
		}
	}
}
