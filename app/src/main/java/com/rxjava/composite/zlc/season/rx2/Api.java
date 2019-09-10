package com.rxjava.composite.zlc.season.rx2;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zlc.season.rx2.entity.LoginRequest;
import zlc.season.rx2.entity.LoginResponse;
import zlc.season.rx2.entity.RegisterRequest;
import zlc.season.rx2.entity.RegisterResponse;
import zlc.season.rx2.entity.UserBaseInfoRequest;
import zlc.season.rx2.entity.UserBaseInfoResponse;
import zlc.season.rx2.entity.UserExtraInfoRequest;
import zlc.season.rx2.entity.UserExtraInfoResponse;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/12/6
 * Time: 11:30
 * FIXME
 */
public interface Api {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
}
