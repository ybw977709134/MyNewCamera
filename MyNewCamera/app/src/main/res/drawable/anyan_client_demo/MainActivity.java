package drawable.anyan_client_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyan.client.anyan_client_demo.custom.CustomDialog;
import com.anyan.client.anyan_client_demo.tmp.PrintFormat;
import com.anyan.client.anyan_client_demo.tmp.UpdateLog;
import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.JDeviceBasic;

/**
 * 主要activity
 */
public class MainActivity extends Activity {
    public static String TAG = "__MainActivity";
    private Context mContext;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private AYClientSDKCallBackHandle sdkCallBackHandle;
    private TextView mTxtAppTitle;
    private TextView mTxtInfo;
    private Button mBtnLogin, mBtnLogout;
    private Button mBtnRefreshDevice, mBtnSetNet, mBtnCleanScreen;
    private Button mBtnUserInfo;
    private ListView mLsvDeviceMy, mLsvDeviceShare;

    private CustomDialog dialog;

    private static final int _Login = 1;

    private String user = "", password = "", registcode = "", regcode = "", oemkey = "anyan", language = "zh";
    private String addSN = "", addName = "", newNickName = "", oldPassword = "", newPassword = "";

    private boolean mIsSquare = false;

    private String tmpUsername = "";
    private String tmpPassword = "";

    private String tmpaddSN = "";
    private String tmpaddName = "";

