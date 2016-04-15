package com.rage.clamber.Networking;

import com.rage.clamber.Data.Climb;
import com.rage.clamber.Data.Comment;
import com.rage.clamber.Data.Project;
import com.rage.clamber.Data.User;
import com.rage.clamber.Data.WallSection;
import com.rage.clamber.Networking.Requests.NewCommentRequest;
import com.rage.clamber.Networking.Requests.NewUserDataRequest;
import com.rage.clamber.Networking.Requests.UserClimbDataRequest;

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
    Call<User> getExistingUser(@Path("username") String username);

    @POST("user")
    Call<User> addNewUser(@Body NewUserDataRequest request);

    @POST("user/{username}/update")
    Call<User> updateExistingUser(@Path("username") String username, @Body NewUserDataRequest request);

    @GET("projects/{username}")
    Call<List<Climb>> getProjectsForUser(@Path("username") String username);

    @GET("user/{username}/walls/{wall_id}/wall_sections")
    Call<List<WallSection>> getWallSectionByWall(@Path("username") String username, @Path("wall_id") int wallId);

    @GET("user/{username}/walls/{wall_id}/wall_sections/{wall_section_id}/climbs")
    Call<List<Climb>> getClimbsByWallSection(@Path("username") String username, @Path("wall_id") int wallId, @Path("wall_section_id") int wallSectionId);

    @POST("projects")
    Call<Project> createProject(@Body UserClimbDataRequest request);

    @DELETE("projects/{username}/climbs/{climb_id}")
    Call<Boolean> removeProject(@Path("username") String username, @Path("climb_id") int climb_id);

    @POST("completed")
    Call<Boolean> createCompleted(@Body UserClimbDataRequest request);

    @DELETE("completed/{username}/climbs/{climb_id}")
    Call<Boolean> removeCompleted(@Path("username") String username, @Path("climb_id") int climb_id);

    @GET("completed/{username}")
    Call<List<Climb>> getCompletedForUser(@Path("username") String username);

    @GET("user/{username}/recommendations")
    Call<List<Climb>> getRecommendations(@Path("username") String username);

    @GET("climbs/{climb_id}/comments")
    Call<List<Comment>> getComments(@Path("climb_id") int climb_id);

    @POST("climbs/{climb_id}/comments")
    Call<Boolean> addComment(@Path("climb_id") int climb_id, @Body NewCommentRequest request);

}
