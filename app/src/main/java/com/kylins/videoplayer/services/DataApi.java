package com.kylins.videoplayer.services;

import com.kylins.videoplayer.bean.Video;
import com.kylins.videoplayer.bean.VideoGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by j-zhouliangshun on 2016/7/16.
 */
public interface DataApi {

    @GET("/ziyuan/getList.txt")
    Call<List<VideoGroup>> getMenuList();

    @GET("/ziyuan/cid/{cid}.txt")
    Call<List<Video>> getVidoList(@Path("cid") String cid);
}
