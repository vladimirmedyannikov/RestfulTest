package ru.medyannikov.restfultest;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Vladimir on 15.09.2015.
 */
public class PostsExplorerDeserializerJson implements JsonDeserializer<PostsExplorer> {

    @Override
    public PostsExplorer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PostsExplorer postsExplorer = new PostsExplorer();
        JsonArray jsonArray =  json.getAsJsonArray();
        Gson gson = new Gson();

        for (JsonElement element:jsonArray) {
            //JsonObject jsonObject = element.getAsJsonObject();
            Posts posts = gson.fromJson(element,Posts.class);
            postsExplorer.add(posts);
            
        }
        return  postsExplorer;
    }
}
