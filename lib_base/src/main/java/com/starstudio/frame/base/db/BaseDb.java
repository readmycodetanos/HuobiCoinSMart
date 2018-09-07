package com.starstudio.frame.base.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chief on 2015-12-22.
 */
public  abstract class BaseDb extends SQLiteOpenHelper {

    /*
    *
    *  @Override
        public void onCreate(SQLiteDatabase db) {
            UtilsLog.i("onCreateDB");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_PARTIN.column_create());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion >= newVersion) return;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIN);
            onCreate(db);
        }
    *
    *
    * */




    protected SQLiteDatabase mDbWriter = null;


    public BaseDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name , factory, version);
    }


    public void openDB() {
        try {
            mDbWriter = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if (mDbWriter != null) {
                mDbWriter.close();
                SQLiteDatabase.releaseMemory();
            }
                close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Cursor fetchAllvalues(String tableName) {
        if (mDbWriter != null) {
            return mDbWriter.query(tableName, null, null, null, null, null, null);
        }
        return null;
    }


    protected int isExistRows(String tableName) {
        int result = 0;
        Cursor fetchAllValues = fetchAllvalues(tableName);
        if (fetchAllValues != null) {
            result = fetchAllValues.getCount();
            fetchAllValues.close();
        }
        return result;
    }





    protected boolean deleteAll(String tableName) {
        int id = -1;
        if (mDbWriter != null) {
            id = mDbWriter.delete(tableName, null, null);
        }
        if (id > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param table
     * @param contentValues
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return  true이면 성곡
     */
    protected boolean delete(String table,ContentValues contentValues, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        long id = -1;

        if (mDbWriter != null) {

            boolean result =false;
            Cursor query = mDbWriter.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            if (query != null) {
                if (query.getCount() > 0) {
                    result = true;
                }
                query.close();
            }

            if (result) {
                id = mDbWriter.delete(table, selection,selectionArgs);
            }
        }

        if (id < 1) {
            return false;
        } else {
            return true;
        }
    }




    /**
     *
     * @param table
     * @param contentValues
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return  true이면 성곡
     */
    protected boolean update(String table,ContentValues contentValues, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        long id = -1;

        if (mDbWriter != null) {

            boolean result =false;
            Cursor query = mDbWriter.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            if (query != null) {
                if (query.getCount() > 0) {
                    result = true;
                }
                query.close();
            }

            if (result) {
                id = mDbWriter.update(table, contentValues, selection,selectionArgs);
            } else {
                id = mDbWriter.insert(table, null, contentValues);
            }
        }

        if (id < 1) {
            return false;
        } else {
            return true;
        }
    }








    /*--------------------------------------------------------------------------------------------------------------------------------*/
    //ContentProvider





}
