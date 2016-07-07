package com.example.wzm.codeaides.thirdLogin_share;

/**
 * Created by LynnChan on 2015/12/10.
 * 用户
 */
public class User {

    private static final long serialVersionUID = 1L;
    private String usercode; // 用户ID
    private String username;// 用户帐号
    private String email; // 用户邮箱
    private String upass;
    private String avatar;// 用户头像
    private String token;// 令牌
    //    private VIPInfo vipInfo;// 用户包月信息
    private String mobile;
    private String nickname; // 昵称
    private String site; // 注册地址
    private String commentcount; // 评论数
//    private int isThirdLogin; // 是否第三方登录 1是 0否

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    // 包月开始时间
    private long vip_start_time;
    // 包月结束时间
    private long vip_end_time;
    // 包月状态
    private int m1905_vip;

    public long getVip_start_time() {
        return vip_start_time;
    }

    public void setVip_start_time(long vip_start_time) {
        this.vip_start_time = vip_start_time;
    }

    public long getVip_end_time() {
        return vip_end_time;
    }

    public void setVip_end_time(long vip_end_time) {
        this.vip_end_time = vip_end_time;
    }

    public int getM1905_vip() {
        return m1905_vip;
    }

    public void setM1905_vip(int m1905_vip) {
        this.m1905_vip = m1905_vip;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpass() {
        return upass;
    }

//    public void setUpass(String upass) {
//        this.upass = DES2.encrypt(upass);
//    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    public int getIsThirdLogin() {
//        return isThirdLogin;
//    }

//    public void setIsThirdLogin(int isThirdLogin) {
//        this.isThirdLogin = isThirdLogin;
//    }

    /**
     * 是否是会员
     *
     * @return
     */
    public boolean isM1905VIP() {
        return (getM1905_vip() == 1 && System
                .currentTimeMillis() <= getVip_end_time() * 1000l) ? true
                : false;
    }


}
