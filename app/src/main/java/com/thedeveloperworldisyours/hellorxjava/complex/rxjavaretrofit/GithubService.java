package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import com.thedeveloperworldisyours.hellorxjava.complex.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by javierg on 08/12/2016.
 */

public interface GithubService {
    String URL_BASE = "https://api.github.com";

    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String login);
}
