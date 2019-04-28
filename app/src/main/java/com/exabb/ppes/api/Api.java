package com.exabb.ppes.api;

import com.exabb.ppes.models.DefaultResponse;
import com.exabb.ppes.models.JobDesc;
import com.exabb.ppes.models.JobResponse;
import com.exabb.ppes.models.LoginResponse;
import com.exabb.ppes.models.Project;
import com.exabb.ppes.models.ProjectResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    String JSONURL = "http://192.168.43.36/api-client-v2/";


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("kegiatan")
    Call<DefaultResponse> tambahKegiatan(
            @Field("tgl") String tgl,
            @Field("waktu_mulai") String waktu_mulai,
            @Field("waktu_selesai") String waktu_selesai,
            @Field("status") String status,
            @Field("id_job") String id_job,
            @Field("project_number") String project_number,
            @Field("id_karyawan") String id_karyawan
    );


    @GET("semuajob")
    Call<JobResponse> getJob(
    );

    @GET("job/{nama_job}")
    Call<JobDesc> getDatilJob(@Path("nama_job") String nama_job
    );


    @GET("semuaproject")
    Call<ProjectResponse> getProject(
    );

    @GET("project/{project_number}")
    Call<Project> getDetailProject(@Path("project_number") String project_number
    );
}
