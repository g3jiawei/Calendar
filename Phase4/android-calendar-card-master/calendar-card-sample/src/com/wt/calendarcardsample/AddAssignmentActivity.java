package com.wt.calendarcardsample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newassignment);

		Intent intent = this.getIntent();
		date = (String) intent.getSerializableExtra("clickDate");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		courseAssignmentsTree = new TreeMap<Course, List<Assignment>>(
				Student.courseAssignments);
		Set<Course> courses = courseAssignmentsTree.keySet();

		final ArrayList<String> list = new ArrayList<String>();

		for (Course cur : courses) {
			list.add(cur.getCode());
		}

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

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}

	/**
	 * Attempt to create a new patient when save button is clicked. * @param
	 * view The layouts view.
	 */
	public void saveNewAssignment(View view) {
		EditText editName = (EditText) findViewById(R.id.et_name_assignment);
		EditText editTime = (EditText) findViewById(R.id.et_time);

		String name = editName.getText().toString();
		String time = editTime.getText().toString().toLowerCase().trim();

		if (validateInput(code, name, date, time)) {
			// Gets current date from built-in calendar as the default date
			// for arrival time.
			// Calendar calendar = Calendar.getInstance();
			// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			// String currentDate = df.format(calendar.getTime());
			Toast.makeText(getApplicationContext(), "Added a new assignment",
					Toast.LENGTH_SHORT).show();
			Assignment.addAssignment(code, name, date, time);
			Student.saveAssignments(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			finish();

		}
		editName.setText(null);
		editTime.setText(null);
	}

	private boolean validateInput(String code, String name, String date,
			String time) {
		// Checks if input is missing and creates the corresponding
		// error message if it is.
		if (code.equals("") || time.equals("") || name.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
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
							return false;
						}
					}
				}
			}
		}else if (!time.equals("")) {
			if(!time.substring(2, 3).contentequals(":")){
				Toast.makeText(getApplicationContext(),
						"invalid time.", Toast.LENGTH_SHORT).show();
				return false;
			}
		} else if (!code.equals("")) {
			Set<Course> courses = Student.courseAssignments.keySet();
			for (Course course : courses) {
				if (course.getCode().equals(code)) {
					return true;
				}
			}
			Toast.makeText(getApplicationContext(),
					"This course doesn't exist.", Toast.LENGTH_SHORT).show();
			return false;
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
