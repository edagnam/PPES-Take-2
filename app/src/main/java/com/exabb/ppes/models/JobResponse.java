package com.exabb.ppes.models;

import java.util.List;

public class JobResponse {

    private boolean error;
    private List<JobDesc> semuajob;
    private String message;

    public JobResponse(boolean error, List<JobDesc> semuajob, String message){
        this.error = error;
        this.semuajob = semuajob;
        this.message = message;
    }

    public boolean isError(){
        return error;
    }

    public List<JobDesc> getJobdesc(){
        return semuajob;
    }

    @Override
    public String toString(){
        return
                "JobResponse{" +
                        "semuajob = '" + semuajob + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
