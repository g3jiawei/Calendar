package Elvenware.MyTester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;
//  
public class HttpGetDemo extends AsyncTask<TextView, Void, String> {
	TextView myTextView;
	String result = "fail";
	
	@Override
	protected String doInBackground(TextView... params) {
		this.myTextView = params[0];
		return makeHttpRequest();
	}
	
	@SuppressWarnings("unchecked")
	final String makeHttpRequest()
	{
		//String url = "http://www.elvenware.com/charlie/development/web/Php/Presidents01.php";
		String url = "http://dev-firmament-772.appspot.com/index.php/api/calendar/courses";
		// String url = "http://www.elvenware.com/cgi-bin/LatLongReadData.py";
		BufferedReader inStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpRequest);
			inStream = new BufferedReader(
					new InputStreamReader(
							response.getEntity().getContent()));

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
		    
			System.out.println(jsonString);
		    JSONObject jsonObject = (JSONObject) new JSONObject(jsonString);
			System.out.println(jsonObject);
		    JSONArray newJSON = jsonObject.getJSONArray("courses");
	        
	        System.out.println(newJSON.toString());
	      
	        
	        int i, len;
	        len = newJSON.length(); 
	        
	        HashMap<String, String> MyMap = new HashMap<String, String>(); 	        
	        
	        for(i=0; i<len; i++){
	        	jsonObject = new JSONObject(newJSON.get(i).toString());
	        	
	        	MyMap.put(jsonObject.getString("code"), jsonObject.getString("title")); 
	        	
		        //System.out.println(jsonObject.getInt("id"));
		        //System.out.println(jsonObject.getString("code"));
		        //System.out.println(jsonObject.getString("title"));
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
	
	protected void onPostExecute(String page)
	{    	
    	myTextView.setText(page);    	
	}	
}
