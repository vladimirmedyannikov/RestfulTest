package ru.medyannikov.restfultest.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vladimir on 11.09.2015.
 */
public class SQLiteHelperPost extends SQLiteOpenHelper {

    public static final String TABLE_POST = "posts";
    public static final String COLUMN_ID = "id_post";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_URL_THM = "url_thm";
    public static final String COLUMN_SHORT_TEXT = "about";
    public static final String COLUMN_TEXT = "text_post";
    public static final String COLUMN_DATE = "date_post";

    public static final String DATABASE_NAME = "post.db";
    public static final int DATABASE_VERSION = 4;

    public static final String DATABASE_CREATE =
            "create table " + TABLE_POST
            +" (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_SHORT_TEXT + " text, "
            + COLUMN_TEXT + " text, "
            + COLUMN_URL + " text, "
            + COLUMN_DATE + " integer, "
            + COLUMN_URL_THM + " text );";

    public SQLiteHelperPost(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
            onCreate(db);
        }
    }
}
