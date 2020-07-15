package com.yywzs.cop_turchat.bean;

import java.util.List;

public class TitleInfo {
    private String title;
    private List<User> info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getInfo() {
        return info;
    }

    public void setInfo(List<User> info) {
        this.info = info;
    }
}
