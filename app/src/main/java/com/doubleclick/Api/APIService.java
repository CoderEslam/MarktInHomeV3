package com.doubleclick.Api;


import com.doubleclick.marktinhome.Model.MyResponse;
import com.doubleclick.marktinhome.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAARjyDtSc:APA91bEihpYuHICZEMcJ-ySGw-lBTjqJDNMKwcn_mAXCsQcPSLSdnu0jEYvwfx9bMkDmEx7I_clT1k1lk5ps5u4yv47M97Tg225kMphG3qcJLplF1hefIuH_YGyDCIMLRCgY-UtMRCQe"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

