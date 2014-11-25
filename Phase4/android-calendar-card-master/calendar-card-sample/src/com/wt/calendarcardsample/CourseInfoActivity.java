package com.wt.calendarcardsample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;

public class CourseInfoActivity extends Activity {

	private String courseCode;
	private Course course1;
	private Course course2;
	private String name;
	private String rest;
	private Course course;

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
				break;
			}
		}
		Set<Course> courses2 = Student.courseTests.keySet();
		for (Course cur : courses2) {
			if (cur.getCode().equals(courseCode)) {
				course2 = cur;
				break;
			}
		}
		list.add("Welcome to " + course1.getCode() + ": " + course1.getTitle());

		// assignment
		list.add("Assignments:");
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();

		if (course1 != null) {
			assignments = (ArrayList<Assignment>) Student.courseAssignments
					.get(course1);
			if (assignments != null && !assignments.isEmpty()) {
				for (Assignment assign : assignments) {
					list.add(assign.getName() + ":  Due at  "
							+ assign.getDate() + "  " + assign.getTime());
				}
			} else {
				list.add("No recent Assignment");
			}
		}

		// test
		list.add("Tests:");
		ArrayList<Test> tests = new ArrayList<Test>();

		if (course2 != null) {
			tests = (ArrayList<Test>) Student.courseTests.get(course2);
			if (tests != null && !tests.isEmpty()) {
				for (Test test : tests) {
					list.add(test.getName() + ":  " + test.getDate()
							+ "\nFrom " + test.getFrom() + " to "
							+ test.getTo() + " at " + test.getLocation());
				}
			} else {
				list.add("No recent Test");
			}
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(200).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {
								Set<Course> courses1 = Student.courseAssignments
										.keySet();
								for (Course cur : courses1) {
									if (cur.getCode().equals(courseCode)) {
										course = cur;
										break;
									}
								}
								if (item.equals("Assignments:")
										|| item.equals("Tests:")
										|| item.equals("No recent Assignment")
										|| item.equals("No recent Test")
										|| item.equals("Welcome to "
												+ course.getCode() + ": "
												+ course.getTitle())) {
									// handleCourse(view);
									// list.remove(item);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								} else {
									String[] info = item.split(":");
									name = info[0].trim();
									rest = info[1].trim();
									createDialog(view);
									// handleCourse(view);
									// list.remove(item);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								}
							}
						});
				return true;
			}

		});
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

	public void lanuchModifyAssignmentActivity() {
		Intent intent = new Intent(this, UpdateAssignmentActivity.class);
		intent.putExtra("courseKey", courseCode);
		startActivity(intent);
	}

	public void lanuchModifyTestActivity() {
		Intent intent = new Intent(this, UpdateAssignmentActivity.class);
		intent.putExtra("courseKey", courseCode);
		startActivity(intent);
	}

	private void createDialog(View view) {
		// Uses a view from xml files in order to allow edittext boxes.
		// LayoutInflater li = LayoutInflater.from(this);
		// View promptsView =
		// li.inflate(R.layout.activity_newevent_dialog,null);
		// Builds the dialog box.
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// alertDialogBuilder.setView(promptsView);

		alertDialogBuilder
				// Makes user unable to leave dialog by clicking
				// outside its borders.
				.setTitle(course.getCode() + ": " + course.getTitle())
				.setMessage("You sure want to delete?")
				.setCancelable(true)
				// Sets a button on the left for submitting data.
				.setNegativeButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (rest.substring(0, 1).equalsIgnoreCase("D")) {
									Assignment.removeAssignment(courseCode,
											name);
									Student.saveAssignments(getApplicationContext());
									Student.loadTests(getApplicationContext());
									Student.loadAssignments(getApplicationContext());
									finish();
								} else {
									Test.removeTest(courseCode, name);
									Student.saveTests(getApplicationContext());
									Student.loadTests(getApplicationContext());
									Student.loadAssignments(getApplicationContext());
									finish();
								}
							}
						})
				// Sets a button on the right for exiting the dialog.
				.setPositiveButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Closes dialog when user chooses to cancel.
								dialog.cancel();
							}
						});

		// Create and show the alert dialog.
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
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
