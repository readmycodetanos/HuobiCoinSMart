package com.starstudio.frame.base.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongseok on 2016-10-19.
 */

public class DbError extends BaseDb {

    public static final String TABLE_Error = "db_error";
    protected final static int DB_APP_VERSION = 2;


    public DbError(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, TABLE_Error, factory, DB_APP_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ColumnError.columnCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Error);
        onCreate(db);
    }

    private static class ColumnError {
        private static final String error = "cate";

        private static String columnCreate() {
            return TABLE_Error + "(" +
                    error + " TEXT PRIMARY KEY" +
                    ");";
        }
    }


    /**
     * Add or remove
     *
     * @return
     */
    public void pushApp(String error) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnError.error, error);
        if (mDbWriter != null) {
               mDbWriter.insert(TABLE_Error, null, contentValues);
        }
    }


    public boolean clear(){
       return deleteAll(TABLE_Error);
    }

    public List<String> getAllIndex(Context context) {

        List<String> launchAppInfos = new ArrayList<>();


        if (isExistRows(TABLE_Error) > 0) {

            Cursor cursor = fetchAllvalues(TABLE_Error);
            if (cursor != null && cursor.moveToFirst()) {

                try {
                    do {
                        String error = cursor.getString(cursor.getColumnIndex(ColumnError.error));

                        launchAppInfos.add(error);
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
