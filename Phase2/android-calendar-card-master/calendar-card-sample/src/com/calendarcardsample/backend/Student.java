package com.calendarcardsample.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class Student implements Serializable {

	private static String file1 = "file1.txt";
	private static String file2 = "file2.txt";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	List<Test> tests = new ArrayList<Test>();
	List<Assignment> assignments = new ArrayList<Assignment>();
    List<String> courses;
	public static Map<Course, List<Test>> courseTests;
    public static Map<Course, List<Assignment>> courseAssignments;

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Student(Context fileContext) {

		courseTests = new HashMap<Course, List<Test>>();
		courseAssignments = new HashMap<Course, List<Assignment>>();

		File file1 = fileContext.getFileStreamPath("file1");
		File file2 = fileContext.getFileStreamPath("file2");

		if (file1.exists()) {
			saveTests(fileContext);
		}
		loadTests(fileContext);
		if (file2.exists()) {
			saveAssignments(fileContext);
		}
		loadAssignments(fileContext);
	}


	
//	public void addPatient(String name, String DoB, String arrivalTime, 
//			String healthNum){
//		if (patients.containsKey(healthNum)){
//			patientUrgencies.add(patients.get(healthNum));
//			patients.get(healthNum).setSeenDoctor(false);
//		}
//		else{
//			// Creates a patient
//			Patient patient = new Patient(name, DoB, arrivalTime, healthNum);
//			// Adds patient to list
//			patients.put(patient.getHealthNumber(), patient);
//			if (patient.getAge() < 2)
//				patient.setUrgency(1);
//			patientUrgencies.add(patient);
//		}
//	}
//	
//	public void addCourse(String code, String title){
//	    if courseTests.containsKey(course)
//	}

	/**
	 * Saves the patients data that this User object has accumulated into a
	 * file.
	 */
	@SuppressWarnings("static-access")
	public static void saveTests(Context fileContext) {
		FileOutputStream fos;
		try {
			fos = fileContext.openFileOutput(file1, fileContext.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(courseTests);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets patients to any saved data of a map with health card numbers as keys
	 * and Patients as values.
	 */
	
	@SuppressWarnings("unchecked")
	public void loadTests(Context fileContext) {
		try {
			FileInputStream fis = fileContext.openFileInput(file1);
			ObjectInputStream in = new ObjectInputStream(fis);
			try {
				courseTests =  (Map<Course, List<Test>>) in.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the patients data that this User object has accumulated into a
	 * file.
	 */
	@SuppressWarnings("static-access")
	public static void saveAssignments(Context fileContext) {
		FileOutputStream fos;
		try {
			fos = fileContext.openFileOutput(file2, fileContext.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(courseAssignments);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets patients to any saved data of a map with health card numbers as keys
	 * and Patients as values.
	 */
	@SuppressWarnings("unchecked")
	public void loadAssignments(Context fileContext) {
		try {
			FileInputStream fis = fileContext.openFileInput(file2);
			ObjectInputStream in = new ObjectInputStream(fis);
			try {
				courseAssignments = (HashMap<Course, List<Assignment>>) in
						.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
