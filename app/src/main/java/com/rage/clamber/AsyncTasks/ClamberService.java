package com.rage.clamber.AsyncTasks;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.User;
import com.rage.clamber.Data.WallSection;

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

    @GET("user/{username}/walls/{wall_id}/wall_sections")
    public Call<List<WallSection>> getWallSectionByWall(@Path("username") String username, @Path("wall_id") int wallId);

    @GET("user/{username}/walls/{wall_id}/wall_sections/{wall_section_id}/climbs")
    public Call<List<Climb>> getClimbsByWallSection(@Path("username") String username, @Path("wall_id") int wallId, @Path("wall_section_id") int wallSectionId);
}
