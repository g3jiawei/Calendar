package com.calendarcardsample.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Calendar;

public class Course implements Serializable {
	
	private int courseID;
	private String code;
	private String title;
	
	public Course(int courseID, String code, String title){
		this.courseID = courseID;
		this.code = code;
		this.title = title;
	}
	
	public int getCourseID() {
		return this.courseID;
	}
	
	public void setCourseID(int newID) {
		this.courseID = newID;
	}
	
	//add some getters and setters here
	public String getCode(){
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
	
}
