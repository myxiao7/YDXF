package com.sizhuo.ydxf.entity;

/**
 * 项目名称: YDXF
 * 类描述:  便民114别离表bean
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class AddressListData {
    private String img;//图标
    private String name;//名称
    private String phone;//电话
    private String tag01;//标签
    private String tag02;//标签
    private String address;//地址

    public AddressListData(String img, String name, String phone, String tag01, String tag02, String address) {
        this.img = img;
        this.name = name;
        this.phone = phone;
        this.tag01 = tag01;
        this.tag02 = tag02;
        this.address = address;
    }

    public AddressListData() {

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTag01() {
        return tag01;
    }

    public void setTag01(String tag01) {
        this.tag01 = tag01;
    }

    public String getTag02() {
        return tag02;
    }

    public void setTag02(String tag02) {
        this.tag02 = tag02;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
