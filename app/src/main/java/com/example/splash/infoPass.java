package com.example.splash;

import java.io.Serializable;

public class infoPass implements Serializable
{
    private int id_contra;
    private String contra;
    private String red;
    private int id_red;

    public int getId_contra() {
        return id_contra;
    }

    public void setId_contra(int id_contra) {
        this.id_contra = id_contra;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public int getId_red() {
        return id_red;
    }

    public void setId_red(int id_red) {
        this.id_red = id_red;
    }
}
