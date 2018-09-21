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

/**
 * 修改密码页面
 * Created by G510 on 2016/6/21.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView change_btn_back;//返回键
    private Button change_password_butoon;//修改按钮
    private EditText editText_olde_password;//旧密码
    private EditText editText_new_password;//新密码
    String olde;
    String newe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_password_view);
        initView();
//        MyApplication.initSDK();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        change_btn_back = (ImageView) findViewById(R.id.change_btn_back);
        change_password_butoon = (Button) findViewById(R.id.change_password_butoon);
        editText_olde_password = (EditText) findViewById(R.id.editText_olde_password);
        editText_new_password = (EditText) findViewById(R.id.editText_new_password);

        change_btn_back.setOnClickListener(this);
        change_password_butoon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_butoon:
                //修改按钮
                getNetChangePass();
                break;
            case R.id.change_btn_back:
                //返回键
                finish();
                break;


        }
    }


    /**
     * 修改密码
     */
    private void getNetChangePass() {
        olde = editText_olde_password.getText().toString().trim();
        newe = editText_new_password.getText().toString().trim();

        if (isEmpty()) {
            if(ClientModel.getClientModel().ChangePassword(olde,newe)){
                toast("密码修改成功");
                Intent  intent=new Intent(ChangePasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }else {
                toast(ClientModel.getClientModel().mLastErrorDesc);
            }

        }


    }


    /**
     * 判断非空
     *
     * @return
     */
    private boolean isEmpty() {
        if ("".equals(olde)) {
            toast("原密码不能为空");
            return false;
        }

        if ("".equals(newe)) {
            toast("新密码不能为空");
            return false;
        }
        return true;
    }
}
