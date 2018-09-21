package com.onemeter.mynewcamera.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.anyan.client.model.ClientModel;
import com.anyan.client.model.RecordRenderer;
import com.anyan.client.sdk.AYClientSDKCallBack;
import com.anyan.client.sdk.JDeviceBasic;
import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.adapter.ChannelAdapter;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.app.MyApplication;
import com.onemeter.mynewcamera.utils.SDcardTools;
import com.onemeter.mynewcamera.view.BuileGestureExt;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 描述：播放器功能
 * 项目名称：MyCamera
 * 时间：2016/6/22 10:04
 * 备注：
 */
public class CameraPlayerActivity extends BaseActivity implements AYClientSDKCallBack, SurfaceHolder.Callback, View.OnClickListener, RecordRenderer.OnAudioRecordListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；

    private Context mContext;
    private FrameLayout mSurfaceLayout;
    private SurfaceView mSurfaceView;//播放组件
    private ProgressBar mProLoading;//进度条
    private TextView txt_rate;//码率
    private ToggleButton tbtn_control_play;//播放按钮
    private ToggleButton tbtn_control_voice;//声音开关
    private ToggleButton mTbtnZoom;//全屏开关
    private Spinner mSpnResolution;//码率选择框

    private SurfaceHolder mSurfaceHolder;
    private ListView mLsvChannel;

    private String mNetworkBits = " 0.00Kb/s";
    boolean bfirtIn = true;
    boolean mIsPlay = false;

    private String type_sreen = "竖屏";
    private RelativeLayout mControlLayout;//播放器底部控制布局

    private ToggleButton record_btn;//录制视频开关
    private ImageButton mBtnSpeak;//按住对讲
    private ImageButton snap_btn;//截图按钮

    private String mun;//获取当前的时间
    private RecordRenderer mRecordRenderer;
    private GestureDetector gestureDetector;//手势操作api类
    private ImageView camera_setting_back;//返回键
    private TextView camera_player_title;//视频直播的标题
    private LinearLayout camera_player_share_btn;//设备分享按钮
    private LinearLayout camera_player_setting_btn;//摄像机设置按钮
    private Intent intent;
    RelativeLayout rel_title;//竖屏标题

    private RelativeLayout btn_device_action_rel;
    private RelativeLayout rlycontrol;

    //横屏操作
    private RelativeLayout right_rel_main;//右边布局容器
    RelativeLayout rel_title_lan;//横屏标题栏
    private ImageView camera_setting_back_lan;//返回键
    private TextView camera_player_title_lan;//标题
    private Spinner tbtn_control_resolution_lan;//码率
    private ToggleButton tbtn_control_voice_lan;//声音
    private ToggleButton record_btn_lan;//录屏
    private ImageButton mBtnSpeak_lan;//录音操作
    private ImageButton snap_btn_lan;//截屏操作

    private TextView txt_rate_lan;//网速

    //竖屏按钮背景
    private RelativeLayout bg_record_btn_rel;
    private RelativeLayout bg_mBtnSpeak_rel;
    private RelativeLayout bg_snap_btn_rel;

    //横屏按钮背景
    private RelativeLayout record_btn_rel;
    private RelativeLayout mBtnSpeak_rel;
    private RelativeLayout snap_btn_rel;


    private Handler mHandler;

    private TextView record_btn_time_min;//录屏时长分钟
    private TextView record_btn_time_sec;//录屏市场秒钟
    private RelativeLayout record_btn_time_rel;//录屏布局

    private TextView bg_mBtnSpeak_time_massg;//录音倒计时
    private TextView bg_snap_btn_massg;//截屏提示
    //秒表计时
    private boolean isPaused = false;
    private String timeUsed;
    private int timeUsedInsec;

    //手势操作提示
    private  ImageView left_and_right;
    private  ImageView up_and_down;

    /**
     * 倒计时
     **/
    private TimeCount timeCount;
    private Handler uiHandle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (!isPaused) {
                        addTimeUsed();
                        updateClockUI();
                    }
                    uiHandle.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        mContext = this;
        mHandler = new Handler();
        setContentView(R.layout.layout_player_main);
        initView();
        initData();
    }


    /**
     * 初始化播放数据
     */
    private void initData() {
        bfirtIn = true;
        mRecordRenderer = new RecordRenderer(this);
        if (ClientModel.getClientModel().mCurDevice.getDeviceOwner() == JDeviceBasic.DeviceOwner.Device_Share) {//如果是分享设备
            //需要隐藏的布局文件
        }
        //设置播放默认码率
        mSpnResolution.setPrompt(ClientModel.getClientModel().mCurDevice.getRates()[0].getRateName());
        tbtn_control_resolution_lan.setPrompt(ClientModel.getClientModel().mCurDevice.getRates()[0].getRateName());
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, ClientModel.getClientModel().GetRateList());
        mSpnResolution.setAdapter(_Adapter);
        tbtn_control_resolution_lan.setAdapter(_Adapter);

        if (ClientModel.getClientModel().QueryDeviceDetailInfo()) {
            if (!ClientModel.getClientModel().getKindergarten()) {
                //NVR-------------------------------------
                if (ClientModel.getClientModel().mCurDevice.getChannelNum() > 1 && ClientModel.getClientModel().mCurDevice.getDeviceTypeId() != JDeviceBasic.DeviceTypeId.Device_IPC) {
                    mLsvChannel.setVisibility(View.VISIBLE);
                    List<String> channels = new ArrayList<String>();
                    for (int i = 0; i < ClientModel.getClientModel().mCurDevice.getChannelNum(); i++) {
                        channels.add("channel " + (i + 1));
                    }
                    String channlestate = ClientModel.getClientModel().mCurDevice.getChannelMask();
                    if (channlestate.length() <= 1) {
                        channlestate = "";
                        for (int i = 0; i < ClientModel.getClientModel().mCurDevice.getChannelNum(); i++) {
                            channlestate += "0";
                        }
                    }
                    mLsvChannel.setAdapter(new ChannelAdapter(mContext, channels, channlestate));

                }
                //IPC-------------------------------------
                else {
                    mLsvChannel.setVisibility(View.GONE);
                }

            }
        }

        //手势相关
        gestureDetector = new BuileGestureExt(this, new BuileGestureExt.OnGestureResult() {
            @Override
            public void onGestureResult(int direction) {
                if (direction == 0) {//向上
                    up_and_down.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ClientModel.getClientModel().MoveUp(10)) {
                                    up_and_down.setVisibility(View.GONE);
                                    ClientModel.getClientModel().StopAction();
                                } else {
                                    toast(ClientModel.getClientModel().mLastErrorDesc);
                                }

                            } catch (Exception e) {
                                mHandler.removeCallbacks(this);
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                }
                if (direction == 1) {//向下
                   up_and_down.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ClientModel.getClientModel().MoveDown(10)) {
                                    up_and_down.setVisibility(View.GONE);
                                    ClientModel.getClientModel().StopAction();
                                } else {
                                    toast(ClientModel.getClientModel().mLastErrorDesc);
                                }


                            } catch (Exception e) {
                                mHandler.removeCallbacks(this);
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                }

                if (direction == 2) {//向左
                   left_and_right.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ClientModel.getClientModel().MoveLeft(10)) {
                                    left_and_right.setVisibility(View.GONE);
                                    ClientModel.getClientModel().StopAction();
                                } else {
                                    toast(ClientModel.getClientModel().mLastErrorDesc);
                                }

                            } catch (Exception e) {
                                mHandler.removeCallbacks(this);
                                e.printStackTrace();
                            }
                        }
                    }, 200);
                }

                if (direction == 3) {//向右
                    left_and_right.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ClientModel.getClientModel().MoveRight(10)) {
                                    left_and_right.setVisibility(View.GONE);
                                    ClientModel.getClientModel().StopAction();
                                } else {
                                    toast(ClientModel.getClientModel().mLastErrorDesc);
                                }
                            } catch (Exception e) {
                                mHandler.removeCallbacks(this);
                                e.printStackTrace();
                            }
                        }
                    }, 200);

                }
            }
        }
        ).Buile();

    }

    /**
     * 横屏竖屏 适配 设置
     */
    private void ChangeLandscape() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            changeSurfaceLandscape(true);
        } else if (this.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            changeSurfaceLandscape(false);
        }
    }


    public int Height_Surface = 350;
    public int dp2pixHeightSurface;

    //    public static final int Height_ControlTable = 180;
    private void changeSurfaceLandscape(boolean landscape) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        FrameLayout.LayoutParams fllpSurface;
        if (landscape) {
            type_sreen = "横屏";
            Height_Surface = 360;
            txt_rate_lan.setVisibility(View.VISIBLE);
            right_rel_main.setVisibility(View.VISIBLE);
            rlycontrol.setVisibility(View.GONE);
            rel_title_lan.setVisibility(View.VISIBLE);
            btn_device_action_rel.setVisibility(View.GONE);
            rel_title.setVisibility(View.GONE);
            txt_rate_lan.setText(mNetworkBits);
            dp2pixHeightSurface = (int) (this.getResources().getDisplayMetrics().density * Height_Surface + 0.5f);
            fllpSurface = new FrameLayout.LayoutParams(dm.widthPixels, dp2pixHeightSurface, Gravity.TOP);
            mSurfaceView.setLayoutParams(fllpSurface);

        } else {
            type_sreen = "竖屏";
            Height_Surface = 211;
            txt_rate_lan.setVisibility(View.GONE);
            right_rel_main.setVisibility(View.GONE);
            rlycontrol.setVisibility(View.VISIBLE);
            rel_title_lan.setVisibility(View.GONE);
            rel_title.setVisibility(View.VISIBLE);
            btn_device_action_rel.setVisibility(View.VISIBLE);
            dp2pixHeightSurface = (int) (this.getResources().getDisplayMetrics().density * Height_Surface + 0.5f);
            fllpSurface = new FrameLayout.LayoutParams(dm.widthPixels, dp2pixHeightSurface, Gravity.TOP);
            mSurfaceView.setLayoutParams(fllpSurface);

        }

    }

    /**
     * 初始化组件
     */
    private void initView() {
        ClientModel.getClientModel().SetSDKCallback(this);
        timeCount = new TimeCount(50000, 1000);
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.cut_picture_music, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级


        left_and_right= (ImageView) findViewById(R.id.left_and_right);
        up_and_down= (ImageView) findViewById(R.id.up_and_down);

        record_btn_time_min = (TextView) findViewById(R.id.record_btn_time_min);
        record_btn_time_sec = (TextView) findViewById(R.id.record_btn_time_sec);
        record_btn_time_rel = (RelativeLayout) findViewById(R.id.record_btn_time_rel);
        record_btn_time_rel.setVisibility(View.GONE);

        bg_mBtnSpeak_time_massg = (TextView) findViewById(R.id.bg_mBtnSpeak_time_massg);
        bg_mBtnSpeak_time_massg.setVisibility(View.GONE);
        bg_snap_btn_massg = (TextView) findViewById(R.id.bg_snap_btn_massg);
        bg_snap_btn_massg.setVisibility(View.GONE);

        record_btn_rel= (RelativeLayout) findViewById(R.id.record_btn_rel);
        mBtnSpeak_rel= (RelativeLayout) findViewById(R.id.mBtnSpeak_rel);
        snap_btn_rel= (RelativeLayout) findViewById(R.id.snap_btn_rel);

        bg_snap_btn_rel = (RelativeLayout) findViewById(R.id.bg_snap_btn_rel);
        bg_record_btn_rel = (RelativeLayout) findViewById(R.id.bg_record_btn_rel);
        bg_mBtnSpeak_rel = (RelativeLayout) findViewById(R.id.bg_mBtnSpeak_rel);
        txt_rate_lan = (TextView) findViewById(R.id.txt_rate_lan);
        right_rel_main = (RelativeLayout) findViewById(R.id.right_rel_main);
        rlycontrol = (RelativeLayout) findViewById(R.id.rlycontrol);
        rel_title_lan = (RelativeLayout) findViewById(R.id.rel_title_lan);
        btn_device_action_rel = (RelativeLayout) findViewById(R.id.btn_device_action_rel);
        rel_title = (RelativeLayout) findViewById(R.id.rel_title);
        camera_player_share_btn = (LinearLayout) findViewById(R.id.camera_player_share_btn);
        camera_player_setting_btn = (LinearLayout) findViewById(R.id.camera_player_setting_btn);

        camera_player_title = (TextView) findViewById(R.id.camera_player_title);
        camera_player_title_lan = (TextView) findViewById(R.id.camera_player_title_lan);
        camera_player_title.setText(MyApplication.device_name.get(0).toString() + "直播中");
        camera_player_title_lan.setText(MyApplication.device_name.get(0).toString() + "直播中");
        camera_setting_back = (ImageView) findViewById(R.id.camera_setting_back);
        camera_setting_back_lan = (ImageView) findViewById(R.id.camera_setting_back_lan);
        record_btn = (ToggleButton) findViewById(R.id.record_btn);
        record_btn_lan = (ToggleButton) findViewById(R.id.record_btn_lan);
        mBtnSpeak = (ImageButton) findViewById(R.id.mBtnSpeak);
        mBtnSpeak_lan = (ImageButton) findViewById(R.id.mBtnSpeak_lan);
        snap_btn = (ImageButton) findViewById(R.id.snap_btn);
        snap_btn_lan = (ImageButton) findViewById(R.id.snap_btn_lan);
        mBtnSpeak.setOnTouchListener(this);
        mBtnSpeak_lan.setOnTouchListener(this);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        ClientModel.getClientModel().SetSurface(mSurfaceView);
        mProLoading = (ProgressBar) findViewById(R.id.pro_loading);
        txt_rate = (TextView) findViewById(R.id.txt_rate);
        txt_rate.setText(mNetworkBits);
        mControlLayout = (RelativeLayout) findViewById(R.id.rlycontrol);
        tbtn_control_play = (ToggleButton) findViewById(R.id.tbtn_control_play);
        tbtn_control_voice = (ToggleButton) findViewById(R.id.tbtn_control_voice);
        tbtn_control_voice_lan = (ToggleButton) findViewById(R.id.tbtn_control_voice_lan);
        mTbtnZoom = (ToggleButton) findViewById(R.id.tbtn_control_zoom);
        mSpnResolution = (Spinner) findViewById(R.id.tbtn_control_resolution);
        tbtn_control_resolution_lan = (Spinner) findViewById(R.id.tbtn_control_resolution_lan);
        mSpnResolution.setOnItemSelectedListener(this);
        tbtn_control_resolution_lan.setOnItemSelectedListener(this);


        mLsvChannel = (ListView) findViewById(R.id.lsv_channel);
        mLsvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProLoading.setVisibility(View.VISIBLE);
                ClientModel.getClientModel().SetSurface(mSurfaceView);
                ClientModel.getClientModel().Quit();
                ClientModel.getClientModel().LiveAndPlay(position + 1);
                mIsPlay = true;
            }
        });

        mSurfaceView.setOnTouchListener(this);

        camera_setting_back.setOnClickListener(this);
        camera_setting_back_lan.setOnClickListener(this);
        mSurfaceView.setOnClickListener(this);
        tbtn_control_play.setOnClickListener(this);
        tbtn_control_voice.setOnClickListener(this);
        tbtn_control_voice_lan.setOnClickListener(this);
        mTbtnZoom.setOnClickListener(this);

        record_btn.setOnClickListener(this);
        record_btn_lan.setOnClickListener(this);
        snap_btn.setOnClickListener(this);
        snap_btn_lan.setOnClickListener(this);

        camera_player_share_btn.setOnClickListener(this);
        camera_player_setting_btn.setOnClickListener(this);
        mSurfaceHolder.addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        tbtn_control_play.setChecked(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        tbtn_control_play.setChecked(false);
    }


    private boolean mFullScreen = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_setting_back:
                //竖屏返回键
                finish();
            case R.id.camera_setting_back_lan:
                //横屏返回键
                ChangeLandscape();
                break;
            case R.id.surfaceView:
                //播放视图
                if (type_sreen.equals("横屏")) {
                    if (mFullScreen) {
                        rel_title_lan.setVisibility(View.VISIBLE);
                        right_rel_main.setVisibility(View.VISIBLE);
                    } else {
                        rel_title_lan.setVisibility(View.GONE);
                        right_rel_main.setVisibility(View.GONE);
                    }
                    mFullScreen = !mFullScreen;
                }

                break;
            case R.id.tbtn_control_play:
                //播放开关
                if (ClientModel.getClientModel().mCurDevice.getDeviceStatus() == 0) {
                    mUpdateHandler.sendEmptyMessageDelayed(UpdateUi_LoadingGONE, 0);
                    return;
                }
                if (tbtn_control_play.isChecked()) {
                    mUpdateHandler.sendEmptyMessageDelayed(UpdateUi_LoadingVISIBLE, 0);
                    ClientModel.getClientModel().SetSurface(mSurfaceView);
                    ClientModel.getClientModel().Quit();
                    if (ClientModel.getClientModel().LiveAndPlay_ChangeRate(1, 500)) {
                        tbtn_control_play.setChecked(true);
                        mIsPlay = true;
                    }
                } else {
                    if (ClientModel.getClientModel().PausePlay()) {
                        mIsPlay = false;
                        return;
                    }
                }
                break;
            case R.id.tbtn_control_voice:
                //
                if (tbtn_control_voice.isChecked()) {
                    if (ClientModel.getClientModel().OpenAudio()) {
                        return;
                    }
                } else {
                    if (ClientModel.getClientModel().CloseAudio()) {
                        return;
                    }
                }

                break;
            case R.id.tbtn_control_voice_lan:
                //声音开关
                if (tbtn_control_voice_lan.isChecked()) {
                    if (ClientModel.getClientModel().OpenAudio()) {
                        return;
                    }
                } else {
                    if (ClientModel.getClientModel().CloseAudio()) {
                        return;
                    }
                }

                break;
            case R.id.tbtn_control_zoom:
                //尺寸开关
                ChangeLandscape();
                break;
            case R.id.record_btn_lan:
                Date date1 = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                mun = sdf1.format(date1);
                //开始录制视频
                if (record_btn_lan.isChecked()) {//打开录制开关
                    if (ClientModel.getClientModel().StartRecord("/sdcard/test" + mun + ".mp4")) {
                        toast("录制视频中");
                        record_btn_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_02));
                    } else {
                        toast(ClientModel.getClientModel().mLastErrorDesc);
                    }
                }else{
                    if (ClientModel.getClientModel().StopRecord()) {
                        toast("录制已完成");
                        record_btn_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_04));
                    }
                }

                break;
            case R.id.record_btn:
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                mun = sdf.format(date);
                //开始录制视频
                if (record_btn.isChecked()) {//打开录制开关
                    if (ClientModel.getClientModel().StartRecord("/sdcard/test" + mun + ".mp4")) {
                        toast("录制视频中");
                        record_btn_time_rel.setVisibility(View.VISIBLE);
                        uiHandle.removeMessages(1);
                        startTime();
                        isPaused = false;
                        bg_record_btn_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_02));
                    } else {
                        toast(ClientModel.getClientModel().mLastErrorDesc);
                    }
                } else {
                    if (ClientModel.getClientModel().StopRecord()) {
                        toast("录制已完成");
                        record_btn_time_rel.setVisibility(View.GONE);
                        isPaused = true;
                        timeUsedInsec = 0;
                        bg_record_btn_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_03));
                    }
                }

                break;
            case R.id.snap_btn_lan:
                sp.play(music, 1, 1, 0, 0, 1);
                getJiePing();
            case R.id.snap_btn:
                //截图
                sp.play(music, 1, 1, 0, 0, 1);
                getJiePing1();
                break;
            case R.id.camera_player_setting_btn:
                //摄像机设置按钮
                intent = new Intent(CameraPlayerActivity.this, CameraSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.camera_player_share_btn:
                //摄像机分享按钮
                intent = new Intent(CameraPlayerActivity.this, ShareCameraIdActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }


    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/good/savePic";//保存的确切位置


    public static void saveFile(Bitmap bm, String fileName, String path) throws IOException {
        String subForder = SAVE_REAL_PATH + path;
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }


    /**
     * 对视频截屏的方法
     */

    private void getJiePing1() {
        if (ClientModel.getClientModel().GetScreenShot(SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg")) {
            Bitmap bitmap = BitmapFactory.decodeFile(SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
            try {
                saveFile(bitmap, MyApplication.device_id.get(0) + ".jpg", "");
                if (MyApplication.device_position.get(0).getType().equals("私有")) {
                    MyApplication.device_path.put(MyApplication.device_position.get(0).getPosition(), SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
                } else {
                    MyApplication.device_gy_path.put(MyApplication.device_position.get(0).getPosition(), SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
//            toast("保存截图成功");
            bg_snap_btn_massg.setVisibility(View.VISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bg_snap_btn_massg.setVisibility(View.GONE);
                }
            }, 1000);


        } else {
            toast(ClientModel.getClientModel().mLastErrorDesc);
        }
    }


    /**
     * 对视频截屏的方法
     */

    private void getJiePing() {
        if (ClientModel.getClientModel().GetScreenShot(SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg")) {
            Bitmap bitmap = BitmapFactory.decodeFile(SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
            try {
                saveFile(bitmap, MyApplication.device_id.get(0) + ".jpg", "");
                if (MyApplication.device_position.get(0).getType().equals("私有")) {
                    MyApplication.device_path.put(MyApplication.device_position.get(0).getPosition(), SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
                } else {
                    MyApplication.device_gy_path.put(MyApplication.device_position.get(0).getPosition(), SDcardTools.getSDPath() + "/" + MyApplication.device_id.get(0) + ".jpg");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            toast(ClientModel.getClientModel().mLastErrorDesc);
        }
    }


    private static final int UpdateUi_Network = 0x1002;
    private static final int UpdateUi_LoadingGONE = 0x1003;
    private static final int UpdateUi_LoadingVISIBLE = 0x1004;
    private static final int UpdateUi_StatusMsg = 0x1005;
    private static final int UpdateUi_PlaystateChange = 0x1006;
    private static final int UpdateUi_Quit = 0x1010;
    private static final int UpdateUi_ControlMax = 0x10FF;
    private static final int UpdateUi_Remove = 0x0000;
    private static final int UpdateUi_Control = 0x1000;
    //
    private static final int UpdateUi_OEM = 0x2100;
    private static final int UpdateUi_OEMMax = 0x21FF;

    private Handler mUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what >= UpdateUi_Control && msg.what <= UpdateUi_ControlMax) {
                switch (msg.what) {
                    case UpdateUi_Network:
                        txt_rate.setText(mNetworkBits);
                        break;
                    case UpdateUi_Quit:
                        ClientModel.getClientModel().Quit();
                        break;
                    case UpdateUi_LoadingGONE:
                        getJiePing();
                        mProLoading.setVisibility(View.GONE);
                        break;
                    case UpdateUi_LoadingVISIBLE:
                        mProLoading.setVisibility(View.VISIBLE);
                        break;
                    case UpdateUi_PlaystateChange:
                    case UpdateUi_StatusMsg:
                        break;
                }

            }
            super.handleMessage(msg);
        }
    };


    private void startTime() {
        uiHandle.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * 更新时间的显示
     */
    private void updateClockUI() {
        record_btn_time_min.setText(getMin() + ":");
        record_btn_time_sec.setText(getSec());
    }

    public void addTimeUsed() {
        timeUsedInsec = timeUsedInsec + 1;
        timeUsed = this.getMin() + ":" + this.getSec();
    }

    public CharSequence getMin() {
        return String.valueOf(timeUsedInsec / 60);
    }

    public CharSequence getSec() {
        int sec = timeUsedInsec % 60;
        return sec < 10 ? "0" + sec : String.valueOf(sec);
    }


    @Override
    public void OnStatusMsg(int status_code, String msg) {
    }

    @Override
    public void OnPlaystateChange(String device_id, int idx, int rate, int state, String msg) {
        Message message = new Message();
        message.what = UpdateUi_PlaystateChange;
        switch (state) {
            case MessageNum.AY_NET_STAT:
                mNetworkBits = msg;
                message.what = UpdateUi_Network;
                message.obj = msg;
                break;
            case MessageNum.AY_PLAYER_LOADING_START:
                message.what = UpdateUi_LoadingVISIBLE;
                message.obj = "[OnPlay] （缓冲开始）, " + msg;
                break;
            case MessageNum.AY_PLAYER_LOADING_END:
                message.what = UpdateUi_LoadingGONE;
                message.obj = "[OnPlay] （缓冲完成）, " + msg;
                break;
        }
        mUpdateHandler.sendMessage(message);

    }

    @Override
    public void OnNvrHistoryList(String s, int i, int i1, int i2, int[] ints, int[] ints1) {

    }

    @Override
    public void OnRecvOEMData(byte[] bytes, int i) {

    }

    @Override
    public void OnApiState(int i, String s) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ClientModel.getClientModel().ActivePlayer(mSurfaceHolder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ClientModel.getClientModel().DisActivePlayer();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ClientModel.getClientModel().Quit()) {
                mUpdateHandler.removeMessages(UpdateUi_Remove);
            }
            mSurfaceHolder.removeCallback(this);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ClientModel.getClientModel().Quit()) {
            mUpdateHandler.removeMessages(UpdateUi_Remove);
        }
        mSurfaceHolder.removeCallback(this);
    }

    /**
     * 语音对讲操作
     *
     * @param audioByte
     */

    @Override
    public void onAudioRecord(byte[] audioByte) {
        if (audioByte != null) {
            if (ClientModel.getClientModel().Speak(audioByte, audioByte.length)) {
//                toast("正常对讲中。。。");
            } else {
                toast(ClientModel.getClientModel().mLastErrorDesc);
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mSpnResolution) {
            if (ClientModel.getClientModel().mCurDevice.getDeviceStatus() == 0) {
                mUpdateHandler.sendEmptyMessageDelayed(UpdateUi_LoadingGONE, 0);
                return;
            }
            TextView tv = (TextView) view;
            if (tv != null) {
                tv.setTextColor(getResources().getColor(R.color.result_view_02));
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
                ClientModel.getInstance().Quit();
                if (ClientModel.getClientModel().ChanageRate(position, mSurfaceHolder.getSurface())) {
                    tbtn_control_play.setChecked(true);
                    mIsPlay = true;
                    return;
                }
            }
        }

        if (parent == tbtn_control_resolution_lan) {
            if (ClientModel.getClientModel().mCurDevice.getDeviceStatus() == 0) {
                mUpdateHandler.sendEmptyMessageDelayed(UpdateUi_LoadingGONE, 0);
                return;
            }
            TextView tv = (TextView) view;
            if (tv != null) {
                tv.setTextColor(getResources().getColor(R.color.result_view_02));
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
                ClientModel.getInstance().Quit();
                if (ClientModel.getClientModel().ChanageRate(position, mSurfaceHolder.getSurface())) {
                    tbtn_control_play.setChecked(true);
                    mIsPlay = true;
                    return;
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 按钮的触摸事件
     *
     * @param v
     * @param event
     * @return
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == mBtnSpeak) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://开始录音
//                    toast("录音中，保持按住按钮，松开即可发送声音");
                    if (mRecordRenderer.isRealExit()) {
                        new Thread(mRecordRenderer).start();
                        timeCount.start();
                        bg_mBtnSpeak_time_massg.setVisibility(View.VISIBLE);
                        bg_mBtnSpeak_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_05));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (event.getY() < -100) {//上滑取消
                        mRecordRenderer.cannelRecord();
                    } else {
                        toast("语音已发送");
                        mRecordRenderer.stopRecord();
                    }
                    bg_mBtnSpeak_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_03));
                    bg_mBtnSpeak_time_massg.setVisibility(View.GONE);
                    timeCount.onFinish();
                    break;
            }
        }
        /****************************************************/

        if (v == mBtnSpeak_lan) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://开始录音
                    toast("录音中");
                    mBtnSpeak_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_05));
                    if (mRecordRenderer.isRealExit()) {
                        new Thread(mRecordRenderer).start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (event.getY() < -100) {//上滑取消
                        mRecordRenderer.cannelRecord();
                    } else {
                        mRecordRenderer.stopRecord();
                    }
                    toast("语音已发送");
                    mBtnSpeak_rel.setBackground(getResources().getDrawable(R.drawable.bg_style_04));

                    break;
            }
        }

        /**********************************************************************/
        if (v == mBtnSpeak || v == mBtnSpeak_lan) {
            return false;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }


    /**
     * 倒计时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mRecordRenderer.stopRecord();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            bg_mBtnSpeak_time_massg.setText(millisUntilFinished / 1000 + "s后结束");
        }
    }
}
