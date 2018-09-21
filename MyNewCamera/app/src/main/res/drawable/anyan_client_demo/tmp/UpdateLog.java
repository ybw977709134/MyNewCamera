package drawable.anyan_client_demo.tmp;

import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anyan.client.model.ClientModel;

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

	public static void update(String TAG, String info) {
		if(Logger.LOG_LEVEL > Logger.INFO) {
			Logger.i(TAG, info);
		}
	}
	
	public static void updateI(String TAG, String info) {
		if(Logger.LOG_LEVEL > Logger.INFO) {
			Logger.i(TAG, info);
			append(TAG, info);
		}
	}

	public static void updateD(String TAG, String info) {
		if(Logger.LOG_LEVEL >Logger. DEBUG) {
			Logger.d(TAG, info);
			append(TAG, info);
		}
	}

	public static void updateW(String TAG, String info) {
		if(Logger.LOG_LEVEL > Logger.WARN) {
			append(TAG, info);
			Logger.w(TAG, info);
		}
	}

	public static void updateE(String TAG, String info) {
		if(Logger.LOG_LEVEL > Logger.ERROR) {
			Logger.e(TAG, info);
			append(TAG, info);
		}
	}

	public static void updateV(String TAG, String info) {
		if(Logger.LOG_LEVEL > Logger.VERBOS) {
			Logger.v(TAG, info);
			append(TAG, info);
		}
	}
	
	public static void clean(String TAG) {
		if(Logger.LOG_LEVEL > Logger.INFO) {
			mTxtInfo.setText("Clean screen ok\n");
			Logger.i(TAG, "Clean screen ok");
		}
	}

	private static void append(String TAG, String info) {
		if (mTxtInfo != null)
			mTxtInfo.append(info + "\n");

		if(TAG.equals("__MainActivity")) {
			ShowLine = 28 + 1;
		} else if(TAG.equals("__DevicePlay")) {
			ShowLine = 15 + 1;
		}

		if(mTxtInfo.getLineCount() > ShowLine)
			mTxtInfo.scrollTo(0, (mTxtInfo.getLineCount() - ShowLine) * mTxtInfo.getLineHeight());
		else
			mTxtInfo.scrollTo(0, 0);
	}

/*	private void getNetJDeviceBasic(final String deviceId) {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < deviceBasics.size(); i++) {
					if (deviceId.equals(deviceBasics.get(i).getDeviceSN())) {
						System.out.println("deviceId:"+deviceId);
						if (deviceBasics.get(i).getDeviceStatus() != JDeviceBasic.DeviceStatus.Offline) {
							ClientModel.getClientModel().StopPlay();
							System.out.println("getDeviceOwner():" + deviceBasics.get(i).getDeviceOwner());
							ClientModel.getClientModel().SetCurDevice( deviceBasics.get(i).getDeviceOwner(), deviceId);
							ClientModel.getClientModel().SetSurface(mSurfaceView.getHolder().getSurface());

							ClientModel.getClientModel().LiveAndPlay();
							mRateUpdateHandler.sendEmptyMessage(100);
						} else {
							CustomToast.showToast("当前设备不在线");
						}
						return;
					}
				}
				ClientModel.getClientModel().StopPlay();
				CustomToast.showToast("当前设备不在线");

			}
		});
	}*/
}
