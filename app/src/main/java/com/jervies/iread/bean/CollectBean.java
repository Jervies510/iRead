package com.jervies.iread.bean;

/**
 * Created by Jervies on 2016/11/19.
 */

public class CollectBean {

    private int _id;
    private int type;
    private int item_id;
    private String title;
    private String summary;
    private String image;

    public CollectBean(int _id,int type, int item_id, String title, String summary, String image) {
        this._id= _id;
        this.type = type;
        this.item_id = item_id;
        this.title = title;
        this.summary = summary;
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "type=" + type +
                ", item_id=" + item_id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
