package com.exabb.ppes.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.exabb.ppes.models.JobDesc;
import com.exabb.ppes.models.Project;
import com.exabb.ppes.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id_karyawan", user.getId_karyawan());
        editor.putString("username", user.getUsername());
        editor.putString("level", user.getLevel());
        editor.putString("nik", user.getNik());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id_karyawan", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id_karyawan", -1),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("level", null),
                sharedPreferences.getString("nik", null)
        );
    }

    public JobDesc getJob() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new JobDesc(
                sharedPreferences.getInt("id_job", -1),
                sharedPreferences.getString("nama_job", null)
        );

    }

    public Project getProject() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Project(

                sharedPreferences.getInt("project_number", -1),
                sharedPreferences.getString("project_name", null),
                sharedPreferences.getString("customer", null)
        );
    }


    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
