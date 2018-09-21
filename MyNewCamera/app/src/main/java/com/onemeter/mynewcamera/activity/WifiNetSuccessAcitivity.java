package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

/**
 * 描述：wifi配置成功页面
 * 项目名称：MyCamera
 * 时间：2016/6/17 13:53
 * 备注：
 */
public class WifiNetSuccessAcitivity extends BaseActivity implements View.OnClickListener {
    private Button  toback_for_camera_fragment_btn;//去看我的摄像机按钮
    private ImageView camera_fragment_back;//返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_wifi_over_main);
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        toback_for_camera_fragment_btn= (Button) findViewById(R.id.toback_for_camera_fragment_btn);
        camera_fragment_back= (ImageView) findViewById(R.id.camera_fragment_back);

        toback_for_camera_fragment_btn.setOnClickListener(this);
        camera_fragment_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toback_for_camera_fragment_btn:
               //去看我的摄像机
                Intent  intent=new Intent(WifiNetSuccessAcitivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.camera_fragment_back:
                //返回
                finish();
                break;

        }
    }
}
