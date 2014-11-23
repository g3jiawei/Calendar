package com.wt.calendarcardsample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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

public class CourseListActivity extends Activity {

	public Course course;
	public String courseCode;
	private TreeMap<Course, List<Assignment>> courseAssignmentsTree;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courselist);

		// Intent intent = this.getIntent();
		// student = (Student) intent.getSerializableExtra("studentKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		final ListView listview = (ListView) findViewById(R.id.listview);

		courseAssignmentsTree = new TreeMap<Course, List<Assignment>>(
				Student.courseAssignments);

		Set<Course> courses1 = courseAssignmentsTree.keySet();

		final ArrayList<String> list = new ArrayList<String>();

		for (Course cur : courses1) {
			list.add(cur.getCode());
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(200).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {

								// handleCourse(view);
								courseCode = item;
								// list.remove(item);
								adapter.notifyDataSetChanged();
								view.setAlpha(1);
								lanuchCourseInfoActivity();
							}
						});
			}

		});

		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(100).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {

								// handleCourse(view);
								courseCode = item;
								Set<Course> courses = Student.courseAssignments
										.keySet();
								for (Course cur : courses) {
									if (cur.getCode().equals(courseCode)) {
										course = cur;
										break;
									}
								}
								// list.remove(item);
								createDialog(view, course);
								adapter.notifyDataSetChanged();
								view.setAlpha(1);
							}
						});
				return true;
			}

		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		final ListView listview = (ListView) findViewById(R.id.listview);

		courseAssignmentsTree = new TreeMap<Course, List<Assignment>>(
				Student.courseAssignments);

		Set<Course> courses1 = courseAssignmentsTree.keySet();

		final ArrayList<String> list = new ArrayList<String>();

		for (Course cur : courses1) {
			list.add(cur.getCode());
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		adapter.notifyDataSetChanged();
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

	public void lanuchCourseInfoActivity() {
		Intent intent = new Intent(this, CourseInfoActivity.class);
		intent.putExtra("courseKey", courseCode);
		startActivity(intent);
	}

	public void lanuchUpdateActivity() {
		Intent intent = new Intent(this, UpdateCourseActivity.class);
		intent.putExtra("courseKey", courseCode);
		startActivity(intent);
	}

	private void createDialog(View view, final Course course) {
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
				.setMessage(
						"Warning:\nOnce you edit or delete the course, all the information about this course will be lost!")
				.setCancelable(true)
				// Sets a button on the left for submitting data.
				.setNegativeButton("Edit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								lanuchUpdateActivity();
							}
						})
				// Sets a button on the right for exiting the dialog.
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Closes dialog when user chooses to cancel.
								Course.removeCourse(course);
								Student.saveAssignments(getApplicationContext());
								Student.saveTests(getApplicationContext());
								Student.loadTests(getApplicationContext());
								Student.loadAssignments(getApplicationContext());
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
