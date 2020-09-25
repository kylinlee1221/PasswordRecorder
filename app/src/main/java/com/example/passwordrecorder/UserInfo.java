package com.example.passwordrecorder;

public class UserInfo {
    protected String username,password,secPhone,website;
    protected long userId;
    public UserInfo(String username,String password,String website,String secPhone,long userId){
        this.username=username;
        this.password=password;
        this.userId=userId;
        this.website=website;
        this.secPhone=secPhone;
    }

    public String getWebsite() {
        return website;
    }

    public String getSecPhone() {
        return secPhone;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setSecPhone(String secPhone) {
        this.secPhone = secPhone;
    }
    public String getInfo(){
        return "username:"+username+"\nPassword:"+password+"\nwebsite:"+website+"\nsecurity:"+secPhone;
    }
    public String getInfoCN(){
        return "用户名:"+username+"\n密码:"+password+"\n注册地:"+website+"\n安全手机:"+secPhone;
    }
}
