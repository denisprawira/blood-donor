package com.example.denisprawira.ta.User.Interface;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public interface VolleyMultipartCallback {
    void getNetworkReponse(NetworkResponse response);
    void getRequestError(VolleyError error);

}
