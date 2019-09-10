package com.rxjava.composite.kam.sqrxjava2.net.api;

import kam.sqrxjava2.bean.ArticleListEntity;
import kam.sqrxjava2.bean.CommonRespone;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by javakam on 2018/5/24.
 */
public interface IRequestApi {
    String HOST = "http://www.wanandroid.com/";

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    Flowable<CommonRespone<ArticleListEntity>> getArticleList(@Path("page") int page);

}
