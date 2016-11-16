package com.jervies.iread.bean;

import java.util.List;

/**
 * Created by Jervies on 2016/11/15.
 */

public class NovelBean {
    /**
     * result : [{"id":100091,"type":2,"publishtime":636147648000000000,"title":"岭南往事","summary":"平淡中的味道就是人和人之间有友好有感念有宽忍","image":""},{"id":100090,"type":2,"publishtime":636146784000000000,"title":"开快车的最佳日子","summary":"就是一种绝望的情绪，很多事都无能为力。","image":""},{"id":100089,"type":2,"publishtime":636145920000000000,"title":"人死了，就像水消失在水中","summary":"本文节选自第四届豆瓣阅读征文大赛入围作品《碧海青天》，这是一个四段式的、探讨永生的软科幻故事。故事之间的部分人物之间有着宿命般的黏连。这种黏连或者说羁绊，与现实中的人际关系别无二致\u2014\u2014我们出于善意或恶意随机做出的选择，最终构成我们的命运。","image":""},{"id":100088,"type":2,"publishtime":636145056000000000,"title":"长安入画","summary":"今晚文章略长，请耐心看完，晚安！","image":""},{"id":100087,"type":2,"publishtime":636144192000000000,"title":"我的好朋友安欲斌也被外星人给抓走了","summary":"没有写不到，只有想不到，今晚是一部想象力丰富的美文，看完后记得晚安！","image":""},{"id":100086,"type":2,"publishtime":636143328000000000,"title":"面鱼儿、魔芋与酿皮","summary":"简简单单的实物，写出不同的感觉，晚安！","image":""},{"id":100085,"type":2,"publishtime":636142464000000000,"title":"世界微尘","summary":"看到这样的故事，感觉很温馨，尤其是睡前，晚安！","image":""},{"id":100084,"type":2,"publishtime":636140736000000000,"title":"余生，请多多指教","summary":"今晚文章略长，请耐心看完，晚安！","image":""},{"id":100083,"type":2,"publishtime":636139872000000000,"title":"那些硬着头皮去做的事，终将让你长进","summary":"我们都不容易啊！需要硬着头皮干很多事。","image":""}]
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
         * id : 100091
         * type : 2
         * publishtime : 636147648000000000
         * title : 岭南往事
         * summary : 平淡中的味道就是人和人之间有友好有感念有宽忍
         * image :
         */

        private int id;
        private int type;
        private long publishtime;
        private String title;
        private String summary;

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
    }
}
