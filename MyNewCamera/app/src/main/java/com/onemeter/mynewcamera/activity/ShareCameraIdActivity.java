package com.onemeter.mynewcamera.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;

import java.util.Hashtable;

/**生成设备分享二维码页面
 * Created by G510 on 2016/6/19.
 */
public class ShareCameraIdActivity extends BaseActivity {
    private ImageView  camera_device_id_qr;//设备ID 生成的二维码图片
    private  String  device_id;
    private static int QR_WIDTH = 700;//二维码宽度
    private static int QR_HEIGHT = 700;//二维码高度
    private TextView  camera_device_id_text;//文字提示
    private TextView  camera_device_text;//文字提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_share_camera_qer);
        device_id= MyApplication.device_id.get(0).toString();
       initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        camera_device_id_qr= (ImageView) findViewById(R.id.camera_device_id_qr);
        camera_device_text= (TextView) findViewById(R.id.camera_device_text);
        camera_device_id_text= (TextView) findViewById(R.id.camera_device_id_text);
        camera_device_id_text.setText("ID："+device_id);
        camera_device_text.setText("扫一扫共享该设备");
        createQRImage(device_id);
    }


    /**
     * 要转换的地址或字符串,可以是中文
     * @param device_id
     */
    public void createQRImage(String device_id) {
        try
        {
            //判断URL合法性
            if (device_id == null || "".equals(device_id) || device_id.length() < 1){
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(device_id, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
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
            camera_device_id_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
