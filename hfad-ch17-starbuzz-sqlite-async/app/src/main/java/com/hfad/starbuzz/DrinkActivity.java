package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        String[] drinkId = new String[] { String.valueOf(getDrinkId()) };
        StarbuzzDatabaseHelper dbHelper = new StarbuzzDatabaseHelper(this);
        Cursor cursor = null;
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("DRINK", new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"}, "_id = ?", drinkId, null, null, null);

            if (!cursor.moveToFirst()) {
                return;
            }

            String nameText = cursor.getString(0);
            String descriptionText = cursor.getString(1);
            int imageId = cursor.getInt(2);
            boolean isFavorite = cursor.getInt(3) == 1;

            TextView name = findViewById(R.id.name);
            name.setText(nameText);

            TextView desc = findViewById(R.id.description);
            desc.setText(descriptionText);

            ImageView photo = findViewById(R.id.photo);
            photo.setImageResource(imageId);
            photo.setContentDescription(nameText);

            CheckBox favorite = findViewById(R.id.favorite);
            favorite.setChecked(isFavorite);

        } catch (Exception e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();

        } finally {
            StarbuzzDatabaseHelper.closeResources(cursor, db, dbHelper);
        }
    }

    public void onFavoriteClicked(View v) {
        new UpdateDrinkTask().execute(getDrinkId());
    }

    private int getDrinkId() {
        return getIntent().getIntExtra(EXTRA_DRINKID, 0);
    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

        private ContentValues values = new ContentValues();

        @Override
        protected void onPreExecute() {
            int isFavorite = ((CheckBox) findViewById(R.id.favorite)).isChecked() ? 1 : 0;
            values.put("FAVORITE", isFavorite);
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            String[] drinkId = new String[] { String.valueOf(drinks[0]) };

            StarbuzzDatabaseHelper dbHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            SQLiteDatabase db = null;

            try {
                db = dbHelper.getWritableDatabase();
                db.update("DRINK", values, "_id = ?", drinkId);

            } catch (Exception e) {
                return false;

            } finally {
                StarbuzzDatabaseHelper.closeResources(db, dbHelper);
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                return;
            }
            Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
