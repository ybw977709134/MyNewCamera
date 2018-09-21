package com.onemeter.mynewcamera.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.JSDKLanguage;
import com.anyan.client.sdk.base.web.JAYWebCfg;
import com.onemeter.mynewcamera.entity.device_info;
import com.onemeter.mynewcamera.entity.userInfo;
import com.onemeter.mynewcamera.net.NetChangeReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * 自定义Application启动类
 */
public class MyApplication extends Application {
	/**登陆用户返回信息**/
	public static List<userInfo>  userInfos;
	/** 网络是否可用的标识 **/
	public static boolean isNetAvailable = false;
	/** 网络状态改变接收器 **/
	public NetChangeReceiver receiver;
	public  static   List<String>  device_id;
	public  static   List<String>  device_token;
	public  static   List<String>  device_name;
	public  static   List<String>  device_type;
	public  static   List<String>  device_return_name;
	//本地缓存图片的路径
	public  static Map<String,String> device_path;//私有的图片路径
	public  static Map<String,String> device_gy_path;//共享的图片路径
	public  static  List<device_info>  device_position;//标记图片的位置

	public  static  Map<String,String> device_gc_path;//广场图片的路径
	public  static  List<String>   device_gc_position;//标记图片的位置

	@Override
	public void onCreate() {
		super.onCreate();
        //登陆返回结果
		device_id=new ArrayList<String>();
		device_token=new ArrayList<String>();
		userInfos=new ArrayList<userInfo>();
		device_name=new ArrayList<String>();
		device_type=new ArrayList<String>();
		device_return_name=new ArrayList<String>();
		device_position=new ArrayList<device_info>();
		device_path=new HashMap<String,String>();
		device_gy_path=new HashMap<String,String>();

		device_gc_path=new HashMap<String,String>();
		device_gc_position=new ArrayList<String>();
		//网络状态是否连通
		isNetAvailable = isNetworkConnected();
		receiver = new NetChangeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addCategory("android.intent.category.DEFAULT");
		registerReceiver(receiver, filter);// 注册广播接收器receiver
	}

	/**
	 * 初始化安眼sdk
	 */
	public static void initSDK() {
		 String mCurLang = JSDKLanguage.Chinese_Simplified;//设置语言
		 String  oemkey = "ay_d62eb7ccfff1fdc030a01a115041792f792984b48f4b6a17ed19b6d1562288fb6d671cf6912853aea4a62550c24cdfc1";
//		 String  oemkey = "anyan";
		JAYWebCfg cfg = new JAYWebCfg();
		int flagTag = 1;
		switch (flagTag) {
			case 0:
				//依儿鸭
				cfg.setFlag(JAYWebCfg.WebFlag.EN_FLAG_AY_EDU_SUPER);
				break;
			case 1:
				//安眼、教育
				cfg.setFlag(JAYWebCfg.WebFlag.EN_FLAG_NONE);
				break;
			case 2:
				break;
		}

		if(false){
			ClientModel.getClientModel().initSDK(cfg);
			ClientModel.getClientModel().SetLanguage(mCurLang);
			ClientModel.getClientModel().SetOemKey(oemkey);
			if(true) ClientModel.getClientModel().setPhoneType();

		}else{
			if(ClientModel.getClientModel().initSDK(cfg, oemkey, mCurLang)) {
				Log.i("log", "初始化成功");
			} else {
				Log.i("log", "初始化失败");
			}

		}

		java.util.Timer timer = new java.util.Timer(true);
		ClientModel.getClientModel().startDiscoveryDevice();
		TimerTask task = new TimerTask() {
			public void run() {
				ClientModel.getClientModel().stopDiscoveryDevice();
			}
		};
		timer.schedule(task, 10 * 1000);

	}

	/**
	 * 判断网络状态是否可用
	 *
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		} else {
			return false;
		}
	}

}
