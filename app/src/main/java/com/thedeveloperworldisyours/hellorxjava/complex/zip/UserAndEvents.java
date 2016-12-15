package com.thedeveloperworldisyours.hellorxjava.complex.zip;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by javierg on 07/12/2016.
 */

public class UserAndEvents {
    public JsonArray events;
    public JsonObject user;

    public UserAndEvents(JsonObject user, JsonArray events) {
        this.events = events;
        this.user = user;
    }

    public JsonArray getEvents() {
        return events;
    }

    public void setEvents(JsonArray events) {
        this.events = events;
    }

    public JsonObject getUser() {
        return user;
    }

    public void setUser(JsonObject user) {
        this.user = user;
    }
}
