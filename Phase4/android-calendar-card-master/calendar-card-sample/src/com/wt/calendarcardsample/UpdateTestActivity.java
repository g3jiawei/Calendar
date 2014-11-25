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

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;

public class UpdateTestActivity extends Activity {

	private String date;
	private String code;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private TreeMap<Course, List<Test>> courseTestsTree;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newtest);

		Intent intent = this.getIntent();
		date = (String) intent.getSerializableExtra("clickDate");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		courseTestsTree = new TreeMap<Course, List<Test>>(Student.courseTests);
		Set<Course> courses = courseTestsTree.keySet();

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
	public void saveNewTest(View view) {
		EditText editName = (EditText) findViewById(R.id.et_name_test);
		EditText editFrom = (EditText) findViewById(R.id.et_fromtime);
		EditText editTo = (EditText) findViewById(R.id.et_totime);
		EditText editLocation = (EditText) findViewById(R.id.et_location);

		String name = editName.getText().toString();
		String from = editFrom.getText().toString().toLowerCase().trim();
		String to = editTo.getText().toString().toLowerCase().trim();
		String location = editLocation.getText().toString();

		if (validateInput(code, name, date, from, to, location)) {
			// // Gets current date from built-in calendar as the default date
			// // for arrival time.
			// Calendar calendar = Calendar.getInstance();
			// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			// String currentDate = df.format(calendar.getTime());
			Toast.makeText(getApplicationContext(), "Added a new test",
					Toast.LENGTH_SHORT).show();
			Test.addTest(code, name, date, from, to, location);
			Student.saveTests(getApplicationContext());
			Student.loadTests(getApplicationContext());
			finish();
		}
		editName.setText(null);
		editFrom.setText(null);
		editTo.setText(null);
		editLocation.setText(null);
	}

	/**
	 * Check the validity of the input.
	 * 
	 * @param name
	 *            The patients name.
	 * @param dateOfBirth
	 *            The patients date of birth.
	 * @param healthCardNumber
	 *            The patients health card number.
	 * @param arrivalTime
	 *            The patients arrival time.
	 * @return true iff input is valid.
	 */
	private boolean validateInput(String code, String name, String date,
			String from, String to, String location) {
		// Checks if input is missing and creates the corresponding
		// error message if it is.
		if (code.equals("") || from.equals("") || to.equals("")
				|| name.equals("") || location.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
			return false;
			// Checks if the date and time inputs are valid and creates the
			// corresponding error message if it is.
		} else if (!checkTime(from, to)) {
			Toast.makeText(getApplicationContext(), "Invalid test time",
					Toast.LENGTH_SHORT).show();
			return false;
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

	// /**
	// * Check the validity of the date and time inputs.
	// *
	// * @param dateOfBirth
	// * The patients date of birth.
	// * @param arrivalTime
	// * The patients arrival time.
	// * @return true iff input is valid.
	// */
	// private boolean matchDateTime(String date, String time) {
	// // Uses a regular expression to make sure the input follows a
	// // specific format.
	// Pattern pdate = Pattern.compile("(0[1-9]|[12]\\d|3[01])"
	// + "/(0[1-9]|1[012])" + "/(19\\d\\d|20[01]\\d)");
	// Pattern ptime = Pattern.compile("([01]\\d|2[0-3]):([0-5]\\d)");
	// Matcher mdate = pdate.matcher(date);
	// Matcher mtime = ptime.matcher(time);
	//
	// String[] dates = date.split("/");
	// Integer day = Integer.parseInt(dates[0]);
	// Integer month = Integer.parseInt(dates[1]);
	// Integer year = Integer.parseInt(dates[2]);
	// // Only returns true if both formats match, and the dates are
	// // confirmed to be valid by matchDaysInMonth.
	// return (mdate.matches() && mtime.matches() && matchDaysInMonth(day,
	// month, year));
	// }

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

	// /**
	// * Checks if the patients date of birth input matches calendar dates.
	// *
	// * @param day
	// * The day the patient was born on.
	// * @param month
	// * The month the patient was born on.
	// * @param year
	// * The year the patient was born on.
	// * @return true iff input is valid.
	// */
	// private boolean matchDaysInMonth(Integer day, Integer month, Integer
	// year) {
	// // Compares inputed date with the calendar dates.
	// if (month == 4 || month == 6 || month == 9 || month == 11) {
	// if (day == 31) {
	// return false;
	// }
	// } else if (month == 2) {
	// // Checks for leap years.
	// if (year % 4 == 0) {
	// if (day > 29) {
	// return false;
	// }
	// } else {
	// if (day > 28) {
	// return false;
	// }
	// }
	// }
	// return true;
	// }

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
