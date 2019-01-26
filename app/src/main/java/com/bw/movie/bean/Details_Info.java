package com.bw.movie.bean;

import java.util.List;

/**
 * date:2019/1/26
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class Details_Info {

    /**
     * result : {"director":"闫非","duration":"118分钟","followMovie":2,"id":3,"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg","movieTypes":"喜剧","name":"西虹市首富","placeOrigin":"中国","posterList":["http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf2.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf4.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf3.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf5.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf6.jpg"],"rank":0,"shortFilmList":[{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf4.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf2.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf3.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf3.ts"}],"starring":"沈腾,宋芸烨,张晨光,艾伦,常远","summary":"故事发生在《夏洛特烦恼》中的\u201c特烦恼\u201d之城\u201c西虹市\u201d。混迹于丙级业余足球队的守门员王多鱼（沈腾 饰），因比赛失利被开除离队。正处于人生最低谷的他接受了神秘台湾财团\u201c一个月花光十亿资金\u201d的挑战。本以为快乐生活就此开始，王多鱼却第一次感到\u201c花钱特烦恼\u201d！想要人生反转走上巅峰，真的没有那么简单."}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * director : 闫非
         * duration : 118分钟
         * followMovie : 2
         * id : 3
         * imageUrl : http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg
         * movieTypes : 喜剧
         * name : 西虹市首富
         * placeOrigin : 中国
         * posterList : ["http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf2.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf4.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf3.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf5.jpg","http://172.17.8.100/images/movie/stills/xhssf/xhssf6.jpg"]
         * rank : 0
         * shortFilmList : [{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf4.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf2.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/xhssf/xhssf3.jpg","videoUrl":"http://172.17.8.100/video/movie/xhssf/xhssf3.ts"}]
         * starring : 沈腾,宋芸烨,张晨光,艾伦,常远
         * summary : 故事发生在《夏洛特烦恼》中的“特烦恼”之城“西虹市”。混迹于丙级业余足球队的守门员王多鱼（沈腾 饰），因比赛失利被开除离队。正处于人生最低谷的他接受了神秘台湾财团“一个月花光十亿资金”的挑战。本以为快乐生活就此开始，王多鱼却第一次感到“花钱特烦恼”！想要人生反转走上巅峰，真的没有那么简单.
         */

        private String director;
        private String duration;
        private int followMovie;
        private int id;
        private String imageUrl;
        private String movieTypes;
        private String name;
        private String placeOrigin;
        private int rank;
        private String starring;
        private String summary;
        private List<String> posterList;
        private List<ShortFilmListBean> shortFilmList;

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(int followMovie) {
            this.followMovie = followMovie;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMovieTypes() {
            return movieTypes;
        }

        public void setMovieTypes(String movieTypes) {
            this.movieTypes = movieTypes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceOrigin() {
            return placeOrigin;
        }

        public void setPlaceOrigin(String placeOrigin) {
            this.placeOrigin = placeOrigin;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getStarring() {
            return starring;
        }

        public void setStarring(String starring) {
            this.starring = starring;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getPosterList() {
            return posterList;
        }

        public void setPosterList(List<String> posterList) {
            this.posterList = posterList;
        }

        public List<ShortFilmListBean> getShortFilmList() {
            return shortFilmList;
        }

        public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
            this.shortFilmList = shortFilmList;
        }

        public static class ShortFilmListBean {
            /**
             * imageUrl : http://172.17.8.100/images/movie/stills/xhssf/xhssf4.jpg
             * videoUrl : http://172.17.8.100/video/movie/xhssf/xhssf1.ts
             */

            private String imageUrl;
            private String videoUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }
}
