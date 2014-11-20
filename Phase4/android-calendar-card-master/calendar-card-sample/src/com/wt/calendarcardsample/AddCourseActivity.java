package com.wt.calendarcardsample;

import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

public class AddCourseActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcourse);

		// Intent intent = this.getIntent();
		// student = (Student) intent.getSerializableExtra("studentKey");
		// course = (Course) intent.getSerializableExtra("courseKey");

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

		String courseCode = editCourseCode.getText().toString().toLowerCase().trim();
		String courseTitle = editCourseTitle.getText().toString().toLowerCase().trim();
		if (validateInput(courseCode, courseTitle)) {
			Toast.makeText(getApplicationContext(), "Add a new course",
					Toast.LENGTH_SHORT).show();
			Course.addCourse(courseCode, courseTitle);
			Student.saveAssignments(getApplicationContext());
			Student.saveTests(getApplicationContext());
			Student.loadAssignments(getApplicationContext());
			Student.loadTests(getApplicationContext());
		}
		editCourseCode.setText(null);
		editCourseTitle.setText(null);
		

	}

	// /**
	// * Attempt to go back to menu page when back button is clicked.
	// *
	// * @param view
	// */
	// public void backToMenu(View view) {
	// super.onBackPressed();
	// }

	// private void launchIntentPatientActivity(String healthCardNumber) {
	// // Goes to the information page for the newly created patient.
	// Intent intent = new Intent(this, PatientActivity.class);
	// Bundle bundle = new Bundle();
	// bundle.putSerializable("nurse", nurse);
	// intent.putExtras(bundle);
	// intent.putExtra("patient", healthCardNumber);
	//
	// startActivity(intent);
	// finish();
	// }
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
			for (Course course : courses) {
				if (course.getCode().equals(code)) {
					Toast.makeText(getApplicationContext(), "Course Exists",
							Toast.LENGTH_SHORT).show();
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
