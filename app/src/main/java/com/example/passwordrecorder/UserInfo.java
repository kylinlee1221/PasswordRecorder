package com.example.passwordrecorder;

public class UserInfo {
    protected String username,password;
    protected long userId;
    public UserInfo(String username,String password,long userId){
        this.username=username;
        this.password=password;
        this.userId=userId;
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
}
