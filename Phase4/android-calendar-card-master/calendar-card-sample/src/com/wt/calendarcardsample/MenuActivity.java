package com.wt.calendarcardsample;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.calendarcardsample.backend.Student;

public class MenuActivity extends Activity {

	private Student student;
	//private String connect;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

//		Intent intent = getIntent();
//		connect = (String) intent.getSerializableExtra("connect");

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		// String url =
		// "http://dev-firmament-772.appspot.com/index.php/api/calendar/courses";
		// // ������������������
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("var1", "123");
		// map.put("var2", "234");
		// try {
		// connect = Student.sendGetRequest(url, map);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// connect = "error";
		// e.printStackTrace();
		// }

		// new CountDownTimer(5000, 1000) {//
		// CountDownTimer(edittext1.getText()+edittext2.getText())
		// // also parse it to long
		// TextView mTextField = (TextView) findViewById(R.id.timer1);
		//
		// public void onTick(long millisUntilFinished) {
		// mTextField.setText("Loading: " + millisUntilFinished
		// / 1000);
		// // here you can have your logic to set text to edittext
		// }
		//
		// public void onFinish() {
		// mTextField.setText("info is :"+connect);
		// }
		// }.start();
	}

	public void handleSample1(View v) {
		Intent intent = new Intent(this, CountDownActivity.class);
		// intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	public void handleSample2(View v) {
		Intent intent = new Intent(this, CalendarActivity.class);
		// intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	public void handleadd(View v) {
		Intent intent = new Intent(this, AddCourseActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
	}

	// student = (Student) intent.getSerializableExtra("studentKey");
	// course = (Course) intent.getSerializableExtra("courseKey");}

	/**
	 * Attempt to go back to menu page when back button is clicked.
	 * 
	 * @param view
	 */
	public void viewCourseList(View view) {
		Intent intent = new Intent(this, CourseListActivity.class);
		intent.putExtra("studentKey", student);
		startActivity(intent);
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
