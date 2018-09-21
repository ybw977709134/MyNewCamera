package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.utils.Utils;


/**忘记密码页面
 * Created by G510 on 2016/6/18.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText  editText_register_username;//手机号码
    private  EditText code;//重置验证码
    private Button  send_sms_btn;//发送验证码
    private  Button  resetting_butoon;//重置下一步
    private ImageView btn_back;//返回键
    private  EditText  editText_new_password;//新密码
    private  EditText  editText_new_password_2;//再次新密码

    private  String  username;
    private  String  password;
    private   String  new_password;
    private  String recode;

    /**
     * 倒计时
     **/
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.layout_forgetword_main);
        initView();
//        MyApplication.initSDK();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        timeCount = new TimeCount(60000, 1000);
        editText_register_username= (EditText) findViewById(R.id.editText_register_username);
        editText_new_password= (EditText) findViewById(R.id.editText_new_password);
        editText_new_password_2= (EditText) findViewById(R.id.editText_new_password_2);
        code= (EditText) findViewById(R.id.code);
        send_sms_btn= (Button) findViewById(R.id.send_sms_btn);
        resetting_butoon= (Button) findViewById(R.id.resetting_butoon);
        btn_back= (ImageView) findViewById(R.id.btn_back);



        send_sms_btn.setOnClickListener(this);
        resetting_butoon.setOnClickListener(this);
        btn_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                //返回键
                 finish();
                break;
            case R.id.resetting_butoon:
                //重置按钮
                 getNetforRestart();
                break;
            case R.id.send_sms_btn:
                //发送验证码
                if(isEmpty2()){
                    getNetforCode();
                }
                break;



        }
    }

    private boolean isEmpty2() {
        username=editText_register_username.getText().toString().trim();
        if("".equals(username)){
            toast("手机号不能为空");
            return false;
        }
        if (!Utils.isPhoneNum(username)) {
            Utils.showToast(this, "手机号码格式不正确");
            return false;
        }

            return true;

    }


    /**
     * 重置密码
     */
    private void getNetforRestart() {
        username=editText_register_username.getText().toString().trim();
        password=editText_new_password.getText().toString().trim();
        new_password=editText_new_password_2.getText().toString().trim();
        recode=code.getText().toString().trim();
      if(isEmpty()){
          if(ClientModel.getClientModel().RetrieveAccount(username,password,recode)){
             toast("重置密码成功");
              Intent  intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
              startActivity(intent);
          }else {
              toast(ClientModel.getClientModel().mLastErrorDesc);
          }

      }



    }





    private boolean isEmpty() {
        if("".equals(recode)){
            toast("请填写验证码");
            return false;
        }
        if("".equals(username)){
            toast("手机号不能为空");
            return false;
        }
        if("".equals(password)){
            toast("密码不能为空");
            return false;
        }

        if (!Utils.isPhoneNum(username)) {
            Utils.showToast(this, "手机号码格式不正确");
            return false;
        }

        if(!Utils.passWordd(password)){
            Utils.showToast(this, "密码必须为英文和数字组合");
            return false;
        }


        if(!password.equals(new_password)){
            Utils.showToast(this, "两次密码不一致");
            return false;
        }
        return true;

    }


    /**
     * 发送验证码
     */
    private void getNetforCode() {

           if(ClientModel.getClientModel().GetChangeWordRegCode(username)){
               toast("验证码发送成功");
               timeCount.start();
           } else{
               toast(ClientModel.getClientModel().mLastErrorDesc);
           }

    }

    /**
     * 倒计时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            send_sms_btn.setText("重新验证");
            send_sms_btn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_sms_btn.setClickable(false);
            send_sms_btn.setText(millisUntilFinished / 1000 + "s后可重发");
        }
    }




}
