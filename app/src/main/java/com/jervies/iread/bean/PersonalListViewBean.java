package com.jervies.iread.bean;

/**
 * Created by Jervies on 2016/11/16.
 */

public class PersonalListViewBean {
    private int icon;
    private String title;

    public PersonalListViewBean(){}

    public PersonalListViewBean(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "PersonalListViewBean{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                '}';
    }
}
