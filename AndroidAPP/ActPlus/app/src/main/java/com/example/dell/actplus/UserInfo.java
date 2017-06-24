package com.example.dell.actplus;

/**
 * Created by DELL on 2017/6/5.
 */

public class UserInfo {
    String UserName, head_img, UserId;
    public UserInfo(String userName, String Head_img, String Userid) {
        this.UserId = Userid;
        this.UserName = userName;
        this.head_img = Head_img;
    }
    public String getHeadImg() {
        return head_img;
    }
    public String getUserName() {
        return UserName;
    }
}
