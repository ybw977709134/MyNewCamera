package com.onemeter.mynewcamera.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.entity.userInfo;
import com.onemeter.mynewcamera.utils.Utils;


/**
 * 描述：
 * 项目名称：MyCamera
 * 时间：2016/6/8 10:31
 * 备注：
 */
 public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText editText_login_username;//账号输入
    private EditText editText_login_password;//密码输入
    private Button btn_login;//登陆按钮
    private String anyan_name;//安眼账号
    private String anyan_password;//安眼密码
    private Button register_btn;//注册按钮
    private Button forget_password_btn;//忘记密码按钮
    private static LoginActivity instance;
    /**
     * 配置账号信息
     */
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = LoginActivity.this;
        initView();
        MyApplication.initSDK();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        forget_password_btn= (Button) findViewById(R.id.forget_password_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        editText_login_username = (EditText) findViewById(R.id.editText_login_username);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        //获取保存的用户名
        sp = getSharedPreferences("userInfo_confing", Context.MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getString("username", "") != null// 设置用户名
                && !sp.getString("username", "").equals("")) {
            editText_login_username.setText(sp.getString("username", ""));
            editText_login_username.setSelection(editText_login_username.getText().toString().length());
        }

        if (sp.getString("password", "") != null// 设置用户密码
                && !sp.getString("password", "").equals("")) {
            editText_login_password.setText(sp.getString("password", ""));
            editText_login_password.setSelection(editText_login_password.getText().toString().length());
        }


        btn_login.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forget_password_btn.setOnClickListener(this);

    }

    public static LoginActivity getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                LoginForAnYan();
                break;
            case R.id.register_btn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_password_btn:
                Intent  intent1=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }


    /**
     * 登陆安眼账号
     */
    private void LoginForAnYan() {
        anyan_name = editText_login_username.getText().toString().trim();
        anyan_password = editText_login_password.getText().toString().trim();
        if (isEmpty()) {
            boolean  isLogin=false;

           isLogin= ClientModel.getClientModel().Login(anyan_name,anyan_password);
            if(isLogin){
                MyApplication.userInfos.clear();
                userInfo ufo=new userInfo();
                ufo.setAnyan_username(anyan_name);
                ufo.setPassword(anyan_password);
                ufo.setToken(ClientModel.getClientModel().getLoginToken());
                if(ClientModel.getClientModel().GetUserInfo()){
                    ufo.setRealname(ClientModel.getClientModel().mUserInfo.getNickName());
                }
                MyApplication.userInfos.add(ufo);
                editor.putString("username", anyan_name);
                editor.putString("password", anyan_password);
                editor.commit();
                toast("登入成功");

                Intent  intent=new Intent(LoginActivity.this,MainActivity.class);
                         startActivity(intent);
                         finish();

            }else{
               String  msg= ClientModel.getClientModel().mLastErrorDesc;
                toast(msg);
            }

        }
    }


    /**
     * 判断输入框是否为空
     *
     * @return
     */
    private boolean isEmpty() {
        if ("".equals(anyan_name)) {
            toast("用户名不能为空");
            return false;
        }
        if ("".equals(anyan_password)) {
            toast("请输入密码");
            return false;
        }
        if(!Utils.passWordd(anyan_password)){
            Utils.showToast(this, "密码必须为英文和数字组合");
            return false;
        }
        return true;
    }


}