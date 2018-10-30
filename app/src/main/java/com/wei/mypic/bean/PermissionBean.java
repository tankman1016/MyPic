package com.wei.mypic.bean;

public class PermissionBean {
    //是否已授权
    private boolean isGranted ;
    //权限名字
    private String name;

    public PermissionBean(boolean isGranted, String name) {
        this.isGranted = isGranted;
        this.name = name;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean granted) {
        isGranted = granted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
