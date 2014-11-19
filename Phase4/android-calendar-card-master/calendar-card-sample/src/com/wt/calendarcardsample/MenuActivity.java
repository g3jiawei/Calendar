package com.wt.calendarcardsample;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.calendarcardsample.backend.Student;

public class MenuActivity extends Activity {

	private Student student;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		// Intent intent = getIntent();
		// student = (Student) intent.getSerializableExtra("studentKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	public void handleSample1(View v) {
		Intent intent = new Intent(this, MonthActivity.class);
		// intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	public void handleSample2(View v) {
		Intent intent = new Intent(this, CalendarActivity.class);
		// intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	public void handleadd(View v) {
		Intent intent = new Intent(this, AddCourseActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	// student = (Student) intent.getSerializableExtra("studentKey");
	// course = (Course) intent.getSerializableExtra("courseKey");}

	// public String getCurrentCourses(View view) {
	// String str = "";
	// for (Course cur : student.getPersonalEvents()) {
	// str += cur.getCode() + "\n";
	// }
	//
	// return str;
	// }

	/**
	 * Attempt to go back to menu page when back button is clicked.
	 * 
	 * @param view
	 */
	public void viewCourseList(View view) {
		Intent intent = new Intent(this, CourseListActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
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
