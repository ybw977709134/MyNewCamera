package drawable.anyan_client_demo.tmp;

import android.util.Log;

import com.anyan.client.sdk.JAlarmSetting;
import com.anyan.client.sdk.JChannelFullInfo;
import com.anyan.client.sdk.JCloudStorageInfo;
import com.anyan.client.sdk.JDeviceBasic;
import com.anyan.client.sdk.JHistory;
import com.anyan.client.sdk.JShareThird;
import com.anyan.client.sdk.JUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flin on 2015/9/15.
 */
public class PrintFormat {
    private static final String TAG = "__PrintFormat";
    public static void UserInfo(JUserInfo userinfo) {
        UpdateLog.updateI(TAG, "JUserInfo >>>>>>");
        UpdateLog.updateI(TAG, "strNickName =" + userinfo.getNickName());
        UpdateLog.updateI(TAG, "strRealName =" + userinfo.getRealName());
        UpdateLog.updateI(TAG, "strSourceIp =" + userinfo.getSourceIp());
        UpdateLog.updateI(TAG, "strEmail =" + userinfo.getEmail());
        UpdateLog.updateI(TAG, "strRegisterTime =" + userinfo.getRegisterTime());

        UpdateLog.updateI(TAG, "strMobile =" + userinfo.getMobile());
        UpdateLog.updateI(TAG, "strLastUpdateTime =" + userinfo.getLastUpdateTime());
        UpdateLog.updateI(TAG, "strHead =" + userinfo.getHead());
    }

    public static void DeviceBasic(JDeviceBasic jdb) {
        UpdateLog.updateI(TAG, "JDeviceBasic >>>>>>");
        UpdateLog.updateI(TAG, "iChannelNum = " + jdb.getChannelNum());
        UpdateLog.updateI(TAG, "iDeviceTypeId = " + jdb.getDeviceTypeId());
        UpdateLog.updateI(TAG, "iDeviceParaFlag = " + jdb.getDeviceParaFlag());
        UpdateLog.updateI(TAG, "iDeviceStatus = " + jdb.getDeviceStatus());
        UpdateLog.updateI(TAG, "iDeviceOwner = " + jdb.getDeviceOwner());
        UpdateLog.updateI(TAG, "strAddTime = " + jdb.getAddTime());
        UpdateLog.updateI(TAG, "strUploadRate = " + jdb.getUploadRate());
        UpdateLog.updateI(TAG, "strDeviceSN = " + jdb.getDeviceSN());
        UpdateLog.updateI(TAG, "strLastUpdateTime = " + jdb.getLastUpdateTime());
        UpdateLog.updateI(TAG, "strDeviceAutoId = " + jdb.getDeviceAutoId());
        UpdateLog.updateI(TAG, "strVersion = " + jdb.getVersion());
        UpdateLog.updateI(TAG, "area_info = " + jdb.getAreaInfo());
        UpdateLog.updateI(TAG, "device_name = " + jdb.getDeviceName());
        UpdateLog.updateI(TAG, "strFirstAccessTime = " + jdb.getFirstAccessTime());
        UpdateLog.updateI(TAG, "strToken = " + jdb.getToken());
        UpdateLog.updateI(TAG, "channel_mask = " + jdb.getChannelMask());
        UpdateLog.updateI(TAG, "model_name = " + jdb.getModelName());
        UpdateLog.updateI(TAG, "factory_name = " + jdb.getFactoryName());
        UpdateLog.updateI(TAG, "desc_info = " + jdb.getDescInfo());
        UpdateLog.updateI(TAG, "prev_photo_url = " + jdb.getPrevPhotoUrl());
        UpdateLog.updateI(TAG, "rate_count = " + jdb.getRateCount());
        UpdateLog.updateI(TAG, "pubilc_add = " + jdb.getGrantState());
        for(int i = 0; i < jdb.getRates().length; i++) {
            UpdateLog.updateI(TAG, "No." + i + ": rate_name = " + jdb.getRates()[i].getRateName() + ", rate_value = " + jdb.getRates()[i].getRateValue());
        }
    }

