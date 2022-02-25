package com.example.denisprawira.ta.User.Interface;

import com.android.volley.VolleyError;

public interface VolleyCallback {
    void getStringResponse(String response);
    void getRequestError(VolleyError error);

}
