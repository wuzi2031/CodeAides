package com.example.wzm.codeaides.thirdLogin_share;

/**
 * Created by wzm on 2016/6/28.
 */
public class ShareParams {
    public static final int SHARE_TEXT = 1;
    public static final int SHARE_IMAGE = 2;
    public static final int SHARE_WEBPAGE = 3;
    private String title;
    private String imageUrl;
    private String titleUrl;
    private String text;
    private String sit;
    private String siteUrl;
    private int shareType;

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public String getText() {
        return text;
    }

    public String getSit() {
        return sit;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSit(String sit) {
        this.sit = sit;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
