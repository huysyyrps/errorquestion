package com.example.errorquestion

import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.RemoveWrite
import com.example.errorquestion.entity.TokenBean
import com.example.errorquestion.util.BaseSharedPreferences
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


interface AllApi {
    /**
     * 获取access_token
     */
    @POST(ApiAddress.TOKENIONINFO)
//    @Headers("Content-Type:application/json; charset=UTF-8")
//    fun getTokenInfo(@Body body: RequestBody?): Observable<TokenBean?>?
    fun getTokenInfo(@Query("grant_type") grant_type:String,
                     @Query("client_id") client_id:String,
                     @Query("client_secret") client_secret:String, ): Observable<TokenBean?>?

    /**
     * 去手写
     */
    @FormUrlEncoded
    @POST(ApiAddress.REMOVEWRITE)
    fun removeWrite(@Field("access_token") access_token:String,
                     @Field("image") image:String,): Observable<RemoveWrite?>?
    /**
     * 获取图片信息
     */
    @FormUrlEncoded
    @POST(ApiAddress.ENTERINFO)
    fun getEnterInfo(@Field("access_token") access_token:String,
                     @Field("image") image:String,): Observable<EnterBean?>?
}