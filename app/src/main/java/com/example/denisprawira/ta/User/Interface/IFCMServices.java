package com.example.denisprawira.ta.User.Interface;

import com.example.denisprawira.ta.User.Model.FCMResponse;
import com.example.denisprawira.ta.User.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface IFCMServices {
    @Headers({
            "Authorization: key=AAAATm44Aj0:APA91bHzSMy-vaJjaQ54DjoPqlAwV5GDYzyFyoLDSfz0tNrpJPr7DeL7ka5aJvY1aaCaFBnfuoCoS1eLyeWJbh3itAQxAh3huRbD0LezVy3jOXrXerATk5s9_tO1kpjOW-wAec05pIEz",
            "Content-Type: application/json"
    })

    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
