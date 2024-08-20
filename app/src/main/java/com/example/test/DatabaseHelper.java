package com.example.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.test.bean.Budget;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRANSACTIONS = "transactions";

    // Common column names
    private static final String KEY_ID = "id";

    // USERS Table - column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // TRANSACTIONS Table - column names
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    SQLiteDatabase db;

    // Table Create Statements
    // User table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME
            + " TEXT," + KEY_PASSWORD + " TEXT" + ")";

    // Transaction table create statement
    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE "
            + TABLE_TRANSACTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AMOUNT
            + " REAL," + KEY_DESCRIPTION + " TEXT," + KEY_DATE + " TEXT" + ")";

    public static final String TABLE_BUDGET = "budget";
    public static final String COLUMN_BUDGET_ID = "id";
    public static final String COLUMN_BUDGET_AMOUNT = "amount";
    public static final String COLUMN_BUDGET_MONTH = "month";
    public static final String COLUMN_BUDGET_YEAR = "year";

    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE "
            + TABLE_BUDGET + "(" + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BUDGET_AMOUNT + " REAL," + COLUMN_BUDGET_MONTH + " INTEGER,"
            + COLUMN_BUDGET_YEAR + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db=context.openOrCreateDatabase( DATABASE_NAME, 0, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_BUDGET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "users" table methods ----------------//

    /**
     * Creating a user
     */
    //方法里面又调方法增加可阅读性解耦，第一个是调取用户密码，第二个方法检测用户是否存在。
    public boolean createUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty() || checkUsernameExists(username)){
            return false;
        }else{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, username);
            values.put(KEY_PASSWORD, password);
            // insert row
            db.insert(TABLE_USERS, null, values);
            return true;
        }

    }

    /**
     * Get single user
     */
    public boolean getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + KEY_USERNAME + " = ? AND " + KEY_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{username, password});
        if (cursor != null) {
            return cursor.moveToFirst();
        }

        return false;
    }

    // ------------------------ "transactions" table methods ----------------//

    /**
     * Creating a transaction
     */
    public long createTransaction(double amount, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, amount);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_DATE, date);

        // insert row
        long transaction_id = db.insert(TABLE_TRANSACTIONS, null, values);

        return transaction_id;
    }

    /**
     * getting all transactions
     */
    @SuppressLint("Range")
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Transaction t = new Transaction();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setAmount(c.getDouble(c.getColumnIndex(KEY_AMOUNT)));
                t.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                t.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

                // adding to transactions list
                transactions.add(t);
            } while (c.moveToNext());
        }

        return transactions;
    }

    /**
     * getting total budget
     */
    public int getTotalBudget() {
        // Return a fixed value for budget as an example
        return 1000;
    }

    /**
     * getting total income
     */
    public int getTotalIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_TRANSACTIONS + " WHERE amount > 0", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    /**
     * getting total expense
     */
    public int getTotalExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_TRANSACTIONS + " WHERE amount < 0", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public boolean addTransaction(String amount, String description) {

        return false;
    }
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d(TAG,"checkUser username="+username);
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + KEY_USERNAME + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        Log.d(TAG,"checkUser username="+username+",cursor="+cursor);
        String cursorString0 = cursor.getString(0);
        String cursorString1 = cursor.getString(1);
        Log.d(TAG,"checkUser username="+username+",cursorString0="+cursorString0
        +",cursorString1="+cursorString1);
        while (cursor.moveToNext()){
            String user = cursor.getString(1);
            Log.d(TAG,"checkUser user="+user+",username="+username);
            if (user.equals(username)){
                return  false;
            }else {
                return true;
            }

        }

        return true;
    }

    public boolean checkUsernameExists(String username) {
        boolean exists = false;
        SQLiteDatabase db = this.getReadableDatabase();
        // 查询用户名是否存在
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + KEY_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        Log.d(TAG,"checkUsernameExists cursorString cursor="+cursor);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            String cursorString = cursor.getString(1);
            Log.d(TAG,"checkUsernameExists cursorString="+cursorString
            +",count="+count);
            if (count > 0) {
                exists = true;
            }
        }
        cursor.close();
        db.close();
        return exists;
    }
    // 添加预算
    public long addBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_AMOUNT, budget.getAmount());
        values.put(COLUMN_BUDGET_MONTH, budget.getMonth());
        values.put(COLUMN_BUDGET_YEAR, budget.getYear());

        long id = db.insert(TABLE_BUDGET, null, values);
        db.close();
        return id;
    }

    // 获取特定月份的预算
    public Budget getBudget(int month, int year) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_BUDGET + " WHERE "
                + COLUMN_BUDGET_MONTH + " = ? AND " + COLUMN_BUDGET_YEAR + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(month), String.valueOf(year)});

        if (cursor != null && cursor.moveToFirst()) {
            Budget budget = new Budget(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_MONTH)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_YEAR))
            );
            cursor.close();
            return budget;
        } else {
            return null;
        }
    }

    // 更新预算
    public int updateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_AMOUNT, budget.getAmount());

        return db.update(TABLE_BUDGET, values, COLUMN_BUDGET_ID + " = ?",
                new String[]{String.valueOf(budget.getId())});
    }

    // 删除预算
    public void deleteBudget(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUDGET, COLUMN_BUDGET_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}




