package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;

/**
 * 输入设备名称activity
 */
public class DeviceNameActivity extends BaseActivity {
	/**获取设备的名称**/
	private EditText editText_device_name;
	/**确定按钮**/
	private Button btn_device_name;
	private TextView  device_id_name;
	private ImageView device_btn_back;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_name);
		device_btn_back= (ImageView) findViewById(R.id.device_btn_back);
		device_btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		device_id_name= (TextView) findViewById(R.id.device_id_name);
		editText_device_name = (EditText) findViewById(R.id.editText_device_name);
		btn_device_name = (Button) findViewById(R.id.btn_device_name);
		device_id_name.setText(MyApplication.device_return_name.get(0));
		btn_device_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editText_device_name.getText().length() <= 0) {
					Toast.makeText(DeviceNameActivity.this, "设备名不能为空", Toast.LENGTH_SHORT).show();
				} else {	
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					intent.putExtra("device_name", editText_device_name.getText().toString());					
					DeviceNameActivity.this.finish();
				}
				
			}
		});
		
		
	}
	
	/**
	 * 消费最外层的点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		finish();
		return true;
	}
	
}
