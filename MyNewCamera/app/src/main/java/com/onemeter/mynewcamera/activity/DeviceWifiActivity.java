package com.onemeter.mynewcamera.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;

import java.util.List;

/**
 * 获得系统的所有wifi名
 *
 * @author hutianfeng at 2015/8/5
 */
public class DeviceWifiActivity extends BaseActivity implements OnClickListener {
    /**
     * wifi管理器
     **/
    private WifiManager wifiManager;
    /**
     * 扫描结果集合
     **/
    private List<ScanResult> list;
    /**
     * 列表组件
     **/
    private ListView listView;
    //返回
    private ImageView imageButton_wifi_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_wifi);
        initView();
    }

    /**
     * 初始化各个控件
     */
    private void initView() {
        imageButton_wifi_back = (ImageView) findViewById(R.id.imageButton_wifi_back);
        imageButton_wifi_back.setOnClickListener(this);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        openWifi();
        list = wifiManager.getScanResults();
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("wifi_name", list.get(position).SSID);
                setResult(RESULT_OK, intent);
                DeviceWifiActivity.this.finish();
            }
        });


        if (list == null) {
            Toast.makeText(this, "wifi未打开", Toast.LENGTH_SHORT).show();
        } else {
            //listview绑定自定义的适配器
            listView.setAdapter(new DeviceWifiAdaper(list));
        }

    }

    /**
     * 打开设备wifi
     */
    private void openWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * listview 适配器
     */
    class DeviceWifiAdaper extends BaseAdapter {
        List<ScanResult> list;

        public DeviceWifiAdaper(List<ScanResult> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_wifi_list, null);
                holder = new ViewHolder();
                holder.textView_wifi_name = (TextView) convertView.findViewById(R.id.textView_wifi_name);
                holder.textView_wifi_signal = (TextView) convertView.findViewById(R.id.textView_wifi_signal);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView_wifi_name.setText(list.get(position).SSID);
            if (Math.abs(list.get(position).level) <= 40) {
                holder.textView_wifi_signal.setText("信号超强");
            } else if (Math.abs(list.get(position).level) <= 50) {
                holder.textView_wifi_signal.setText("信号强");
            } else if (Math.abs(list.get(position).level) <= 60) {
                holder.textView_wifi_signal.setText("信号一般");
            } else if (Math.abs(list.get(position).level) <= 70) {
                holder.textView_wifi_signal.setText("信号差");
            } else {
                holder.textView_wifi_signal.setText("无信号");
            }
            return convertView;
        }

    }

    class ViewHolder {
        TextView textView_wifi_name; //wifi名
        TextView textView_wifi_signal; //wifi的信号强弱
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_wifi_back:
                finish();
                break;

            default:
                break;
        }

    }

}








