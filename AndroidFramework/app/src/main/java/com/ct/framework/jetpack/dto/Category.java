package com.ct.framework.jetpack.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ClassName : Category
 * @CreateDate : 2020/4/15 9:23
 * @Author : CT
 * @Description : 数据实体
 * <p>
 * https://gank.io/api/v2/data/category/GanHuo/type/Android/page/1/count/10
 */


public class Category {


    /**
     * _id : 5e958ee017bf93950887f208
     * author : 鸢媛
     * category : Girl
     * createdAt : 2020-04-15 08:00:00
     * desc : 愿所期之事皆能成，所念之人皆能到。
     * images : ["http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35"]
     * likeCounts : 0
     * publishedAt : 2020-04-15 08:00:00
     * stars : 1
     * title : 第56期
     * type : Girl
     * url : http://gank.io/images/28fc02e86d584ff08802c8dcd9535b35
     * views : 16
     */

    @NonNull

    @SerializedName("_id")
    public String id = "";
    @SerializedName("author")
    public String author;
    @SerializedName("category")
    public String category;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String desc;
    @SerializedName("likeCounts")
    public int likeCounts;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("stars")
    public int stars;
    @SerializedName("title")
    public String title;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String url;
    @SerializedName("views")
    public int views;

    @SerializedName("images")
    public List<String> images;

    //记录当前分页
    public int page = 1;

    @NonNull
    @Override
    public String toString() {
        return id;
    }
}
