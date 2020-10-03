package com.example.passwordrecorder;

public class Account {
    private String name;            //用户名
    private String password;        //密码
    long id;
    public Account(String name, String password,long id) {
        this.name = name;
        this.password = password;
        this.id=id;
    }
    public Account(){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
