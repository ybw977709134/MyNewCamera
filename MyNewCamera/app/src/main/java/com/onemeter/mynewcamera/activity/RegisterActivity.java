package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.utils.Utils;

/**
 * 描述：注册安眼账号
 * 项目名称：MyCamera
 * 时间：2016/6/14 10:21
 * 备注：
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText  editText_register_username;//用户名
    private EditText  editText_register_password;//密码
    private  EditText  register_code;//验证码
    private Button   register_sms_btn;//发送验证码按钮
    private  Button  register_butoon;//注册按钮
    private ImageView register_btn_back;//返回键
    private String  oemkey = "anyan", language = "zh";
    private RelativeLayout   rel_main_register_budi;//隐私条款协议

    private  String  username;
    private   String  password;
    private   String  regcode;
    private static RegisterActivity instance;

    /**
     * 倒计时
     **/
    private TimeCount timeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instance=RegisterActivity.this;
        ClientModel.getClientModel().SetOemKey(oemkey);
        ClientModel.getClientModel().SetLanguage(language);
        ClientModel.getClientModel().setPhoneType();
        initView();
    }


    /**
     * 单例模式
     * @return
     */
    public static RegisterActivity getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    private void initView() {
        timeCount = new TimeCount(60000, 1000);
        rel_main_register_budi= (RelativeLayout) findViewById(R.id.rel_main_register_budi);
        editText_register_username= (EditText) findViewById(R.id.editText_register_username);
        editText_register_password= (EditText) findViewById(R.id.editText_register_password);
        register_code= (EditText) findViewById(R.id.register_code);
        register_sms_btn= (Button) findViewById(R.id.register_sms_btn);
        register_butoon= (Button) findViewById(R.id.register_butoon);
        register_btn_back= (ImageView) findViewById(R.id.register_btn_back);

        register_sms_btn.setOnClickListener(this);
        register_butoon.setOnClickListener(this);
        register_btn_back.setOnClickListener(this);
        rel_main_register_budi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_back:
                //返回键
                this.finish();
                break;
            case R.id.register_butoon:
                //注册
                registerForAnyan();
                break;
            case R.id.register_sms_btn:
                //发送验证码

                SmsRegisterCode();
                break;
            case R.id.rel_main_register_budi:
                //隐私协议
                Intent intent=new Intent(this,ServerWebActivity.class);
                startActivity(intent);
                break;
        }
    }



    /**
     * 发送注册验证码
     */
    private void SmsRegisterCode() {
        if(isEmpty()){
        boolean flag= ClientModel.getClientModel().GetRegistRegCode(username);
            if(flag){
                toast("验证码发送成功");
                timeCount.start();

            }else{
                toast("该手机号已被注册");
            }
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
            register_sms_btn.setText("重新验证");
            register_sms_btn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            register_sms_btn.setClickable(false);
            register_sms_btn.setText(millisUntilFinished / 1000 + "s后可重发");
        }
    }



    /**
     * 注册安眼账号
     */
    private void registerForAnyan() {
        if(isEmpty()){
            if("".equals(regcode)){
                toast("验证码码不能为空");
                return;
            }
            if(!Utils.passWordd(password)){
                Utils.showToast(this, "密码必须为英文和数字组合");
                return ;
            }

          boolean  regis= ClientModel.getClientModel().Register(username,password,regcode);
            if(regis){
                toast("注册成功");
                Intent  intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                toast("注册失败"+ ClientModel.getClientModel().mLastErrorDesc);
            }
        }
    }


    /**
     * 判断是否为空
     * @return
     */
    private boolean isEmpty() {
        username=editText_register_username.getText().toString().trim();
        password=editText_register_password.getText().toString().trim();
        regcode=register_code.getText().toString().trim();
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
        return true;
    }


}
