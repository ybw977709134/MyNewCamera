package com.onemeter.mynewcamera.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyan.client.sdk.JDeviceBasic;
import com.lidroid.xutils.BitmapUtils;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.MyApplication;

import java.util.List;

/**视频广场列表
 * Created by flin on 2015/9/14.
 */
public class DeviceAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<JDeviceBasic> mDevices;
    private BitmapUtils bitmapUtils;


    public DeviceAdapter(Context context, List<JDeviceBasic> devices)
    {
        this.mContext = context;
        this.mDevices = devices;
        this.mInflater = LayoutInflater.from(context);
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.deviceitem, null);
            holder.deviceName = (TextView) convertView.findViewById(R.id.txt_devicename);
            holder.bg_guangchang_img = (ImageView) convertView.findViewById(R.id.bg_guangchang_img);
            holder.bg_guangchang = (ImageView) convertView.findViewById(R.id.bg_guangchang);
            holder.deviceOwner = (TextView) convertView.findViewById(R.id.txt_devicesowner);
            holder.deviceOnline = (TextView) convertView.findViewById(R.id.txt_devicesonline);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.deviceName.setText(mDevices.get(position).getDeviceName());
        //设置图片
        if(MyApplication.device_gc_path.size()!=0){
          if(MyApplication.device_gc_position.size()!=0&&MyApplication.device_gc_position.get(0).equals(position + "")){
                 holder.bg_guangchang_img.setVisibility(View.GONE);
                  holder.bg_guangchang.setVisibility(View.VISIBLE);
                  bitmapUtils.clearCache(MyApplication.device_gc_path.get(MyApplication.device_gc_position.get(0)));
                  bitmapUtils.display(holder.bg_guangchang, MyApplication.device_gc_path.get(MyApplication.device_gc_position.get(0)));
          }
            bitmapUtils.display(holder.bg_guangchang, MyApplication.device_gc_path.get(position+""));
        }

        if(mDevices.get(position).getDeviceStatus() != JDeviceBasic.DeviceStatus.Offline) {
            holder.deviceOnline.setText("在线");
            holder.deviceOnline.setTextColor(Color.WHITE);
        } else {
            holder.deviceOnline.setText("离线");
            holder.deviceOnline.setTextColor(Color.GRAY);
        }
        return convertView;
    }

    public static class ViewHolder
    {
        public TextView deviceName;
        public TextView deviceOwner;
        public TextView deviceOnline;
        public ImageView bg_guangchang_img;
        public ImageView bg_guangchang;
    }
}
