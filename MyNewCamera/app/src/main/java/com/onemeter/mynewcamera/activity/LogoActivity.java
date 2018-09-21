package com.onemeter.mynewcamera.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.entity.userInfo;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 描述：logo启动页面
 * 作者：angelyin
 * 时间：2016/3/11 10:22
 * 备注：
 */
public class LogoActivity  extends BaseActivity{
    Intent intent;
    SharedPreferences  preferences;
    int count;
    /**
     * 配置账号信息
     */
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    String username;
    String password;
    Handler  mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_logo_main);
        preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
        //记录是否是第一次启动app
        count = preferences.getInt("count", 0);
        //获取保存的用户名
        sp = getSharedPreferences("userInfo_confing", Context.MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getString("username", "") != null// 设置用户名
            && !sp.getString("username", "").equals("")) {
           username=sp.getString("username","");
        }

        if (sp.getString("password", "") != null// 设置用户密码
                && !sp.getString("password", "").equals("")) {
            password=sp.getString("password", "");
        }
        initView();


    }



    /**
     * 初始化组件
     */
    private void initView() {
        mHandler=new Handler();

       intent=new Intent();
        Timer  timer=new Timer();
        TimerTask  task=new TimerTask() {
            @Override
            public void run() {
              //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
                if (count == 0) {
                  intent = new Intent();
                    intent.setClass(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                //自动登陆安眼账号
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isEmpty()) {
                                    boolean  isLogin=false;
                                     isLogin= ClientModel.getClientModel().Login(username,password);
                                    if(isLogin){
                                        MyApplication.userInfos.clear();
                                        userInfo ufo=new userInfo();
                                        ufo.setAnyan_username(username);
                                        ufo.setPassword(password);
                                        ufo.setToken(ClientModel.getClientModel().getLoginToken());
                                        if(ClientModel.getClientModel().GetUserInfo()){
                                            ufo.setRealname(ClientModel.getClientModel().mUserInfo.getNickName());
                                        }
                                        MyApplication.userInfos.add(ufo);
                                        editor.putString("username", username);
                                        editor.putString("password", password);
                                        editor.commit();
                                        toast("登入成功");
                                        intent=new Intent(LogoActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        String  msg= ClientModel.getClientModel().mLastErrorDesc;
                                        toast(msg);
                                        intent = new Intent(LogoActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else {
                                    intent = new Intent(LogoActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (Exception e) {
                                mHandler.removeCallbacks(this);
                                e.printStackTrace();
                            }
                        }
                    }, 200);

                }
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putInt("count", ++count);
                //提交修改
                editor.commit();
            }
        };
        timer.schedule(task, 1000 * 2);
    }


    /**
     * 判断是否为空
     * @return
     */
    private boolean isEmpty() {
        if ("".equals(username)) {
            return false;
        }
        if ("".equals(password)) {
            return false;
        }

        return true;
    }

}
