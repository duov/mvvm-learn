package com.ld.model_user.bean;

import java.util.List;

/**
 * @author: liangduo
 * @date: 2021/4/13 4:13 PM
 */
public class UserListBean {
    private List<ModelData> modelData;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ModelData> getModelData() {
        return modelData;
    }

    public void setModelData(List<ModelData> modelData) {
        this.modelData = modelData;
    }

    public class ModelData{
        private String imgUrl;//广告图片地址
        private String link;//广告链接
        private String dataType;//内容类型
        private Long id;//内容ID
        private String title;//标题
        private String description;//描述
        private Long firstLabel;//一级标签
        private List<SecondLabelObjBeanXX> secondLabel;
        private List<CustomLabelBean> customLabel;//
        private String mainImage;//封面
        private int channel;//赛道
        private String channelVal;//赛道描述
        private String channelColor;//赛道颜色
        private String nick;//作者昵称
        private int userType;//作者类型
        private Long userId;//作者ID
        private String avatar;//作者头像
        private Long userFirstLabel;//作者一级标签
        private String roleUuid;//作者角色
        private int mainField;//作者主要领域
        private String mainFieldVal;//作者主要领域VAL
        private String mainFieldColor;//作者主要领域描述
        private Long praiseNum;//点赞数
        private Long browseNum;//浏览量
        private boolean isShow;//前台是否显示
        private String createdAt;//
        private String updatedAt;//
        private List<SecondLabelObjBeanXX> contentTag;
        private String slogan;

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public List<SecondLabelObjBeanXX> getContentTag() {
            return contentTag;
        }

        public void setContentTag(List<SecondLabelObjBeanXX> contentTag) {
            this.contentTag = contentTag;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public Long getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(Long browseNum) {
            this.browseNum = browseNum;
        }

        public Long getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(Long praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getMainFieldColor() {
            return mainFieldColor;
        }

        public void setMainFieldColor(String mainFieldColor) {
            this.mainFieldColor = mainFieldColor;
        }

        public String getMainFieldVal() {
            return mainFieldVal;
        }

        public void setMainFieldVal(String mainFieldVal) {
            this.mainFieldVal = mainFieldVal;
        }

        public int getMainField() {
            return mainField;
        }

        public void setMainField(int mainField) {
            this.mainField = mainField;
        }

        public String getRoleUuid() {
            return roleUuid;
        }

        public void setRoleUuid(String roleUuid) {
            this.roleUuid = roleUuid;
        }

        public Long getUserFirstLabel() {
            return userFirstLabel;
        }

        public void setUserFirstLabel(Long userFirstLabel) {
            this.userFirstLabel = userFirstLabel;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getChannelColor() {
            return channelColor;
        }

        public void setChannelColor(String channelColor) {
            this.channelColor = channelColor;
        }

        public String getChannelVal() {
            return channelVal;
        }

        public void setChannelVal(String channelVal) {
            this.channelVal = channelVal;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public List<CustomLabelBean> getCustomLabel() {
            return customLabel;
        }

        public void setCustomLabel(List<CustomLabelBean> customLabel) {
            this.customLabel = customLabel;
        }

        public List<SecondLabelObjBeanXX> getSecondLabel() {
            return secondLabel;
        }

        public void setSecondLabel(List<SecondLabelObjBeanXX> secondLabel) {
            this.secondLabel = secondLabel;
        }

        public Long getFirstLabel() {
            return firstLabel;
        }

        public void setFirstLabel(Long firstLabel) {
            this.firstLabel = firstLabel;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public class  CustomLabelBean {

        }
        public class SecondLabelObjBeanXX {
            /**
             * code : db44683131d04a9a9e94a3c5502515aa
             * name : 嘿嘿嘿
             */

            private String code;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }
}
