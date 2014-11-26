package Elvenware.MyTester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.os.AsyncTask;
import android.os.Bundle;

public class MyTesterActivity extends Activity {	
	TextView textView;
	
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);        
        tv.setText(R.string.Joe);
        
        setContentView(R.layout.main);
        registerForContextMenu(tv);
    }
    
    public void onGetClick(View v) 
    {
    	TextView textView = (TextView)findViewById(R.id.viewText1);
    	new HttpGetDemo().execute(textView);    	
    }
    
    public void onPostClick(View v) 
    {
    	textView = (TextView)findViewById(R.id.viewText1);
    	new CopyOfHttpPostDemo().execute(new String[] { "1", "3", "4" });    	
    	new CopyOfHttpPostDemo().execute(new String[] { "2", "4", "6" });
    	new CopyOfHttpPostDemo().execute(new String[] { "9", "7", "16" });
    }
    
    public class CopyOfHttpPostDemo extends AsyncTask<String, Void, String> 
    {    	
    	
    	@Override
    	protected String doInBackground(String... params) 	
    	{    		
    		BufferedReader inBuffer = null;
    		String url = "http://dev-firmament-772.appspot.com/index.php/api/calendar/courses";
    		String result = "fail";
    		try {
    			HttpClient httpClient = new DefaultHttpClient();
    			HttpPost request = new HttpPost(url);
    			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    			postParameters.add(new BasicNameValuePair("operanda", params[0]));
    			postParameters.add(new BasicNameValuePair("operandb", params[1]));
    			postParameters.add(new BasicNameValuePair("answer", params[2]));
    			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
    					postParameters);

    			request.setEntity(formEntity);
    			HttpResponse httpResponse = httpClient.execute(request);
    			inBuffer = new BufferedReader(
    					new InputStreamReader(
    							httpResponse.getEntity().getContent()));

    			StringBuffer stringBuffer = new StringBuffer("");
    			String line = "";
    			String newLine = System.getProperty("line.separator");
    			while ((line = inBuffer.readLine()) != null) {
    				stringBuffer.append(line + newLine);
    			}
    			inBuffer.close();

    			result = stringBuffer.toString();
    			
    		} catch(Exception e) {
    			// Do something about exceptions
    			result = e.getMessage();
    		} finally {
    			if (inBuffer != null) {
    				try {
    					inBuffer.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    		return result;
    	}
    	
    	protected void onPostExecute(String page)
    	{    	
        	textView.setText(page);    	
    	}	
    }  
}