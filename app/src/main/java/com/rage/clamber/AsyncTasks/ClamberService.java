package com.rage.clamber.AsyncTasks;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * interface for network activity used with Retrofit
 */
public interface ClamberService {

    @GET("user/{username}")
    public Call<User> getExistingUser(@Path("username") String username);

    @GET("projects/{username}")
    public Call<List<Climb>> getProjectsForUser(@Path("username") String username);
}
