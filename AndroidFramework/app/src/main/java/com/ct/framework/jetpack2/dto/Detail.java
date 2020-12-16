package com.ct.framework.jetpack2.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {


    /**
     * _id : 5e777432b8ea09cade05263f
     * author : 鸢媛
     * category : Girl
     * content : 这世界总有人在笨拙地爱着你，想把全部的温柔都给你。
     * createdAt : 2020-03-25 08:00:00
     * desc : 这世界总有人在笨拙地爱着你，想把全部的温柔都给你。
     * images : ["http://gank.io/images/624ade89f93f421b8d4e8fafd86b1d8d"]
     * index : 35
     * isOriginal : true
     * license :
     * likeCount : 0
     * likeCounts : 1
     * likes : ["DBRef('users', ObjectId('5b6ce9c89d21226f4e09c779'))"]
     * markdown :
     * originalAuthor :
     * publishedAt : 2020-03-25 08:00:00
     * stars : 1
     * status : 1
     * tags : []
     * title : 第35期
     * type : Girl
     * updatedAt : 2020-03-25 08:00:00
     * url : http://gank.io/images/624ade89f93f421b8d4e8fafd86b1d8d
     * views : 1318
     */

    @SerializedName("_id")
    public String id;
    @SerializedName("author")
    public String author;
    @SerializedName("category")
    public String category;
    @SerializedName("content")
    public String content;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String desc;
    @SerializedName("index")
    public int index;
    @SerializedName("isOriginal")
    public boolean isOriginal;
    @SerializedName("license")
    public String license;
    @SerializedName("likeCount")
    public int likeCount;
    @SerializedName("likeCounts")
    public int likeCounts;
    @SerializedName("markdown")
    public String markdown;
    @SerializedName("originalAuthor")
    public String originalAuthor;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("stars")
    public int stars;
    @SerializedName("status")
    public int status;
    @SerializedName("title")
    public String title;
    @SerializedName("type")
    public String type;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("url")
    public String url;
    @SerializedName("views")
    public int views;
    @SerializedName("images")
    public List<String> images;
    @SerializedName("likes")
    public List<String> likes;


    @Override
    public String toString() {
        return "Detail{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", index=" + index +
                ", isOriginal=" + isOriginal +
                ", license='" + license + '\'' +
                ", likeCount=" + likeCount +
                ", likeCounts=" + likeCounts +
                ", markdown='" + markdown + '\'' +
                ", originalAuthor='" + originalAuthor + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", stars=" + stars +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", url='" + url + '\'' +
                ", views=" + views +
                ", images=" + images +
                ", likes=" + likes +
                '}';
    }
}
