package com.kacper.healthchat.view;

import com.kacper.healthchat.model.Message;

import java.util.List;

/**
 * Created by Kacper on 18.11.2017.
 */

public interface ChatView {
    void displayMessages(List<Message> messages);
}
