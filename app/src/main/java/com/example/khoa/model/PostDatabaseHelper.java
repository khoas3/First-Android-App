package com.example.khoa.model;

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
    private static final String KEY_POST_CONTENT = "content";

    private static final String TAG = "PostDatabase";

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
                KEY_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_POST_CONTENT + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            Log.w(TAG, "Upgrading database. Existing contents will be lost. ["
                    + oldVersion + "] -> [" + newVersion + "]");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            onCreate(db);
        }
    }

    /* Insert the post into database */
    public int create(Post post) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POST_CONTENT, post.content);

        int id = (int) db.insert(TABLE_POSTS, null, values);
        db.close();

        return id;
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
                    newPost.setContent(cursor.getString(cursor.getColumnIndex(KEY_POST_CONTENT)));
                    newPost.setId(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                    posts.add(newPost);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
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
        values.put(KEY_POST_CONTENT, post.content);

        // Updating post with KEY_ID
        return db.update(TABLE_POSTS, values, KEY_POST_ID + " = ?",
                new String[] { String.valueOf(post.getId()) });
    }

    /* Delete the post */
    public void deletePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_POSTS, KEY_POST_ID + " = ?", new String[]{String.valueOf(post.getId())});
        db.close();
    }
}
