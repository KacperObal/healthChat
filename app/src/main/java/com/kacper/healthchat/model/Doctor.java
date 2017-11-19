package com.kacper.healthchat.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Kacper on 18.11.2017.
 */

public class Doctor extends User implements Parcelable{
    private String speciality;

    public Doctor(String id, String username, String email , String speciality){
        super(id, username,email,"doctor");
        this.speciality = speciality;
    }

    public Doctor() {
        super();
    }

    public Doctor(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        super.setUsername(data[0]);
        super.setEmail(data[1]);
        this.speciality = data[2];
        super.setId(data[3]);
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {

            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {super.getUsername(), super.getEmail(), speciality, super.getId()});
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "speciality='" + speciality + '\'' +
                '}';
    }
}

