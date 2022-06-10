package com.example.denisprawira.ta.Api;
import com.example.denisprawira.ta.Api.Model.Blood;
import com.example.denisprawira.ta.Api.Model.Event;
import com.example.denisprawira.ta.Api.Model.HelpRequest;
import com.example.denisprawira.ta.Api.Model.Pmi;
import com.example.denisprawira.ta.Api.Model.Pmi2;
import com.example.denisprawira.ta.Api.Model.Response;
import com.example.denisprawira.ta.Api.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    //////////////////////// AUTHENTICATION ////////////////////////
    @FormUrlEncoded
    @POST("user/login")
    Call<Response> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/loadCurrentUser")
    Call<User> showCurrentUser(@Header("Authorization") String authHeader, @Field("email") String email);

    @FormUrlEncoded
    @POST("user/updateFirebaseToken")
    Call<Response> updateFirebaseToken(@Field("id") String id,@Field("token") String token);

    @FormUrlEncoded
    @POST("user/loadUserPmi")
    Call<Pmi2> showCurrentPmi(@Field("userId") String userId);

    @POST("user/logout")
    Call<Response> logout();


    //////////////////////// USER EVENT ////////////////////////
    @FormUrlEncoded
    @POST("user/event/show")
    Call<List<Event>> showAvailableEvent(@Field("lat") double lat, @Field("lng") double lng);


    ///////////////////////////////////////////////////////////////////
    /////////////////////////// USER EVENT ////////////////////////////
    ///////////////////////////////////////////////////////////////////
    @Multipart
    @POST("user/event/store")
    Call<Response> storeEvent(
            @Part("eventUserId") RequestBody eventUserId,
            @Part("eventPmiId") RequestBody eventPmiId,
            @Part("eventTitle") RequestBody eventTitle,
            @Part("eventInstitution") RequestBody eventInstitution,
            @Part("eventDescription") RequestBody eventDescription,
            @Part("eventAmount") RequestBody eventAmount,
            @Part("eventDateStart") RequestBody eventDateStart,
            @Part("eventAddress") RequestBody eventAddress,
            @Part("eventLat") RequestBody eventLat,
            @Part("eventLng") RequestBody eventLng,
            @Part("eventStatus") RequestBody eventStatus,
            @Part MultipartBody.Part map
    );

    @Multipart
    @POST("user/event/store")
    Call<Response> storeEvent(
            @Part("eventUserId") RequestBody eventUserId,
            @Part("eventPmiId") RequestBody eventPmiId,
            @Part("eventTitle") RequestBody eventTitle,
            @Part("eventInstitution") RequestBody eventInstitution,
            @Part("eventDescription") RequestBody eventDescription,
            @Part("eventAmount") RequestBody eventAmount,
            @Part("eventDateStart") RequestBody eventDateStart,
            @Part("eventAddress") RequestBody eventAddress,
            @Part("eventLat") RequestBody eventLat,
            @Part("eventLng") RequestBody eventLng,
            @Part("eventStatus") RequestBody eventStatus
    );

    @FormUrlEncoded
    @POST("user/event/delete")
    Call<List<Event>> deleteEvent(@Field("eventId") String id);

    @FormUrlEncoded
    @POST("user/event/showByUserId")
    Call<List<Event>> showEvent(@Field("userId") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("user/event/showJoined")
    Call<List<Event>> showJoined(@Field("userId") String id);


    ///////////////////////////////////////////////////////////////////
    //////////////////////// USER HELP REQUEST ////////////////////////
    ///////////////////////////////////////////////////////////////////
    @Multipart
    @POST("user/helprequest/store")
    Call<Response> storeHelprequest(
            @Part("idUser") RequestBody idUser,
            @Part("idPmi") RequestBody idPmi,
            @Part("bloodType") RequestBody bloodType,
            @Part("description") RequestBody description,
            @Part("patientName") RequestBody patientName,
            @Part("target") RequestBody target,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part map
    );


    @Multipart
    @POST("user/helprequest/store")
    Call<Response> storeHelprequest(
            @Part("idUser") RequestBody idUser,
            @Part("idPmi") RequestBody idPmi,
            @Part("bloodType") RequestBody bloodType,
            @Part("description") RequestBody description,
            @Part("patientName") RequestBody patientName,
            @Part("target") RequestBody target,
            @Part("status") RequestBody status
    );

    @FormUrlEncoded
    @POST("user/helprequest/show")
    Call<List<HelpRequest>> showHelprequest(@Field("idUser") String idUser,@Field("status") String status);

    @FormUrlEncoded
    @POST("user/event/joinEvent")
    Call<Response> joinEvent(@Field("idUser") String id,@Field("idEvent") String idEvent,@Field("status") int status);


    @FormUrlEncoded
    @POST("user/event/checkJoinedEvent")
    Call<Response> checkJoinedEvent(@Field("idUser") String id,@Field("idEvent") String idEvent);


    @POST("user/helprequest/showAllBloodType")
    Call<List<Blood>> showBloodType();

    //////////////////////// USER PMI ////////////////////////
    @POST("user/pmi/loadAllPmi")
    Call<List<Pmi>> showPmi();

    @FormUrlEncoded
    @POST("user/pmi/loadPmi")
    Call<Pmi> loadPmi(@Field("id") String id);



    //////////////////////// PMI ////////////////////////
    @FormUrlEncoded
    @POST("pmi/login")
    Call<Response> loginPmi(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("pmi/loadCurrentUser")
    Call<Pmi> loadCurrentUserPmi(@Field("email") String email);

    /////////////////////// PMI EVENT ///////////////////
    @FormUrlEncoded
    @POST("pmi/event/loadEvent")
    Call<List<Event>> loadRequestedEvent(@Field("idPmi") String idPmi,@Field("status") String status);

    @FormUrlEncoded
    @POST("pmi/event/acceptEvent")
    Call<Response> acceptEvent(@Field("idEvent") String idEvent,@Field("status") String status);

    @FormUrlEncoded
    @POST("pmi/event/update")
    Call<Response> updateEventPmi(
            @Field("id") String id,
            @Field("title") String title,
            @Field("institution") String institution,
            @Field("description") String description,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("address") String address,
            @Field("target") String target,
            @Field("dateStart") String dateStart,
            @Field("dateEnd") String dateEnd
    );


    ////////////////////// PMI USER ////////////////////
    @FormUrlEncoded
    @POST("pmi/user/loadUserEvent")
    Call<User> loadUserEvent(@Field("idUser") String idUser);


}