    public static void ChannelFullInfo(JChannelFullInfo jcf, JDeviceBasic jdb) {
        UpdateLog.updateI(TAG, "JChannelFullInfo >>>>>>");
        UpdateLog.updateI(TAG, "JRateSetting: rate_name=" + jcf.getUploadRate().getRateName() + ",rate_value=" + jcf.getUploadRate().getRateValue());
        UpdateLog.updateI(TAG, "can_handoff_rate = " + jcf.getCanHandoffRate());
        UpdateLog.updateI(TAG, "strCloudStartTime = " + jcf.getCloudStartTime());
        UpdateLog.updateI(TAG, "strCloudEndTime = " + jcf.getCloudEndTime());
        UpdateLog.updateI(TAG, "iChannelId = " + jcf.getChannelId());
        UpdateLog.updateI(TAG, "strDeviceAutoId = " + jcf.getDeviceAutoId());
        UpdateLog.updateI(TAG, "strCreateTime = " + jcf.getCreateTime());
        UpdateLog.updateI(TAG, "strLastUpdateTime = " + jcf.getLastUpdateTime());
        UpdateLog.updateI(TAG, "iCycle = " + jcf.getCycle());
        UpdateLog.updateI(TAG, "iShareVideoMarket = " + jcf.getShareVideoMarket());
        UpdateLog.updateI(TAG, "iAllowHistoryVideo =" + jcf.getAllowHistoryVideo());
        UpdateLog.updateI(TAG, "iAllowWebShare =" + jcf.getAllowWebShare());
        UpdateLog.updateI(TAG, "iWebShareRange =" + jcf.getWebShareRange());
        UpdateLog.updateI(TAG, "iChannelStatus =" + jcf.getChannelStatus());
        UpdateLog.updateI(TAG, "strChannelName = " + jcf.getChannelName());
        UpdateLog.updateI(TAG, "iUsedHistoryCount = " + jcf.getUsedHistoryCount());
        UpdateLog.updateI(TAG, "iMaxHistoryCount = " + jcf.getMaxHistoryCount());
        UpdateLog.updateI(TAG, "iMaxVideoCount = " + jcf.getMaxVideoCount());
        UpdateLog.updateI(TAG, "iMaxGrantNum = " + jcf.getMaxGrantNum());
        UpdateLog.updateI(TAG, "iCurGrantNum = " + jcf.getCurGrantNum());
        UpdateLog.updateI(TAG, "iIsOnline = " + jcf.getIsOnline());

        DeviceBasic(jdb);
    }

    public static void GrantUsers(ArrayList<String> grantusers) {
        if(grantusers.size() == 0) {
            UpdateLog.updateI(TAG, "No grant user");
            return;
        }
        UpdateLog.updateI(TAG, "GrantUsers >>>>>>");
        for(int i = 0; i < grantusers.size(); i++) {
            UpdateLog.updateI(TAG, "No." + (i + 1) + " User = " + grantusers.get(i));
        }

    }

    public static void AlarmSettings(List<JAlarmSetting> alarmSettingList) {
        if(alarmSettingList.size() == 0) {
            UpdateLog.updateI(TAG, "No alarm setting");
            return;
        }
        UpdateLog.updateI(TAG, "AlarmSettings >>>>>>");
        for(int i = 0; i < alarmSettingList.size(); i++) {
            UpdateLog.updateI(TAG, "No." + (i + 1) + " Alarm Setting");
            UpdateLog.updateI(TAG, "strSetId = " + alarmSettingList.get(i).getSetId());
            UpdateLog.updateI(TAG, "iType = " + alarmSettingList.get(i).getType());
            UpdateLog.updateI(TAG, "strCreateTime = " + alarmSettingList.get(i).getCreateTime());
            UpdateLog.updateI(TAG, "strUpdateTime = " + alarmSettingList.get(i).getUpdateTime());
            UpdateLog.updateI(TAG, "strStartTime = " + alarmSettingList.get(i).getStartTime());
            UpdateLog.updateI(TAG, "strEndTime = " + alarmSettingList.get(i).getEndTime());
            UpdateLog.updateI(TAG, "strUserId = " + alarmSettingList.get(i).getUserId());
            UpdateLog.updateI(TAG, "iInterval = " + alarmSettingList.get(i).getInterval());
            UpdateLog.updateI(TAG, "strDeviceAutoId = " + alarmSettingList.get(i).getDeviceAutoId());
            UpdateLog.updateI(TAG, "iChannelId = " + alarmSettingList.get(i).getChannelId());
        }
    }

    public static void ShareThird(JShareThird shareThird) {
        UpdateLog.updateI(TAG, "JShareThird >>>>>>");
        UpdateLog.updateI(TAG, "strTitle = " + shareThird.getTitle());
        UpdateLog.updateI(TAG, "strCenter = " + shareThird.getCenter());
        UpdateLog.updateI(TAG, "strUrl = " + shareThird.getUrl());
    }

    public static void CludeStorage(JCloudStorageInfo cloudStorageInfo) {
        UpdateLog.updateI(TAG, "JCloudStorageInfo >>>>>>");
        UpdateLog.updateI(TAG, "strStartTime = " + cloudStorageInfo.getStrStartTime());
        UpdateLog.updateI(TAG, "strEndTime = " + cloudStorageInfo.getEndTime());
        UpdateLog.updateI(TAG, "iChannelId = " + cloudStorageInfo.getChannelId());
        UpdateLog.updateI(TAG, "strDeviceAutoId = " + cloudStorageInfo.getSDeviceAutoId());
        UpdateLog.updateI(TAG, "iCycle = " + cloudStorageInfo.getCycle());
        UpdateLog.updateI(TAG, "iIsCloud = " + cloudStorageInfo.getIsCloud());
        UpdateLog.updateI(TAG, "iUploadRate = " + cloudStorageInfo.getUploadRate());
    }

    public static void NVRHistory(ArrayList<JHistory> historys) {
        if(historys.size() == 0) {
            Log.i(TAG, "No NVR history");
            return;
        }
        Log.i(TAG, "NVRHistory >>>>>>");
        for(int i = 0; i < historys.size(); i++) {
            Log.i(TAG, "No." + (i + 1) + " NVR history");
            Log.i(TAG, "iStartTime = " + historys.get(i).getStartTime());
            Log.i(TAG, "iEndTime = " + historys.get(i).getEndTime());
        }
    }
}
