package com.wt.calendarcardsample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;
import com.wt.calendarcard.CalendarCardPager;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

public class CalendarActivity extends Activity {
	private Set<String> dates1;
	private Set<String> dates2;
	private CalendarCardPager mCalendarCardPager;
	private String info = "";
	private String clickDate;

	// private TextView mTextView;
	@SuppressLint({ "NewApi", "SimpleDateFormat" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		// Intent intent = getIntent();
		// student = (Student) intent.getSerializableExtra("studentKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		//CalendarCard.setCell(cell);
//		CardGridItem testItem = null;
//		testItem.isEnabled();

		// Calendar calendar = Calendar.getInstance();
		// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		// final String currentDate = df.format(calendar.getTime());

		mCalendarCardPager = (CalendarCardPager) findViewById(R.id.calendarCard1);
		dates1 = getAllDates1();
		dates2 = getAllDates2();
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar today = Calendar.getInstance();
		String a = dateFormat.format(today.getTime());
		TextView view = (TextView) findViewById(R.id.textView1);
		view.setText("Today is "+ a);
		
		mCalendarCardPager.setOnCellItemClick(new OnCellItemClick() {

			@Override
			public void onCellClick(View v, CardGridItem item) {
				clickDate = new SimpleDateFormat("dd/MM/yyyy", Locale
						.getDefault()).format(item.getDate().getTime());
				if (dates1 != null && dates2 != null) {
					if (dates1.contains(new SimpleDateFormat("dd/MM/yyyy",
							Locale.getDefault()).format(item.getDate()
							.getTime()))
							|| dates2.contains(new SimpleDateFormat(
									"dd/MM/yyyy", Locale.getDefault())
									.format(item.getDate().getTime()))) {
						Set<Course> courses1 = Student.courseAssignments
								.keySet();
						info += "Assignment:\n";
						if (!dates2.contains(new SimpleDateFormat("dd/MM/yyyy",
								Locale.getDefault()).format(item.getDate()
								.getTime()))) {
							info += "No Assignment Today\n";
						}
						for (Course cur : courses1) {
							for (Assignment assign : Student.courseAssignments
									.get(cur)) {
								if (assign.getDate().equals(
										new SimpleDateFormat("dd/MM/yyyy",
												Locale.getDefault())
												.format(item.getDate()
														.getTime()))) {
									info += (assign.getCode() + " "
											+ assign.getName() + "\n");
									info += ("Due at " + assign.getTime() + "\n");
								}
							}
						}
						Set<Course> courses2 = Student.courseTests.keySet();
						info += "Test:\n";
						if (!dates1.contains(new SimpleDateFormat("dd/MM/yyyy",
								Locale.getDefault()).format(item.getDate()
								.getTime()))) {
							info += "No Test Today\n";
						}
						for (Course cur : courses2) {
							for (Test test : Student.courseTests.get(cur)) {
								if (test.getDate().equals(
										new SimpleDateFormat("dd/MM/yyyy",
												Locale.getDefault())
												.format(item.getDate()
														.getTime()))) {
									info += (test.getCode() + " "
											+ test.getName() + "\n");
									info += ("From " + test.getFrom() + " to "
											+ test.getTo() + " at "
											+ test.getLocation() + "\n");
								}
							}
						}

						createDialog(v, item, info);
						info = "";
					} else {
						createDialog2(v, item);
					}

				} else {
					createDialog2(v, item);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dates1 = getAllDates1();
		dates2 = getAllDates2();
		// adapter.notifyDataSetChanged();
	}

	/**
	 * Creates a dialog box for searching patients by health card number.
	 * */
	private void createDialog(View view, CardGridItem item, String info) {
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
				.setTitle(
						getResources().getString(
								R.string.sel_date,
								new SimpleDateFormat("yyyy-MM-dd", Locale
										.getDefault()).format(item.getDate()
										.getTime())))
				.setMessage(info)
				.setCancelable(true)
				// Sets a button on the left for submitting data.
				.setNegativeButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Needs to get context in order to read
								// values from the text boxes.
								createChooseEventDialog(dialog);
								// Checks if a patient exists with the input
								// health
								// card number.
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

	/**
	 * Creates a dialog box for searching patients by health card number.
	 * */
	private void createDialog2(View view, CardGridItem item) {
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
				.setTitle(
						getResources().getString(
								R.string.sel_date,
								new SimpleDateFormat("yyyy-MM-dd", Locale
										.getDefault()).format(item.getDate()
										.getTime())))

				.setMessage("No event today")
				.setCancelable(true)
				// Sets a button on the left for submitting data.
				.setNegativeButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Needs to get context in order to read
								// values from the text boxes.
								createChooseEventDialog(dialog);
								// Checks if a patient exists with the inputted
								// health
								// card number.
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

	private void createChooseEventDialog(DialogInterface dialog) {
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
				.setTitle("Which event you want to add")
				.setItems(R.array.events_array,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// The 'which' argument contains the index
								// position
								// of the selected item
								if (which == 0) {
									launchIntentAddEventActivity();
								} else if (which == 1) {
									launchIntentAddLectureActivity();
								}
							}
						})

				.setCancelable(true)
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

	public Set<String> getAllDates1() {
		Collection<List<Test>> tests = Student.courseTests.values();

		Set<String> dates = new HashSet<String>();
		if (tests != null) {
			for (List<Test> list_test : tests) {
				if (list_test != null) {
					for (Test test : list_test) {
						dates.add(test.getDate());
					}
				}
			}
		}
		return dates;
	}

	public Set<String> getAllDates2() {
		Collection<List<Assignment>> assignments = Student.courseAssignments
				.values();
		Set<String> dates = new HashSet<String>();
		if (assignments != null) {
			for (List<Assignment> list_assignment : assignments) {
				if (list_assignment != null) {
					for (Assignment assignment : list_assignment) {
						dates.add(assignment.getDate());
					}
				}
			}
		}
		return dates;
	}

	private void launchIntentAddEventActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddAssignmentActivity.class);
		intent.putExtra("clickDate", clickDate);
		startActivity(intent);
	}

	private void launchIntentAddLectureActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddTestActivity.class);
		intent.putExtra("clickDate", clickDate);
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
