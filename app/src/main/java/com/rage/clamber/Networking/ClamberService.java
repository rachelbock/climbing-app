package com.rage.clamber.Networking;

import com.rage.clamber.Networking.Requests.NewUserDataRequest;
import com.rage.clamber.Networking.Requests.UserClimbDataRequest;
import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.Project;
import com.rage.clamber.Data.User;
import com.rage.clamber.Data.WallSection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * interface for network activity used with Retrofit
 */
public interface ClamberService {

    @GET("user/{username}")
    public Call<User> getExistingUser(@Path("username") String username);

    @POST("user")
    public Call<User> addNewUser(@Body NewUserDataRequest request);

    @GET("projects/{username}")
    public Call<List<Climb>> getProjectsForUser(@Path("username") String username);

    @GET("user/{username}/walls/{wall_id}/wall_sections")
    public Call<List<WallSection>> getWallSectionByWall(@Path("username") String username, @Path("wall_id") int wallId);

    @GET("user/{username}/walls/{wall_id}/wall_sections/{wall_section_id}/climbs")
    public Call<List<Climb>> getClimbsByWallSection(@Path("username") String username, @Path("wall_id") int wallId, @Path("wall_section_id") int wallSectionId);

    @POST("projects")
    Call<Project> createProject(@Body UserClimbDataRequest request);

    @DELETE("projects/{username}/climbs/{climb_id}")
    Call<Boolean> removeProject(@Path("username") String username, @Path("climb_id") int climb_id);

    @POST("completed")
    Call<Boolean> createCompleted(@Body UserClimbDataRequest request);

    @DELETE("completed/{username}/climbs/{climb_id}")
    Call<Boolean> removeCompleted(@Path("username") String username, @Path("climb_id") int climb_id);





}
