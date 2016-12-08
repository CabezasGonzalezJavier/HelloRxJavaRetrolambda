package com.thedeveloperworldisyours.hellorxjava.complex.zip;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by javierg on 07/12/2016.
 */

public interface UserService {

    String URL_BASE = "https://api.github.com";

    @GET("users/{user}")
    Observable<JsonObject> getUser(@Path("user") String user);
}
