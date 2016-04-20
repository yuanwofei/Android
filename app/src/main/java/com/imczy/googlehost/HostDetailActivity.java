package com.imczy.googlehost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.imczy.googlehost.util.CloseUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by chenzhiyong on 16/4/17.
 */
public class HostDetailActivity extends AppCompatActivity {
	private static final String TAG = "HostDetailActivity";

	TextView mTextView;
	ProgressDialog mProgressDialog;
	StringBuffer hostString = new StringBuffer();

	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mTextView = (TextView) findViewById(R.id.detail_txt);


		showProgressDialog();
		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				File host = new File(Constants.SYSTEM_HOST_FILE_PATH);
				BufferedReader bre = null;
				try {
					bre = new BufferedReader(new FileReader(host));//此时获取到的bre就是整个文件的缓存流
					String str;
					while ((str = bre.readLine()) != null) { // 判断最后一行不存在，为空结束循环
						hostString.append(str).append("\n");
					}

					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mTextView.setText(hostString);
							dismissDialog();
						}
					}, 300);
				} catch (Exception e) {
					showProgressDialog();
					e.printStackTrace();
				} finally {
					CloseUtil.close(bre);
				}
			}
		});

	}

	private void showProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
		}
		mProgressDialog.setTitle("loading ....");
		mProgressDialog.show();
	}

	private void dismissDialog() {
		if (mProgressDialog == null) {
			return;
		}
		mProgressDialog.dismiss();
	}

}
