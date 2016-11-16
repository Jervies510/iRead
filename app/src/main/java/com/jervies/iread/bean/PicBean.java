package com.jervies.iread.bean;

import java.util.List;

/**
 * Created by Jervies on 2016/11/16.
 */

public class PicBean {
    /**
     * result : [{"id":100097,"type":3,"publishtime":636148512000000000,"title":"喵星人占领世界","summary":"世界那么大，我却偏偏遇见你；世界那么小，我却偏偏丢了你。 \r\n\r\n世界那么大，我却总是无法忘记你；世界那么小，我却总是无法遇见你。\r\n","image":"images/69B6CE641E6E385B3DD05F8957FA6A40.jpg"},{"id":100096,"type":3,"publishtime":636147648000000000,"title":"肉欲慵色惰性","summary":"在我们的既定印象中，私房总是伴随着情色一词，那些我们耳熟能详的时尚私房大师更是将情色发挥的淋漓尽致。然而天才私房摄影师Ivan Warhammer在攥住性感色域的同时，却将情色拿捏的柔韧有余，通过淡色调子来叙述作品是他的拿手好戏，唇指间中模特充满质感的肌肤展现出一种古典的韵味，令人联想到床笫之间那忽明忽暗的花花世界。","image":"images/A502C6011A075402DFEF3494EB07170B.jpg"},{"id":100095,"type":3,"publishtime":636146784000000000,"title":"其实要过那条马路并不难","summary":"It wasn't so hard to cross the street after all . It all depends on who's waiting for you on the other side. \r\n\r\n其实要过那条马路并不难，就看谁在对面等你。","image":"images/FE8A1798472BF2596A6F7171397EB8BF.jpg"},{"id":100094,"type":3,"publishtime":636145920000000000,"title":"世间最美的艳遇，是遇见另一个自己","summary":"遇见是两个人的事，离开却是一个人的决定，遇见是一个开始，离开却是为了遇见下一个离开。这是一个流行离开的世界，但是我们都不擅长告别。","image":"images/0542F3B0D65A36C118C4154F26192AD2.jpg"},{"id":100093,"type":3,"publishtime":636145056000000000,"title":"透过我的身体，看你的风景","summary":"在空无一人的房间里醒来，看到窗外的暗蓝天际。曾经跋山涉水而山高水远，也曾困守城市繁华不知何去何从。看过世间风景，尝过人情冷暖，身体是成年的，心是提前老去的。","image":"images/F914CFCED8B81AB91E1C33FE7A78FA30.jpg"},{"id":100092,"type":3,"publishtime":636144192000000000,"title":"漂亮的水彩动物插画","summary":"我恋爱了，怎么办呢？很痛苦但是我愿意继续痛苦下去。 ","image":"images/E0F0C2EF9C49215049DA604370C50C18.jpg"},{"id":100091,"type":3,"publishtime":636143328000000000,"title":"幸福","summary":"人总是在接近幸福时倍感幸福，在幸福进行时却患得患失。","image":"images/6A6E8344F2402CEF219D8C7643CD7D80.jpg"},{"id":100090,"type":3,"publishtime":636142464000000000,"title":"带不走的留不下","summary":"带不走的留不下，留不下的别牵挂。\r\n\r\n Things which cannot be taken away are bound to leave, and should be out of concern。","image":"images/87CE3886D04E2BE4D965D5D1964F545C.jpg"},{"id":100089,"type":3,"publishtime":636140736000000000,"title":"浓郁颓废妖冶之奇美","summary":"在青春的掩护下，颓废是勇气，懒惰是反抗，空虚是性感。","image":"images/0E33AB4D2E0F478E13919124C27FC2F2.jpg"}]
     * status : 0
     * errMsg : null
     */
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 100097
         * type : 3
         * publishtime : 636148512000000000
         * title : 喵星人占领世界
         * summary : 世界那么大，我却偏偏遇见你；世界那么小，我却偏偏丢了你。

         世界那么大，我却总是无法忘记你；世界那么小，我却总是无法遇见你。

         * image : images/69B6CE641E6E385B3DD05F8957FA6A40.jpg
         */
        private int id;
        private int type;
        private long publishtime;
        private String title;
        private String summary;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(long publishtime) {
            this.publishtime = publishtime;
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
    }
}
