package com.wt.calendarcardsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.calendarcardsample.backend.Course;
import com.calendarcardsample.backend.Student;

public class InternetActivity extends Activity implements OnClickListener {

	TextView textView1;
	private EditText value;
	private EditText name;
	private Button btn;
	private ProgressBar pb;
	CheckBox mCbShowPwd;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet);
		name = (EditText) findViewById(R.id.editText2);
		value = (EditText) findViewById(R.id.editText1);
		btn = (Button) findViewById(R.id.button1);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		btn.setOnClickListener(this);
		pb.setVisibility(View.GONE);
		mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mCbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// checkbox status is changed from uncheck to checked.
				if (!isChecked) {
					// show password
					value.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				} else {
					// hide password
					value.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				}
			}
		});

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(menu);
	// return true;
	// }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (value.getText().toString().length() < 1) {
			// out of range
			Toast.makeText(this, "please enter password", Toast.LENGTH_LONG)
					.show();
		} else if (!(name.getText().toString().trim().toLowerCase()
				.equals("group10"))) {
			Toast.makeText(this, "user doesn't exist", Toast.LENGTH_LONG)
					.show();
		} else if ((value.getText().toString().trim().toLowerCase()
				.equals("123456"))) {
			pb.setVisibility(View.VISIBLE);
			textView1 = (TextView) findViewById(R.id.viewText1);
			new HttpGetDemo().execute(textView1);
			// pb.setVisibility(View.GONE);
			Toast.makeText(this, "loaded", Toast.LENGTH_LONG).show();
			finish();
		} else {
			Toast.makeText(this, "invalid password", Toast.LENGTH_LONG).show();
		}
	}

	public class HttpGetDemo extends AsyncTask<TextView, Void, String> {

		TextView myTextView;
		String result = "fail";

		@Override
		protected String doInBackground(TextView... params) {
			myTextView = params[0];
			return makeHttpRequest();
		}

		final String makeHttpRequest() {
			String url = "http://dev-firmament-772.appspot.com/index.php/api/calendar/courses";
			BufferedReader inStream = null;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpRequest = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpRequest);
				inStream = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));

				StringBuffer buffer = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = inStream.readLine()) != null) {
					buffer.append(line + NL);
				}
				inStream.close();

				result = buffer.toString();

				System.out.println(result);

				String jsonString = result;

				// System.out.println(jsonString);
				JSONObject jsonObject = (JSONObject) new JSONObject(jsonString);
				// System.out.println(jsonObject);
				JSONArray newJSON = jsonObject.getJSONArray("courses");

				// System.out.println(newJSON.toString());

				int i, len;
				boolean exist = false;
				len = newJSON.length();

				for (i = 0; i < len; i++) {
					jsonObject = new JSONObject(newJSON.get(i).toString());
					String courseCode = jsonObject.getString("code");
					String courseTitle = jsonObject.getString("title");
					Set<Course> courses = Student.courseAssignments.keySet();
					for (Course course : courses) {
						if (course.getCode().equals(courseCode)) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						Course.addCourse(courseCode, courseTitle);
						Student.saveAssignments(getApplicationContext());
						Student.saveTests(getApplicationContext());
						Student.loadAssignments(getApplicationContext());
						Student.loadTests(getApplicationContext());
					}
					exist = false;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getCurrentFocus()
				.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		return super.onTouchEvent(event);

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
