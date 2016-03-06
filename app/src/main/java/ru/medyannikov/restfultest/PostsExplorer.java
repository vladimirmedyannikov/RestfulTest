package ru.medyannikov.restfultest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vladimir on 15.09.2015.
 */
public class PostsExplorer {

    List<Posts> posts;

    public List<Posts> getPosts()
    {
        return posts;
    }

    public void add(Posts post)
    {
        if (posts == null){
            posts = new LinkedList<>();
        }
        posts.add(post);
    }
}
