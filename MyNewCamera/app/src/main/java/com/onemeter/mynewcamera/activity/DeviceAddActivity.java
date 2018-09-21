package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;


/**
 * 手动添加摄像头的设备id
 * @author hutianfeng created at 2015/9/10 pm 16:01
 *
 */
public class DeviceAddActivity extends BaseActivity implements OnClickListener {
	private ImageView imageButton_add_back;//返回键
	private EditText editText_add_deviceId;//设备id
	private Button btn_add;//确定按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_no_auto);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		imageButton_add_back = (ImageView) findViewById(R.id.imageButton_add_back);
		editText_add_deviceId = (EditText) findViewById(R.id.editText_add_deviceId);
		btn_add = (Button) findViewById(R.id.btn_add);
		
		imageButton_add_back.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		
		
	}

	/**
	 * 各个view的监听实现
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_add_back:
			//返回
			finish();
			break;
			
		case R.id.btn_add:
			//添加
			if (editText_add_deviceId.getText().length() == 0) {
				Toast.makeText(DeviceAddActivity.this, "设备号不能为空", Toast.LENGTH_SHORT);
			} else {
				Intent intent = new Intent();
				intent.putExtra("device_Id", editText_add_deviceId.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
			break;
		default:

			break;
		}
		
	}
}
