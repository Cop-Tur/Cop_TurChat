package com.yywzs.cop_turchat.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String nickname, gender, area;
    private List<User> first,second,third,fourth;

    public List<User> getFirst() {
        return first;
    }

    public void setFirst(List<User> first) {
        this.first = first;
    }

    public List<User> getSecond() {
        return second;
    }

    public void setSecond(List<User> second) {
        this.second = second;
    }

    public List<User> getThird() {
        return third;
    }

    public void setThird(List<User> third) {
        this.third = third;
    }

    public List<User> getFourth() {
        return fourth;
    }

    public void setFourth(List<User> fourth) {
        this.fourth = fourth;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
