package ru.medyannikov.restfultest;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vladimir on 01.09.2015.
 */
public class Post implements Serializable{

    private String title;
    private String about;
    private String text;
    private long datePost;
    private long id_post;
    private String url;

    public static int getCount() {
        return count;
    }

    private static int count = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDatePost() {
        return datePost;
    }

    public void setDatePost(long datePost) {
        this.datePost = datePost;
    }

    public long getId_post() {
        return id_post;
    }

    public Post(String title, String about, String text, long datePost) {
        this.title = title;
        this.about = about;
        this.text = text;
        this.datePost = datePost;
        this.id_post = ++count;
    }

    public Post(long id_post,String title, String about, String text, long datePost) {
        this.title = title;
        this.about = about;
        this.text = text;
        this.datePost = datePost;
        this.id_post = id_post;
    }
    public Post(long id_post,String title, String about, String text, long datePost, String url) {
        this.title = title;
        this.about = about;
        this.text = text;
        this.datePost = datePost;
        this.id_post = id_post;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public Post()
    {

        this("Unknow title"
                ,"Здесь короткое описание посат. Со всякой фигней."
                ,"https://cdn4.iconfinder.com/data/icons/food-and-drink-5/100/large-food-go-04-128.png"
                ,new Date().getTime());
    }

    public Post(String url)
    {
        this("Unknow title"
                ,"Здесь короткое описание посат. Со всякой фигней."
                ,url
                ,new Date().getTime());
    }

    public String toString(){
        return title;
    }
}
