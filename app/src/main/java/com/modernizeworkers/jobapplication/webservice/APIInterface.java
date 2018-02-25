package com.modernizeworkers.jobapplication.webservice;


import com.modernizeworkers.jobapplication.model.GetJobDetailsBaseModel;
import com.modernizeworkers.jobapplication.model.JobSearchBaseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bino on 4/20/2017.
 */
public interface APIInterface {
    //AUTHENTICATION

    @GET("ads/apisearch")
    Call<JobSearchBaseModel> jobSearch(@Query("publisher") String publisherId,
                                       @Query("q") String searchQuery,
                                       @Query("l") String location,
                                       @Query("sort") String sort,
                                       @Query("radius") String radius,
                                       @Query("st") String siteType,
                                       @Query("jt") String jobType,
                                       @Query("start") String pageStart,
                                       @Query("limit") String serachLimit,
                                       @Query("fromage") String numberOfDaysBackToSearch,
                                       @Query("filter") String filter,
                                       @Query("latlong") String latlng,
                                       @Query("co") String country,
                                       @Query("chnl") String channel,
                                       @Query("userip") String userIp,
                                       @Query("useragent") String userAgent,
                                       @Query("v") String version,
                                       @Query("format") String resultFormat);


    @GET("ads/apigetjobs")
    Call<GetJobDetailsBaseModel> jobSDetails(@Query("publisher") String publisherId,
                                             @Query("jobkeys") String jobKey,
                                             @Query("v") String version,
                                             @Query("format") String format);


}
