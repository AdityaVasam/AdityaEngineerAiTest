package com.smita.engineeraitest.retrofit;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    //call api to get list of posts
    @GET(APIConstants.API_POSTS_URL)
    Call<JsonElement> getPostsFromApi(@Query("tags") String tags, @Query("page") int pageNo);
}
