package com.bw.movie.bean;

import java.util.List;

public class MovieCommentDetailsBean {

    /**
     * result : [{"commentContent":"楼下站住","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-12-19/20181219102900.png","commentId":1445,"commentTime":1545221808000,"commentUserId":2896,"commentUserName":"气人","greatNum":5,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"喀喀喀","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-10-11/20181011163109.unknown","commentId":917,"commentTime":1539253540000,"commentUserId":776,"commentUserName":"小熊维尼","greatNum":3,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"哇喔哇喔","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-20/20180920111801.png","commentId":835,"commentTime":1537257586000,"commentUserId":303,"commentUserName":"烤冷面","greatNum":7,"hotComment":0,"isGreat":0,"replyNum":8}]
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
         * commentContent : 楼下站住
         * commentHeadPic : http://172.17.8.100/images/movie/head_pic/2018-12-19/20181219102900.png
         * commentId : 1445
         * commentTime : 1545221808000
         * commentUserId : 2896
         * commentUserName : 气人
         * greatNum : 5
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
