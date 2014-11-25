package com.calendarcardsample.backend;

import java.io.Serializable;
import java.util.Set;

public class Assignment implements Serializable {

	private static final long serialVersionUID = -4415663512701202820L;
	/**
	 * 
	 */
	// private int assignmentID;
	private String code;
	private String name;
	private String date;
	private String time;

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Assignment(String code, String name, String date, String time) {
		// this.courseID = courseID;
		this.code = code;
		this.name = name;
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

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
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

	public static void addAssignment(String code, String name, String date,
			String time) {
		Assignment assignment = new Assignment(code, name, date, time);
		Set<Course> courses = Student.courseAssignments.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(code)) {
				Student.courseAssignments.get(course).add(assignment);
				break;
			}
		}
	}

	public static void removeAssignment(String courseCode, String name) {
		Set<Course> courses = Student.courseAssignments.keySet();
		for (Course course : courses) {
			if (course.getCode().equals(courseCode)) {
				for (Assignment assignment : Student.courseAssignments
						.get(course)) {
					if (assignment.getName().equals(name)) {
						Student.courseAssignments.get(course)
								.remove(assignment);
						break;
					}
				}
			}
		}
	}
}
