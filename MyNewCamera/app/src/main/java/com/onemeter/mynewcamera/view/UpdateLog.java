package com.onemeter.mynewcamera.view;

import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anyan.client.model.ClientModel;
import com.anyan.client.model.tools.ILog;

public class UpdateLog {
	private static ScrollView mScvInfo = null;
	private static TextView mTxtInfo = null;
	private static int ShowLine = 29;

	public static void setTxtInfo(TextView aTxtInfo) {
		mTxtInfo = aTxtInfo;
		mTxtInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	public static void updateLastError(String TAG) {
		UpdateLog.updateI(TAG, "Last error code:" + ClientModel.getClientModel().mLastErrorCode + ", desc:" + ClientModel.getClientModel().mLastErrorDesc);
	}

	public static void updateI(String TAG, String info) {
		ILog.i(TAG, info);
		append(TAG, info);
	}

	public static void d(String TAG, String info) {
		ILog.d(TAG, info);
		append(TAG, info);
	}

	public static void w(String TAG, String info) {
		ILog.w(TAG, info);
		append(TAG, info);
	}

	public static void e(String TAG, String info) {
		ILog.w(TAG, info);
		append(TAG, info);
	}

	public static void v(String TAG, String info) {
		ILog.v(TAG, info);
		append(TAG, info);
	}

	public static void clean(String TAG) {
		mTxtInfo.setText("Clean screen ok\n");
		ILog.i(TAG, "Clean screen ok");
	}

	private static void append(String TAG, String info) {
		if (mTxtInfo != null)
			mTxtInfo.append(info + "\n");

		if(TAG.equals("__MainActivity")) {
			ShowLine = 28 + 1;
		} else if(TAG.equals("__DevicePlay")) {
			ShowLine = 11 + 1;
		}

		if(mTxtInfo.getLineCount() > ShowLine)
			mTxtInfo.scrollTo(0, (mTxtInfo.getLineCount() - ShowLine) * mTxtInfo.getLineHeight());
		else
			mTxtInfo.scrollTo(0, 0);
	}
}
