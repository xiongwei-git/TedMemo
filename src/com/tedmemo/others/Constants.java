package com.tedmemo.others;

import android.os.Environment;

/**
 * Created by w_xiong on 2014/8/11.
 */
public class Constants {
    /**DB Name*/
    public static final String DATABASE_NAME = "MyMemo.db";
    /**DB version*/
    public static final int DATABASE_VERSION = 1;

    /**Color*/
    public static final String GRAY_ICON_COLOR = "#c6c8c7";

    /**MSG*/
    public static final int CLOSE_SHAKE_MSG = 0x1;
    public static final int WAIT_ANIM_OVER = 0x2;

    public static final String PHOTO_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

    /**Select icon from where*/
    public static final String SELECT_KEY = "select_icon_from";
    public static final int FROM_CREATE = 0x1;
    public static final int FROM_WRITE = 0x2;
    public static final int FROM_DETAIL = 0x3;
    public static final int FROM_LIST = 0x4;



    /**OTHER*/
    public static final long LISTEN_SHAKE_TIME = 1500l;

    public static final String[] ICON_COLORS = {
            "#fdcb35","#e46682","#aebe7e","#7f74b4",
            "#a0bde8","#f5ae68","#7c866d","#a8877c",
            "#b8aa83","#a26b9f","#6d92b4","#f07a63",
            "#d35543","#eaa7bd","#9aad9a","#5ebab0"};//"#303030",

    /**十六个icon在数据库里面有一个对应的ID，默认的NONE的ID为15*/
    public static final int DEFAULT_NONE_ICON_ID = 15;

    public static final String[] ICON_NAME_A = {
            "a_diamond","a_club","a_spade","a_square",
            "a_triangle","a_laugh","a_smile","a_surprised","a_wooden",
            "a_upset","a_cry","a_exclamation","a_question","a_checked",
            "a_cross","a_sharp","a_asterisk","a_phone","a_post",
            "a_pointing","a_flower","a_heart","a_star"};
    public static final String[] ICON_NAME_B = {
            "b_book","b_pc","b_smartphone","b_mail","b_facebook",
            "b_twitter","b_line","b_ie","b_android","b_ios",
            "b_atmark","b_chat","b_tag","b_secret","b_github",
            "b_idea","b_building","b_worker","b_bag","b_graph",
            "b_picture","b_study","b_pencil","b_paper","b_news",
            "b_clock","b_pin","b_rench"};
    public static final String[] ICON_NAME_C = {
            "c_cooking","c_health","c_art","c_body","c_fashion",
            "c_funny","c_game","c_girl","c_home","c_kids",
            "c_money","c_movie","c_outdoor","c_pet","c_present",
            "c_gourmet","c_cafe","c_sweets","c_beer","c_wine",
            "c_movie","c_music","c_ribbon","c_shop","c_car",
            "c_bike","c_train","c_airplane"};
    public static final String ICON_NAME_S = "s_none";
}
