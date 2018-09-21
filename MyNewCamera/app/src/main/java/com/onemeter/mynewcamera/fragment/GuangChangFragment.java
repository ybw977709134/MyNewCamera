package com.onemeter.mynewcamera.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.AYClientSDKModel;
import com.anyan.client.sdk.JDeviceBasic;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.activity.ShareCameraPlayerActivity;
import com.onemeter.mynewcamera.adapter.DeviceAdapter;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.utils.Utils;


/**
 * 描述：视频广场
 * 项目名称：MyCamera
 * 时间：2016/6/23 14:07
 * 备注：
 */
public class GuangChangFragment extends Fragment implements  AdapterView.OnItemClickListener {
    View  view;
    private GridView   share_camera_listview;//视频广场
    private  DeviceAdapter  mDeviceAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guangchang_main, container, false);
        AYClientSDKModel.getInstance().SetLoginUser("13916054202");
        initView();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDeviceAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        if (ClientModel.getClientModel().QueryVideoSquareDeviceList(1, 0, 1, 10)) {
            mDeviceAdapter=new DeviceAdapter(getActivity(), ClientModel.getClientModel().getVideoSquareDeviceList());
            share_camera_listview.setAdapter(mDeviceAdapter);
            share_camera_listview.setOnItemClickListener(this);
        } else {
            Utils.showToast(getActivity(), ClientModel.getClientModel().mLastErrorDesc);
        }

    }


    /**
     * 初始化组件
     */
    private void initView() {
        share_camera_listview= (GridView) view.findViewById(R.id.share_camera_listview);
    }


          //视频列表监听事件

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//           Utils.showToast(getActivity(),position+"");
        if (position < ClientModel.getClientModel().getVideoSquareDeviceList().size()) {
            Intent intent = new Intent(getActivity(), ShareCameraPlayerActivity.class);
            MyApplication.device_gc_position.clear();
            MyApplication.device_gc_position.add(position+"");
            ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_Square, position);
            startActivity(intent);
        }


    }
}
