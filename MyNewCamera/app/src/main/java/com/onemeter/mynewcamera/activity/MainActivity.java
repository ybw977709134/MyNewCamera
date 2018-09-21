package com.onemeter.mynewcamera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onemeter.mynewcamera.R;
import com.onemeter.mynewcamera.app.BaseActivity;
import com.onemeter.mynewcamera.fragment.CameraFragment;
import com.onemeter.mynewcamera.fragment.GuangChangFragment;
import com.onemeter.mynewcamera.fragment.MyFragment;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private long mExitTime;
    /**
     * 三个切换按钮
     **/
    private View rb1, rb2,rb3;
    /**
     * 三个fragment对象
     **/
    private CameraFragment fragment1;
    private MyFragment fragment2;
    private GuangChangFragment fragment3;
    // 定义FragmentManager
     FragmentManager fragmentManager;
    /**
     * 三种切换的图标和文字
     **/
    private ImageView rb1_img, rb2_img,rb3_img;
    private TextView rb1_text, rb2_text,rb3_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragmentManager =getSupportFragmentManager();
        // 默认打开第一个选项
        setTabSelection(R.id.rb1);
    }



    /**
     * 初始化组件
     */
    private void initView() {
        rb1_img = (ImageView) findViewById(R.id.rb1_img);
        rb2_img = (ImageView) findViewById(R.id.rb2_img);
        rb3_img = (ImageView) findViewById(R.id.rb3_img);

        rb1_text = (TextView) findViewById(R.id.rb1_text);
        rb2_text = (TextView) findViewById(R.id.rb2_text);
        rb3_text = (TextView) findViewById(R.id.rb3_text);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);

        rb2.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb3.setOnClickListener(this);
    }

    // 当点击了消息tab时，选中第1个tab
    public void setTabSelection(int i) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 创建FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case R.id.rb1:
                rb1_text.setTextColor(getResources().getColor(R.color.result_view));
                rb1_img.setImageResource(R.mipmap.icon_camera_current);
                if (fragment1 == null) {
                    // 如果HomeFragment为空，则创建一个并添加到界面上
                    fragment1 = new CameraFragment();
                    fragmentTransaction.add(R.id.main_fragment, fragment1);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(fragment1);
                }
                break;
            case R.id.rb2:
                rb2_text.setTextColor(getResources().getColor(R.color.result_view));
                rb2_img.setImageResource(R.mipmap.icon_my_h);
                if (fragment2 == null) {
                    // 如果HomeFragment为空，则创建一个并添加到界面上
                    fragment2 = new MyFragment();
                    fragmentTransaction.add(R.id.main_fragment, fragment2);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(fragment2);
                }
                break;
            case R.id.rb3:
                rb3_text.setTextColor(getResources().getColor(R.color.result_view));
                rb3_img.setImageResource(R.mipmap.icon_square_current);
                if(fragment3==null){
                    fragment3 = new GuangChangFragment();
                    fragmentTransaction.add(R.id.main_fragment, fragment3);
                }else {
                    fragmentTransaction.show(fragment3);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        toast("数据"+data);
    }

    // 清除掉所有的选中状态。
    private void clearSelection() {
        rb1_text.setTextColor(getResources().getColor(R.color.balck_title_02));
        rb1_img.setImageResource(R.mipmap.icon_camera);
        rb2_text.setTextColor(getResources().getColor(R.color.balck_title_02));
        rb2_img.setImageResource(R.mipmap.icon_my);
        rb3_text.setTextColor(getResources().getColor(R.color.balck_title_02));
        rb3_img.setImageResource(R.mipmap.icon_square);
    }


    // 将所有的Fragment都置为隐藏状态。
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }
        if (fragment3 != null) {
            fragmentTransaction.hide(fragment3);
        }

    }

    // 监听手机上的BACK键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 判断两次点击的时间间隔（默认设置为2秒）
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
//                finish();
//                System.exit(0);
                Intent intent = new Intent();
                intent.setAction(BaseActivity.SYSTEM_EXIT);
                sendBroadcast(intent);
                super.onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                // 当点击了消息tab时，
                setTabSelection(R.id.rb1);
                break;
            case R.id.rb2:
                //选中我的
                setTabSelection(R.id.rb2);
                break;
            case R.id.rb3:
                //视频广场
                setTabSelection(R.id.rb3);
                break;
        }
    }
}
