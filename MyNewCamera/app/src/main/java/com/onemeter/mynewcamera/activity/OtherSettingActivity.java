package com.onemeter.mynewcamera.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

/**
 * 描述：其他设置页面
 * 项目名称：MyCamera
 * 时间：2016/6/21 17:39
 * 备注：
 */
public class OtherSettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView other_setting_back;//返回键
    private RelativeLayout  restart_btn;//重启摄像机
    private RelativeLayout  resetting_btn;//恢复出厂设置
    private TextView  camera_ver_btn_text;//固件版本
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_other_setting_main);
//        MyApplication.initSDK();
        initView();

    }

    /**
     * 初始化组件
     */
    private void initView() {
        other_setting_back= (ImageView) findViewById(R.id.other_setting_back);
        restart_btn= (RelativeLayout) findViewById(R.id.restart_btn);
        resetting_btn= (RelativeLayout) findViewById(R.id.resetting_btn);
        camera_ver_btn_text= (TextView) findViewById(R.id.camera_ver_btn_text);

        other_setting_back.setOnClickListener(this);
        resetting_btn.setOnClickListener(this);
        restart_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.other_setting_back:
                //返回键
                finish();
                break;
            case R.id.resetting_btn:
                // 恢复出厂(无效)
//                if(MachineControl.getInstance().SetReset(MachineControl.Control_Switch_On)) {
//                    toast("恢复出厂设置成功");
//                } else {
//                    toast(ClientModel.getClientModel().mLastErrorDesc);
//                }
                break;
            case R.id.restart_btn:
                //重启摄像机（无效）
//                if(MachineControl.getInstance().SetReboot(MachineControl.Control_Switch_On)) {
//                    toast("重启成功");
//                } else {
//                    toast(ClientModel.getClientModel().mLastErrorDesc);
//                }
                break;

        }
    }
}
