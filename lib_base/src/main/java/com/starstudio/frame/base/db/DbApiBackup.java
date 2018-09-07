package com.starstudio.frame.base.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongseok on 2016-10-19.
 */

public class DbApiBackup extends BaseDb {

    public static final String TABLE_API = "db_api_backup";
    protected final static int DB_APP_VERSION = 1;


    public DbApiBackup(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, TABLE_API, factory, DB_APP_VERSION);
    }


    public static void saveResult(Context context,String key,String value){
        if(context==null || TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }

        DbApiBackup dbApiBackup =new DbApiBackup(context.getApplicationContext(),null);
        dbApiBackup.openDB();
        dbApiBackup.pushJson(key,value);
        dbApiBackup.closeDB();
    }
    public static String getResult(Context context,String key){
        if(context==null || TextUtils.isEmpty(key)  ) {
            return "";
        }

        DbApiBackup dbApiBackup =new DbApiBackup(context.getApplicationContext(),null);
        dbApiBackup.openDB();
        String json = dbApiBackup.getJson(key);
        dbApiBackup.closeDB();
        return json;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_API.columnCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API);
        onCreate(db);
    }

    private static class COLUMN_API {
        private static final String KEY = "key";
        private static final String JSON_STR = "json_str";
        private static String columnCreate() {
            return TABLE_API + "(" +
                    KEY + " TEXT PRIMARY KEY," +
                    JSON_STR+ " TEXT" +
                    ");";
        }
    }


    /**
     * Add or remove
     *
     * @return
     */
    public void pushJson(String key,String values ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_API.KEY,key);
        contentValues.put(COLUMN_API.JSON_STR, values);
        if (mDbWriter != null) {
            String info = getJson(key);
            if(TextUtils.isEmpty(info)){
                mDbWriter.insert(TABLE_API, null, contentValues);
            }else{
                mDbWriter.update(TABLE_API,contentValues, COLUMN_API.KEY+"=?",new String[]{key});
            }
        }
    }

    /**
     * Add or remove
     *
     * @return
     */
    public void remodeJson(String key ) {
        if (mDbWriter != null) {
            mDbWriter.delete(TABLE_API, COLUMN_API.KEY+"=?",new String[]{key});
        }
    }
    public boolean clear(){
       return deleteAll(TABLE_API);
    }


    public String getJson(String key){
        if (mDbWriter == null){
            return null;
        }
        Cursor cursor = mDbWriter.query(TABLE_API, null, COLUMN_API.KEY + "=?", new String[]{key}, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                String values = cursor.getString(cursor.getColumnIndex(COLUMN_API.JSON_STR));

                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return "";
    }

    public Map<String,String> getAllIndex(Context context) {

        Map<String,String> launchAppInfos = new HashMap();


        if (isExistRows(TABLE_API) > 0) {

            Cursor cursor = fetchAllvalues(TABLE_API);
            if (cursor != null && cursor.moveToFirst()) {

                try {
                    do {
                        String key = cursor.getString(cursor.getColumnIndex(COLUMN_API.KEY));
                        String value = cursor.getString(cursor.getColumnIndex(COLUMN_API.JSON_STR));
                        launchAppInfos.put(key,value);
                    } while (cursor.moveToNext());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }


            }

        }

        return launchAppInfos;
    }


}
