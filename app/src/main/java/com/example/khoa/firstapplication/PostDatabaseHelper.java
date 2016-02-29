package com.example.khoa.firstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khoa.nguyen on 2/29/2016.
 */
public class PostDatabaseHelper extends SQLiteOpenHelper {
    /* Database info */
    private static final String DATABASE_NAME = "postsDatabase";
    private static final int DATABASE_VERSION = 1;

    /* Table name */
    private static final String TABLE_POSTS = "posts";

    /* Post table column */
    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_TEXT = "text";

    private static PostDatabaseHelper sInstance;

    /*
    * Use the application context, which will ensure that you
    * don't accidentally leak an Activity's context.
    */
    public static synchronized PostDatabaseHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PostDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * @param context
     */
    private PostDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS +
                "(" +
                KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_POST_TEXT + " TEXT" +
                ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            onCreate(db);
        }
    }

    /* Insert the post into database */
    public void create(Post post) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(KEY_POST_TEXT, post.text);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_POSTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DB", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ORDER BY id DESC",
                        TABLE_POSTS
                        );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Post newPost = new Post();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_POST_ID)));
                    newPost.text = cursor.getString(cursor.getColumnIndex(KEY_POST_TEXT));
                    posts.add(newPost);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }

    /* Update the post */
    public int updatePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POST_TEXT, post.text);

        // Updating post with KEY_ID
        return db.update(TABLE_POSTS, values, KEY_POST_ID + " = ?",
                new String[] { String.valueOf(post) });
    }
}
