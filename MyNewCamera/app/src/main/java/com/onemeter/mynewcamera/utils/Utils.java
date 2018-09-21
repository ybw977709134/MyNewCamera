package com.onemeter.mynewcamera.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这里提供的功能有可能发生变化
 */
public class Utils {

    public static final long DEF_VIBRATE_TIME=500;//ms
    public static final long VIRBRATE_TIME_DELAY = 0;
    public static final long VIRBRATE_TIME_FIRST = 200;
    public static final long VIRBRATE_TIME_INTERVAL = 200;
    public static final long VIRBRATE_TIME_SECOND = 100;
    public static final int VIRBRATE_REPEAT_NO = -1;

    public static void triggerVibrate(Context context,long ms) {
        if(null != context) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if(null != vibrator) {
                vibrator.vibrate(ms);
            }
        }
    }

    /**
     * 间隔震动，入参参见android.os.Vibrator.vibrate(long[] pattern, int repeat)
     * @param context
     * @param pattern an array of longs of times for which to turn the vibrator on or off.
     * @param repeat the index into pattern at which to repeat, or -1 if you don't want to repeat.
     */
    public static void triggerVibrate(Context context, long[] pattern, int repeat) {
        if(null != context) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if(null != vibrator && null != pattern) {
                vibrator.vibrate(pattern, repeat);
            }
        }
    }

    /**
     * 弹出一个Toast
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        if(context==null)return;
        Toast.makeText(context, text, duration).show();
    }

    /**
     * 弹出一个Toast
     * @param context
     */
    public static void showToast(Context context,  int resid) {
        if(context==null)return;
        Utils.showToast(context, context.getString(resid));
    }

    /**
     * 弹出一个Toast,持续Toast.LENGTH_SHORT时间
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }




    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private static void fixPopupWindow(final PopupWindow window) {
        if(null == window) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                final Field fAnchor = PopupWindow.class
                        .getDeclaredField("mAnchor");
                fAnchor.setAccessible(true);
                Field listener = PopupWindow.class
                        .getDeclaredField("mOnScrollChangedListener");
                listener.setAccessible(true);
                final ViewTreeObserver.OnScrollChangedListener originalListener = (ViewTreeObserver.OnScrollChangedListener) listener
                        .get(window);
                ViewTreeObserver.OnScrollChangedListener newListener = new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        try {
                            WeakReference<View> mAnchor = (WeakReference<View>) fAnchor.get(window);
                            if (mAnchor == null || mAnchor.get() == null) {
                                return;
                            } else {
                                originalListener.onScrollChanged();
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                };
                listener.set(window, newListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PopupWindow getFixedPopupWindow(View contentView,int width,int height) {
        PopupWindow momentOpPopWindow = new PopupWindow(contentView, width,height);

        fixPopupWindow(momentOpPopWindow);

        return momentOpPopWindow;
    }

    @SuppressLint("NewApi")
	public static void setViewAlpha(View view, float alpha)
    {
        if (Build.VERSION.SDK_INT < 11)
        {
            AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        }
        else view.setAlpha(alpha);
    }

	public static boolean isNullOrEmpty(String s)
	{
		return s == null || s.equals("");
	}
	
 	public static Element getFirstElementByTagName(Element root, String name) {
 		NodeList lst = root.getElementsByTagName(name);
 		if(lst == null)
 			return null;
 		return (Element)lst.item(0);
 	}

    public static String getFirstTextByTagName(Element root, String name) {
        NodeList lst = root.getElementsByTagName(name);
        if(lst == null)
            return null;
        Element e = (Element)lst.item(0);
        return null == e ? null : e.getTextContent();
    }

    public static long getFirstLongByTagName(Element e, String name, long defValue) {
        return tryParseLong(getFirstTextByTagName(e, name), defValue);
    }

    public static int getFirstIntByTagName(Element e, String name, int defValue) {
        return tryParseInt(getFirstTextByTagName(e, name), defValue);
    }

    public static int tryParseInt(String s, int defaultValue) {
        if (isNullOrEmpty(s))
            return defaultValue;

 		try {
 			return Integer.parseInt(s);
 		} catch (Exception e) {
 			e.printStackTrace();
 			return defaultValue;
 		}
 	}

 	public static long tryParseLong(String s, long defaultValue) {
        if (isNullOrEmpty(s))
            return defaultValue;

        try {
 			return Long.parseLong(s);
 		} catch (Exception e) {
 			e.printStackTrace();
 			return defaultValue;
 		}
 	}

 	public static float tryParseFloat(String s, int defaultValue) {
 		try {
 			return Float.parseFloat(s);
 		} catch (Exception e) {
 			e.printStackTrace();
 			return defaultValue;
 		}
 	}

    public static double tryParseDouble(String s, int defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    // 不抛异常的 url encoder
    public static String urlencodeUtf8(String s)
    {
        String encodeUrl = "";
        if (null != s) {
            try {
                // It will throw NullPointerException if s is null.
                encodeUrl = java.net.URLEncoder.encode(s, "UTF-8");
            }
            catch(java.io.UnsupportedEncodingException e)
            {             
                Log.e("error:", e.toString());
            }
        }
        return encodeUrl;
    }
    
    /**
	 * 转储对象 字符串的内容
	 * <p>
	 */
    public static String dump(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(object.getClass().getSimpleName()).append('{');

        boolean firstRound = true;

        for (Field field : fields) {
            if (!firstRound) {
                sb.append(", ");
            }
            firstRound = false;
            field.setAccessible(true);
            try {
                final Object fieldObj = field.get(object);
                final String value;
                if (null == fieldObj) {
                    value = "null";
                } else {
                    value = fieldObj.toString();
                }
                sb.append(field.getName()).append('=').append('\'')
                        .append(value).append('\'');
            } catch (IllegalAccessException ignore) {
                //this should never happen
            }

        }

        sb.append('}');
        return sb.toString();
    }
    
	public static byte[] concat(byte[] a, byte[] b, int count) {
		// Arrays.copyOf() requires API LEVEL 9
		//byte[] result = Arrays.copyOf(a, a.length + count);
		byte[] result = new byte[a.length + count];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, count);
		return result;
    }


    Context  context;
    /**
     * 从assets文件下解析图片
     * @param resName
     * @return
     */
    public static Bitmap decodeBitmapFromAssets(Context context,String resName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream in = null;
        try {
            //in = AssetsResourcesUtil.openResource(resName);
            in =context.getAssets().open(resName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(in, null, options);
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }



    /**
     * 控制软键盘隐藏
     *
     * @param
     */
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 验证手机号码的格式是否正确
     *
     * @author hutianfeng created at 2015/8/17
     * @param phNum
     * @return
     */
    public static boolean isPhoneNum(String phNum) {
		/*
		 * 电信：189 181 180 177 153 133 1890 1330 1700 173联通：186 185 176 145 156 155
		 * 132 131 130 1860 1709 移动：139 138 137 136 135 134 147 188 187 184 183
		 * 182 1705 178 159 158 157 152 151 150 1391 1390
		 */
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(phNum)) {
            return false;
        } else {
            return phNum.matches(telRegex);
        }

    }

    /**
     * 验证邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
                + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 密码正则表达式
     *
     * @param str
     * @return
     */
    public static boolean passWordd(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z_-]{6,20}");
        Matcher matcher = pattern.matcher(str);
        boolean b = matcher.matches();
        return b;
    }


    /**
     * 手机号码的正则表达式
     */
    public static final String MOBILE = "^1[3458]\\d{9}";
    /**
     * 获取json中指定key对应的value，只支持value为String类型，
     * </br>例：获取json中courier对应的value{"courier"
     * :{"identitycode":"111111111111111",
     * "cellphone":"18516606694","name":"来忠"},"code":0}
     *
     * @author JPH
     * @date 2015-5-22 下午4:31:40
     * @param key
     * @param jsonStr
     * @return
     */
    public static int page = 1;
    public static boolean isupdateChecked = false;

    public static String getInternalJson(String key, String jsonStr) {
        String targetJson = "";
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(jsonStr))
            return targetJson;
        try {
            JSONObject object = new JSONObject(jsonStr);
            targetJson = object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return targetJson == null ? "" : targetJson;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return int[widthPixels,heightPixels]
     */
    public static int[] getScreenWidth(Activity activity) {
        // 获得屏幕宽度
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int displayWidth = outMetrics.widthPixels;// 屏幕的宽度
        int displayHeight = outMetrics.heightPixels;// 屏幕高度
        return new int[] { displayWidth, displayHeight };
    }




    /**
     * 控制手机震动
     *
     * @param context
     * @param duration
     *            震动时间，单位毫秒
     */
    public static void vibration(Context context, Integer... duration) {
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        if (duration != null && duration.length > 0) {
            vibrator.vibrate(duration[0]); // 长按振动
        } else {
            vibrator.vibrate(60); // 长按振动
        }
    }

    /**
     * 获取临时文件的路径如：（/storage/emulated/0/ksudi/temp）
     *
     * @return
     */
    public static File getTempFilePath() {
        File tempFile = new File(getAppFilePath() + "/temp/");
        if (!tempFile.exists()) {// 如果目录不存在则创建目录
            tempFile.mkdirs();
        }
        return tempFile;
    }

    public static String getAppFilePath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return Environment.getExternalStorageDirectory().getPath()
                    + "/ksudi";
        return Environment.getRootDirectory().getPath() + "/ksudi";
    }



    /**
     * 清除应用的本地缓存
     *
     * @author angelyin
     * @date 2014-12-10 下午8:35:59
     * @param context
     * @param cacheDir
     * @return
     */
    public static boolean clearCatch(Context context, File cacheDir) {
        // TODO Auto-generated method stub
        if (cacheDir == null) {
            return false;
        }
        ;
        if (cacheDir.isDirectory()) {
            File[] files = cacheDir.listFiles();
            if (files == null)
                return false;
            for (int i = 0; i < files.length; i++) {
                File tempFile = files[i];
                if (tempFile != null) {
                    if (tempFile.isFile()) {
                        tempFile.delete();
                    } else {
                        clearCatch(context, tempFile);
                    }
                }
            }
        } else {
            cacheDir.delete();
        }
        return true;
    }

    /**
     * 获取缓存目录的大小
     *
     * @author JPH
     * @date 2014-12-10 下午8:36:42
     * @param directory
     * @return
     */
    public static long getCacheSize(File directory) {
        if (directory == null)
            return 0;
        long catchSize = 0;
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null)
                return 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    catchSize += getCacheSize(files[i]);
                } else {
                    catchSize += files[i].length();
                }
            }
        } else {
            catchSize += directory.length();
        }
        return catchSize;
    }

    /**
     * 发声音和震动
     *
     * @author Shoxz
     * @date 2012-12-15 17:32
     */
    public void sendSoundAndVibrate() {

    }

    /**
     * 验证手机号是否正确
     *
     * @Author shawn
     * @Date 2014年12月18日
     * @param cellphone
     * @return true为通过
     */
    public static boolean cellphoneValid(String cellphone) {
        String rgx = "[1][3578]\\d{9}";
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(cellphone);
        return m.matches();
    }

    /**
     * 验证密码是否包含中文
     *
     * @Author shawn
     * @Date 2014年12月18日
     * @param password
     * @return
     */
    public static boolean passwordValid(String password) {
        String rgx = ".*?[\u4E00-\u9FA5]+.*?";
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /** 清楚通知 */
    public static void clearNotification(Context context, int noticeid) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(noticeid);
    }



    /**
     * dp转换成px
     *
     * @author shawn
     * @date 2015-3-3_14-30-42
     * @param dp
     * @param context
     */
    public static int dp2px(int dp, Context context) {
        return ((int) ((context.getResources().getDisplayMetrics().density) * dp));
    }

    /**
     * 将double形式的object转换成long
     *
     * @param object
     * @return
     * @author JPH
     * @date 2015-4-28 下午4:44:14
     */
    public static long doubleObjectToLong(Object object) {
        if (object == null)
            return -1000;
        return Long.parseLong(object.toString().replace(".0", ""));
    }



    /**
     * 密码正则表达式
     *
     * @param str
     * @return
     */
    public static boolean passWord(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z_-]{6,20}");
        Matcher matcher = pattern.matcher(str);
        boolean b = matcher.matches();
        return b;
    }

    /**
     * 判断是否为手机号码 符合返回ture
     *
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        return Regular(str, MOBILE);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param str
     *            匹配的字符串
     * @param pattern
     *            匹配模式
     * @return boolean
     */
    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }


}

