package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private void insertDrink(SQLiteDatabase db, String name, String desc, int resourceId) {
        ContentValues row = new ContentValues();
        row.put("NAME", name);
        row.put("DESCRIPTION", desc);
        row.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, row);
    }

    public static void closeResources(Cursor cursor, SQLiteDatabase db, SQLiteOpenHelper dbHelper) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception ignored) { }
        }

        if (dbHelper != null) {
            try {
                dbHelper.close();
            } catch (Exception ignored) { }
        }

        if (db != null) {
            try {
                db.close();
            } catch (Exception ignored) { }
        }
    }
}
