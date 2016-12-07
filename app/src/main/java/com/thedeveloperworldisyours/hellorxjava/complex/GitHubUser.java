package com.thedeveloperworldisyours.hellorxjava.complex;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by javierg on 07/12/2016.
 */

public interface GitHubUser {
    @GET("users/{user}")
    Observable<JsonObject> getUser(@Path("user") String user);
}
