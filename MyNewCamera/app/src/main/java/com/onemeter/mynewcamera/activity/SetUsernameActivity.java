package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

/** 修改昵称页面
 * Created by G510 on 2016/6/21.
 */
public class SetUsernameActivity extends BaseActivity implements View.OnClickListener {

    private ImageView activity_chose_sex_img_return;//返回键
    private Button  nice_change_queding;//确定
    private EditText  text_setting_nice;//输入框
    String resultCod;
    String user_relname;
    String  relname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_set_username_view);
        resultCod= getIntent().getStringExtra("requestCode");
        user_relname= getIntent().getStringExtra("user_relname");
        initView();
//        MyApplication.initSDK();
    }


    /**
     *初始化组件
     */
    private void initView() {
        activity_chose_sex_img_return= (ImageView) findViewById(R.id.activity_chose_sex_img_return);
        nice_change_queding= (Button) findViewById(R.id.nice_change_queding);
        text_setting_nice= (EditText) findViewById(R.id.text_setting_nice);
        text_setting_nice.setText(user_relname);
        activity_chose_sex_img_return.setOnClickListener(this);
        nice_change_queding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_chose_sex_img_return:
                //返回键
                finish();
                break;
            case R.id.nice_change_queding:
                //确定
                 getNetSetUsername();
                break;
        }
    }


    /**
     * 修改昵称
     */
    private void getNetSetUsername() {
        relname=text_setting_nice.getText().toString();
        if("".equals(relname)){
            toast("请输入昵称");
            return;
        }

        if(ClientModel.getClientModel().SetNickName(relname)){
            toast("修改成功");
            Intent intent=new Intent();
            intent.putExtra("one", relname);
            setResult(1, intent);
            finish();
        }else {
            toast(ClientModel.getClientModel().mLastErrorDesc);

        }

    }
}
