package com.onemeter.mynewcamera.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.AYClientSDKCallBack;
import com.anyan.client.sdk.JDeviceBasic;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.view.Switch;

/**
 * 描述： 设备共享设置
 * 项目名称：MyCamera
 * 时间：2016/6/21 11:31
 * 备注：
 */
public class ShareCameraActivity extends BaseActivity implements View.OnClickListener, AYClientSDKCallBack {
    private ImageView camera_setting_back;//返回键
    private Switch camera_isshare_swch_btn;//设备共享开关
    private  Switch   speech_isswch_btn;//语音对讲开关
    private   Switch   yuntai_isswch_btn;//云台操作开关
    private TextView pvu_user_text;//可授权人数
    private  TextView  Ypvu_user_text;//已授权人数
   private ListView pvu_listview;//显示共享设备的用户列表
    Handler mHandler;
    ArrayAdapter<String> _Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_share_main);
        mHandler=new Handler();
//        MyApplication.initSDK();
        initView();
        getinitData();
        ClientModel.getClientModel().SetSDKCallback(this);
    }

    /**
     * 初始化组件
     */
    private void initView() {
          pvu_listview= (ListView) findViewById(R.id.pvu_listview);
        camera_setting_back= (ImageView) findViewById(R.id.camera_setting_back);
        camera_isshare_swch_btn= (Switch) findViewById(R.id.camera_isshare_swch_btn);
        speech_isswch_btn= (Switch) findViewById(R.id.speech_isswch_btn);
        yuntai_isswch_btn= (Switch) findViewById(R.id.yuntai_isswch_btn);
        pvu_user_text= (TextView) findViewById(R.id.pvu_user_text);
        Ypvu_user_text= (TextView) findViewById(R.id.Ypvu_user_text);

        camera_setting_back.setOnClickListener(this);
        camera_isshare_swch_btn.setOnClickListener(this);
        speech_isswch_btn.setOnClickListener(this);
        yuntai_isswch_btn.setOnClickListener(this);

    }


    /**
     * 初始化设备信息
     */
    private void getinitData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ClientModel.getClientModel().QueryDeviceDetailInfo()) {
                    if (ClientModel.getClientModel().mCurDevice.getChannelNum() > 1 && ClientModel.getClientModel().mCurDevice.getDeviceTypeId() != JDeviceBasic.DeviceTypeId.Device_IPC) {
                    } else {
                        if (ClientModel.getClientModel().mCurDevice.getGrantState() == 1) {//初始化设备共享开关
                            camera_isshare_swch_btn.setChecked(true);
                        } else {
                            camera_isshare_swch_btn.setChecked(false);
                        }

                        if (ClientModel.getClientModel().mCurDevice.getSharedPantiltSwitch() == 1) {//初始化云台操作开关
                            yuntai_isswch_btn.setChecked(true);
                        } else {
                            yuntai_isswch_btn.setChecked(false);
                        }

                        if (ClientModel.getClientModel().mCurDevice.getSharedIntercomSwitch() == 1) {//初始化语音对讲开关
                            speech_isswch_btn.setChecked(true);
                        } else {
                            speech_isswch_btn.setChecked(false);
                        }
                        pvu_user_text.setText("2人");
                        Ypvu_user_text.setText(ClientModel.getClientModel().mCurDevice.getDeviceOwner() + 1 + "人");

                    }

                }

            }
        }, 200);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_setting_back:
                finish();
                break;
            case R.id.camera_isshare_swch_btn:
                //设备共享授权开关
                if(camera_isshare_swch_btn.isChecked()) {
                    if (ClientModel.getClientModel().EnableGrant(1)) {
                        toast("共享授权打开");
                    }
                } else {
                    if (ClientModel.getClientModel().DisableGrant(1)) {
                        toast("共享授权关闭");
                    }
                }
                break;
            case R.id.speech_isswch_btn:
                //语音对讲开关
                if (speech_isswch_btn.isChecked()) {
                    ClientModel.getClientModel().EnableSharedIntercomSwitch();
                    toast("语音打开");
                } else {
                    ClientModel.getClientModel().DisableSharedIntercomSwitch();
                    toast("语音关闭");
                }
                break;
            case R.id.yuntai_isswch_btn:
                //云台操作
                if (yuntai_isswch_btn.isChecked()) {
                    if(ClientModel.getClientModel().EnableSharedPantiltSwitch()){
                       toast("云台开");
                    }
                } else {
                    if(ClientModel.getClientModel().DisableSharedPantiltSwitch()){
                        toast("云台关");
                    }

                }
                break;
        }
    }

    @Override
    public void OnStatusMsg(int i, String s) {

    }

    @Override
    public void OnPlaystateChange(String s, int i, int i1, int i2, String s1) {

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





}
