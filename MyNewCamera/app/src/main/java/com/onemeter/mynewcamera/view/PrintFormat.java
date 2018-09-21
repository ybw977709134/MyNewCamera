package com.onemeter.mynewcamera.view;

import com.anyan.client.model.tools.TimeModel;
import com.anyan.client.sdk.JAlarmSetting;
import com.anyan.client.sdk.JChannelFullInfo;
import com.anyan.client.sdk.JCloudStorageInfo;
import com.anyan.client.sdk.JDeviceBasic;
import com.anyan.client.sdk.JHistory;
import com.anyan.client.sdk.JMarketInfo;
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
        UpdateLog.updateI(TAG, "strUserId =" + userinfo.getUserId());

        UpdateLog.updateI(TAG, "strRegisterTime =" + userinfo.getRegisterTime());
        UpdateLog.updateI(TAG, "strMobile =" + userinfo.getMobile());
        UpdateLog.updateI(TAG, "strLastUpdateTime =" + userinfo.getLastUpdateTime());
        UpdateLog.updateI(TAG, "strHead =" + userinfo.getHead());
        UpdateLog.updateI(TAG, "strLoginToken =" + userinfo.getLoginToken());
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
        UpdateLog.updateI(TAG, "alarm_switch = " + jdb.getAlarmState());
        //UpdateLog.updateI(TAG, "has_disk = " + jdb.getHasDisk());
        UpdateLog.updateI(TAG, "can_pan = " + jdb.getHorizontalRotationState());
        UpdateLog.updateI(TAG, "can_tilt = " + jdb.getVerticalRotationState());
        UpdateLog.updateI(TAG, "can_zoom = " + jdb.getZoomState());
        UpdateLog.updateI(TAG, "live_switch = " + jdb.getLiveSwitch());
        UpdateLog.updateI(TAG, "sharedcontroller_switch = " + jdb.getSharedControllerSwitch());
        UpdateLog.updateI(TAG, "sharedpantilt_switch = " + jdb.getSharedPantiltSwitch());
        UpdateLog.updateI(TAG, "sharedintercom_switch = " + jdb.getSharedIntercomSwitch());
        UpdateLog.updateI(TAG, "can_position = " + jdb.getCanPosition());
        UpdateLog.updateI(TAG, "sharedzoom_switch = " + jdb.getSharedZoomSwitch());

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
        UpdateLog.updateI(TAG, "strStartTime = " + cloudStorageInfo.getStartTime());
        UpdateLog.updateI(TAG, "strEndTime = " + cloudStorageInfo.getEndTime());
        UpdateLog.updateI(TAG, "iChannelId = " + cloudStorageInfo.getChannelId());
        UpdateLog.updateI(TAG, "strDeviceAutoId = " + cloudStorageInfo.getDeviceAutoId());
        UpdateLog.updateI(TAG, "iCycle = " + cloudStorageInfo.getCycle());
        UpdateLog.updateI(TAG, "iIsCloud = " + cloudStorageInfo.getIsCloud());
        UpdateLog.updateI(TAG, "iUploadRate = " + cloudStorageInfo.getUploadRate());
    }

    public static void NVRHistory(ArrayList<JHistory> historys) {
        if(historys.size() == 0) {
            UpdateLog.updateI(TAG, "No history");
            return;
        }
        for(int i = 0; i < historys.size(); i++) {
            UpdateLog.updateI(TAG, "History >>>No." + (i + 1));
            UpdateLog.updateI(TAG, "History >iStartTime = " + historys.get(i).getStartTime() + ", " + TimeModel.formatYMDhms(historys.get(i).getStartTime()));
            UpdateLog.updateI(TAG, "History >iEndTime = " + historys.get(i).getEndTime() + ", " + TimeModel.formatYMDhms(historys.get(i).getEndTime()));
        }
    }

    public static void DeviceMarketInfo(JMarketInfo mMarketInfo) {
        UpdateLog.updateI(TAG, "JMarketInfo >>>>>>");
        UpdateLog.updateI(TAG, "strMarketId = " + mMarketInfo.getMarketId());
        UpdateLog.updateI(TAG, "strDeviceAutoId = " + mMarketInfo.getDeviceAutoId());
        UpdateLog.updateI(TAG, "strVideoStatus = " + mMarketInfo.getVideoStatus());
        UpdateLog.updateI(TAG, "strChannelId = " + mMarketInfo.getChannelId());
        UpdateLog.updateI(TAG, "strDescription = " + mMarketInfo.getDescription());

        UpdateLog.updateI(TAG, "strMarketGroupId = " + mMarketInfo.getMarketGroupId());
        UpdateLog.updateI(TAG, "strShareFrom = " + mMarketInfo.getShareFrom());
    }
}
