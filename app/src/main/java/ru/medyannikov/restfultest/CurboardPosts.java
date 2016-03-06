package ru.medyannikov.restfultest;

import java.io.Serializable;

/**
 * Created by Vladimir on 14.09.2015.
 */
public class CurboardPosts implements Serializable {

    public Long _id;
    public String title;
    public String about;
    public String text;
    public String url;
    public String url_thm;
    public Long date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_thm() {
        return url_thm;
    }

    public void setUrl_thm(String url_thm) {
        this.url_thm = url_thm;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public CurboardPosts(Long id_post, String title, String about) {
        super();

        this._id = id_post;
        this.title = title;
        this.about = about;
    }

    public CurboardPosts(Long id_post, String title, String about, String text, String url, String url_thm, long date) {
        this._id = id_post;
        this.title = title;
        this.about = about;
        this.text = text;
        this.url = url;
        this.url_thm = url_thm;
        this.date = date;
    }

    public CurboardPosts()
    {

    }

    public Long getId_post() {
        return _id;
    }

    public void setId_post(Long id_post) {
        this._id = id_post;
    }

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
}
