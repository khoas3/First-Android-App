package com.example.khoa.firstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.khoa.adapter.PostsAdapter;
import com.example.khoa.model.Post;
import com.example.khoa.model.PostDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    List<Post> items = new ArrayList<Post>();
//    ArrayAdapter<Post> itemsAdapter;
    ListView lvItems;
    PostsAdapter postsAdapter ;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<Post>();
        readItems();
//        itemsAdapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, items);
//        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        //load adapter.
        postsAdapter = new PostsAdapter(this, items);
        //fill data.
        lvItems.setAdapter(postsAdapter);
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchEditView(position);
                    }
                }
        );
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        deleteItem(items.get(position));
                        items.remove(position);
                        postsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
    }

    public void launchEditView(int position) {
        String content = items.get(position).getContent();
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        /*Put extras into the bundle for access in the second activity*/
        i.putExtra("content", content);
        i.putExtra("position", position);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        Post post = new Post();
        post.setContent(etNewItem.getText().toString());
        postsAdapter.add(post);
        etNewItem.setText("");
        writeItem(post);

    }

    private void readItems() {
        PostDatabaseHelper dbHelper = PostDatabaseHelper.getsInstance(this);
        items = dbHelper.getAllPosts();
    }

    private void writeItem(Post post) {
        PostDatabaseHelper dbHelper = PostDatabaseHelper.getsInstance(this);
        int id = dbHelper.create(post);
        post.setId(id);
    }

    private void deleteItem(Post post) {
        PostDatabaseHelper dbHelper = PostDatabaseHelper.getsInstance(this);
        dbHelper.deletePost(post);
    }

    private void updateItem(Post post) {
        PostDatabaseHelper dbHelper = PostDatabaseHelper.getsInstance(this);
        dbHelper.updatePost(post);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String content = data.getExtras().getString("content");
            int position = data.getExtras().getInt("position", -1);
            Post updatePost = items.get(position);
            updatePost.setContent(content);
            items.set(position, updatePost);
            postsAdapter.notifyDataSetChanged();
            updateItem(updatePost);
        }
    }
}
