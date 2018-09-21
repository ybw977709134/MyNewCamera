package com.onemeter.mynewcamera.entity;

import com.anyan.client.sdk.JRateSetting;

/**
 * 摄像头实体类
 */
public class OmCamera {
    private int iChannelNum;//设备通道
    private int iDeviceTypeId;//设备类型id
    private int iDeviceParaFlag;//设备参数flag
    private int iDeviceStatus;//设备在线状态
    private int iDeviceOwner;//设备所有者
    private String strAddTime;//添加的时间
    private String strUploadRate;//上传速率
    private String strDeviceSN;//设备ID号
    private String strLastUpdateTime;//最后更新时间
    private String strDeviceAutoId;//设备自动识别号
    private String strVersion;//版本
    private String area_info;
    private String device_name;//设备名称
    private String strFirstAccessTime;//第一次存取时间
    private String strToken;//设备token
    private String channel_mask;//设备通道mask信息
    private String model_name;//模型名称
    private String factory_name;//厂家名称
    private String desc_info;//描述信息
    private String prev_photo_url;//
    private int rate_count;//码率数量
    private JRateSetting[] rates;//码率数组
    private int iGrantState;//
    private int iAlarmState;//警报状态
    private int iScreenDirectionState;//屏幕方向的状态
    private int iHorizontalRotationState;//水平旋转状态
    private int iVerticalRotationState;//垂直旋转状态
    private int iZoomState;//变焦状态
    private int iLiveSwitch;//播放开关
    private int iSharedControllerSwitch;//分享控制开关
    private int iSharedPantiltSwitch;//分享云台开关
    private int iSharedIntercomSwitch;//分享对讲机开关
    private int iCanPosition;//可以定位
    private int iSharedZoomSwitch;//分享变焦开关
    private int iViewCount;//查看数

    public int getiChannelNum() {
        return iChannelNum;
    }

    public void setiChannelNum(int iChannelNum) {
        this.iChannelNum = iChannelNum;
    }

    public int getiDeviceTypeId() {
        return iDeviceTypeId;
    }

    public void setiDeviceTypeId(int iDeviceTypeId) {
        this.iDeviceTypeId = iDeviceTypeId;
    }

    public int getiDeviceParaFlag() {
        return iDeviceParaFlag;
    }

    public void setiDeviceParaFlag(int iDeviceParaFlag) {
        this.iDeviceParaFlag = iDeviceParaFlag;
    }

    public int getiDeviceStatus() {
        return iDeviceStatus;
    }

    public void setiDeviceStatus(int iDeviceStatus) {
        this.iDeviceStatus = iDeviceStatus;
    }

    public int getiDeviceOwner() {
        return iDeviceOwner;
    }

    public void setiDeviceOwner(int iDeviceOwner) {
        this.iDeviceOwner = iDeviceOwner;
    }

    public String getStrAddTime() {
        return strAddTime;
    }

    public void setStrAddTime(String strAddTime) {
        this.strAddTime = strAddTime;
    }

    public String getStrUploadRate() {
        return strUploadRate;
    }

    public void setStrUploadRate(String strUploadRate) {
        this.strUploadRate = strUploadRate;
    }

    public String getStrDeviceSN() {
        return strDeviceSN;
    }

    public void setStrDeviceSN(String strDeviceSN) {
        this.strDeviceSN = strDeviceSN;
    }

    public String getStrLastUpdateTime() {
        return strLastUpdateTime;
    }

    public void setStrLastUpdateTime(String strLastUpdateTime) {
        this.strLastUpdateTime = strLastUpdateTime;
    }

    public String getStrDeviceAutoId() {
        return strDeviceAutoId;
    }

    public void setStrDeviceAutoId(String strDeviceAutoId) {
        this.strDeviceAutoId = strDeviceAutoId;
    }

    public String getStrVersion() {
        return strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getStrFirstAccessTime() {
        return strFirstAccessTime;
    }

    public void setStrFirstAccessTime(String strFirstAccessTime) {
        this.strFirstAccessTime = strFirstAccessTime;
    }

    public String getStrToken() {
        return strToken;
    }

    public void setStrToken(String strToken) {
        this.strToken = strToken;
    }

    public String getChannel_mask() {
        return channel_mask;
    }

    public void setChannel_mask(String channel_mask) {
        this.channel_mask = channel_mask;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public String getDesc_info() {
        return desc_info;
    }

    public void setDesc_info(String desc_info) {
        this.desc_info = desc_info;
    }

    public String getPrev_photo_url() {
        return prev_photo_url;
    }

    public void setPrev_photo_url(String prev_photo_url) {
        this.prev_photo_url = prev_photo_url;
    }

    public int getRate_count() {
        return rate_count;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public JRateSetting[] getRates() {
        return rates;
    }

    public void setRates(JRateSetting[] rates) {
        this.rates = rates;
    }

    public int getiGrantState() {
        return iGrantState;
    }

    public void setiGrantState(int iGrantState) {
        this.iGrantState = iGrantState;
    }

    public int getiAlarmState() {
        return iAlarmState;
    }

    public void setiAlarmState(int iAlarmState) {
        this.iAlarmState = iAlarmState;
    }

    public int getiHorizontalRotationState() {
        return iHorizontalRotationState;
    }

    public void setiHorizontalRotationState(int iHorizontalRotationState) {
        this.iHorizontalRotationState = iHorizontalRotationState;
    }

    public int getiScreenDirectionState() {
        return iScreenDirectionState;
    }

    public void setiScreenDirectionState(int iScreenDirectionState) {
        this.iScreenDirectionState = iScreenDirectionState;
    }

    public int getiVerticalRotationState() {
        return iVerticalRotationState;
    }

    public void setiVerticalRotationState(int iVerticalRotationState) {
        this.iVerticalRotationState = iVerticalRotationState;
    }

    public int getiZoomState() {
        return iZoomState;
    }

    public void setiZoomState(int iZoomState) {
        this.iZoomState = iZoomState;
    }

    public int getiLiveSwitch() {
        return iLiveSwitch;
    }

    public void setiLiveSwitch(int iLiveSwitch) {
        this.iLiveSwitch = iLiveSwitch;
    }

    public int getiSharedControllerSwitch() {
        return iSharedControllerSwitch;
    }

    public void setiSharedControllerSwitch(int iSharedControllerSwitch) {
        this.iSharedControllerSwitch = iSharedControllerSwitch;
    }

    public int getiSharedPantiltSwitch() {
        return iSharedPantiltSwitch;
    }

    public void setiSharedPantiltSwitch(int iSharedPantiltSwitch) {
        this.iSharedPantiltSwitch = iSharedPantiltSwitch;
    }

    public int getiSharedIntercomSwitch() {
        return iSharedIntercomSwitch;
    }

    public void setiSharedIntercomSwitch(int iSharedIntercomSwitch) {
        this.iSharedIntercomSwitch = iSharedIntercomSwitch;
    }

    public int getiCanPosition() {
        return iCanPosition;
    }

    public void setiCanPosition(int iCanPosition) {
        this.iCanPosition = iCanPosition;
    }

    public int getiSharedZoomSwitch() {
        return iSharedZoomSwitch;
    }

    public void setiSharedZoomSwitch(int iSharedZoomSwitch) {
        this.iSharedZoomSwitch = iSharedZoomSwitch;
    }

    public int getiViewCount() {
        return iViewCount;
    }

    public void setiViewCount(int iViewCount) {
        this.iViewCount = iViewCount;
    }
}
