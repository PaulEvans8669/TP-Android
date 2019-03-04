package com.adrien32cm.myfirstapplication27;

import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Contact implements Serializable {
    public transient static String filesDir;

    private String nom;
    private String prenom;
    private String dateNaiss;
    private String tel;
    private String mail;
    private String adresse;
    private String zipcode;
    private boolean isMale;
    private String picture;

    public Contact(String nom, String prenom, String dateNaiss, String tel, String mail, String adresse, String zipcode, boolean isMale, Uri picture) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.tel = tel;
        this.mail = mail;
        this.adresse = adresse;
        this.zipcode = zipcode;
        this.isMale = isMale;

        if(picture != null)
            this.picture = picture.toString();
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Uri getPicture() {
        if(picture != null)
            return Uri.parse(picture);
        return null;
    }

    public void setPicture(Uri picture) {
        if(picture != null)
            this.picture = picture.toString();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaiss='" + dateNaiss + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                ", adresse='" + adresse + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", isMale=" + isMale +
                ", picture=" + picture +
                '}';
    }
}
