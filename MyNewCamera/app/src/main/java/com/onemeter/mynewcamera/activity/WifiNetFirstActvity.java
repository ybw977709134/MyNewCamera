package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ant.liao.GifView;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

/**
 * 描述：
 * 配置wifi第一步
 * 备注：
 */
public class WifiNetFirstActvity extends BaseActivity implements View.OnClickListener {
    private Button  wifi_first_next_btn;//下一步
    private ImageView camera_fragment_back;//返回键
    private GifView   wifi_first_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_first_main);
        initView();

    }

    /**
     * 初始化
     */
    private void initView() {
        wifi_first_img= (GifView) findViewById(R.id.wifi_first_img);
        wifi_first_img.setGifImage(R.drawable.illu_step1);
        // 添加监听器
        wifi_first_img.setOnClickListener(this);
        // 设置显示的大小，拉伸或者压缩
        wifi_first_img.setShowDimension(1000, 1100);
        // 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
        wifi_first_img.setGifImageType(GifView.GifImageType.COVER);
        wifi_first_next_btn= (Button) findViewById(R.id.wifi_first_next_btn);
        camera_fragment_back= (ImageView) findViewById(R.id.camera_fragment_back);

        wifi_first_next_btn.setOnClickListener(this);
        camera_fragment_back.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_fragment_back:
                //返回键
                finish();
                break;
            case R.id.wifi_first_next_btn:
                //下一步
                Intent  intent=new Intent(WifiNetFirstActvity.this,WifiNetSecondAcitivity.class);
                startActivity(intent);
                break;
        }
    }
}