    private void toast(String info) {
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        mContext = this;
        setContentView(R.layout.activity_main);
        initView();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        UpdateLog.updateI(TAG, "SDK Version:" + ClientModel.getClientModel().GetVersion());
        UpdateLog.updateI(TAG, "API Version:" + ClientModel.getClientModel().GetJarVersion());
        UpdateLog.updateI(TAG, "SDK OEM KEY:" + oemkey);
        ClientModel.getClientModel().SetOemKey(oemkey);
        UpdateLog.updateI(TAG, "SDK Language:" + language);
        ClientModel.getClientModel().SetLanguage(language);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTxtInfo = (TextView) findViewById(R.id.txt_Info);
        UpdateLog.setTxtInfo(mTxtInfo);

        SetTitle();
        mLsvDeviceMy.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getMyDeviceList()));
        mLsvDeviceShare.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getShareDeviceList()));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mTxtInfo = (TextView) findViewById(R.id.txt_Info);
        UpdateLog.setTxtInfo(mTxtInfo);

        mTxtAppTitle = (TextView) findViewById(R.id.txt_apptitle);

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(mClickListener);
        mBtnLogout = (Button) findViewById(R.id.btn_logout);
        mBtnLogout.setOnClickListener(mClickListener);
        mBtnRefreshDevice = (Button) findViewById(R.id.btn_refreshlist);
        mBtnRefreshDevice.setOnClickListener(mClickListener);
        mBtnSetNet = (Button) findViewById(R.id.btn_setnet);
        mBtnSetNet.setOnClickListener(mClickListener);
        mBtnCleanScreen = (Button) findViewById(R.id.btn_cleanscreen);
        mBtnCleanScreen.setOnClickListener(mClickListener);

        mBtnUserInfo = (Button) findViewById(R.id.btn_userinfo);
        mBtnUserInfo.setOnClickListener(mClickListener);

        mLsvDeviceMy = (ListView) findViewById(R.id.lsv_devicemy);
        mLsvDeviceMy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(mContext, DevicePlay.class);
                ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_Share, "Ay44140be67769b3d9FN");
                startActivity(intent);*/

                if (position < ClientModel.getClientModel().getMyDeviceList().size()) {
                    //IPC
                    if (ClientModel.getClientModel().getMyDeviceList().get(position).getDeviceTypeId() == JDeviceBasic.DeviceTypeId.Device_IPC) {
                        Intent intent = new Intent(mContext, DevicePlay.class);
                        ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_My, position);
                        startActivity(intent);
                    } else if (ClientModel.getClientModel().getMyDeviceList().get(position).getDeviceTypeId() == JDeviceBasic.DeviceTypeId.Device_NVR) { //NVR
                        if (ClientModel.getClientModel().getMyDeviceList().get(position).getChannelNum() > 0) { //通道大于0，用1通道测试
                            Intent intent = new Intent(mContext, DevicePlay.class);
                            ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_My, position);
                            ClientModel.getClientModel().SetChannelNum(1); //NVR设备可以使用这个设置通道号，也可以在直播使用LiveAndPlay(1)。
                            startActivity(intent);
                        } else {
                            UpdateLog.updateW(TAG, "nvr device channel number: 0");
                        }
                    }
                }
            }
        });

        mLsvDeviceShare = (ListView) findViewById(R.id.lsv_deviceshare);
        mLsvDeviceShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position < ClientModel.getClientModel().getShareDeviceList().size()) {
                    Intent intent = new Intent(mContext, DevicePlay.class);
                    ClientModel.getClientModel().SetCurDevice(JDeviceBasic.DeviceOwner.Device_Share, position);
                    startActivity(intent);
                }
            }
        });

        if (Common.mIsSchool) {
            //ClientModel.getClientModel().SetCompanyMode(Common.mSchoolID);
            mBtnSetNet.setVisibility(View.INVISIBLE);
            mBtnUserInfo.setVisibility(View.GONE);
        }
    }

    private void SetTitle() {
        //MyWifiInfo.getWifiInfo(this);
        mTxtAppTitle.setText("Current Network: " + MyWifiInfo.getSSID(mContext));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // button line 1 -----------------------------------------
                case R.id.btn_login:
                    dialogLogin();
                    break;
                case R.id.btn_logout:
                    dialogLogout();
                    break;

                // button line 2 -----------------------------------------
                case R.id.btn_refreshlist:
                    if (ClientModel.getClientModel().QueryDeviceList()) {
                        UpdateLog.updateI(TAG, "【true】QueryDeviceList state");
                        mLsvDeviceMy.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getMyDeviceList()));
                        mLsvDeviceShare.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getShareDeviceList()));
                        mLsvDeviceShare.setBackgroundResource(R.color.backgroud_list_share);
                        mIsSquare = false;
                        UpdateLog.updateI(TAG, "MyDevice Num=" + ClientModel.getClientModel().getMyDeviceList().size()
                                + "\nShareDevice Num=" + ClientModel.getClientModel().getShareDeviceList().size());
                    } else {
                        UpdateLog.updateI(TAG, "【false】QueryDeviceList state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;

                case R.id.btn_setnet:
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                    break;
                case R.id.btn_cleanscreen:
                    UpdateLog.clean(TAG);
                    break;
                // button line 3 -----------------------------------------
                case R.id.btn_userinfo:
                    if (ClientModel.getClientModel().GetUserInfo()) {
                        UpdateLog.updateI(TAG, "【true】GetUserInfo state");
                        PrintFormat.UserInfo(ClientModel.getClientModel().mUserInfo);
                    } else {
                        UpdateLog.updateI(TAG, "【false】GetUserInfo state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case _Login:
                    if (ClientModel.getClientModel().Login(user, password)) {
                        UpdateLog.updateI(TAG, "【true】Login state"); // ClientModel.getClientModel().mLoginState
                        UpdateLog.updateI(TAG, "MyDevice Num=" + ClientModel.getClientModel().getMyDeviceList().size()
                                + "\nShareDevice Num=" + ClientModel.getClientModel().getShareDeviceList().size());

                        mLsvDeviceMy.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getMyDeviceList()));
                        mLsvDeviceShare.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getShareDeviceList()));
                        mLsvDeviceShare.setBackgroundResource(R.color.backgroud_list_share);
                        mIsSquare = false;
                    } else {
                        UpdateLog.updateI(TAG, "【false】Login state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private void dialogLogin() {
        dialog = new CustomDialog(MainActivity.this);
        final TextView textTitle = dialog.mTxt_title;
        textTitle.setText("登入");
        final EditText editText1 = (EditText) dialog.mEdt_Line1;//方法在CustomDialog中实现
        editText1.setText(tmpUsername);
        editText1.setHint("用户名");
        final EditText editText2 = (EditText) dialog.mEdt_Line2;//方法在CustomDialog中实现
        editText2.setText(tmpPassword);
        editText2.setHint("密码");
        final EditText editText3 = (EditText) dialog.mEdt_Line3;//方法在CustomDialog中实现
        editText3.setVisibility(View.GONE);
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = editText1.getText().toString();
                password = editText2.getText().toString();
                mHandler.sendEmptyMessage(_Login);
                dialog.dismiss();
            }
        });
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogLogout() {
        if (ClientModel.getClientModel().Logout()) {
            UpdateLog.updateI(TAG, "【true】Logout state");
            mLsvDeviceMy.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getMyDeviceList()));
            mLsvDeviceShare.setAdapter(new DeviceAdapter(mContext, ClientModel.getClientModel().getShareDeviceList()));
        } else {
            UpdateLog.updateI(TAG, "【false】Logout state");
            UpdateLog.updateLastError(TAG);
        }
    }

}
