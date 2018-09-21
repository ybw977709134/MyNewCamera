package com.onemeter.mynewcamera.qrCode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemeter.mynewcamera.R;


/**
 * 测试扫描和生成二维码页面
 */
public class QrCodeActivity extends Activity {
	/**扫描应答标识**/
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**结果-文本**/
	private TextView mTextView ;
	/**生成二维码的结果图标**/
	private ImageView mImageView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		
		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		

		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(QrCodeActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		
				
		Button mButton2 = (Button) findViewById(R.id.button2);
		mButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(QrCodeActivity.this, CreateQrCodeActivity.class);
				intent.putExtra("urlString", "hutianfeng");
//				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				startActivity(intent);
			}
		});						
	}
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				mTextView.setText(bundle.getString("result"));
				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }	

}
