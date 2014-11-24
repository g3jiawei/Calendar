package com.wt.calendarcardsample;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class CountDownActivity extends Activity {

	Button btnStart, btnStop;
	TextView textViewTime;
	TextView textViewTime2;
	TextView view1;
	TextView view2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countdown);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		textViewTime2 = (TextView) findViewById(R.id.textViewTime2);
		view1 = (TextView) findViewById(R.id.textView1);
		view2 = (TextView) findViewById(R.id.textView2);
		textViewTime.setText("00:00:00");
		textViewTime2.setText("00:00:00");
		view1.setText("Assignment:");
		view2.setText("Test:");

		// Calendar calendar = Calendar.getInstance();
		// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// final String currentDate = df.format(calendar.getTime());
		// long diffInMillies = calendar.getTime() - calendar.getTime();
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(2013, 07, 05, 13, 30);
		calendar2.set(2013, 07, 05, 14, 30);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff1 = milliseconds2 - milliseconds1;
		
		Calendar calendar3 = Calendar.getInstance();
		Calendar calendar4 = Calendar.getInstance();
		calendar3.set(2013, 07, 04, 14, 30);
		calendar4.set(2013, 07, 05, 18, 30);
		long milliseconds3 = calendar3.getTimeInMillis();
		long milliseconds4 = calendar4.getTimeInMillis();
		long diff2 = milliseconds4 - milliseconds3;
		
		final CounterClass timer = new CounterClass(diff1, 1000);
		final CounterClass2 timer2 = new CounterClass2(diff2, 1000);
		
		timer.start();
		timer2.start();
		
		btnStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				timer.start();
			}
		});

		btnStop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				timer.cancel();
			}
		});
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
