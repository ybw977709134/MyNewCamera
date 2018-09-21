package drawable.anyan_client_demo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anyan.client.sdk.JDeviceBasic;

import java.util.List;

/**
 * Created by flin on 2015/9/14.
 */
public class DeviceAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<JDeviceBasic> mDevices;

    public DeviceAdapter(Context context, List<JDeviceBasic> devices)
    {
        this.mContext = context;
        this.mDevices = devices;
        this.mInflater = LayoutInflater.from(context);
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

        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.deviceitem, null);
            holder.deviceName = (TextView)convertView.findViewById(R.id.txt_devicename);
            holder.deviceOwner = (TextView)convertView.findViewById(R.id.txt_devicesowner);
            holder.deviceOnline = (TextView)convertView.findViewById(R.id.txt_devicesonline);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.deviceName.setText((String)mDevices.get(position).getDeviceName());
        switch (mDevices.get(position).getDeviceOwner()) {
            case JDeviceBasic.DeviceOwner.Device_My:
                holder.deviceOwner.setText("My");
                break;
            case JDeviceBasic.DeviceOwner.Device_Share:
                holder.deviceOwner.setText("Share");
                break;
            case JDeviceBasic.DeviceOwner.Device_Square:
                holder.deviceOwner.setText("Square");
                break;
        }
        if(mDevices.get(position).getDeviceStatus() != JDeviceBasic.DeviceStatus.Offline) {
            holder.deviceOnline.setText("Online");
            holder.deviceOnline.setTextColor(Color.GREEN);
        } else {
            holder.deviceOnline.setText("Offline");
            holder.deviceOnline.setTextColor(Color.RED);
        }
        return convertView;
    }

    public static class ViewHolder
    {
        public TextView deviceName;
        public TextView deviceOwner;
        public TextView deviceOnline;
    }
}
