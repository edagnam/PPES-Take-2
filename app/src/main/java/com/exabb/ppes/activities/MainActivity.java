package com.exabb.ppes.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.exabb.ppes.models.LoginResponse;
import com.exabb.ppes.R;
import com.exabb.ppes.api.RetrofitClient;
import com.exabb.ppes.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etusername2;
    private EditText etpassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername2 = findViewById(R.id.etNik);
        etpassword2 = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {

        String username = etusername2.getText().toString().trim();
        String password = etpassword2.getText().toString().trim();

        if (username.isEmpty()) {
            etusername2.setError("NIK harus diisi");
            etusername2.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            etpassword2.setError("Password harus diisi");
            etpassword2.requestFocus();
            return;
        }


        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {

                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    boolean doublebackonce = false;

    @Override
    public void onBackPressed(){
        if (doublebackonce){
            super.onBackPressed();
        }

        this.doublebackonce = true;
        Toast.makeText(this, "Tekan BACK sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublebackonce = false;
            }
        }, 2000);

    }


}
