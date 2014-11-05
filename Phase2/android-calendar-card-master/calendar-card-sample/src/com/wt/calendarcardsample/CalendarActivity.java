package com.wt.calendarcardsample;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

import com.calendarcardsample.backend.Student;
import com.wt.calendarcard.CalendarCardPager;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

public class CalendarActivity extends Activity {
	private Student student;

	private CalendarCardPager mCalendarCardPager;

	// private TextView mTextView;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		Intent intent = getIntent();
		student = (Student) intent.getSerializableExtra("studentKey");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		
		mCalendarCardPager = (CalendarCardPager) findViewById(R.id.calendarCard1);
		mCalendarCardPager.setOnCellItemClick(new OnCellItemClick() {
			@Override
			public void onCellClick(View v, CardGridItem item) {
				createDialog(v, item);
			}
		});

		// mTextView = (TextView)findViewById(R.id.textView1);
	}

	/**
	 * Creates a dialog box for searching patients by health card number.
	 * */
	private void createDialog(View view, CardGridItem item) {
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

	private void launchIntentAddCourseActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddCourseActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("nurse", nurse);
		intent.putExtra("studentKey", student);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void launchIntentAddEventActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddAssignmentActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("nurse", nurse);
		intent.putExtra("studentKey", student);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void launchIntentAddLectureActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddTestActivity.class);
		Bundle bundle = new Bundle();
		// bundle.putSerializable("nurse", nurse);
		intent.putExtra("studentKey", student);
		intent.putExtras(bundle);
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
	      super. onBackPressed();
	      return true;
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}

}
