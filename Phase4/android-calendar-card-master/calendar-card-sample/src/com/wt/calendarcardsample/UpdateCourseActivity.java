package com.wt.calendarcardsample;

import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

public class UpdateCourseActivity extends Activity {

	private String OldCourseCode;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatecourse);

		Intent intent = this.getIntent();
		OldCourseCode = (String) intent.getSerializableExtra("courseKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

	}

	/**
	 * Attempt to create a new patient when save button is clicked. * @param
	 * view The layouts view.
	 */
	public void saveNewCourse(View view) {
		EditText editCourseCode = (EditText) findViewById(R.id.et_coursecode);
		EditText editCourseTitle = (EditText) findViewById(R.id.et_coursetitle);

		String courseCode = editCourseCode.getText().toString().toUpperCase()
				.trim().replaceAll("\\s+", "");
		String courseTitle = editCourseTitle.getText().toString();
		if (validateInput(courseCode, courseTitle)) {
			Course.removeCourse(OldCourseCode);
			Student.saveAssignments(getApplicationContext());
			Student.saveTests(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			Student.loadTests(getApplicationContext());
			Toast.makeText(getApplicationContext(), "Updated",
					Toast.LENGTH_SHORT).show();
			Course.addCourse(courseCode, courseTitle);
			Student.saveAssignments(getApplicationContext());
			Student.saveTests(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			Student.loadTests(getApplicationContext());
			super.onBackPressed();
		}
		editCourseCode.setText(null);
		editCourseTitle.setText(null);

	}

	/**
	 * Check the validity of the input.
	 */
	private boolean validateInput(String code, String title) {
		// Checks if input is missing and creates the corresponding
		// error message if it is.
		if (code.equals("") || title.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!code.equals("")) {
			Set<Course> courses = Student.courseAssignments.keySet();
			if (!code.equals(OldCourseCode)) {
				for (Course course1 : courses) {
					if (course1.getCode().equals(code)) {
						Toast.makeText(getApplicationContext(),
								"Course Exists", Toast.LENGTH_SHORT).show();
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getCurrentFocus()
				.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		return super.onTouchEvent(event);

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			// ProjectsActivity is my 'home' activity
			super.onBackPressed();
			return true;
		}
		return (super.onOptionsItemSelected(menuItem));
	}
}
