package com.vktech.expensetrackerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TransactionDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ExpenseTrackerDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "transactions";

    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_TYPE = "type";
    private static final String COL_DESCRIPTION = "description";

    public TransactionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_DATE + " TEXT," +
                COL_AMOUNT + " REAL," +
                COL_TYPE + " TEXT," +
                COL_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTransaction(String date, double amount, String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_AMOUNT, amount);
        values.put(COL_TYPE, type);
        values.put(COL_DESCRIPTION, description);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> transactionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION));

                transactionList.add(new TransactionModel(id, date, amount, type, description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return transactionList;
    }
}