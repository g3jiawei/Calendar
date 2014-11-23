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
	private Button mSendReqBtn = null;// ��������İ�ť
	private WebView mWebView = null;// ������ʾ�����������html�ַ����ķ�ʽ��ʾ��Ӧ�����������ʹ��WebView�Լ��ķ�ʽ����URL

	// ��Ӧ
	private HttpResponse mHttpResponse = null;
	// ʵ��
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
			// ����һ���������
			HttpGet httpGet = new HttpGet("https://www.google.ca/");
			// ����һ��Http�ͻ��˶���
			HttpClient httpClient = new DefaultHttpClient();

			// ����ʹ��Http�ͻ��˷������󣬲���ȡ��Ӧ����

			InputStream inputStream = null;
			try {
				// �������󲢻����Ӧ����
				mHttpResponse = httpClient.execute(httpGet);
				// �����Ӧ����Ϣʵ��
				mHttpEntity = mHttpResponse.getEntity();

				// ��ȡһ��������
				inputStream = mHttpEntity.getContent();

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));

				String result = "";
				String line = "";

				while (null != (line = bufferedReader.readLine())) {
					result += line;
				}

				// �������ӡ������������LogCat�鿴
				System.out.println(result);

				// ����������WebView��ʾ
				mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
				// ֱ��ʹ��mWebView.loadData(result, "text/html", "utf-8");����ʾ�Ҳ�����ҳ

				// ��������ķ�ʽ����������ʾ�����ǱȽϿ��϶��ɼ��ٶ�logo��
				mWebView.loadDataWithBaseURL(null, result, "text/html","utf-8", null);

				// ֱ������URLҲ������ʾҳ�棨���Ǵ�������Ҫ��Ϊ����֤��Ӧ���ص��ַ����Ƿ���ȷ�����Բ����������д��룩
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