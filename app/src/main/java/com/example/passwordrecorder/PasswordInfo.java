package com.example.passwordrecorder;

public class PasswordInfo {
    protected String username,password;
    protected String website;
    protected String securityPhone;
    public PasswordInfo (String username,String password,String website,String securityPhone){
        this.username=username;
        this.password=password;
        this.website=website;
        this.securityPhone=securityPhone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityPhone(String securityPhone) {
        this.securityPhone = securityPhone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityPhone() {
        return securityPhone;
    }

    public String getWebsite() {
        return website;
    }
}
