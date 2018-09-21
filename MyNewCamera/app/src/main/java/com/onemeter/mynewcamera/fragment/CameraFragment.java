package com.onemeter.mynewcamera.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.JDeviceBasic;
import com.lidroid.xutils.BitmapUtils;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.activity.CameraPlayerActivity;
import com.onemeter.mynewcamera.activity.CameraSettingActivity;
import com.onemeter.mynewcamera.activity.DeviceAddActivity;
import com.onemeter.mynewcamera.activity.DeviceNameActivity;
import com.onemeter.mynewcamera.activity.ShareCameraIdActivity;
import com.onemeter.mynewcamera.activity.WifiNetFirstActvity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.entity.OmCamera;
import com.onemeter.mynewcamera.entity.device_info;
import com.onemeter.mynewcamera.qrCode.MipcaActivityCapture;
import com.onemeter.mynewcamera.utils.Utils;
import com.pulltorefresh.PullToRefreshBase;
import com.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：摄像头列表页面(我的 /共享)
 * 项目名称：MyCamera
 * 时间：2016/6/14 17:11
 * 备注：
 */
public class CameraFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private View view;
    private ImageButton add_camera;//添加摄像头
    private PullToRefreshListView camera_listview;//设备列表组件
    private ListView listView;
    private RelativeLayout isempty_text;//设备为空的提示信息
    private CameraAdaper adapter;//适配器
    public static List<OmCamera> deviceList;//设备列表集合
    public static List<JDeviceBasic> mylist;//安眼设备列表集合

    private String addDeviceId;//添加新设备id
    private String addDeviceName;//添加新设备名

    private PopupWindow popupwindow;//添加选择弹窗
    private View camera_view;//标题栏组件
    private final static int SCANNIN_GREQUEST_CODE = 1;//扫一扫
    private final static int DEVICE_ADD_CODE = 2;
    private final static int DEVICE_NAME_CODE = 3;
    private  String Device_type ="私有" ;

    private  RadioButton camera_my_btn;//我的摄像机
    private RadioButton  camera_gongxiang_btn;//共享摄像机

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera, container, false);
        mylist = new ArrayList<JDeviceBasic>();
        deviceList=new ArrayList<OmCamera>();
        initView();
        return view;
    }

    /**
     * 初始化组件
     */
    private void initView() {
        camera_view=view.findViewById(R.id.camera_view);
        camera_my_btn= (RadioButton) view.findViewById(R.id.camera_my_btn);
        camera_gongxiang_btn= (RadioButton) view.findViewById(R.id.camera_gongxiang_btn);
        camera_my_btn.setChecked(true);
        Device_type="私有";
        camera_gongxiang_btn.setChecked(false);

        add_camera = (ImageButton) view.findViewById(R.id.add_camera);
        adapter = new CameraAdaper(getActivity(), deviceList);
        camera_listview = (PullToRefreshListView) view.findViewById(R.id.camera_listview);
        listView = camera_listview.getRefreshableView();
        isempty_text = (RelativeLayout) view.findViewById(R.id.isempty_text);


        listView.setEmptyView(isempty_text);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        add_camera.setOnClickListener(this);
        camera_my_btn.setOnClickListener(this);
        camera_gongxiang_btn.setOnClickListener(this);

        //初始化默认显示我的设备列表
        if(camera_my_btn.isChecked()){
            camera_my_btn.setTextColor(getResources().getColor(R.color.result_view));
            camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.white));
            getMyAllDevice();
        }else{
            camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.result_view));
            camera_my_btn.setTextColor(getResources().getColor(R.color.white));
            getShareDevice();
        }

        camera_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (deviceList.size() == 0) {
                    listView.setEmptyView(isempty_text);
                    return;
                }

                if(Device_type.equals("私有")){
                    camera_my_btn.setTextColor(getResources().getColor(R.color.result_view));
                    camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.white));
                    getMyAllDevice();
                }else {
                    camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.result_view));
                    camera_my_btn.setTextColor(getResources().getColor(R.color.white));
                   getShareDevice();
                }
                camera_listview.onRefreshComplete();//刷新完成
            }
        });
    }


    /**
     * 获取共享设备信息
     */
    private void getShareDevice() {
        deviceList.clear();
        adapter.notifyDataSetChanged();
        ClientModel.getClientModel().QueryDeviceList();
        mylist= ClientModel.getClientModel().getShareDeviceList();
        if(mylist.size()>0){
            for(int i=0;i<mylist.size();i++){
                OmCamera oca = new OmCamera();
                oca.setStrDeviceSN(mylist.get(i).getDeviceSN());
                oca.setDevice_name(mylist.get(i).getDeviceName());
                oca.setStrToken(mylist.get(i).getToken());
                oca.setiChannelNum(mylist.get(i).getChannelNum());
                oca.setiDeviceTypeId(mylist.get(i).getDeviceTypeId());
                oca.setiDeviceStatus(mylist.get(i).getDeviceStatus());
                oca.setRates(mylist.get(i).getRates());
                oca.setDesc_info(mylist.get(i).getDescInfo());
                oca.setiAlarmState(mylist.get(i).getAlarmState());
                oca.setiCanPosition(mylist.get(i).getCanPosition());
                oca.setiDeviceOwner(mylist.get(i).getDeviceOwner());
                oca.setiLiveSwitch(mylist.get(i).getLiveSwitch());
                oca.setStrAddTime(mylist.get(i).getAddTime());
                oca.setiSharedControllerSwitch(mylist.get(i).getSharedControllerSwitch());
                oca.setiSharedPantiltSwitch(mylist.get(i).getSharedPantiltSwitch());
                oca.setiSharedZoomSwitch(mylist.get(i).getSharedZoomSwitch());
                oca.setiViewCount(mylist.get(i).getViewCount());
                deviceList.add(oca);
            }
            adapter.notifyDataSetChanged();

        }

    }


    /**
     * 获取我的摄像机设备列表
     */

    private void getMyAllDevice() {
        deviceList.clear();
        adapter.notifyDataSetChanged();
        ClientModel.getClientModel().QueryDeviceList();
        mylist= ClientModel.getClientModel().getMyDeviceList();
        if(mylist.size()>0){
            for(int i=0;i<mylist.size();i++){
                OmCamera oca = new OmCamera();
                oca.setStrDeviceSN(mylist.get(i).getDeviceSN());
                oca.setDevice_name(mylist.get(i).getDeviceName());
                oca.setStrToken(mylist.get(i).getToken());
                oca.setiChannelNum(mylist.get(i).getChannelNum());
                oca.setiDeviceTypeId(mylist.get(i).getDeviceTypeId());
                oca.setiDeviceStatus(mylist.get(i).getDeviceStatus());
                oca.setRates(mylist.get(i).getRates());
                oca.setDesc_info(mylist.get(i).getDescInfo());
                oca.setiAlarmState(mylist.get(i).getAlarmState());
                oca.setiCanPosition(mylist.get(i).getCanPosition());
                oca.setiDeviceOwner(mylist.get(i).getDeviceOwner());
                oca.setiLiveSwitch(mylist.get(i).getLiveSwitch());
                oca.setStrAddTime(mylist.get(i).getAddTime());
                oca.setiSharedControllerSwitch(mylist.get(i).getSharedControllerSwitch());
                oca.setiSharedPantiltSwitch(mylist.get(i).getSharedPantiltSwitch());
                oca.setiSharedZoomSwitch(mylist.get(i).getSharedZoomSwitch());
                oca.setiViewCount(mylist.get(i).getViewCount());
                deviceList.add(oca);

            }
            adapter.notifyDataSetChanged();

        }

    }







    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_my_btn:
                //我的
                Device_type="私有";
                camera_my_btn.setTextColor(getResources().getColor(R.color.result_view));
                camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.white));
                getMyAllDevice();
                break;
            case R.id.camera_gongxiang_btn:
                //共享
                Device_type="共享";
                camera_gongxiang_btn.setTextColor(getResources().getColor(R.color.result_view));
                camera_my_btn.setTextColor(getResources().getColor(R.color.white));
                getShareDevice();
                break;
            case R.id.add_camera:
                //添加摄像头
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                } else {
                    AddCameraforUserPop(camera_view);
                }
                break;
            case R.id.add_camera_btn:
                //扫一扫添加
                Intent addIntent = new Intent(getActivity(), MipcaActivityCapture.class);
                startActivityForResult(addIntent, SCANNIN_GREQUEST_CODE);
                popupwindow.dismiss();
                break;
            case R.id.add_camera_for_perbtn:
                //手动添加
                Intent noAutoAddIntent = new Intent(getActivity(), DeviceAddActivity.class);
                startActivityForResult(noAutoAddIntent, DEVICE_ADD_CODE);
                popupwindow.dismiss();
                break;
            case R.id.camera_wifi_btn:
                //配置设备wifi联网
                Intent  intent=new Intent(getActivity(),WifiNetFirstActvity.class);
                startActivity(intent);
                popupwindow.dismiss();
                break;

        }
    }

    /**
     * 添加摄像头
     */
    private void AddCameraforUserPop(View v) {
        // // 获取自定义布局文件pop.xml的视图
        View itmeView = getActivity().getLayoutInflater().inflate(R.layout.logout_popview_item,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(itmeView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 自定义view添加触摸事件
        popupwindow.setOutsideTouchable(true);
        popupwindow.setTouchable(true);
        popupwindow.setFocusable(true);
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popupwindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],location[1]);
        popupwindow.update();
        itmeView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }
                return false;
            }
        });
        //初始化
        Button add_camera_for_perbtn = (Button) itmeView.findViewById(R.id.add_camera_for_perbtn);
        Button add_camera_btn = (Button) itmeView.findViewById(R.id.add_camera_btn);
        Button camera_wifi_btn = (Button) itmeView.findViewById(R.id.camera_wifi_btn);

        camera_wifi_btn.setOnClickListener(this);
        add_camera_for_perbtn.setOnClickListener(this);
        add_camera_btn.setOnClickListener(this);

    }

    /**
     * item监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyApplication.device_name.clear();
            MyApplication.device_id.clear();
            MyApplication.device_type.clear();
            MyApplication.device_position.clear();

        MyApplication.device_id.add(deviceList.get(position).getStrDeviceSN());
            MyApplication.device_name.add(deviceList.get(position).getDevice_name());
            if(Device_type.equals("私有")){//我的设备
                MyApplication.device_type.add("私有");
                device_info ffo=new device_info();
                ffo.setPosition(position + "");
                ffo.setType(Device_type);
                MyApplication.device_position.add(ffo);

                if(position< ClientModel.getClientModel().getMyDeviceList().size()){
                ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_My, position);
                Intent intent = new Intent(getActivity(), CameraPlayerActivity.class);
                if(ClientModel.getClientModel().getMyDeviceList().get(position).getDeviceTypeId() != JDeviceBasic.DeviceTypeId.Device_IPC) {
                    if(ClientModel.getClientModel().getMyDeviceList().get(position).getChannelNum() > 0) { //通道大于0，用1通道测试
                        ClientModel.getClientModel().SetChannelNum(1); //NVR设备可以使用这个设置通道号，也可以在直播使用LiveAndPlay(1)。
                    } else {
                        return;
                    }
                }
                startActivity(intent);
                }

            }
           /**************************************************************************************/
             if(Device_type.equals("共享")){
                 MyApplication.device_type.add("共享");
                 device_info ffo=new device_info();
                 ffo.setPosition(position + "");
                 ffo.setType(Device_type);
                 MyApplication.device_position.add(ffo);

                 if(position< ClientModel.getClientModel().getShareDeviceList().size()){
                     Intent intent = new Intent(getActivity(), CameraPlayerActivity.class);
                     ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_Share, position);
                     startActivity(intent);
                 }
                }

    }




    /**
     * 获取设备号和设备名
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                //scannin_grequest_code
                if (resultCode == Activity.RESULT_OK) {
                    //获取扫描后的设备id号
                    addDeviceId = data.getExtras().getString("result");
                    Intent nameIntent = new Intent(getActivity(), DeviceNameActivity.class);
                    startActivityForResult(nameIntent, DEVICE_NAME_CODE);
                }
//                Utils.showToast(getActivity(), addDeviceId + "");
                break;

            case DEVICE_ADD_CODE:
                //device_add_code
                if (resultCode == Activity.RESULT_OK) {
                    //获取扫描后的设备id号
                    addDeviceId = data.getStringExtra("device_Id");
                    Intent nameIntent = new Intent(getActivity(), DeviceNameActivity.class);
                    startActivityForResult(nameIntent, DEVICE_NAME_CODE);
                }
//                Utils.showToast(getActivity(), addDeviceId + "");
                break;

            case DEVICE_NAME_CODE://设备名称填入后，添加设备（添加安眼）
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("device_name") != null) {
                        addDeviceName = data.getStringExtra("device_name");
                        //添加设备
                        addDevice();
                    }
//                    Utils.showToast(getActivity(), addDeviceName);
                    break;
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
      if(Device_type.equals("私有")){
          getMyAllDevice();
      }

       if(Device_type.equals("共享")){
         getShareDevice();
       }

    }

    /**
     * 添加设备到安眼
     */
    private void addDevice() {
        //向安眼平台添加设备
        if(Device_type.equals("私有")){
            ClientModel.getClientModel().getMyDeviceList();
            if(ClientModel.getClientModel().AddDevice(addDeviceId,addDeviceName)){
                Utils.showToast(getActivity(), "我的设备添加成功");
                getMyAllDevice();
            }else{
                Utils.showToast(getActivity(), ClientModel.getClientModel().mLastErrorDesc);
            }
        }

        if(Device_type.equals("共享")){
            ClientModel.getClientModel().getShareDeviceList();
           if(ClientModel.getClientModel().AddDevice(addDeviceId,addDeviceName)){
               Utils.showToast(getActivity(),"共享设备添加成功");
               getShareDevice();
           }else {
               Utils.showToast(getActivity(), ClientModel.getClientModel().mLastErrorDesc);
           }
        }
    }




    /**
     * 设备列表适配器
     */

    class CameraAdaper extends BaseAdapter{
        Context context;
        public  List<OmCamera> deviceList;
        private BitmapUtils bitmapUtils;


        public CameraAdaper(Context context, List<OmCamera> deviceList) {
            this.context = context;
            this.deviceList = deviceList;
            bitmapUtils = new BitmapUtils(context);

        }


        @Override
        public int getCount() {
            if (deviceList == null) {
                return 0;
            } else {
                return deviceList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (deviceList == null) {
                return null;
            } else {
                return deviceList.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.camera_listview_item_layout, null);
                holder.rel_main = (RelativeLayout) convertView.findViewById(R.id.rel_main);
                holder.camera_device_id_img = (ImageView) convertView.findViewById(R.id.camera_device_id_img);

                holder.camera_name = (TextView) convertView.findViewById(R.id.camera_name);
                holder.camera_tatus = (TextView) convertView.findViewById(R.id.camera_tatus);
                holder.camera_device_id = (TextView) convertView.findViewById(R.id.camera_device_id);
                holder.camera_device_owuner = (TextView) convertView.findViewById(R.id.camera_device_owuner);
                holder.camera_setting_btn = (LinearLayout) convertView.findViewById(R.id.camera_setting_btn);
                holder.camera_share_btn = (LinearLayout) convertView.findViewById(R.id.camera_share_btn);
                holder.camera_setting_btn.setOnClickListener(new View.OnClickListener() {//设置摄像机
                    @Override
                    public void onClick(View v) {
                        SettingCamera(position);
                    }
                });

                holder.camera_share_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareCameraId(position);
                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //设置数据

            if (Device_type.equals("私有")) {
                if (MyApplication.device_path.size() != 0) {
                    if (MyApplication.device_position.size() != 0 && MyApplication.device_position.get(0).getPosition().equals(position + "")) {
                        holder.camera_device_id_img.setVisibility(View.GONE);
                        bitmapUtils.clearCache(MyApplication.device_path.get(MyApplication.device_position.get(0).getPosition()));
                        bitmapUtils.display(holder.rel_main, MyApplication.device_path.get(MyApplication.device_position.get(0).getPosition()));
                    }
                }

            } else {
                if (MyApplication.device_gy_path.size() != 0) {
                    if (MyApplication.device_position.size() != 0 && MyApplication.device_position.get(0).getPosition().equals(position + "")) {
                        holder.camera_device_id_img.setVisibility(View.GONE);
                        bitmapUtils.clearCache(MyApplication.device_gy_path.get(MyApplication.device_position.get(0).getPosition()));
                        bitmapUtils.display(holder.rel_main, MyApplication.device_gy_path.get(MyApplication.device_position.get(0).getPosition()));
                    }

                }
            }

            if(deviceList.get(position).getiDeviceStatus()!=0){
                holder.camera_tatus.setText("在线");
                holder.camera_tatus.setBackground(getResources().getDrawable(R.drawable.edt_style_green));
            }else{
                holder.camera_tatus.setText("离线");
                holder.camera_tatus.setBackground(getResources().getDrawable(R.drawable.edt_style_gray));
            }

            holder.camera_name.setText(deviceList.get(position).getDevice_name());
//            holder.camera_device_id.setText(deviceList.get(position).getStrDeviceSN());
//            holder.camera_device_owuner.setText("所有者："+deviceList.get(position).getiDeviceOwner());

            return convertView;
        }


        /**
         * 设置摄像头的方法
         * @param p
         */
    public  void   SettingCamera(int p){
//        Utils.showToast(getActivity(),p+"");
        MyApplication.device_type.clear();
        MyApplication.device_id.clear();
        MyApplication.device_id.add(deviceList.get(p).getStrDeviceSN());
        if(Device_type.equals("私有")){//我的设备
            if(p< ClientModel.getClientModel().getMyDeviceList().size()){
                ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_My, p);
                if(ClientModel.getClientModel().getMyDeviceList().get(p).getDeviceTypeId() != JDeviceBasic.DeviceTypeId.Device_IPC) {
                    if(ClientModel.getClientModel().getMyDeviceList().get(p).getChannelNum() > 0) { //通道大于0，用1通道测试
                        ClientModel.getClientModel().SetChannelNum(1); //NVR设备可以使用这个设置通道号，也可以在直播使用LiveAndPlay(1)。
                    } else {
                        return;
                    }
                }
                Intent  intent=new Intent(getActivity(),CameraSettingActivity.class);
                MyApplication.device_type.add("私有");
                startActivity(intent);
            }
        }
        /**************************************************************************************/
        if(Device_type.equals("共享")){
            if(p< ClientModel.getClientModel().getShareDeviceList().size()){
                Intent  intent=new Intent(getActivity(),CameraSettingActivity.class);
                ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_Share, p);
                MyApplication.device_type.add("共享");
                startActivity(intent);
            }
        }

    }

        /**
         * 分享设备二维码的方法
         * @param p
         */
        public void  ShareCameraId(int p){
            MyApplication.device_id.clear();
            MyApplication.device_id.add(deviceList.get(p).getStrDeviceSN());
            Intent  intent1=new Intent(getActivity(),ShareCameraIdActivity.class);
            startActivity(intent1);
        }

        /**
         * 存放组件
         */

        public class ViewHolder {
            private TextView camera_tatus;
            private TextView camera_name;
            private TextView camera_device_id;
            private TextView camera_device_owuner;
            private LinearLayout  camera_setting_btn;//摄像头设置
            private LinearLayout  camera_share_btn;//分享摄像头id，生成二维码
            private ImageView  camera_device_id_img;//显示默认图标
            private  RelativeLayout  rel_main;//背景图片

        }
}

}
