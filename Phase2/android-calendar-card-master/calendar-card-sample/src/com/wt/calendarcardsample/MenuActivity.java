package com.wt.calendarcardsample;

import java.util.HashSet;

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

public class MenuActivity extends Activity {
    
	private Student student;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		//setupActionBar();
		Intent intent = getIntent();
		student = (Student) intent.getSerializableExtra("studentKey");
		
	}
	

	public void handleSample1(View v) {
		Intent intent = new Intent(this, Sample1.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	public void handleSample2(View v) {
		Intent intent = new Intent(this, CalendarActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}
	
	public void handleadd(View v) {
		Intent intent = new Intent(this, AddCourseActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}
	
//	public String getCurrentCourses(View view) {
//		String str = "";
//		for (Course cur : student.getPersonalEvents()) {
//			str += cur.getCode() + "\n";
//		}
//		
//		return str;
//	}
	
	/**
	 * Attempt to go back to menu page when back button is clicked.
	 * @param view
	 */
	public void viewCourseList(View view) {
		Intent intent = new Intent(this, CourseListActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}
	
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#
			// up-vs-back
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
