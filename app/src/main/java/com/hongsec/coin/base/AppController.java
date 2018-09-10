package com.hongsec.coin.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.hongsec.coin.BuildConfig;
import com.hongsec.coin.config.Cconfig;
import com.hongsec.coin.mvp.bean.DaoMaster;
import com.hongsec.coin.mvp.bean.DaoSession;
import com.starstudio.frame.base.BaseApplication;
import com.starstudio.frame.net.OkGo;

import org.greenrobot.greendao.database.Database;


/**
 * Created by Hongsec on 2016-09-05.
 */
public class AppController extends BaseApplication {




    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }


    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        if(daoSession==null){

        }
        return daoSession;
    }

    @Override
    public void onCreate() {

//        Base.initialize(getApplicationContext());
        super.onCreate();

        
            Cconfig.getInstance().init(getApplicationContext());



        //net work init
        OkGo.getInstance().init(this);


        initGreenDao();


    }

    private void initGreenDao() {
        if(BuildConfig.DEBUG){
            DaoMaster.DevOpenHelper devOpenHelper =new DaoMaster.DevOpenHelper(this,"coinrobot-db");
            Database writableDb = devOpenHelper.getWritableDb();
            daoSession =new DaoMaster(writableDb).newSession();
        }else{
            DaoAppOpenHelper devOpenHelper =new DaoAppOpenHelper(this,"memorycamera-db");
            Database writableDb = devOpenHelper.getWritableDb();
            daoSession =new DaoMaster(writableDb).newSession();
        }
    }

    static class DaoAppOpenHelper extends DaoMaster.OpenHelper{

        public DaoAppOpenHelper(Context context, String name) {
            super(context, name);
        }
        public DaoAppOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }
    }



}
