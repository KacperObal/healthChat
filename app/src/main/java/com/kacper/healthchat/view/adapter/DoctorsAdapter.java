package com.kacper.healthchat.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kacper.healthchat.R;
import com.kacper.healthchat.model.Doctor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 19.11.2017.
 */

public class DoctorsAdapter extends ArrayAdapter<Doctor> implements View.OnClickListener {

    private List<Doctor> dataSet;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        @BindView(R.id.userName)
        public TextView txtUserName;
        @BindView(R.id.email)
        public TextView txtEmail;
        @BindView(R.id.specialization)
        public TextView txtSpecialization;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public DoctorsAdapter(List<Doctor> data, Context context) {
        super(context, R.layout.row_doctor_item, data);

        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Doctor doctor = (Doctor) object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_doctor_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.txtUserName.setText(doctor.getUsername());
        viewHolder.txtEmail.setText(doctor.getEmail());
        viewHolder.txtSpecialization.setText(doctor.getSpeciality());

        return convertView;
    }
}