package com.onemeter.mynewcamera.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.AYClientSDKCallBack;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.utils.SDcardTools;

import java.text.SimpleDateFormat;

/**
 * 描述：
 * 项目名称：MyCamera
 * 作者：Administrator
 * 时间：2016/6/23 15:00
 * 备注：
 */
public class ShareCameraPlayerActivity extends BaseActivity implements View.OnClickListener, AYClientSDKCallBack{
    private ToggleButton mTbtnZoom;//全屏
    private TextView mTxtRate;//网速
    private SurfaceView mSurfaceView;

    private ProgressBar mProLoading;//进度条
    private boolean mUprate = true;
    private String mNetworkBits = " 0.00KB/S";
    private int mChannelIndex = 1, mRate = 700;
    private Handler mRateUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mTxtRate.setText(mNetworkBits);
                    mUprate = true;
                    break;
                case 0x02:
                    mProLoading.setVisibility(View.GONE);
                    break;
                case 0x03:
                    //截屏功能
                       getJiePing();
                     break;
                default:
                    break;
            }
        }
    };

    private ImageView camera_setting_back;//返回键

    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_camera_main);
        initView();
        ClientModel.getClientModel().SetSDKCallback(this);
        ClientModel.getClientModel().LiveAndPlay();

    }


    /**
     * 初始化组件
     */

    private void initView() {
        camera_setting_back= (ImageView) findViewById(R.id.camera_setting_back);
        mTxtRate = (TextView) findViewById(R.id.txt_rate);
        mTxtRate.setText(mNetworkBits);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
//        mSurfaceHolder = mSurfaceView.getHolder();
        ClientModel.getClientModel().SetSurface(mSurfaceView);
        mProLoading = (ProgressBar) findViewById(R.id.pro_loading);

        mTbtnZoom = (ToggleButton) findViewById(R.id.tbtn_control_zoom);
        mTbtnZoom.setOnClickListener(this);
        camera_setting_back.setOnClickListener(this);

//        mSurfaceHolder.addCallback(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_setting_back:
                //返回键
                finish();
                break;
            case R.id.tbtn_control_zoom:
                //播放全屏切换
                ChangeLandscape();
                break;
            default:
                break;
        }
    }

    /**
     * 对视频截屏的方法
     */

    private void getJiePing() {
        if (ClientModel.getClientModel().GetScreenShot(SDcardTools.getSDPath() + "/abcdefg" + MyApplication.device_gc_position.get(0)+ ".jpg")) {
//                toast(SDcardTools.getSDPath() + "/abcdefg" + MyApplication.device_gc_position.get(0)+ ".jpg");
            MyApplication.device_gc_path.put(MyApplication.device_gc_position.get(0), SDcardTools.getSDPath() + "/abcdefg" + MyApplication.device_gc_position.get(0) + ".jpg");

        } else {
            toast(ClientModel.getClientModel().mLastErrorDesc);
        }
    }


    private void ChangeLandscape() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void OnStatusMsg(int i, String s) {

    }

    @Override
    public void OnPlaystateChange(String device_id, int idx, int rate, int state, String msg) {
        switch (state) {
            case MessageNum.AY_NET_STAT:
                if (mUprate) {
                    mUprate = false;
                    //mNetworkBits = msg.substring(0, msg.indexOf("/") + 2); //隐藏以缓冲数据大小
                    mNetworkBits = msg;
                    mRateUpdateHandler.sendEmptyMessageDelayed(0x01, 1000);
                }
                break;
            case MessageNum.AY_SESSION_RECV_KEY_FRAME:
                mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 0);
                break;
            case MessageNum.AY_SESSION_RECV_TS:
                mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 1000);
                break;
            case MessageNum.AY_PLAYER_LOADING_END:
                mRateUpdateHandler.sendEmptyMessageDelayed(0x03, 1000);
                break;

        }
    }

    @Override
    public void OnNvrHistoryList(String s, int i, int i1, int i2, int[] ints, int[] ints1) {
    }

    @Override
    public void OnRecvOEMData(byte[] bytes, int i) {

    }

    @Override
    public void OnApiState(int i, String s) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //竖屏
            if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
               finish();
            }else{//横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT  );
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ClientModel.getClientModel().Quit()) {
            mRateUpdateHandler.removeMessages(0x01);
        }

//        mSurfaceHolder.removeCallback(this);

    }
}
