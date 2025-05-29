package com.example.myproject;

import android.content.Context;
import android.content.SharedPreferences;

//import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "MySharedPreference";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;

    private static final String KEY_REMEMBERED_USERS = "remembered_users";

    private static SharedPrefManager ourInstance = null;
    private static SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static SharedPrefManager getInstance(Context context) {
        if (ourInstance != null) {
            return ourInstance;
        }
        ourInstance = new SharedPrefManager(context);
        return ourInstance;
    }

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean writeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Save user list
    public boolean saveUserList(List<User> userList) {
        Gson gson = new Gson();
        String json = gson.toJson(userList);
        editor.putString(KEY_REMEMBERED_USERS, json);
        return editor.commit();
    }

    // Load user list
    public List<User> getUserList() {
        String json = sharedPreferences.getString(KEY_REMEMBERED_USERS, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        return gson.fromJson(json, type);
    }

    // User class
    public static class User {
        public String email;
        public String password;
        public boolean remembered; // flag to save who click remember me

        public User(String email, String password, boolean remembered) {
            this.email = email;
            this.password = password;
            this.remembered = remembered;
        }
    }

}