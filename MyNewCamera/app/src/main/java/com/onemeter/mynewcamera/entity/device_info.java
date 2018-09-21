package com.onemeter.mynewcamera.entity;

/**
 * 描述：标记缓存图片的实体类
 * 项目名称：MyNewCamera
 * 时间：2016/7/12 16:30
 * 备注：
 */
public class device_info {
    private  String  type;//判断是私有还是共享
    private  String  position;//标记缓存图片的位置

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
