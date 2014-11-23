package com.calendarcardsample.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class Student implements Serializable {

	private static String file1 = "file1.txt";
	private static String file2 = "file2.txt";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Test> tests = new ArrayList<Test>();
	List<Assignment> assignments = new ArrayList<Assignment>();
	List<String> courses;
	public static HashMap<Course, List<Test>> courseTests;
	public static HashMap<Course, List<Assignment>> courseAssignments;
	private static final String ENCODE = "utf-8";  
	public static final int TIMEOUT = 30000;// 30秒

	/**
	 * Constructs a User by initializing patients to saved data, if it exists.
	 */

	public Student(Context fileContext) {

		courseTests = new HashMap<Course, List<Test>>();
		courseAssignments = new HashMap<Course, List<Assignment>>();

		File file1 = fileContext.getFileStreamPath("file1");
		File file2 = fileContext.getFileStreamPath("file2");
		
		

		if (file1.exists()) {
			saveTests(fileContext);
		}
		loadTests(fileContext);
		if (file2.exists()) {
			saveAssignments(fileContext);
		}
		loadAssignments(fileContext);
	}

	/**
	 * Saves the data that this User object has accumulated into a file.
	 */
	@SuppressWarnings("static-access")
	public static void saveTests(Context fileContext) {
		FileOutputStream fos;
		try {
			fos = fileContext.openFileOutput(file1, fileContext.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(courseTests);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the data saved in Map.
	 */

	@SuppressWarnings("unchecked")
	public static void loadTests(Context fileContext) {
		try {
			FileInputStream fis = fileContext.openFileInput(file1);
			ObjectInputStream in = new ObjectInputStream(fis);
			try {
				courseTests = (HashMap<Course, List<Test>>) in.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the data that this User object has accumulated into a file.
	 */
	@SuppressWarnings("static-access")
	public static void saveAssignments(Context fileContext) {
		FileOutputStream fos;
		try {
			fos = fileContext.openFileOutput(file2, fileContext.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(courseAssignments);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the data saved in Map.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void loadAssignments(Context fileContext) {
		try {
			FileInputStream fis = fileContext.openFileInput(file2);
			ObjectInputStream in = new ObjectInputStream(fis);
			try {
				courseAssignments = (HashMap<Course, List<Assignment>>) in
						.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 通过get方式提交参数给服务器 
     */  
    public static String sendGetRequest(String urlPath, Map<String, String> params)  
            throws Exception {  
        //根据传进来的链接和参数构建完整的url  
        StringBuilder sb = new StringBuilder(urlPath);  
        if (params != null) {  
            sb.append('?');  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                sb.append(entry.getKey()).append('=')  
                        .append(URLEncoder.encode(entry.getValue(), ENCODE))  
                        .append('&');  
            }  
        }  
        // 删掉多余的&  
        sb.deleteCharAt(sb.length() - 1);  
        Log.d("URL:", sb.toString());  
        //打开链接，发送请求  
        URL url = new URL(sb.toString());  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setRequestProperty("Content-Type", "text/xml");  
        conn.setRequestProperty("Charset", ENCODE);  
        conn.setConnectTimeout(TIMEOUT);  
        Log.v("REQUEST", "服务器响应码：" + conn.getResponseCode());  
        // 如果请求响应码是200，则表示成功  
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {  
            // 获得服务器返回的数据  
            BufferedReader ins = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), ENCODE));  
            String retData = null;  
            String responseData = "loading:  ";  
            while ((retData = ins.readLine()) != null) {  
                responseData += retData;  
            }  
            ins.close();  
            //System.out.println(responseData);
            return responseData;  
        }else{
        	//System.out.println("NONONONONONONONONONONO!");
        }
        return "sendGetRequest error!";  
    }
 
 
}
