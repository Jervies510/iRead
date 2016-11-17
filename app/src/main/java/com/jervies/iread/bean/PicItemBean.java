package com.jervies.iread.bean;

/**
 * Created by Jervies on 2016/11/17.
 */

public class PicItemBean {

    /**
     * id : 100093
     * title : 透过我的身体，看你的风景
     * author : Emily Acosta
     * authorbrief : Emily Acosta，美国摄影师
     * text1 : 在空无一人的房间里醒来，看到窗外的暗蓝天际。曾经跋山涉水而山高水远，也曾困守城市繁华不知何去何从。看过世间风景，尝过人情冷暖，身体是成年的，心是提前老去的。
     * image1 : images/F914CFCED8B81AB91E1C33FE7A78FA30.jpg
     * text2 : —《素年锦时》
     * times : 8114
     * publishtime : 636145056000000000
     * status : 0
     * errMsg : null
     */

    private int id;
    private String title;
    private String author;
    private String authorbrief;
    private String text1;
    private String image1;
    private String text2;
    private int times;
    private long publishtime;
    private int status;
    private Object errMsg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorbrief() {
        return authorbrief;
    }

    public void setAuthorbrief(String authorbrief) {
        this.authorbrief = authorbrief;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(long publishtime) {
        this.publishtime = publishtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "PicItemBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorbrief='" + authorbrief + '\'' +
                ", text1='" + text1 + '\'' +
                ", image1='" + image1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", times=" + times +
                ", publishtime=" + publishtime +
                ", status=" + status +
                ", errMsg=" + errMsg +
                '}';
    }
}
