package com.wt.calendarcardsample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

public class AddAssignmentActivity extends Activity {

	private String date;
	private String code;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private TreeMap<Course, List<Assignment>> courseAssignmentsTree;
	EditText editName;
	EditText editTime;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newassignment);
		// Receive data
		Intent intent = this.getIntent();
		date = (String) intent.getSerializableExtra("clickDate");
		// Action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// Get all course in order
		courseAssignmentsTree = new TreeMap<Course, List<Assignment>>(
				Student.courseAssignments);
		Set<Course> courses = courseAssignmentsTree.keySet();
		final ArrayList<String> list = new ArrayList<String>();
		for (Course cur : courses) {
			list.add(cur.getCode());
		}
		// Spinner thing
		spinner = (Spinner) findViewById(R.id.Spinner01);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.VISIBLE);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				Object item = parent.getItemAtPosition(pos);
				//
				code = (String) item;
			}

			// Interface umimplemented function
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}

	// Save asssignment
	public void saveNewAssignment(View view) {
		// Define views
		editName = (EditText) findViewById(R.id.et_name_assignment);
		editTime = (EditText) findViewById(R.id.et_time);
		// Get info from input
		String name = editName.getText().toString();
		String time = editTime.getText().toString().toLowerCase().trim();

		if (validateInput(code, name, date, time)) {
			Toast.makeText(getApplicationContext(), "Added a new assignment",
					Toast.LENGTH_SHORT).show();
			Assignment.addAssignment(code, name, date, time);
			Student.saveAssignments(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			finish();
		}
	}

	// Checks if input is missing and creates the corresponding
	// error message if it is.
	// Checks if the date and time inputs are valid and creates the
	// corresponding error message if it is.
	private boolean validateInput(String code, String name, String date,
			String time) {
		if (code.equals("") || time.equals("") || name.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!matchDateTime(time)) {
			Toast.makeText(getApplicationContext(), "invalid time.",
					Toast.LENGTH_SHORT).show();
			editTime.setText(null);
			return false;
		} else if (!name.equals("")) {
			Set<Course> courses = Student.courseAssignments.keySet();
			for (Course course : courses) {
				if (course.getCode().equals(code)) {
					for (Assignment assignment : Student.courseAssignments
							.get(course)) {
						if (assignment.getName().equals(name)) {
							Toast.makeText(getApplicationContext(),
									"This assignment already exists.",
									Toast.LENGTH_SHORT).show();
							editName.setText(null);
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean matchDateTime(String time) {
		// Uses a regular expression to make sure the input follows a
		// specific format.
		Pattern ptime = Pattern.compile("([01]\\d|2[0-3]):([0-5]\\d)");
		Matcher mtime = ptime.matcher(time);
		return mtime.matches();
	}

	// Hide keyboard
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getCurrentFocus()
				.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		return super.onTouchEvent(event);

	}

	// Action bar stuff
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
