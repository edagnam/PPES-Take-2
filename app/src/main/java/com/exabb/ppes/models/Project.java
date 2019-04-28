package com.exabb.ppes.models;

public class Project {

    private String project_number, project_name, customer, message;
    private boolean error;


    public Project(String project_number, String project_name, String customer, String message, boolean error) {
        this.project_number = project_number;
        this.project_name = project_name;
        this.customer = customer;
        this.message = message;
        this.error = error;
    }

    public String getProject_number() {
        return project_number;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getCustomer(){
        return customer;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "Project{" +
                        "project_number = '" + project_number + '\'' +
                        ",project_name = '" + project_name + '\'' +
                        ",customer = '" + customer + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

}

