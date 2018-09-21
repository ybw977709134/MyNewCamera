package com.onemeter.mynewcamera.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.view.Switch;

/**摄像机设置页面
 * Created by G510 on 2016/6/19.
 */
public class CameraSettingActivity extends BaseActivity implements View.OnClickListener{
    private  String  device_id;
    private ImageView camera_setting_back;//返回键
    private Switch camera_swch_text_btn;//摄像头开关
    private RelativeLayout  baojing_btn;//侦测报警
    private  RelativeLayout  wifi_setting_btn;//网络配置
    private  RelativeLayout  share_camera_btn;//设备共享
    private  RelativeLayout  yun_cunchu_btn;//云存储
    private   RelativeLayout  other_setting_btn;//其他设置
    private  Button  delete_camera_btn;//删除设备
    private Spinner mSpnResolution;//码率选择框
    Handler mHandler;


    private  RelativeLayout  main_rel;//各种设置布局
    private  RelativeLayout  camera_swch_btn;//总控制布局

    private ImageButton  seting_share_device_id;//共享设备按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_camera_setting_main);
        mHandler=new Handler();
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        seting_share_device_id= (ImageButton) findViewById(R.id.seting_share_device_id);
        camera_swch_btn= (RelativeLayout) findViewById(R.id.camera_swch_btn);
        main_rel= (RelativeLayout) findViewById(R.id.main_rel);

        camera_setting_back= (ImageView) findViewById(R.id.camera_setting_back);
        camera_swch_text_btn= (Switch) findViewById(R.id.camera_swch_text_btn);
        baojing_btn= (RelativeLayout) findViewById(R.id.baojing_btn);
        wifi_setting_btn= (RelativeLayout) findViewById(R.id.wifi_setting_btn);
        share_camera_btn= (RelativeLayout) findViewById(R.id.share_camera_btn);
        yun_cunchu_btn= (RelativeLayout) findViewById(R.id.yun_cunchu_btn);
        other_setting_btn= (RelativeLayout) findViewById(R.id.other_setting_btn);
        delete_camera_btn= (Button) findViewById(R.id.delete_camera_btn);

        if(MyApplication.device_type.get(0).equals("共享")){
            main_rel.setVisibility(View.GONE);
            camera_swch_btn.setVisibility(View.GONE);
        }

        camera_setting_back.setOnClickListener(this);
        baojing_btn.setOnClickListener(this);
        wifi_setting_btn.setOnClickListener(this);
        share_camera_btn.setOnClickListener(this);
        yun_cunchu_btn.setOnClickListener(this);
        other_setting_btn.setOnClickListener(this);
        delete_camera_btn.setOnClickListener(this);
        camera_swch_text_btn.setOnClickListener(this);
        seting_share_device_id.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seting_share_device_id:
                //共享设备
                Intent  intent2=new Intent(CameraSettingActivity.this,ShareCameraIdActivity.class);
                startActivity(intent2);
                break;
            case R.id.delete_camera_btn:
                //删除设备
                deleteCameraDialog();
                break;
            case R.id.camera_setting_back:
                //返回键
                finish();
                break;
            case R.id.baojing_btn:
                //报警
                toast("当前固件版本暂不支持此功能");
                break;
            case R.id.wifi_setting_btn:
                //配置网络
                Intent  intent=new Intent(CameraSettingActivity.this,WifiNewNetSecondAcitivity.class);
                startActivity(intent);
                break;
            case R.id.share_camera_btn:
                //设备共享
                Intent  intent1=new Intent(CameraSettingActivity.this,ShareCameraActivity.class);
                startActivity(intent1);
                break;
            case R.id.yun_cunchu_btn:
                //云存储
                toast("当前固件版本暂不支持此功能");
                break;
            case R.id.other_setting_btn:
                //其他设置
              Intent  intent3=new Intent(CameraSettingActivity.this,OtherSettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.camera_swch_text_btn:
                //分享到广场开关
                if(camera_swch_text_btn.isChecked()) {
                    if (ClientModel.getClientModel().EnablePublic(1)) {
                        toast("分享到广场打开");
                    } else {
                         toast(ClientModel.getClientModel().mLastErrorDesc);
                    }
                } else {
                    if (ClientModel.getClientModel().DisablePublic(1)) {
                        toast("分享到广场关闭");
                    } else {
                        toast(ClientModel.getClientModel().mLastErrorDesc);
                    }
                }

                break;
        }
    }


    /**
     * 删除摄像机对话框
     */
    private void deleteCameraDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View view = this.getLayoutInflater().inflate(
                R.layout.dialog_normal_layout, null);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lParams = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);
        dialog.onWindowAttributesChanged(lParams);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Button button_qd = (Button) view.findViewById(R.id.button_qd);
        Button button_qx = (Button) view.findViewById(R.id.button_qx);
        button_qd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //删除设备
                if(ClientModel.getClientModel().RemoveDevice()){
                    ClientModel.getClientModel().QueryDeviceList();
                    toast("删除设备成功");
                    finish();
                }else {
                    toast(ClientModel.getClientModel().mLastErrorDesc);
                }
                dialog.dismiss();
            }
        });
        button_qx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}


