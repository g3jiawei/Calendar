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

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;

public class AddTestActivity extends Activity {

	private String date;
	private String code;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private TreeMap<Course, List<Test>> courseTestsTree;
	EditText editName;
	EditText editFrom;
	EditText editTo;
	EditText editLocation;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newtest);
		// Receive data
		Intent intent = this.getIntent();
		date = (String) intent.getSerializableExtra("clickDate");
		// Action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// Get all course in order
		courseTestsTree = new TreeMap<Course, List<Test>>(Student.courseTests);
		Set<Course> courses = courseTestsTree.keySet();

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
				code = (String) item;
			}

			// Interface umimplemented function
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}

	// Save test
	public void saveNewTest(View view) {
		// Define views
		editName = (EditText) findViewById(R.id.et_name_test);
		editFrom = (EditText) findViewById(R.id.et_fromtime);
		editTo = (EditText) findViewById(R.id.et_totime);
		editLocation = (EditText) findViewById(R.id.et_location);
		// Get info from input
		String name = editName.getText().toString();
		String from = editFrom.getText().toString().toLowerCase().trim();
		String to = editTo.getText().toString().toLowerCase().trim();
		String location = editLocation.getText().toString();
		// Save and load
		if (validateInput(code, name, date, from, to, location)) {
			Toast.makeText(getApplicationContext(), "Added a new test",
					Toast.LENGTH_SHORT).show();
			Test.addTest(code, name, date, from, to, location);
			Student.saveTests(getApplicationContext());
			Student.loadTests(getApplicationContext());
			finish();
		}
	}

	// Checks if input is missing and creates the corresponding
	// error message if it is.
	// Checks if the date and time inputs are valid and creates the
	// corresponding error message if it is.
	private boolean validateInput(String code, String name, String date,
			String from, String to, String location) {

		if (code.equals("") || from.equals("") || to.equals("")
				|| name.equals("") || location.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
			return false;

		} else if (!matchDateTime(from) || !matchDateTime(to)) {
			Toast.makeText(getApplicationContext(), "invalid time.",
					Toast.LENGTH_SHORT).show();
			editFrom.setText(null);
			editTo.setText(null);
			return false;
		} else if (!checkTime(from, to)) {
			Toast.makeText(getApplicationContext(), "Invalid test time",
					Toast.LENGTH_SHORT).show();
			editFrom.setText(null);
			editTo.setText(null);
			return false;
		} else if (!name.equals("")) {
			Set<Course> courses = Student.courseTests.keySet();
			for (Course course : courses) {
				if (course.getCode().equals(code)) {
					for (Test test : Student.courseTests.get(course)) {
						if (test.getName().equals(name)) {
							Toast.makeText(getApplicationContext(),
									"This test already exists.",
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

	// Check if test time make sense
	private boolean checkTime(String from, String to) {
		String[] froms = from.split(":");
		Integer hours1 = Integer.parseInt(froms[0]);
		Integer minutes1 = Integer.parseInt(froms[1]);
		String[] tos = to.split(":");
		Integer hours2 = Integer.parseInt(tos[0]);
		Integer minutes2 = Integer.parseInt(tos[1]);

		if (hours1 == hours2) {
			return (minutes1 < minutes2);
		} else {
			return (hours1 < hours2);
		}
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
