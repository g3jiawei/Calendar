package com.wt.calendarcardsample;

import com.calendarcardsample.backend.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	private Student student;

	/** The amount of time this activity will display the splash screen. */
	protected int SPLASH_TIME = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Sets main layout as a splash screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		displaySplashScreen();

		student = new Student(getApplicationContext());
	}

	/**
	 * Allows splash screen to be displayed for a certain amount of time.
	 */
	protected void displaySplashScreen() {
		// Creates new thread to act as timer.
		Thread logoTimer = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					// Pauses for SPLASH_TIME amount of time
					while (logoTimer < SPLASH_TIME) {
						sleep(100);
						logoTimer = logoTimer + 100;
					}
					// Calls the activity from manifest.xml
					Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
					intent.putExtra("studentKey", student);
					startActivity(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		// Starts the thread.
		logoTimer.start();
	}
}
