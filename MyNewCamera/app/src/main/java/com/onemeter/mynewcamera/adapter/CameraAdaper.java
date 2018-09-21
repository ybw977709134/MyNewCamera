package com.onemeter.mynewcamera.adapter;//package com.onemeter.mycamera.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.onemeter.mycamera.R;
//import com.onemeter.mycamera.entity.OmCamera;
//
//import java.util.List;
//
///**
// * 描述：设备列表适配器
// * 项目名称：MyCamera
// * 时间：2016/6/14 19:12
// * 备注：
// */
//public class CameraAdaper extends BaseAdapter {
//    Context context;
//    public static List<OmCamera> deviceList;
//
//    public CameraAdaper(Context context, List<OmCamera> deviceList) {
//        this.context = context;
//        this.deviceList = deviceList;
//    }
//
//
//    @Override
//    public int getCount() {
//        if (deviceList == null) {
//            return 0;
//        } else {
//            return deviceList.size();
//        }
//    }
//
//    @Override
//    public Object getItem(int position) {
//        if (deviceList == null) {
//            return null;
//        } else {
//            return deviceList.get(position);
//        }
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.camera_listview_item_layout, null);
//            holder.camera_name = (TextView) convertView.findViewById(R.id.camera_name);
//            holder.camera_tatus = (TextView) convertView.findViewById(R.id.camera_tatus);
//            holder.camera_device_id = (TextView) convertView.findViewById(R.id.camera_device_id);
//            holder.camera_device_owuner = (TextView) convertView.findViewById(R.id.camera_device_owuner);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder)convertView.getTag();
//        }
//        //设置数据
//
//        if(deviceList.get(position).getiDeviceStatus()!=0){
//            holder.camera_tatus.setText("在线");
//        }else{
//            holder.camera_tatus.setText("离线");
//        }
//
//        holder.camera_name.setText(deviceList.get(position).getDevice_name());
//        holder.camera_device_id.setText(deviceList.get(position).getStrDeviceSN());
//        holder.camera_device_owuner.setText("所有者："+deviceList.get(position).getiDeviceOwner());
//
//        return convertView;
//    }
//
//
//    /**
//     * 存放组件
//     */
//
//    public class ViewHolder {
//        private TextView camera_tatus;
//        private TextView camera_name;
//        private TextView camera_device_id;
//        private TextView camera_device_owuner;
//        private ImageButton  camera_setting_btn;//摄像头设置
//        private ImageButton  camera_share_btn;//分享摄像头id，生成二维码
//
//    }
//}
