package com.github.cptzee.kusinniimang.Data.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.cptzee.kusinniimang.Data.Account;
import com.github.cptzee.kusinniimang.Data.Enums.AccountType;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton helper class that manages the account data from the local database
 * @see Account
 * @see SQLiteOpenHelper
 * @see SQLiteDatabase
 */
public class AccountHelper extends SQLiteOpenHelper {
    private AccountHelper(@Nullable Context context) {
        super(context, "db_Kusina", null, 1);
    }

    private final String TABLENAME = "tbl_Accounts";
    private final SQLiteDatabase dbw = getWritableDatabase();
    private final SQLiteDatabase dbr = getReadableDatabase();
    private static AccountHelper instance;

    public static AccountHelper instance(Context context){
        if(instance == null)
            instance = new AccountHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "firstName TEXT," +
                    "lastName TEXT," +
                    "middleInitial TEXT," +
                    "email TEXT," +
                    "contactNo TEXT," +
                    "credentialID INTEGER," +
                    "type TEXT," +
                    "archived INTEGER);");
            Log.i(TABLENAME + "_Helper", "Table successfully created.");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + "_Helper", "Table creation failed.", e.getCause());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE " + TABLENAME);
            onCreate(db);
            Log.i(TABLENAME + "_Helper", "Table successfully upgraded.");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + "_Helper", "Table upgrade failed.", e.getCause());
        }
    }

    public void insert(Account data){
        new insertTask().execute(new taskParam(prepareData(data), data.getID()));
    }

    public void update(Account data){
        new updateTask().execute(new taskParam(prepareData(data), data.getID()));
    }

    public void remove(Account data){
        new removeTask().execute(new taskParam(prepareData(data), data.getID()));
    }

    /**
     * <p>
     * The method must be run on an AsyncTask as to reduce load up times.
     * @return the whole list of data
     * </p>
     */
    public List<Account> get(){
        List<Account> list = new ArrayList<>();
        try {
            Cursor cursor = dbr.rawQuery("SELECT ID, firstName, lastName, middleInitial, email, contactNo, credentialID, type " +
                    "FROM " + TABLENAME + " WHERE archived = ?",
                    new String[]{String.valueOf(0)});
            while (cursor.moveToNext())
                list.add(prepareData(cursor));
            Log.i(TABLENAME + "_Helper", "Table retrieval success.");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + "_Helper", "Table retrieval failed.", e.getCause());
        }
        return list;
    }

    /**
     * <p>
     * The method must be run on an AsyncTask as to reduce load up times.
     * @param ID ID of the data to be retrieved
     * @return the specified data
     * </p>
     */
    public Account get(int ID){
        Account data = null;
        try {
            Cursor cursor = dbr.rawQuery("SELECT ID, firstName, lastName, middleInitial, email, contactNo, credentialID, type " +
                            "FROM " + TABLENAME + " WHERE ID = ?",
                    new String[]{String.valueOf(ID)});
            while (cursor.moveToNext())
                data = prepareData(cursor);
            Log.i(TABLENAME + "_Helper", "Table retrieval success.");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + "_Helper", "Table retrieval failed.", e.getCause());
        }
        return data;
    }

    /**
     * <p>
     * The method can be run optionally on an AsyncTask as to reduce load up times.
     * @return the current ID of the table
     * </p>
     */
    public int getCurrentID(){
        int ID = 0;
        try {
            Cursor cursor = dbr.rawQuery("SELECT MAX(ID) " +
                            "FROM " + TABLENAME + " WHERE ID = ?",
                    new String[]{String.valueOf(ID)});
            while (cursor.moveToNext()) {
                ID = cursor.getInt(0);
                ID++;
            }
            Log.i(TABLENAME + "_Helper", "Table ID retrieval success.");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + "_Helper", "Table ID retrieval failed.", e.getCause());
        }
        return ID;
    }

    private ContentValues prepareData(Account data) {
        ContentValues contentValues = new ContentValues();
        if (data.getFirstName() != null)
            contentValues.put("firstName", data.getFirstName());
        if (data.getLastName() != null)
            contentValues.put("lastName", data.getLastName());
        if (data.getMiddleInitial() != null)
            contentValues.put("middleInitial", data.getMiddleInitial());
        if (data.getEmail() != null)
            contentValues.put("email", data.getEmail());
        if (data.getCredentialID() != 0)
            contentValues.put("credentialID", data.getCredentialID());
        if (data.getType().toString().isEmpty())
            contentValues.put("type", data.getType().toString());
        contentValues.put("archived", 0);
        return contentValues;
    }

    private Account prepareData(Cursor cursor) {
        Account data = new Account();
        data.setID(cursor.getInt(0));
        data.setFirstName(cursor.getString(1));
        data.setLastName(cursor.getString(2));
        data.setMiddleInitial(cursor.getString(3));
        data.setEmail(cursor.getString(4));
        data.setCredentialID(cursor.getInt(5));
        if (cursor.getString(6).equals("ADMIN"))
            data.setType(AccountType.ADMIN);
        else
            data.setType(AccountType.USER);
        return data;
    }

    private class taskParam{
        private ContentValues content;
        private int ID;
        public taskParam(ContentValues content, int ID) {
            this.content = content;
            this.ID = ID;
        }
    }

    private class insertTask extends AsyncTask<taskParam, Void, Void>{
        @Override
        protected Void doInBackground(taskParam... taskParams) {
            try {
                dbw.insert(TABLENAME, null, taskParams[0].content);
                Log.i(TABLENAME + "_Helper", "Table successfully inserted into.");
            } catch (SQLiteException e) {
                Log.e(TABLENAME + "_Helper", "Table insertion failed.", e.getCause());
            }
            return null;
        }
    }

    private class updateTask extends AsyncTask<taskParam, Void, Void>{
        @Override
        protected Void doInBackground(taskParam... taskParams) {
            try {
                dbw.update(TABLENAME, taskParams[0].content, "ID = ?", new String[]{String.valueOf(taskParams[0].ID)});
                Log.i(TABLENAME + "_Helper", "Table update successfully.");
            } catch (SQLiteException e) {
                Log.e(TABLENAME + "_Helper", "Table update failed.", e.getCause());
            }
            return null;
        }
    }

    private class removeTask extends AsyncTask<taskParam, Void, Void>{
        @Override
        protected Void doInBackground(taskParam... taskParams) {
            try {
                ContentValues contentValues = taskParams[0].content;
                contentValues.put("archived", 1);
                dbw.update(TABLENAME, contentValues, "ID = ?", new String[]{String.valueOf(taskParams[0].ID)});
                Log.i(TABLENAME + "_Helper", "Table removal successfully.");
            } catch (SQLiteException e) {
                Log.e(TABLENAME + "_Helper", "Table removal failed.", e.getCause());
            }
            return null;
        }
    }
}