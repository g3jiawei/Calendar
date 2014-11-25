package com.calendarcardsample.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Course implements Serializable, Comparable<Course> {

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

	public static void removeCourse(String courseCode) {
		Set<Course> courses1 = Student.courseAssignments.keySet();
		for (Course cur : courses1) {
			if (cur.getCode().equals(courseCode)) {
				Student.courseAssignments.remove(cur);
				break;
			}
		}
		Set<Course> courses2 = Student.courseTests.keySet();
		for (Course cur : courses2) {
			if (cur.getCode().equals(courseCode)) {
				Student.courseTests.remove(cur);
				break;
			}
		}
	}

	public static void saveCourse(Course course) {
		List<Test> array1 = new ArrayList<Test>();
		List<Assignment> array2 = new ArrayList<Assignment>();
		Student.courseTests.put(course, array1);
		Student.courseAssignments.put(course, array2);
	}

	@Override
	public int compareTo(Course course) {
		// TODO Auto-generated method stub
		int last = this.code.compareTo(course.code);
		return last;
	}

}
