package com.wt.calendarcardsample;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

public class AddAssignmentActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newassignment);

		// Intent intent = getIntent();
		// student = (Student) intent.getSerializableExtra("studentKey");
		// assignment = (Assignment)
		// intent.getSerializableExtra("assignmentKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

	}

	/**
	 * Attempt to create a new patient when save button is clicked. * @param
	 * view The layouts view.
	 */
	public void saveNewAssignment(View view) {
		EditText editCode = (EditText) findViewById(R.id.et_course_assignment);
		EditText editName = (EditText) findViewById(R.id.et_name_assignment);
		EditText editDate = (EditText) findViewById(R.id.et_date_assignment);
		EditText editTime = (EditText) findViewById(R.id.et_time);

		String code = editCode.getText().toString().toUpperCase().trim().replaceAll("\\s+", "");
		String name = editName.getText().toString();
		String date = editDate.getText().toString().toLowerCase().trim();
		String time = editTime.getText().toString().toLowerCase().trim();

		if (validateInput(code, name, date, time)) {
			// Gets current date from built-in calendar as the default date
			// for arrival time.
			// Calendar calendar = Calendar.getInstance();
			// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			// String currentDate = df.format(calendar.getTime());
			Toast.makeText(getApplicationContext(), "Add a new assignment",
					Toast.LENGTH_SHORT).show();
			Assignment.addAssignment(code, name, date, time);
			Student.saveAssignments(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			finish();

		}
		editCode.setText(null);
		editName.setText(null);
		editDate.setText(null);
		editTime.setText(null);
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
			String time) {
		// Checks if input is missing and creates the corresponding
		// error message if it is.
		if (code.equals("") || date.equals("") || time.equals("")
				|| name.equals("")) {
			Toast.makeText(getApplicationContext(), "Missing input",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		// Checks if the date and time inputs are valid and creates the
		// corresponding error message if it is.
		else if (!matchDateTime(date, time)) {
			Toast.makeText(getApplicationContext(),
					"Invalid date or time input", Toast.LENGTH_SHORT).show();
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

	/**
	 * Check the validity of the date and time inputs.
	 * 
	 * @param dateOfBirth
	 *            The patients date of birth.
	 * @param arrivalTime
	 *            The patients arrival time.
	 * @return true iff input is valid.
	 */
	private boolean matchDateTime(String date, String time) {
		// Uses a regular expression to make sure the input follows a
		// specific format.
		Pattern pdate = Pattern.compile("(0[1-9]|[12]\\d|3[01])"
				+ "/(0[1-9]|1[012])" + "/(19\\d\\d|20[01]\\d)");
		Pattern ptime = Pattern.compile("([01]\\d|2[0-3]):([0-5]\\d)");
		Matcher mdate = pdate.matcher(date);
		Matcher mtime = ptime.matcher(time);

		String[] dates = date.split("/");
		Integer day = Integer.parseInt(dates[0]);
		Integer month = Integer.parseInt(dates[1]);
		Integer year = Integer.parseInt(dates[2]);
		// Only returns true if both formats match, and the dates are
		// confirmed to be valid by matchDaysInMonth.
		return (mdate.matches() && mtime.matches() && matchDaysInMonth(day,
				month, year));
	}

	/**
	 * Checks if the patients date of birth input matches calendar dates.
	 * 
	 * @param day
	 *            The day the patient was born on.
	 * @param month
	 *            The month the patient was born on.
	 * @param year
	 *            The year the patient was born on.
	 * @return true iff input is valid.
	 */
	private boolean matchDaysInMonth(Integer day, Integer month, Integer year) {
		// Compares inputed date with the calendar dates.
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day == 31) {
				return false;
			}
		} else if (month == 2) {
			// Checks for leap years.
			if (year % 4 == 0) {
				if (day > 29) {
					return false;
				}
			} else {
				if (day > 28) {
					return false;
				}
			}
		}
		return true;
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
