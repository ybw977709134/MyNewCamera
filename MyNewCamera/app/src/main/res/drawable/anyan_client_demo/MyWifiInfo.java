package drawable.anyan_client_demo;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.anyan.client.anyan_client_demo.tmp.UpdateLog;

/**
 * 设置wifi网络
 */
public class MyWifiInfo {
    public static final String TAG = "__MyWifiInfo";
    public static void getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi网络是否连通
        if(!wifiManager.isWifiEnabled())  {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        UpdateLog.updateI(TAG, "* * * * * * * * * * * * * * * * * * *");
        UpdateLog.updateI(TAG, "Wi-Fi Info:");
        UpdateLog.updateI(TAG, "SSID　　: " + wifiInfo.getSSID());
        UpdateLog.updateI(TAG, "IP　　　: " + intToIp(wifiInfo.getIpAddress()));
        UpdateLog.updateI(TAG, "Netmask : " + FormatString(wifiManager.getDhcpInfo().netmask));
        UpdateLog.updateI(TAG, "Gateway : " + FormatString(wifiManager.getDhcpInfo().gateway));
        UpdateLog.updateI(TAG, "DNS 　　: " + FormatString(wifiManager.getDhcpInfo().dns1));
        UpdateLog.updateI(TAG, "* * * * * * * * * * * * * * * * * * *");
    }

    private static String intToIp(int i)  {
        return (i & 0xFF) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"."+((i >> 24 ) & 0xFF );
    }

    public static String FormatString(int value){
        String strValue="";
        byte[] ary = intToByteArray(value);
        for(int i=ary.length-1;i>=0;i--){
            strValue += (ary[i] & 0xFF);
            if(i>0){
                strValue+=".";
            }
        }
        return strValue;
    }
    public static byte[] intToByteArray(int value){
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++){
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    public static String getSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled())  {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID().replace("\"", "");
    }
}
