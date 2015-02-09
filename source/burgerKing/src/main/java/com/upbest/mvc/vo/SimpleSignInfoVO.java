package com.upbest.mvc.vo;

import java.io.Serializable;

/**
 * Created by lili on 15-2-9.
 */
public class SimpleSignInfoVO implements Serializable{
    private String sign_in_time;
    private String user_id;
    private String user_name;
    private String shop_id;
    private String shop_num;

    public String getSign_in_time() {
        return sign_in_time;
    }

    public void setSign_in_time(String sign_in_time) {
        this.sign_in_time = sign_in_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_num() {
        return shop_num;
    }

    public void setShop_num(String shop_num) {
        this.shop_num = shop_num;
    }
}
