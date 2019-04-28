package com.exabb.ppes.models;

public class User {
    private int id_karyawan;
    private String username, level, nik;

    public User(int id_karyawan, String username, String level, String nik) {
        this.id_karyawan = id_karyawan;
        this.username = username;
        this.level = level;
        this.nik = nik;
    }

    public int getId_karyawan() {
        return id_karyawan;
    }

    public String getUsername() {
        return username;
    }

    public String getLevel() {
        return level;
    }

    public String getNik() {
        return nik;
    }
}
