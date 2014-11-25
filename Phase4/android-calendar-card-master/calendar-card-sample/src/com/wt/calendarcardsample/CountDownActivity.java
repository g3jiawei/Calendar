package com.wt.calendarcardsample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

import com.calendarcardsample.backend.Assignment;
import com.calendarcardsample.backend.Student;
import com.calendarcardsample.backend.Test;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class CountDownActivity extends Activity {

	private Set<String> dates1;
	private Set<String> dates2;
	TextView textViewTime;
	TextView textViewTime2;
	TextView view1;
	TextView view2;
	long minimum1 = 655360000;
	long minimum2 = 655360000;
	String recentAssignment = "";
	String recentTest = "";
	String recentaCode = "";
	String recenttCode = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countdown);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy/HH:mm:ss");
		Calendar today = Calendar.getInstance();
		String a = dateFormat.format(today.getTime());
		String[] dates0 = a.split("/");
		Integer day0 = Integer.parseInt(dates0[0]);
		Integer month0 = Integer.parseInt(dates0[1]);
		Integer year0 = Integer.parseInt(dates0[2]);
		String time0 = dates0[3];
		String[] times0 = time0.split(":");
		Integer hour0 = Integer.parseInt(times0[0]);
		Integer minute0 = Integer.parseInt(times0[1]);
		Integer second0 = Integer.parseInt(times0[2]);
		today.set(year0, month0, day0, hour0, minute0, second0);

		dates1 = getAllDates1();
		dates2 = getAllDates2();
		// recent assignment
		for (String date : dates1) {
			String[] dates = date.split("/");
			Integer day = Integer.parseInt(dates[0]);
			Integer month = Integer.parseInt(dates[1]);
			Integer year = Integer.parseInt(dates[2]);
			String time = dates[3];
			String assignment = dates[4];
			String aCode = dates[5];
			String[] times = time.split(":");
			Integer hour = Integer.parseInt(times[0]);
			Integer minute = Integer.parseInt(times[1]);
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, minute, 00);
			long milliseconds1 = calendar.getTimeInMillis();
			long milliseconds2 = today.getTimeInMillis();
			long diff = milliseconds1 - milliseconds2;
			if (diff > 0 & diff < minimum1) {
				minimum1 = diff;
				recentAssignment = assignment;
				recentaCode = aCode;
			}
		}
		// recent test
		for (String date : dates2) {
			String[] dates = date.split("/");
			Integer day = Integer.parseInt(dates[0]);
			Integer month = Integer.parseInt(dates[1]);
			Integer year = Integer.parseInt(dates[2]);
			String time = dates[3];
			String test = dates[4];
			String tCode = dates[5];
			String[] times = time.split(":");
			Integer hour = Integer.parseInt(times[0]);
			Integer minute = Integer.parseInt(times[1]);
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, minute, 00);
			long milliseconds1 = calendar.getTimeInMillis();
			long milliseconds2 = today.getTimeInMillis();
			long diff = milliseconds1 - milliseconds2;
			if (diff > 0 & diff < minimum2) {
				minimum2 = diff;
				recentTest = test;
				recenttCode = tCode;
			}

		}

		textViewTime = (TextView) findViewById(R.id.textViewTime);
		textViewTime2 = (TextView) findViewById(R.id.textViewTime2);

		view1 = (TextView) findViewById(R.id.textView1);
		view2 = (TextView) findViewById(R.id.textView2);

		if (minimum1 != 655360000) {
			final CounterClass timer = new CounterClass(minimum1, 1000);
			timer.start();
			view1.setText("Assignment:\n" + recentaCode + " "
					+ recentAssignment + "\nDue in");
		} else {
			view1.setText("No Assignment in the following week.");
			textViewTime.setText("00:00:00");
		}
		if (minimum2 != 655360000) {
			final CounterClass2 timer2 = new CounterClass2(minimum2, 1000);
			timer2.start();
			view2.setText("Test:\n" + recenttCode + " " + recentTest
					+ "\nComing in");
		} else {
			view2.setText("No Test in the following week.");
			textViewTime2.setText("00:00:00");
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public class CounterClass extends CountDownTimer {

		public CounterClass(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			textViewTime.setText("DUE!");
		}

		@SuppressLint("NewApi")
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onTick(long millisUntilFinished) {

			long millis = millisUntilFinished;
			String hms = String.format(
					"%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
									.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis)));
			System.out.println(hms);

			textViewTime.setText(hms);
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public class CounterClass2 extends CountDownTimer {

		public CounterClass2(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			textViewTime2.setText("DUE!");
		}

		@SuppressLint("NewApi")
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onTick(long millisUntilFinished) {

			long millis = millisUntilFinished;
			String hms = String.format(
					"%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
									.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis)));
			System.out.println(hms);

			textViewTime2.setText(hms);
		}
	}

	public Set<String> getAllDates1() {
		Collection<List<Assignment>> assignments = Student.courseAssignments
				.values();
		Set<String> dates = new HashSet<String>();
		if (assignments != null) {
			for (List<Assignment> list_assignment : assignments) {
				if (list_assignment != null) {
					for (Assignment assignment : list_assignment) {
						dates.add(assignment.getDate() + "/"
								+ assignment.getTime() + "/"
								+ assignment.getName() + "/"
								+ assignment.getCode());
					}
				}
			}
		}
		return dates;
	}

	public Set<String> getAllDates2() {
		Collection<List<Test>> tests = Student.courseTests.values();

		Set<String> dates = new HashSet<String>();
		if (tests != null) {
			for (List<Test> list_test : tests) {
				if (list_test != null) {
					for (Test test : list_test) {
						dates.add(test.getDate() + "/" + test.getFrom() + "/"
								+ test.getName() + "/" + test.getCode());
					}
				}
			}
		}
		return dates;
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
