package com.example.denisprawira.ta.User.Api;
import com.example.denisprawira.ta.User.Api.Model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
//    @Headers({
//            "Authorization: Bearer 10|jkCBOmr5dfVVMKJzw1gNspOQC845Iw12HoDYKoMv",
//            "Content-Type: application/json"
//    })

    @FormUrlEncoded
    @POST("user/login")
    Call<Response> login(@Field("email") String email, @Field("password") String password);

//    @POST("event/displayAll")
//    Call<List<Event>> loadEvent();


}
