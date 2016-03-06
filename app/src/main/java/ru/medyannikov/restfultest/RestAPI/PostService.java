package ru.medyannikov.restfultest.RestAPI;

import java.util.List;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Path;
import ru.medyannikov.restfultest.Posts;

/**
 * Created by Vladimir on 14.09.2015.
 */
public interface PostService  {
    @GET("/server/v1/posts")
    void  postList(Callback<List<Posts>> res);

    @GET("/server/v1/posts/{limit}/{offset}")
    void  postList(@Path("limit") int limit, @Path("offset") int offset, Callback<List<Posts>> res);



    @GET("/api/posts/{id}")
    Posts getPost(@Path("id") int id);
}
