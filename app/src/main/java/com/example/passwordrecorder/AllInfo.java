package com.example.passwordrecorder;

public class AllInfo {
    protected String username,password,secPhone,website,otherInfo,accUser;
    protected long userId;
    public AllInfo(String username,String password,String secPhone,String website,String otherInfo,String accUser,long userId){
        this.accUser=accUser;
        this.username=username;
        this.password=password;
        this.secPhone=secPhone;
        this.website=website;
        this.otherInfo=otherInfo;
        this.userId=userId;
    }

    public AllInfo() {

    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getSecPhone() {
        return secPhone;
    }

    public String getWebsite() {
        return website;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public String getAccUser() {
        return accUser;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setSecPhone(String secPhone) {
        this.secPhone = secPhone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAccUser(String accUser) {
        this.accUser = accUser;
    }
}
