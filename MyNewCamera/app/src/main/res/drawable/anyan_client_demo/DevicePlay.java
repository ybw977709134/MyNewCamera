package drawable.anyan_client_demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.anyan.client.anyan_client_demo.tmp.PrintFormat;
import com.anyan.client.anyan_client_demo.tmp.UpdateLog;
import com.anyan.client.model.ClientModel;
import com.anyan.client.sdk.AYClientSDKCallBack;
import com.anyan.client.sdk.JDeviceBasic;
import com.anyan.client.sdk.JHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 播放设备
 */
public class DevicePlay extends Activity implements AYClientSDKCallBack, /*RecordRenderer.OnAudioRecordListener, */SurfaceHolder.Callback {
    public static String TAG = "__DevicePlay";
    private Context mContext;

    private TextView mTxtInfo, mTxtRate;
    private ToggleButton mTbtnPlay, mTbtnVoice, mTbtnZoom;
    private Button mBtnUp, mBtnDown, mBtnLeft, mBtnRight;
    private Button mBtnLive, mBtnStop, mBtnQuit, mBtnSnap;
    private Button mBtnDeviceDetail, mBtnGrantUsers, mBtnDeviceOnline, mBtnCleanScreen;

    private Spinner mSpnResolution;
    private TableLayout mControlTableLayout;
    private SeekBar mSkbProcess;
    private static final int MAXProcess = 24 * 60 * 60;
    private ProgressBar mProLoading;
    private ListView mLsvChannel;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    boolean bfirtIn = true;

    private int mRatePosition = 0;
    private boolean mUprate = true;
    private String mNetworkBits = " 0.00KB/S";
    private boolean mIsChangeLandscape;

    //private RecordRenderer mRecordRenderer;

   //吐司弹屏
    private void toast(String info) {
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }

