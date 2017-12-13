package com.hfad.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        StarbuzzDatabaseHelper dbHelper = new StarbuzzDatabaseHelper(this);

        String drinkId = String.valueOf(getIntent().getIntExtra(EXTRA_DRINKID, 0));
        Cursor cursor = null;
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("DRINK", new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[] {drinkId}, null, null, null);

            if (!cursor.moveToFirst()) {
                return;
            }

            String nameText = cursor.getString(0);
            String descriptionText = cursor.getString(1);
            int imageId = cursor.getInt(2);

            TextView name = findViewById(R.id.name);
            name.setText(nameText);

            TextView desc = findViewById(R.id.description);
            desc.setText(descriptionText);

            ImageView photo = findViewById(R.id.photo);
            photo.setImageResource(imageId);
            photo.setContentDescription(nameText);

        } catch (Exception e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();

        } finally {
            StarbuzzDatabaseHelper.closeResources(cursor, db, dbHelper);
        }
    }
}
