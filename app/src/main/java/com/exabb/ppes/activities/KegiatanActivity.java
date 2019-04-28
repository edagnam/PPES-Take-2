package com.exabb.ppes.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.exabb.ppes.R;
import com.exabb.ppes.api.RetrofitClient;
import com.exabb.ppes.models.DefaultResponse;
import com.exabb.ppes.models.JobDesc;
import com.exabb.ppes.models.JobResponse;
import com.exabb.ppes.models.Project;
import com.exabb.ppes.models.ProjectResponse;
import com.exabb.ppes.models.User;
import com.exabb.ppes.storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KegiatanActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Deklarasi button
    private Button start4 ,stop4;

    //Deklarasi editText
    private EditText etId, etNik, etTgl, etStart, etStop;

    //Deklarasi spinner status
    private static final String TAG = KegiatanActivity.class.getSimpleName();
    private String txtSpinStatus;

    Spinner spinnerJob, spinnerProject;
    Context mContext;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan);

        mContext = this;

        initSpinnerJob();
        spinnerJob = findViewById(R.id.spinJob4);
        spinnerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJOB = parent.getItemAtPosition(position).toString();
                requestJOB(selectedJOB);
                Toast.makeText(mContext, "Anda memilih job " + selectedJOB, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        iniSpinnerProject();
        spinnerProject = findViewById(R.id.spinProject4);
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPROJECT = parent.getItemAtPosition(position).toString();
                requestPROJECT(selectedPROJECT);
                Toast.makeText(mContext, "Anda memilih job " + selectedPROJECT, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Inisialisasi variabel yang terdapat pada layout activity_kegiatan.xml
        etId = findViewById(R.id.etId4);
        etNik = findViewById(R.id.etNik4);
        etTgl = findViewById(R.id.etTgl4);
        etStart = findViewById(R.id.etStart4);
        etStop = findViewById(R.id.etStop4);



        //Spinner Status
        Spinner spinnerstatus = (Spinner) findViewById(R.id.spinStatus4);
        if (spinnerstatus != null){
            spinnerstatus.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        if (spinnerstatus != null){
            spinnerstatus.setAdapter(adapter);
        }

        findViewById(R.id.btnKegiatan2).setOnClickListener(this);


        //Mengambil dan menampilkan data tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String hariini = ("" + format.format(calendar.getTime()));
        etTgl.setText(hariini);

        //Menampilkan data karyawan yaitu ID, NIK, dan Nama Karyawan dari database
        User user = SharedPrefManager.getInstance(this).getUser();
        etId.setText("" + user.getId_karyawan());
        etNik.setText("" + user.getNik());

        //Mengambil dan menampilkan data waktu saat menekan tombol start saat mulai bekerja
        start4 = findViewById(R.id.btn_start_time);
        start4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance();
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                String waktumulai = "" + format2.format(calendar2.getTime());

                etStart = findViewById(R.id.etStart4);
                etStart.setText(waktumulai);

            }
        });

        //Mengambil dam menampilkan data waktu saat menekan tombol stop saat berhenti bekerja
        stop4 = findViewById(R.id.btn_stop_time);
        stop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar3 = Calendar.getInstance();
                SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");
                String waktuberhenti = "" + format3.format(calendar3.getTime());

                etStop = findViewById(R.id.etStop4);
                etStop.setText(waktuberhenti);
            }
        });


    }


    private void initSpinnerJob(){
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        Call<JobResponse> call = RetrofitClient.getInstance().getApi().getJob();
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    List<JobDesc> semuajob = response.body().getJobdesc();
                    List<String> listjob = new ArrayList<String>();
                    for (int i = 0; i < semuajob.size(); i++){
                        listjob.add(semuajob.get(i).getNama_job());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listjob);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerJob.setAdapter(adapter);
                }

                else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext,"Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniSpinnerProject(){
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        Call<ProjectResponse> call = RetrofitClient.getInstance().getApi().getProject();
        call.enqueue(new Callback<ProjectResponse>() {
            @Override
            public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    List<Project> semuaproject = response.body().getProject();
                    List<String> listproject = new ArrayList<String>();
                    for (int i = 0; i < semuaproject.size(); i++){
                        listproject.add(semuaproject.get(i).getProject_number());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listproject);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProject.setAdapter(adapter);
                }

                else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data project", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext,"Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestJOB(String nama_job){

        Call<JobDesc> call = RetrofitClient.getInstance().getApi().getDatilJob(nama_job);
        call.enqueue(new Callback<JobDesc>() {
            @Override
            public void onResponse(Call<JobDesc> call, Response<JobDesc> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext," Gagal mengambil data detail job", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDesc> call, Throwable t) {
                Toast.makeText(mContext,"Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPROJECT(String project_number){

        Call<Project> call = RetrofitClient.getInstance().getApi().getDetailProject(project_number);
        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext," Gagal mengambil data detail project", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Toast.makeText(mContext,"Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


        //Metode agar user tetap login pada aplikasi
        @Override
        protected void onStart () {
            super.onStart();

            if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }


        //Fungsi menyimpan data kegiatan
        private void simpanKegiatan () {


        }

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.btnKegiatan2:
                    simpanKegiatan();
                    break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        txtSpinStatus = String.valueOf(adapterView.getItemIdAtPosition(pos));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.i(TAG, getString(R.string.nothing_selected));

    }
}
