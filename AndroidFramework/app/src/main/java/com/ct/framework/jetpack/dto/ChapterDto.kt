package com.ct.framework.jetpack.dto

import com.google.gson.annotations.SerializedName

data class ChapterParent(
    @SerializedName("datas")
    val datas: List<Chapter>
)

data class Chapter(
    @SerializedName("apkLink")
    val apkLink: String?,
    @SerializedName("audit")
    val audit: Int?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("canEdit")
    val canEdit: Boolean?,
    @SerializedName("chapterId")
    val chapterId: Int?,
    @SerializedName("chapterName")
    val chapterName: String?,
    @SerializedName("collect")
    val collect: Boolean?,
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("descMd")
    val descMd: String?,
    @SerializedName("envelopePic")
    val envelopePic: String?,
    @SerializedName("fresh")
    val fresh: Boolean?,
    @SerializedName("host")
    val host: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("niceDate")
    val niceDate: String?,
    @SerializedName("niceShareDate")
    val niceShareDate: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("prefix")
    val prefix: String?,
    @SerializedName("projectLink")
    val projectLink: String?,
    @SerializedName("publishTime")
    val publishTime: Long?,
    @SerializedName("realSuperChapterId")
    val realSuperChapterId: Int?,
    @SerializedName("selfVisible")
    val selfVisible: Int?,
    @SerializedName("shareDate")
    val shareDate: Long?,
    @SerializedName("shareUser")
    val shareUser: String?,
    @SerializedName("superChapterId")
    val superChapterId: Int?,
    @SerializedName("superChapterName")
    val superChapterName: String?,
    @SerializedName("tags")
    val tags: List<Any>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("visible")
    val visible: Int?,
    @SerializedName("zan")
    val zan: Int?
)