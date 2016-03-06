package ru.medyannikov.restfultest.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import ru.medyannikov.restfultest.Post;

/**
 * Created by Vladimir on 11.09.2015.
 */
public class PostDataSource {

    private SQLiteDatabase database;
    private SQLiteHelperPost dbHelper;
    private String[] allColumns = {SQLiteHelperPost.COLUMN_ID,
            SQLiteHelperPost.COLUMN_TITLE,
            SQLiteHelperPost.COLUMN_SHORT_TEXT,
            SQLiteHelperPost.COLUMN_TEXT,
            SQLiteHelperPost.COLUMN_DATE,
            SQLiteHelperPost.COLUMN_URL};

    public PostDataSource(Context context)
    {
        dbHelper = new SQLiteHelperPost(context);
    }

    public void open() throws SQLiteException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
    }

    public Post insertPost(String title, String short_text, String text, long date, String url)
    {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelperPost.COLUMN_TITLE, title);
        values.put(SQLiteHelperPost.COLUMN_SHORT_TEXT,short_text);
        values.put(SQLiteHelperPost.COLUMN_TEXT, text);
        values.put(SQLiteHelperPost.COLUMN_DATE, date);
        values.put(SQLiteHelperPost.COLUMN_URL, url);

        long insertId = database.insert(SQLiteHelperPost.TABLE_POST, null, values);

        Cursor cursor = database.query(SQLiteHelperPost.TABLE_POST, allColumns, SQLiteHelperPost.COLUMN_ID + " = "+ insertId,null,null,null,null);

        cursor.moveToFirst();
        Post newPost = cursorToPostr(cursor);
        cursor.close();

        return newPost;
    }

    public void updatePost(Post post)
    {
        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelperPost.COLUMN_TITLE, post.getTitle());
        cv.put(SQLiteHelperPost.COLUMN_TEXT, post.getText());

        int i = database.update(SQLiteHelperPost.TABLE_POST,cv,SQLiteHelperPost.COLUMN_ID +" = ?",new String[]{Long.toString(post.getId_post())});

        Log.d("update", Long.toString(i));
    }

    public void deletePost(Post post)
    {
        long id = post.getId_post();
        database.delete(SQLiteHelperPost.TABLE_POST, SQLiteHelperPost.COLUMN_ID + "=?", new String[]{Long.toString(id)});
    }

    public ArrayList<Post> getAllPosts()
    {
        ArrayList<Post> posts = new ArrayList<Post>();
        Cursor cursor = database.query(SQLiteHelperPost.TABLE_POST,allColumns,null,null,null,null,SQLiteHelperPost.COLUMN_ID + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            posts.add(cursorToPostr(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return posts;
    }

    private Post cursorToPostr(Cursor cursor) {
        Post result = new Post(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4),cursor.getString(5));
        return result;
    }
}
