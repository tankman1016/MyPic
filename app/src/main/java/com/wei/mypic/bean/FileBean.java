package com.wei.mypic.bean;

public class FileBean {

    private String imgPath;
    private String fileName;
    private boolean isSelected = false;
    private int size = 0;

    public FileBean(){

    }

    public FileBean(String imgPath, String fileName) {
        this.imgPath = imgPath;
        this.fileName = fileName;
        size = size + 1;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void addSize() {
        this.size = this.size + 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
