package com.wt.calendarcardsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class InternetActivity extends Activity {
	private Button mSendReqBtn = null;// 发送请求的按钮
	private WebView mWebView = null;// 用于显示结果，用载入html字符串的方式显示响应结果，而不是使用WebView自己的方式加载URL

	// 响应
	private HttpResponse mHttpResponse = null;
	// 实体
	private HttpEntity mHttpEntity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet);

		mSendReqBtn = (Button) findViewById(R.id.requestBtn);
		mSendReqBtn.setOnClickListener(mSendClickListener);

		mWebView = (WebView) findViewById(R.id.webview);
	}

	private OnClickListener mSendClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 生成一个请求对象
			HttpGet httpGet = new HttpGet("https://www.google.ca/");
			// 生成一个Http客户端对象
			HttpClient httpClient = new DefaultHttpClient();

			// 下面使用Http客户端发送请求，并获取响应内容

			InputStream inputStream = null;
			try {
				// 发送请求并获得响应对象
				mHttpResponse = httpClient.execute(httpGet);
				// 获得响应的消息实体
				mHttpEntity = mHttpResponse.getEntity();

				// 获取一个输入流
				inputStream = mHttpEntity.getContent();

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));

				String result = "";
				String line = "";

				while (null != (line = bufferedReader.readLine())) {
					result += line;
				}

				// 将结果打印出来，可以在LogCat查看
				System.out.println(result);

				// 将内容载入WebView显示
				mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
				// 直接使用mWebView.loadData(result, "text/html", "utf-8");会显示找不到网页

				// 换成下面的方式可以正常显示（但是比较宽，拖动可见百度logo）
				mWebView.loadDataWithBaseURL(null, result, "text/html","utf-8", null);

				// 直接载入URL也可以显示页面（但是此例子主要是为了验证响应返回的字符串是否正确，所以不用下面这行代码）
				 mWebView.loadUrl("https://www.google.ca/");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	};

}