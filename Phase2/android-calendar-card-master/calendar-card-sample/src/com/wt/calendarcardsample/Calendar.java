package com.wt.calendarcardsample;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wt.calendarcard.CalendarCardPager;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

public class Calendar extends Activity {
	
	private CalendarCardPager mCalendarCardPager;
	//private TextView mTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample2);
		mCalendarCardPager = (CalendarCardPager)findViewById(R.id.calendarCard1);
		mCalendarCardPager.setOnCellItemClick(new OnCellItemClick() {
			@Override
			public void onCellClick(View v, CardGridItem item) {
				createFindPatientDialog(v,item);
			}
		});
		
		
		//mTextView = (TextView)findViewById(R.id.textView1);
	}
	
	/** 
	 * Creates a dialog box for searching patients by health card number.
	 * */
	private void createFindPatientDialog(View view, CardGridItem item){
		// Uses a view from xml files in order to allow edittext boxes.
		//LayoutInflater li = LayoutInflater.from(this);
		//View promptsView = li.inflate(R.layout.activity_newevent_dialog,null);
		// Builds the dialog box.
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.
				Builder(this);
		//alertDialogBuilder.setView(promptsView);
		
		alertDialogBuilder
			// Makes user unable to leave dialog by clicking
			// outside its borders.
			.setTitle(getResources().getString(R.string.sel_date,new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.getDate().getTime())))
			.setCancelable(true)
			// Sets a button on the left for submitting data.
			.setPositiveButton("Add", 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					// Needs to get context in order to read
					// values from the text boxes.
					launchIntentAddEventActivity();
					// Checks if a patient exists with the inputted health
					// card number.
				}
		  	})
		  	// Sets a button on the right for exiting the dialog.
		  	.setNegativeButton("Cancel",
		  			new DialogInterface.OnClickListener() {
		  		public void onClick(DialogInterface dialog,
		  				int id) {
		  			// Closes dialog when user chooses to cancel.
		  			dialog.cancel();
		  		}
		  	});

		// Create and show the alert dialog.
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private void launchIntentAddEventActivity() {
		// Only sends nurse to the adding patient screen, as only nurses can
		// access it.
		Intent intent = new Intent(this, AddEventActivity.class);
		Bundle bundle = new Bundle();
		//bundle.putSerializable("nurse", nurse);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
