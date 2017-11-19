package com.kacper.healthchat.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kacper.healthchat.R;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kacper on 19.11.2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> implements View.OnClickListener {

    private List<Message> dataSet;
    Context mContext;

    static class ViewHolder {
        @BindView(R.id.messageMessage)
        public TextView messageMessage;
        @BindView(R.id.messageSenderName)
        public TextView messageSenderName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MessageAdapter(List<Message> data, Context context) {
        super(context, R.layout.row_message_item, data);

        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Message message = (Message) object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        MessageAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_message_item, parent, false);
            viewHolder = new MessageAdapter.ViewHolder(convertView);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MessageAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.messageSenderName.setText(message.getSenderName());
        viewHolder.messageMessage.setText(message.getMessage());

        return convertView;
    }
}