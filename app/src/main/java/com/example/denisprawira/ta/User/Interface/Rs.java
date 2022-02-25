package com.example.denisprawira.ta.User.Interface;

import com.example.denisprawira.ta.User.Model.ServerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Rs {

//    @Multipart
//    @POST("upload_image.php")
//    Call<ServerResponse> upload2(
//            @Header("Authorization") String authorization,
//            @PartMap Map<String, RequestBody> map
//    );

    @Multipart
    @POST("donor/insertFile")
    Call<ServerResponse> upload(@Part("description") RequestBody description,
            @Part MultipartBody.Part map);

    @FormUrlEncoded
    @POST("donor/insertFile")
    Call<ServerResponse> upload2(@Field("description") String description);
}


