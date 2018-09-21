package com.onemeter.mynewcamera.qrCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.activity.WifiNetSuccessAcitivity;
import com.onemeter.mynewcamera.app.BaseActivity;

import java.util.Hashtable;

/**
 * 设备联网生成二维码页面
 */
public class CreateQrCodeActivity extends BaseActivity implements OnClickListener {
	private Button  qrcode_next_btn;
	/**生成的二维码图片框**/
	private ImageView imageView_createQr;
	/**返回-图标**/
	private ImageView imageButton_create_back;
	/**二维码宽度**/
	private static int QR_WIDTH = 700;
	/**二维码高度**/
	private static int QR_HEIGHT = 700;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		  initView();
	}


	/**
	 * 初始化组件
	 */

	private void initView() {
		qrcode_next_btn= (Button) findViewById(R.id.qrcode_next_btn);
		imageView_createQr = (ImageView) findViewById(R.id.imageView_createQr);
		imageButton_create_back = (ImageView) findViewById(R.id.imageButton_create_back);

		imageButton_create_back.setOnClickListener(this);
		qrcode_next_btn.setOnClickListener(this);

		//获取传递过来的二维码URL地址
		String urlString = getIntent().getStringExtra("urlString");
		createQRImage(urlString);


	}

	/**
	 * 要转换的地址或字符串,可以是中文
	 * @param url
	 */
	 public void createQRImage(String url) {
	        try
	        {
	        	//判断URL合法性
	            if (url == null || "".equals(url) || url.length() < 1){
	                return;
	            }
	            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            //图像数据转换，使用了矩阵转换
	            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
	            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
	            //下面这里按照二维码的算法，逐个生成二维码的图片，
	            //两个for循环是图片横列扫描的结果
	            for (int y = 0; y < QR_HEIGHT; y++)
	            {
	                for (int x = 0; x < QR_WIDTH; x++)
	                {
	                    if (bitMatrix.get(x, y))
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xff000000;
	                    }
	                    else
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xffffffff;
	                    }
	                }
	            }
	            //生成二维码图片的格式，使用ARGB_8888
	            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
	            //显示到一个ImageView上面
	            imageView_createQr.setImageBitmap(bitmap);
	        } catch (WriterException e) {
	            e.printStackTrace();
	        }
	    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_create_back:
			finish();
			break;
			case R.id.qrcode_next_btn:
				Intent  intent=new Intent(CreateQrCodeActivity.this, WifiNetSuccessAcitivity.class);
				startActivity(intent);
				break;

		default:
			break;
		}
	}
}
