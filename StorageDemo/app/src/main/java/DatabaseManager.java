package com.example.storagedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase.db"; // Tên database
    private static final int DATABASE_VERSION = 1; // Phiên bản database
    private static final String TABLE_USERS = "users"; // Tên bảng

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // **1. Chèn người dùng vào database**
    public void insertUser(String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    // Đóng database đúng cách
    public void closeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

    }
    // **2. Lấy danh sách tất cả người dùng**
    public List<String> getAllUsers() {
        List<String> usersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                usersList.add("ID: " + id + ", Name: " + name + ", Age: " + age);
            } while (cursor.moveToNext());
        }

        cursor.close();
//        db.close();
        return usersList;
    }
}
