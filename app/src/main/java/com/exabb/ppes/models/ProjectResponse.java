package com.exabb.ppes.models;

import java.util.List;

public class ProjectResponse {

    private boolean error;
    private List<Project> semuaproject;
    private String message;

    public ProjectResponse(boolean error, List<Project> semuaproject, String message){
        this.error = error;
        this.semuaproject = semuaproject;
        this.message = message;
    }

    public boolean isError(){
        return error;
    }

    public List<Project> getProject(){
        return semuaproject;
    }

    @Override
    public String toString(){
        return
                "ProjectResponse{" +
                        "semuaproject = '" + semuaproject + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
