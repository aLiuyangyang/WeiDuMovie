package com.bw.movie.bean;

import java.util.List;

public class MovieCommentDetailsBean {


    /**
     * result : [{"commentContent":"透了","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":181,"commentTime":1550400098000,"commentUserId":242,"commentUserName":"sunj","greatNum":1,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"一名","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-02-14/20190214155110.jpg","commentId":105,"commentTime":1550111129000,"commentUserId":15,"commentUserName":"旺旺旺","greatNum":7,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"哈哈","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-02-14/20190214204750.png","commentId":102,"commentTime":1550103477000,"commentUserId":99,"commentUserName":"冷風","greatNum":5,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"来啦老弟","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-02-14/20190214204750.png","commentId":101,"commentTime":1550103455000,"commentUserId":99,"commentUserName":"冷風","greatNum":5,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"关静音","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":99,"commentTime":1550062113000,"commentUserId":131,"commentUserName":"AndroidASD","greatNum":3,"hotComment":0,"isGreat":0,"replyNum":2},{"commentContent":"阿阿阿阿阿阿阿","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-02-14/20190214204132.jpg","commentId":84,"commentTime":1550057335000,"commentUserId":6,"commentUserName":"年","greatNum":4,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"阿阿阿阿阿","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-02-14/20190214204132.jpg","commentId":83,"commentTime":1550057316000,"commentUserId":6,"commentUserName":"年","greatNum":3,"hotComment":0,"isGreat":0,"replyNum":0}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commentContent : 透了
         * commentHeadPic : http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg
         * commentId : 181
         * commentTime : 1550400098000
         * commentUserId : 242
         * commentUserName : sunj
         * greatNum : 1
         * hotComment : 0
         * isGreat : 0
         * replyNum : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;
        private int replyNum;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }
    }
}
