package com.kacper.healthchat.view;

import com.kacper.healthchat.model.Doctor;


import java.util.List;

/**
 * Created by Kacper on 18.11.2017.
 */

public interface DoctorListView {
    void onSelectedDoctor(Doctor doctor);
    void displayDoctorsList(List<Doctor> doctorList);
    void showLoadingDoctors();
    void showNoDoctorAvailable();
}
