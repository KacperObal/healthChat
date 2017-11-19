package com.kacper.healthchat.utli;

import android.support.annotation.NonNull;

/**
 * Created by Kacper on 19.11.2017.
 */

public interface ResultCallback<T> {
    void onSuccess(@NonNull T result);

    void onFailure(Exception exception);
}