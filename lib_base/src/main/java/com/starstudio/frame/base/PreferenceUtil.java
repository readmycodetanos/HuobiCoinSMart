package com.starstudio.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PreferenceUtil {
    private final String   TAG = PreferenceUtil.class.getSimpleName();


    private static PreferenceUtil preferenceUtil = null;


    private SharedPreferences sp = null;
    private SharedPreferences.Editor edit = null;

    public PreferenceUtil(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    public PreferenceUtil(Context context, String filename) {
        this(context, context.getSharedPreferences(filename, Context.MODE_PRIVATE));
    }


    public static PreferenceUtil getInstance(Context context) {

        if (preferenceUtil == null ) {
            synchronized (PreferenceUtil.class) {
                if (preferenceUtil == null) {
                    preferenceUtil = new PreferenceUtil(context);
                }
            }
        }
        return preferenceUtil;
    }

    public PreferenceUtil(Context context, SharedPreferences sp) {

        if (context instanceof Activity) {
            throw new UnsupportedOperationException("UtilsSP not allowed use activity instance of context!!!!");
        }

        this.sp = sp;
        edit = sp.edit();
    }


    public synchronized void setValue(String key, boolean value) {
        edit.putBoolean(key, value);
        edit.commit();
    }


    // Float
    public synchronized void setValue(String key, float value) {
        edit.putFloat(key, value);
        edit.commit();
    }



    // Long
    public synchronized void setValue(String key, long value) {
        edit.putLong(key, value);
        edit.commit();
    }
    public synchronized void setValue(String key, int value) {
        edit.putInt(key, value);
        edit.commit();
    }


    // String
    public synchronized void setValue(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }


    public synchronized void setValue(String key, Set<String> value) {
        edit.putStringSet(key, value);
        edit.commit();
    }


    public Set<String> getValue(String key, Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }



    public synchronized void setValue(String key, ArrayList<String> value) {
        Set<String> strings = new HashSet<>();
        int i = 0;
        for (String str : value) {
            strings.add((i++) + "=>" + str);
        }
        edit.putStringSet(key, strings);
        edit.commit();
    }


    public ArrayList<String> getValue(String key, ArrayList<String> defaultValue) {

        Set<String> stringSet = sp.getStringSet(key, new HashSet<String>());
        Iterator<String> iterator = stringSet.iterator();

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String[] split = next.split("=>");
            if (split != null) {
                if (split.length > 1) {
                    stringStringHashMap.put(split[0], next.substring(next.indexOf(split[1])));
                } else {
                    stringStringHashMap.put(split[0], "");
                }
            }
        }

        for (int i = 0; i < stringStringHashMap.size(); i++) {
            result.add(stringStringHashMap.get(i + "").toString());
        }

        if (result.size() == 0) {
            return defaultValue;
        }

        return result;
    }


    // Get

    // Boolean
    public boolean getValue(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }



    // Float
    public float getValue(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }



    // Integer
    public int getValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }



    // Long
    public long getValue(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }


    // String
    public String getValue(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }


    // Delete
    public synchronized void remove(String key) {
        edit.remove(key);
        edit.commit();
    }

    public synchronized void clear() {
        edit.clear();
        edit.commit();
    }


}
