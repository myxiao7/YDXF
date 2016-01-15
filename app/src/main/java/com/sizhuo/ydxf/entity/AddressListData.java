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
    private String address;//地址

    public AddressListData() {
    }

    public AddressListData(String img, String name, String phone, String address) {
        this.img = img;
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
