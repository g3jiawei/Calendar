package com.wt.calendarcardsample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CourseInfoActivity extends Activity {

	private String courseCode;
	private Course course1;
	private Course course2;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courselist);

		Intent intent = this.getIntent();
		courseCode = (String) intent.getSerializableExtra("courseKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		final ListView listview = (ListView) findViewById(R.id.listview);
		final ArrayList<String> list = new ArrayList<String>();

		// Course
		Set<Course> courses1 = Student.courseAssignments.keySet();
		for (Course cur : courses1) {
			if (cur.getCode().equals(courseCode)) {
				course1 = cur;
			}
		}
		Set<Course> courses2 = Student.courseTests.keySet();
		for (Course cur : courses2) {
			if (cur.getCode().equals(courseCode)) {
				course2 = cur;
			}
		}
		list.add("Welcome to " + course1.getCode() + ": " + course1.getTitle()
				+ "!!");

		// assignment
		list.add("Assignments:");
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();

		if (course1 != null) {
			assignments = (ArrayList<Assignment>) Student.courseAssignments
					.get(course1);
			if (!assignments.isEmpty()) {
				for (Assignment assign : assignments) {
					list.add("On " + assign.getDate() + "    Due at "
							+ assign.getTime());
				}
			} else {
				list.add("No recent Assignment!!");
			}
		}

		// test
		list.add("Tests:");
		ArrayList<Test> tests = new ArrayList<Test>();

		if (course2 != null) {
			tests = (ArrayList<Test>) Student.courseTests.get(course2);
			if (!tests.isEmpty()) {
				for (Test test : tests) {

					list.add("On " + test.getDate() + "    From "
							+ test.getFrom() + " to " + test.getTo());
				}
			} else {
				list.add("No recent Test!!");
			}
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

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
