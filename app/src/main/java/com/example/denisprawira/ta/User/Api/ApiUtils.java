package com.example.denisprawira.ta.User.Api;

public class ApiUtils {
    public static UserService getUserService(){
        return RetrofitClient.getClient(ApiUrl.BASE_URL).create(UserService.class);
    }
}
