package com.example.denisprawira.ta.User.Api;

import com.example.denisprawira.ta.User.Model.Blood.RequestBlood;
import com.example.denisprawira.ta.User.Model.BloodStock;
import com.example.denisprawira.ta.User.Model.Component;
import com.example.denisprawira.ta.User.Model.Donor.JoinedDonor;
import com.example.denisprawira.ta.User.Model.Donor.RequestDonor;
import com.example.denisprawira.ta.User.Model.Event.Event;
import com.example.denisprawira.ta.User.Model.Event.UnapprovedEvent;
import com.example.denisprawira.ta.User.Model.GolDarah;
import com.example.denisprawira.ta.User.Model.Person;
import com.example.denisprawira.ta.User.Model.RetrofitResult;
import com.example.denisprawira.ta.User.Model.ServerResponse;
import com.example.denisprawira.ta.User.Model.User;
import com.example.denisprawira.ta.User.Model.Chat.UserChat;
import com.example.denisprawira.ta.User.Model.Utd;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface RetrofitServices {

    @FormUrlEncoded
    @POST("user/loadNearestUser")
    Call<List<User>> loadNearestUser(@Field("golDarah") String gol);

    @FormUrlEncoded
    @POST("user/updateToken")
    Call<List<RetrofitResult>> updateToken(@Field("id") String id,@Field("token") String token);

    @FormUrlEncoded
    @POST("user/loadNearEvent")
    Call<List<Event>> findNearEvent(@Field("lat") String lat, @Field("lng") String lng,@Field("status") String status);

    @POST("user/loadContact")
    Call<List<User>> loadContact();

    @POST("user/loadContactUtd")
    Call<List<User>> loadContactUtd();

    @FormUrlEncoded
    @POST("user/loadChatList")
    Call<List<User>> loadChatList(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/joinEvent")
    Call<List<RetrofitResult>> jointEvent(@Field("idUser") String idUser, @Field("idEvent") String idEvent);

    @FormUrlEncoded
    @POST("user/loadJoinedEvent")
    Call<List<Event>> loadJoinedEvent(@Field("idUser") String idUser,@Field("lat") String lat,@Field("lng") String lng, @Field("status") String status);

    @FormUrlEncoded
    @POST("user/loadRequestEvent")
    Call<List<Event>> loadRequestEvent(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("user/loadRequestEventById")
    Call<List<Event>> loadRequestEventById(@Field("idEvent") String idEvent);

    @FormUrlEncoded
    @POST("user/checkJoinedEvent")
    Call<List<RetrofitResult>> checkJoinedEvent(@Field("idUser") String idUser,@Field("idEvent") String idEvent);

    @POST("user/countEvent")
    Call<List<RetrofitResult>> countEvent();

    @Multipart
    @POST("user/countEvent")
    Call<List<RetrofitResult>> uploadLetter(@Part MultipartBody.Part file);

    @POST("user/loadUtd")
    Call<List<Utd>> loadUtd();

    @Multipart
    @POST("user/submitEvent")
    Call<ServerResponse> submitEvent(@Part("idUser") RequestBody idUser,
                                @Part("title") RequestBody title,
                                @Part("instansi") RequestBody instansi,
                                @Part("date") RequestBody date,
                                @Part("startTime") RequestBody startTime,
                                @Part("endTime") RequestBody endTime,
                                @Part("lat") RequestBody lat,
                                @Part("lng") RequestBody lng,
                                @Part("address") RequestBody address,
                                @Part("description") RequestBody description,
                                @Part("idUtd") RequestBody idUtd,
                                @Part("target") RequestBody target,
                                @Part MultipartBody.Part map);


    @Multipart
    @POST("user/updateEvent")
    Call<ServerResponse> updateEvent(@Part("idEvent") RequestBody idEvent,
                                     @Part("idUtd") RequestBody idUtd,
                                     @Part("title") RequestBody title,
                                     @Part("description") RequestBody description,
                                     @Part("instansi") RequestBody instansi,
                                     @Part("target") RequestBody target,
                                     @Part("date") RequestBody date,
                                     @Part("startTime") RequestBody startTime,
                                     @Part("endTime") RequestBody endTime,
                                     @Part("address") RequestBody address,
                                     @Part("lat") RequestBody lat,
                                     @Part("lng") RequestBody lng,
                                     @Part MultipartBody.Part map);

    @FormUrlEncoded
    @POST("user/deleteJoinedEvent")
    Call<List<RetrofitResult>> deleteJoinedEvent(@Field("idUser") String idUser, @Field("idEvent") String idEvent);

    @FormUrlEncoded
    @POST("user/cancelRequestEvent")
    Call<ServerResponse> cancelRequestEvent(@Field("idEvent") String idEvent);

    @POST("user/loadGolDarah")
    Call<List<GolDarah>> loadGolDarah();

    @POST("user/loadBloodComponent")
    Call<List<Component>> loadBloodComponent();

    @FormUrlEncoded
    @POST("user/searchBlood")
    Call<List<BloodStock>> searchBlood(@Field("idGolDarah") String idGolDarah, @Field("idComponent") String idComponent, @Field("idUtd") String idUtd);

    @FormUrlEncoded
    @POST("user/loadEventApproved")
    Call<List<Event>> loadEventApproved(@Field("idEvent") String idEvent,@Field("status") String status);

    @FormUrlEncoded
    @POST("user/editApprovedEvent")
    Call<ServerResponse> editApprovedEvent(@Field("idEvent") String idEvent,
                                           @Field("title") String title,
                                           @Field("description") String description,
                                           @Field("instansi") String instansi,
                                           @Field("target") String target,
                                           @Field("date") String date,
                                           @Field("startTime") String startTime,
                                           @Field("endTime") String endTime);


    @Multipart
    @POST("user/signUp")
    Call<ServerResponse> signUp(     @Part("nama") RequestBody nama,
                                     @Part("email") RequestBody email,
                                     @Part("telp") RequestBody telp,
                                     @Part("goldarah") RequestBody goldarah,
                                     @Part("gender") RequestBody gender,
                                     @Part("password") RequestBody password,
                                     @Part MultipartBody.Part map);


    @POST("user/loadPerson")
    Call<List<Person>> loadPerson();

    @FormUrlEncoded
    @POST("user/loadEventById")
    Call<List<Event>> loadEventById(@Field("lat") String lat, @Field("lng") String lng,@Field("status") String status,@Field("idEvent") String idEvent);

    @FormUrlEncoded
    @POST("user/addDonor")
    Call<ServerResponse> addDonor(@Field("idUser") String idEvent,@Field("userName") String userName,@Field("golDarah") int golDarah,@Field("idUtd") String idUtd,@Field("radius") int radius,@Field("tanggal") String tanggal,@Field("status") String status,@Field("userPhoto") String userPhoto);

    @FormUrlEncoded
    @POST("user/updateLocation")
    Call<ServerResponse> updateLocation(@Field("idUser") String idUser, @Field("lat") String lat,@Field("lng") String lng);

    @FormUrlEncoded
    @POST("user/loadDonorReponse")
    Call<List<User>> loadDonorResponse(@Field("idDonor") String idDonor);

    @FormUrlEncoded
    @POST("user/deleteRequestDonor")
    Call<ServerResponse> deleteRequestDonor(@Field("idUser") String idUser, @Field("golDarah") String golDarah,@Field("idDonor") String idDonor,@Field("radius") String radius,@Field("lat") String lat, @Field("lng") String lng);

    @FormUrlEncoded
    @POST("user/acceptDonor")
    Call<ServerResponse> acceptDonor(@Field("idUser") String idUser,@Field("idDonor") String idDonor);

    @FormUrlEncoded
    @POST("user/loadJoinedDonor")
    Call<List<JoinedDonor>> loadjoinedDonor(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("user/loadRequestDonor")
    Call<List<RequestDonor>> loadRequestDonor(@Field("idUser") String idUser);

    @POST("user/addUserData")
    Call<List<UserChat>> addUserData();

    @FormUrlEncoded
    @POST("pmi/loadUserById")
    Call<User> loadUserById(@Field("idUser") String id,@Field("userStatus") String userStatus);

    @FormUrlEncoded
    @POST("user/loadUnApprovedEvent")
    Call<List<UnapprovedEvent>> loadUnApprovedEvent (@Field("idEvent") String idEvent);

    @FormUrlEncoded
    @POST("user/loadBloodRequest")
    Call<RequestBlood> loadBloodRequest(@Field("golDarah") String golDarah,@Field("component") String component);


    //update Profile
    @Multipart
    @POST("user/updateProfile")
    Call<ServerResponse> updateProfile(@Part("idUser") RequestBody idUser,
                                       @Part("userName") RequestBody userName,
                                       @Part("phone") RequestBody phone,
                                       @Part("email") RequestBody email,
                                       @Part("golDarah") RequestBody golDarah,
                                       @Part("gender") RequestBody gender,
                                       @Part MultipartBody.Part map);

    @FormUrlEncoded
    @POST("user/updateProfile")
    Call<ServerResponse> updateProfile2(@Field("idUser") String idUser,
                                        @Field("userName") String userName,
                                        @Field("phone") String phone,
                                        @Field("email") String email,
                                        @Field("golDarah") String golDarah,
                                        @Field("gender") String gender);
}
