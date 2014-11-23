package com.wt.calendarcardsample;

import java.util.HashMap;
import java.util.Map;

import com.calendarcardsample.backend.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	private String connect;

	/** The amount of time this activity will display the splash screen. */
	protected int SPLASH_TIME = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Sets main layout as a splash screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		new Student(getApplicationContext());
		String url = "http://dev-firmament-772.appspot.com/index.php/api/calendar/courses";
		// 向服务器端提交参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("var1", "123");
		map.put("var2", "234");
		try {
			connect = Student.sendGetRequest(url, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		displaySplashScreen();

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
					Intent intent = new Intent(getApplicationContext(),
							MenuActivity.class);
					intent.putExtra("connect", connect);
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
