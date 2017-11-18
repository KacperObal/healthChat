package com.kacper.healthchat.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 
 * Created by Kacper on 18.11.2017.
 *
 */

public class MyTask extends AsyncTask<Void,Void,Void> {

    private Context mContext;
    private ProgressDialog pDialog;

    public MyTask(Context context){
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        pDialog = ProgressDialog.show(mContext, "Wait...", "sending data ...", true);
        pDialog.setCancelable(false);
        super.onPreExecute();
    }
}