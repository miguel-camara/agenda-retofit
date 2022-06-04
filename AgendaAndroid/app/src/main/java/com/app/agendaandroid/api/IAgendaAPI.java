package com.app.agendaandroid.api;

import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAgendaAPI {
    @FormUrlEncoded
    @POST("users/sign")
    Call<User> login(@Field("name") String name,
                     @Field("password") String password );

    @POST("users")
    Call<User> registerUser(@Body User user);

    @POST("users/sign")
    Call<User> login(@Body User user);

    @GET("users")
    Call<List<User>> getUsers();

    @DELETE("events/{id}")
    Call<Event> deleteUser(@Path("id") String id);

    @GET("events/{id}")
    Call<List<Event>> getEventsId(@Path("id") String id);

    @GET("events")
    Call<List<Event>> getEvents();

    @POST("events/{id}")
    Call<Event> updateEvent( @Path("id") String id, @Body Event event );

    @POST("events")
    Call<Event> addEvent( @Body Event event );
}