    private  Handler mRateUpdateHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mTxtRate.setText(mNetworkBits);
                    mUprate = true;
                    break;
                case 0x02:
                    mProLoading.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        mContext = this;
        setContentView(R.layout.device);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume ActivePlayer begin.");
        //ClientModel.getClientModel().ActivePlayer(mSurfaceHolder.getSurface());
        Log.w(TAG, "onResume ActivePlayer end.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause DisActivePlayer begin.");
        //ClientModel.getClientModel().DisActivePlayer();
        Log.w(TAG, "onPause DisActivePlayer end.");
        mTbtnPlay.setChecked(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.e(TAG, "orientation=" + getRequestedOrientation());

        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            mTxtInfo.setVisibility(View.VISIBLE);
            mControlTableLayout.setVisibility(View.VISIBLE);
        } else {
            mTxtInfo.setVisibility(View.GONE);
            mControlTableLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(ClientModel.getClientModel().Quit()) {
                UpdateLog.updateI(TAG, "【true】Quit state");
                mRateUpdateHandler.removeMessages(0x01);
            } else {
                UpdateLog.updateI(TAG, "【false】Quit state");
            }

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        mTxtInfo = (TextView) findViewById(R.id.txt_Info);
        UpdateLog.setTxtInfo(mTxtInfo);

        mTxtRate = (TextView) findViewById(R.id.txt_rate);
        mTxtRate.setText(mNetworkBits);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        ClientModel.getClientModel().SetSurface(mSurfaceHolder.getSurface());
        mProLoading = (ProgressBar) findViewById(R.id.pro_loading);

        mControlTableLayout = (TableLayout) findViewById(R.id.tablrLayout);
        mControlTableLayout.setVisibility(View.VISIBLE);
        mSkbProcess = (SeekBar) findViewById(R.id.skb_process);
        mSkbProcess.setMax(MAXProcess);

        mTbtnPlay = (ToggleButton) findViewById(R.id.tbtn_control_play);
        mTbtnPlay.setOnClickListener(mClickListener);
        mTbtnVoice = (ToggleButton) findViewById(R.id.tbtn_control_voice);
        mTbtnVoice.setOnClickListener(mClickListener);
        mTbtnZoom = (ToggleButton) findViewById(R.id.tbtn_control_zoom);
        mTbtnZoom.setOnClickListener(mClickListener);

        mBtnUp = (Button) findViewById(R.id.btn_up);
        mBtnUp.setOnClickListener(mClickListener);
        mBtnDown = (Button) findViewById(R.id.btn_down);
        mBtnDown.setOnClickListener(mClickListener);
        mBtnLeft = (Button) findViewById(R.id.btn_left);
        mBtnLeft.setOnClickListener(mClickListener);
        mBtnRight = (Button) findViewById(R.id.btn_right);
        mBtnRight.setOnClickListener(mClickListener);

        mBtnLive = (Button) findViewById(R.id.btn_live);
        mBtnLive.setOnClickListener(mClickListener);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnStop.setOnClickListener(mClickListener);
        mBtnQuit = (Button) findViewById(R.id.btn_quit);
        mBtnQuit.setOnClickListener(mClickListener);
        mBtnSnap = (Button) findViewById(R.id.btn_snap);
        mBtnSnap.setOnClickListener(mClickListener);
        mBtnDeviceDetail = (Button) findViewById(R.id.btn_devicedetail);
        mBtnDeviceDetail.setOnClickListener(mClickListener);
        mBtnGrantUsers = (Button) findViewById(R.id.btn_grantusers);
        mBtnGrantUsers.setOnClickListener(mClickListener);
        mBtnDeviceOnline = (Button) findViewById(R.id.btn_deviceonline);
        mBtnDeviceOnline.setOnClickListener(mClickListener);
        mBtnCleanScreen = (Button) findViewById(R.id.btn_cleanscreen);
        mBtnCleanScreen.setOnClickListener(mClickListener);

        mSpnResolution = (Spinner) findViewById(R.id.tbtn_control_resolution);
        mSpnResolution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!ClientModel.getClientModel().CheckDeviceOnline()) {
                    mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 0);
                    return;
                }
                TextView tv = (TextView) view;
                if (tv != null) {
                    mRatePosition = position;
                    tv.setTextColor(Color.WHITE);
                    tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
                    if (ClientModel.getClientModel().ChanageRate(position, mSurfaceHolder.getSurface())) {
                        mTbtnPlay.setChecked(true);

                        Calendar now = Calendar.getInstance();
                        mSkbProcess.setProgress(now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));

                        UpdateLog.updateI(TAG, "ChanageRate OK");
                        return;
                    }
                    UpdateLog.updateI(TAG, "ChanageRate faild");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSkbProcess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //ClientModel.getClientModel().SeekAndPlay(mSkbProcess.getProgress(), ClientModel.SEEK_DRAG);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mProLoading.setVisibility(View.VISIBLE);
                ClientModel.getClientModel().Quit();
                int cur = mSkbProcess.getProgress();
                int h = cur / 3600;
                int m = cur % 3600 / 60;
                int s = cur % 60;
                Calendar seektime = Calendar.getInstance();
                seektime.set(2015, 8, 29, h, m, s);
                ClientModel.getClientModel().SetSurface(mSurfaceHolder.getSurface());
                ClientModel.getClientModel().SeekAndPlay(seektime.getTimeInMillis(), ClientModel.SEEK_AUTO);
            }
        });

        mLsvChannel = (ListView) findViewById(R.id.lsv_channel);
        mLsvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProLoading.setVisibility(View.VISIBLE);
                ClientModel.getClientModel().Quit();
                ClientModel.getClientModel().SetSurface(mSurfaceHolder.getSurface());
                ClientModel.getClientModel().LiveAndPlay(position + 1);
            }
        });

        if(Common.mIsSchool) {
            mBtnSnap.setVisibility(View.INVISIBLE);

            mBtnGrantUsers.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {

        ClientModel.getClientModel().SetSDKCallback(this);
        //mRecordRenderer = new RecordRenderer(this);
        if(ClientModel.getClientModel().mCurDevice.getDeviceOwner() == JDeviceBasic.DeviceOwner.Device_Share) {
            mSkbProcess.setVisibility(View.GONE);

            mBtnUp.setVisibility(View.INVISIBLE);
            mBtnDown.setVisibility(View.INVISIBLE);
            mBtnLeft.setVisibility(View.INVISIBLE);
            mBtnRight.setVisibility(View.INVISIBLE);

            mBtnSnap.setVisibility(View.INVISIBLE);
            mBtnGrantUsers.setVisibility(View.INVISIBLE);
        }

        mSpnResolution.setPrompt(ClientModel.getClientModel().mCurDevice.getRates()[0].getRateName());
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ClientModel.getClientModel().GetRateList());
        mSpnResolution.setAdapter(_Adapter);

        if(ClientModel.getClientModel().QueryDeviceDetailInfo()) {
            if(ClientModel.getClientModel().mCurDevice.getChannelNum() > 1 && ClientModel.getClientModel().mCurDevice.getDeviceTypeId() != JDeviceBasic.DeviceTypeId.Device_IPC) {
                mLsvChannel.setVisibility(View.VISIBLE);
                List<String> channels = new ArrayList<String>();
                for(int i = 0; i < ClientModel.getClientModel().mCurDevice.getChannelNum(); i++) {
                    channels.add("channel " + (i + 1));
                }
                String channlestate = ClientModel.getClientModel().mCurDevice.getChannelMask();
                if(channlestate.length() <= 1) {
                    channlestate = "";
                    for(int i = 0; i < ClientModel.getClientModel().mCurDevice.getChannelNum(); i++) {
                        channlestate += "0";
                    }
                }
                mLsvChannel.setAdapter(new ChannelAdapter(mContext, channels, channlestate));
            } else {
                mLsvChannel.setVisibility(View.GONE);
            }

            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long timesdiff = 0;
            try {
                long starttime = dateformat.parse(ClientModel.getClientModel().getChannelFullInfo().get(0).getCloudStartTime()).getTime();
                long endtime = dateformat.parse(ClientModel.getClientModel().getChannelFullInfo().get(0).getCloudEndTime()).getTime();
                timesdiff = starttime - endtime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChangeLandscape() {
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private  int mPosition = 1;
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // control line 0 -----------------------------------------
                case R.id.btn_up:
                    if(ClientModel.getClientModel().MoveUp(5)) {
                        UpdateLog.updateI(TAG, "【true】MoveUp state");
                        ClientModel.getClientModel().StopAction();
                    } else {
                        UpdateLog.updateI(TAG, "【false】MoveUp state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_down:
                    if(ClientModel.getClientModel().MoveDown(5)) {
                        UpdateLog.updateI(TAG, "【true】MoveDown state");
                        ClientModel.getClientModel().StopAction();
                    } else {
                        UpdateLog.updateI(TAG, "【false】MoveDown state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_left:
                    if(ClientModel.getClientModel().MoveLeft(5)) {
                        UpdateLog.updateI(TAG, "【true】MoveLeft state");
                        ClientModel.getClientModel().StopAction();
                    } else {
                        UpdateLog.updateI(TAG, "【false】MoveLeft state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_right:
                    if(ClientModel.getClientModel().MoveRight(5)) {
                        UpdateLog.updateI(TAG, "【true】MoveRight state");
                        ClientModel.getClientModel().StopAction();
                    } else {
                        UpdateLog.updateI(TAG, "【false】MoveRight state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                // control line 1 -----------------------------------------
                case R.id.tbtn_control_play:
                    if(!ClientModel.getClientModel().CheckDeviceOnline()) {
                        mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 0);
                        return;
                    }
                    if(mTbtnPlay.isChecked()) {
                        if(ClientModel.getClientModel().LiveAndPlay()) {
                            UpdateLog.updateI(TAG, "Live OK");
                            return;
                        }
                        UpdateLog.updateI(TAG, "Live faild");
                        UpdateLog.updateLastError(TAG);
                    } else {
                        if(ClientModel.getClientModel().StopPlay()) {
                            UpdateLog.updateI(TAG, "StopPlay OK");
                            return;
                        }
                        UpdateLog.updateI(TAG, "Stop faild");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.tbtn_control_voice:
                    if(mTbtnVoice.isChecked()) {
                        if(ClientModel.getClientModel().OpenAudio()) {
                            UpdateLog.updateI(TAG, "OpenAudio OK");
                            return;
                        }
                        UpdateLog.updateI(TAG, "OpenAudio faild");
                        UpdateLog.updateLastError(TAG);
                    } else {
                        if(ClientModel.getClientModel().CloseAudio()) {
                            UpdateLog.updateI(TAG, "CloseAudio OK");
                            return;
                        }
                        UpdateLog.updateI(TAG, "CloseAudio faild");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.tbtn_control_zoom:
                    ChangeLandscape();
                    break;
                // button line 2 -----------------------------------------

                case R.id.btn_live:
                    ClientModel.getClientModel().Quit();
                    if(!ClientModel.getClientModel().CheckDeviceOnline()) {
                        mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 0);
                        return;
                    }
                    UpdateLog.updateI(TAG, "LiveAndPlay ...");
                    if(ClientModel.getClientModel().LiveAndPlay()) {
                        Calendar now = Calendar.getInstance();
                        mSkbProcess.setProgress(now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));

                        UpdateLog.updateI(TAG, "【true】Play state");
                        mTbtnPlay.setChecked(true);
                    } else {
                        UpdateLog.updateI(TAG, "Un Start!");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_stop:
                    if(ClientModel.getClientModel().StopPlay()) {
                        UpdateLog.updateI(TAG, "【true】Stop state");
                        mRateUpdateHandler.removeMessages(0x01);
                    } else
                        UpdateLog.updateI(TAG, "Un Start or unplay!");
                    UpdateLog.updateLastError(TAG);
                    break;
                case R.id.btn_quit:
                    if(ClientModel.getClientModel().Quit()) {
                        UpdateLog.updateI(TAG, "【true】Quit state");
                        mRateUpdateHandler.removeMessages(0x01);
                        finish();
                    } else {
                        UpdateLog.updateI(TAG, "【false】Quit state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_snap:
                    if(ClientModel.getClientModel().GetScreenShot("/sdcard/test.jpg")) {
                        UpdateLog.updateI(TAG, "【true】GetScreenShot state");
                    } else {
                        UpdateLog.updateI(TAG, "【false】GetScreenShot state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                // button line 3 -----------------------------------------

                case R.id.btn_devicedetail:
                    if(ClientModel.getClientModel().QueryDeviceDetailInfo()) {
                        UpdateLog.updateI(TAG, "【true】QueryDeviceDetailInfo state");
                        for (int i = 0; i < ClientModel.getClientModel().getChannelFullInfo().size(); i++) {
                            PrintFormat.ChannelFullInfo(ClientModel.getClientModel().getChannelFullInfo().get(i), ClientModel.getClientModel().mCurDevice);
                        }
                    } else {
                        UpdateLog.updateI(TAG, "【false】QueryDeviceDetailInfo state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_grantusers:
                    if(ClientModel.getClientModel().GetGrantUsers()) {
                        UpdateLog.updateI(TAG, "【true】GetGrantUsers state");
                        PrintFormat.GrantUsers(ClientModel.getClientModel().mGrantUsers);
                    } else {
                        UpdateLog.updateI(TAG, "【false】GetGrantUsers state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_deviceonline:
                    if(ClientModel.getClientModel().CheckDeviceOnline()) {
                        UpdateLog.updateI(TAG, "【true】CheckDeviceOnline state");
                    } else {
                        UpdateLog.updateI(TAG, "【false】CheckDeviceOnline state");
                        UpdateLog.updateLastError(TAG);
                    }
                    break;
                case R.id.btn_cleanscreen:
                    UpdateLog.clean(TAG);
                    break;

                default:
                    break;

            }
        }
    };

    @Override
    public void OnStatusMsg(int status_code, String msg) {
        //UpdateLog.updateI(TAG, "Error:401");
        Log.i(TAG, "Error:401 001");
    }

    @Override
    public void OnPlaystateChange(String device_id, int idx, int rate, int state, String msg) {
        switch (state) {
            case MessageNum.AY_NET_STAT:
                if(mUprate) {
                    mUprate = false;
                    //mNetworkBits = msg.substring(0, msg.indexOf("/") + 2); //隐藏以缓冲数据大小
                    mNetworkBits = msg;
                    mRateUpdateHandler.sendEmptyMessageDelayed(0x01, 1000);
                }
                break;
            case MessageNum.AY_SESSION_RECV_KEY_FRAME:
                mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 300);
                break;
            case MessageNum.AY_SESSION_RECV_TS:
                mRateUpdateHandler.sendEmptyMessageDelayed(0x02, 1000);
                break;
        }
    }

    @Override
     public void OnNvrHistoryList(String device_id, int channel_idx, int rate, int start_time, int[] start_history, int[] end_history) {
        Log.i(TAG, "OnNvrHistoryList CallBack.");
        Log.i(TAG, "start_time=" + start_time);
        Log.i(TAG, "list size = " + start_history.length);
        Log.i(TAG, "list size = " + end_history.length);

        PrintFormat.NVRHistory(JHistory.ParseJHistory(start_history, end_history));
    }

    @Override
    public void OnRecvOEMData(byte[] data, int data_len) {
        Log.i(TAG, "OnRecvOEMData CallBack.");
    }

    /*@Override
    public void onAudioRecord(byte[] audioByte) {
        if (audioByte != null) {
            if(ClientModel.getClientModel().Speak(audioByte, audioByte.length)) {
                UpdateLog.updateI(TAG, "【true】Speak state");
            } else {
                UpdateLog.updateI(TAG, "【false】Speak state");
                UpdateLog.updateLastError(TAG);
            }
        }
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.w(TAG, "SurfaceHolder Created");
        if(bfirtIn == true)
        {
            bfirtIn = false;
        }
        else
        {
            ClientModel.getClientModel().ActivePlayer(mSurfaceHolder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.w(TAG, "SurfaceHolder Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.w(TAG, "SurfaceHolder Destroyed");
        ClientModel.getClientModel().DisActivePlayer();
    }
}
