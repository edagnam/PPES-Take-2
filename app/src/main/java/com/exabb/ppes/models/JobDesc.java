package com.exabb.ppes.models;

public class JobDesc {

    private String id_job, nama_job, message;
    private boolean error;


    public JobDesc(String id_job, String nama_job, String message, boolean error) {
        this.id_job = id_job;
        this.nama_job = nama_job;
        this.message = message;
        this.error = error;
    }

    public String getId_job() {
        return id_job;
    }

    public String getNama_job() {
        return nama_job;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "JobDesc{" +
                        "id_job = '" + id_job + '\'' +
                        ",nama_job = '" + nama_job + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }


}

