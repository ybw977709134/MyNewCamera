package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.qrCode.CreateQrCodeActivity;

/**
 * 描述：设备联网第二步
 * 时间：2016/6/16 19:27
 * 备注：
 */
public class WifiNetSecondAcitivity extends BaseActivity implements View.OnClickListener {
    private EditText  editText_net_name;//wifi名称
    private EditText  editText_net_password;//wifi密码
    private Button   btn_net_wifi;//获取wifi的按钮
    private  Button  wifi_three_next_btn;//第三步按钮
    private ImageView camera_fragment_back;//返回
    /**wifi名称标识**/
    public static int REQ_WIFI_NAME = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_net_second_main);
         initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        editText_net_name= (EditText) findViewById(R.id.editText_net_name);
        editText_net_password= (EditText) findViewById(R.id.editText_net_password);
        btn_net_wifi= (Button) findViewById(R.id.btn_net_wifi);
        wifi_three_next_btn= (Button) findViewById(R.id.wifi_three_next_btn);
        camera_fragment_back= (ImageView) findViewById(R.id.camera_fragment_back);


        btn_net_wifi.setOnClickListener(this);
        camera_fragment_back.setOnClickListener(this);
        wifi_three_next_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_net_wifi:
               //跳转到获取本机连接的wifi名称列表
                Intent intent = new Intent(this,DeviceWifiActivity.class);
                startActivityForResult(intent, REQ_WIFI_NAME);
                break;
            case R.id.camera_fragment_back:
                //返回键
                finish();
                break;
            case R.id.wifi_three_next_btn:
                //第二步
                getWiFiNumberforQr();
                break;

        }
    }


    /**
     * 点击下一步 生成设备联网二维码
     */
    private void getWiFiNumberforQr() {
        String name = editText_net_name.getText().toString().trim();
        String password = editText_net_password.getText().toString().trim();

        if (name.length() <= 0) {
            Toast.makeText(this, "请输入wifi账号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.length() > 0) {
            String urlString = "S:"+name+"\r\n"+"P:"+password+"\r\n";
            Intent qrIntent = new Intent(WifiNetSecondAcitivity.this,CreateQrCodeActivity.class);
            qrIntent.putExtra("urlString", urlString);
            startActivity(qrIntent);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1 :
                if (resultCode == RESULT_OK) {
                    editText_net_name.setText(data.getStringExtra("wifi_name"));
                }
                break;
            default:
                break;
        }
    }

}
