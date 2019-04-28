package com.exabb.ppes.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.exabb.ppes.R;
import com.exabb.ppes.models.User;
import com.exabb.ppes.storage.SharedPrefManager;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etId, etName;
    ProgressDialog loading;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        etId = findViewById(R.id.etNik3);
        etName = findViewById(R.id.etNama3);

        findViewById(R.id.btnKegiatan).setOnClickListener(this);
        findViewById(R.id.btnKeluar).setOnClickListener(this);

        User user = SharedPrefManager.getInstance(this).getUser();
        etId.setText("" + user.getNik());
        etName.setText("" + user.getUsername());
    }

    private void logout(){
        SharedPrefManager.getInstance(new DashboardActivity()).clear();
        Intent ilogout = new Intent(DashboardActivity.this, MainActivity.class);
        ilogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ilogout);
        finish();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnKegiatan:
                startActivity(new Intent(this, KegiatanActivity.class));
                break;
            case R.id.btnKeluar:

                logout();
                break;
        }
    }
}
