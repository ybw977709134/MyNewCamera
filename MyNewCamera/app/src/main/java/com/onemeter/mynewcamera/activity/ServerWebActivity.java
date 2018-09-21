package com.onemeter.mynewcamera.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

/**
 * 描述：隐私条款web页面
 * 项目名称：MyNewCamera
 * 作者：angelyin
 * 时间：2016/7/7 19:09
 * 备注：
 */
public class ServerWebActivity extends BaseActivity implements View.OnClickListener {
    private ImageView web_btn_back;//返回键
    private WebView  web_main;//隐私条款web页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_web_main);
        initView();

    }


    /**
     * 初始化
     */
    private void initView() {
        web_btn_back= (ImageView) findViewById(R.id.web_btn_back);
        web_main= (WebView) findViewById(R.id.web_main);
        web_main.loadUrl("file:///android_asset/indexF.html");

        web_btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_btn_back:
                //返回键
                finish();
                break;
        }
    }
}
