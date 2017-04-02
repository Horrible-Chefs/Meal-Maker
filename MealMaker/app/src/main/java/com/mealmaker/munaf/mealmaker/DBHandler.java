package com.mealmaker.munaf.mealmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by munaf on 4/1/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PANTRY";
    // table name
    private static final String TABLE_CurrentItems = "CurrentItems";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Item";
    private static final String KEY_QUANTITY = "Quantity";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PANTRY_TABLE = "CREATE TABLE " + TABLE_CurrentItems + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " REAL" + ")";
        db.execSQL(CREATE_PANTRY_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CurrentItems);
        // Creating tables again
        onCreate(db);
    }

    // Adding new item
    public void addItem(PantryItem pantryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pantryItem.getName()); // Item Name
        values.put(KEY_QUANTITY, pantryItem.getQuantity()); // Quantity

        // Inserting Row
        db.insert(TABLE_CurrentItems, null, values);
        db.close(); // Closing database connection
    }


    // Getting one item
    public PantryItem getPantryItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CurrentItems, new String[]{KEY_ID,
                KEY_NAME, KEY_QUANTITY}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PantryItem details = new PantryItem(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Float.parseFloat(cursor.getString(2)));
    // return details
        return details;
    }


    // Getting All Items
    public List<PantryItem> getAllItems() {
        List<PantryItem> itemList = new ArrayList<PantryItem>();

    // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CurrentItems;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Float.parseFloat(cursor.getString(2)));

    // Adding to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

    // return contact list
        return itemList;
    }

    // Getting item Count
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CurrentItems;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

    // return count
        return cursor.getCount();
    }

    // Updating an item
    public int updateItem(PantryItem pantryItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pantryItem.getName());
        values.put(KEY_QUANTITY, pantryItem.getQuantity());

    // updating row
        return db.update(TABLE_CurrentItems, values, KEY_ID + " = ?",
        new String[]{String.valueOf(pantryItem.getId())});
    }

    // Deleting an item
    public void deleteItem(PantryItem pantryItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CurrentItems, KEY_ID + " = ?",
        new String[] { String.valueOf(pantryItem.getId()) });
        db.close();
    }

    public void deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        List<PantryItem> list = getAllItems();
        for (PantryItem item:list){
            if (item.getName().equals(name)){
                deleteItem(item);
            }
        }
    }
}

