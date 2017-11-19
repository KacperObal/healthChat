package com.kacper.healthchat.service;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kacper.healthchat.model.Doctor;
import com.kacper.healthchat.model.Message;
import com.kacper.healthchat.model.User;
import com.kacper.healthchat.presenter.ChatPresenter;
import com.kacper.healthchat.utli.ResultCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Kacper on 18.11.2017.
 */

public class DatabaseService {
    private DatabaseReference mDatabase;

    public void onInit(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void onAuthSuccess(FirebaseUser user, String role, String speciality) {
        String username = usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail(), role, speciality);
    }

    public void getDoctorList(final ResultCallback<List<Doctor>> callback){

        final List<Doctor> doctorsList = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                doctorsList.clear();
                for (DataSnapshot postSnapshot: snapshot.child("doctors").getChildren()) {
                    Doctor user = postSnapshot.getValue(Doctor.class);
                    doctorsList.add(user);
                }
                callback.onSuccess(doctorsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    public Single<List<Doctor>> loadDoctor(){
        return Single.create(new SingleOnSubscribe<List<Doctor>>() {
            @Override
            public void subscribe(final SingleEmitter<List<Doctor>> emiter) throws Exception {
                getDoctorList(new ResultCallback<List<Doctor>>() {
                    @Override
                    public void onSuccess(@NonNull List<Doctor> result) {
                        emiter.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        emiter.tryOnError(exception);
                    }
                });
            }
        });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email, String role, String speciality) {
        if(role == "doctor") {
            Doctor doctor = new Doctor(userId, name, email, speciality);
            mDatabase.child("doctors").child(userId).setValue(doctor);
        }
        else {
            User user = new User(userId, name, email, role);
            mDatabase.child("users").child(userId).setValue(user);
        }
    }

    public void getCurrentUserFromDB(final String uid, final ResultCallback<User> callback){

        final User[] currUser = {new User()};
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                currUser[0] = snapshot.child("users").child(uid).getValue(User.class);
                callback.onSuccess(currUser[0]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }

    public void saveMessage(Message message) {
        UUID uid = UUID.randomUUID();
        mDatabase.child("message").child(uid.toString()).setValue(message);
    }

    public Single<List<Message>> getMesseges(final String currId, final String doctorId){
            return Single.create(new SingleOnSubscribe<List<Message>>() {
                @Override
                public void subscribe(final SingleEmitter<List<Message>> emiter) throws Exception {
                    getAllMessagesByCurrentUserAndDoctor(currId,doctorId, new ResultCallback<List<Message>>() {
                        @Override
                        public void onSuccess(@NonNull List<Message> result) {
                            emiter.onSuccess(result);
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            emiter.tryOnError(exception);
                        }
                    });
                }
            });
    }

    public void getAllMessagesByCurrentUserAndDoctor(String userId, String doctorId, final ResultCallback<List<Message>> callback){

        final List<Message> messages = new ArrayList<>();

        Query query = mDatabase.child("message").orderByChild("sender_recipient").equalTo(userId+doctorId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Message user = postSnapshot.getValue(Message.class);
                    messages.add(user);
                }

                callback.onSuccess(messages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

}
