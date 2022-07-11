package com.example.sqlitemvvm.model;

public class BookInfo {
    int id;
    String name;
    int page;
    int price;
    String desc;
    byte[] avt;

    public BookInfo(int id, String name, int page, int price, String desc, byte[] avt) {
        this.id = id;
        this.name = name;
        this.page = page;
        this.price = price;
        this.desc = desc;
        this.avt = avt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getAvt() {
        return avt;
    }

    public void setAvt(byte[] avt) {
        this.avt = avt;
    }
}
