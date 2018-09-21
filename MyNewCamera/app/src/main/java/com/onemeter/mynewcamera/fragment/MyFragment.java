package com.onemeter.mynewcamera.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyan.client.model.ClientModel;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.activity.ChangePasswordActivity;
import com.onemeter.mynewcamera.activity.LoginActivity;
import com.onemeter.mynewcamera.activity.ServerWebActivity;
import com.onemeter.mynewcamera.activity.SetUsernameActivity;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.utils.Utils;
import com.onemeter.mynewcamera.view.CircleImageView;
import com.onemeter.mynewcamera.view.Switch;


/**
 * 描述：
 * 项目名称：MyCamera
 * 作者：Administrator
 * 时间：2016/6/14 17:16
 * 备注：
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RelativeLayout user_setting_btn;//修改昵称
    private TextView user_relname;//用户昵称
    private Switch msg_swch_btn;//消息控制开关
    private RelativeLayout change_password_btn;//修改密码
    private RelativeLayout ver_rel;//检查版本号
    private RelativeLayout yinsi_rel_btn;//隐私协议
    private Button logout_button;//退出登录
    private CircleImageView user_pic;//圆形图片
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
//        MyApplication.initSDK();
        return view;
    }

    /**
     * 初始化组件
     */
    private void initView() {
        user_pic = (CircleImageView) view.findViewById(R.id.user_pic);
        user_setting_btn = (RelativeLayout) view.findViewById(R.id.user_setting_btn);
        user_relname = (TextView) view.findViewById(R.id.user_relname);
        user_relname.setText(MyApplication.userInfos.get(0).getRealname());
        msg_swch_btn = (Switch) view.findViewById(R.id.msg_swch_btn);
        change_password_btn = (RelativeLayout) view.findViewById(R.id.change_password_btn);
        ver_rel = (RelativeLayout) view.findViewById(R.id.ver_rel);
        yinsi_rel_btn = (RelativeLayout) view.findViewById(R.id.yinsi_rel_btn);
        logout_button = (Button) view.findViewById(R.id.logout_button);

        user_setting_btn.setOnClickListener(this);
        change_password_btn.setOnClickListener(this);
        ver_rel.setOnClickListener(this);
        logout_button.setOnClickListener(this);
        yinsi_rel_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_setting_btn:
                //修改昵称
                intent = new Intent(getActivity(), SetUsernameActivity.class);
                intent.putExtra("requestCode", "1");
                intent.putExtra("user_relname", user_relname.getText().toString());
                startActivityForResult(intent, 111);
                break;
            case R.id.change_password_btn:
                //修改密码
                intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ver_rel:
                //检查版本号
                Utils.showToast(getActivity(), "已是最新版本");
                break;
            case R.id.yinsi_rel_btn:
                //隐私协议
                Intent intent = new Intent(getActivity(), ServerWebActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_button:
                //退出
                deleteCameraDialog();
                break;
        }
    }


    /**
     * 删除摄像机对话框
     */
    private void deleteCameraDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog);
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.dialog_normal2_layout, null);
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
                //退出登录
                if (ClientModel.getClientModel().Logout()) {
                    Intent intent = new Intent();
                    intent.setAction(BaseActivity.SYSTEM_EXIT);
                    getActivity().sendBroadcast(intent);
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent1);
                    dialog.dismiss();
                } else {
                    Utils.showToast(getActivity(), ClientModel.getClientModel().mLastErrorDesc);
                }
            }
        });
        button_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == 1) {
                String result = data.getStringExtra("one");
                user_relname.setText(result);
            }
        }
    }
}
