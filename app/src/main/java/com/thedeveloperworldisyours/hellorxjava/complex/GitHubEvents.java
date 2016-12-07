package com.thedeveloperworldisyours.hellorxjava.complex;

import com.google.gson.JsonArray;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by javierg on 07/12/2016.
 */

public interface GitHubEvents {
    @GET("users/{user}/events")
    Observable<JsonArray> listEvents(@Path("user") String user);
}
