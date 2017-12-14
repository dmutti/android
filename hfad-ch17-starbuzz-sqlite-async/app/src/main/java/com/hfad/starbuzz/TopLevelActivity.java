package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private StarbuzzDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setUpOptionsListView();
        setUpFavoritesListView();
    }

    private void setUpOptionsListView() {
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpFavoritesListView() {
        ListView listView = findViewById(R.id.list_favorites);

        try {
            cursor = populateCursor();
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {"NAME"}, new int[] {android.R.id.text1}, 0);
            listView.setAdapter(listAdapter);

        } catch (Exception e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            Cursor newCursor = populateCursor();
            ListView listView = findViewById(R.id.list_favorites);
            SimpleCursorAdapter adapter = (SimpleCursorAdapter) listView.getAdapter();
            adapter.changeCursor(newCursor);
            cursor = newCursor;

        } catch (Exception e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private Cursor populateCursor() {
        dbHelper = new StarbuzzDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        return db.query("DRINK", new String[] {"_id", "NAME"}, "FAVORITE = 1", null, null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StarbuzzDatabaseHelper.closeResources(cursor, db, dbHelper);
    }
}
