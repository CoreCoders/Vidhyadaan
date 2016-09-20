package com.unbeatable.vidhyadaan.firebasemodle;

import android.support.annotation.NonNull;

import com.google.android.gms.nearby.messages.internal.RegisterStatusCallbackRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.extra.Log;
import com.unbeatable.vidhyadaan.extra.Util;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by rutvik on 05-08-2016 at 03:04 PM.
 */

public class User {

    public static final String TAG = AppUtil.APP_TAG + User.class.getSimpleName();

    String user_id, name, password, standard;

    public User() {

    }

    public User(String user_id, String password, String name, String standard) {
        this.user_id = user_id;
        this.password = Util.MD5(password);
        this.name = name;
        this.standard = standard;
    }

    public static void register(final DatabaseReference dbRef, final String userId, final String name, final String password, final String standard, final UserRegistrationCallback userRegistrationCallback) {

        Log.i(TAG, "INSIDE REGISTER FUNCTION ");

        dbRef.getRoot();
        checkIfUserExist(dbRef, userId, new UserExistCheckCallback() {
            @Override
            public void onCheckComplete(DataSnapshot dataSnapshot, int existStatus) {
                Log.i(TAG, "INSIDE ON CHECK COMPLETE LISTENER FOR CHECK IF USER EXIST");
                if (existStatus == UserExistCheckCallback.NOT_EXIST) {
                    Log.i(TAG, "REGISTERING USER");
                    //REGISTER USER
                    dbRef.getRoot();
                    Map<String, String> userDataMap = new HashMap<>();
                    userDataMap.put("name", name);
                    userDataMap.put("password", Util.MD5(password));
                    userDataMap.put("standard", standard);
                    Map<String, Object> newUser = new HashMap();
                    newUser.put("/user/" + userId + "/", userDataMap);
                    dbRef.updateChildren(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i(TAG, "ON COMPLETION OF REGESTERING USER");
                            if (task.isSuccessful()) {
                                Log.i(TAG, "SUCCESSFULLY REGISTERED");
                                userRegistrationCallback.onRegistrationComplete(UserRegistrationCallback.REGISTERED);
                            } else {
                                Log.i(TAG, "FAILED TO REGISTER");
                                userRegistrationCallback.onRegistrationComplete(UserRegistrationCallback.FAILED);
                            }
                        }
                    });
                } else if (existStatus == UserExistCheckCallback.EXIST) {
                    Log.i(TAG, "DUPLICATE USER");
                    userRegistrationCallback.onRegistrationComplete(UserRegistrationCallback.DUPLICATE);
                }
            }
        });
    }

    public static void tryLogin(final DatabaseReference dbRef, final String userId, final String password, final LoginResponseCallback callback) {

        checkIfUserExist(dbRef, userId, new UserExistCheckCallback() {
            @Override
            public void onCheckComplete(DataSnapshot dataSnapshot, int existStatus) {
                if (existStatus == UserExistCheckCallback.EXIST) {
                    final Map map = (Map) dataSnapshot.getValue();
                    if (map != null && map.size() != 0) {
                        final String pwd = map.get("password").toString();
                        if (Util.MD5(password).equals(pwd)) {
                            callback.onLoginResult(dataSnapshot, LoginResponseCallback.SUCCESS);
                        } else {
                            callback.onLoginResult(dataSnapshot, LoginResponseCallback.FAILED);
                        }
                    } else {
                        callback.onLoginResult(dataSnapshot, LoginResponseCallback.FAILED);
                    }
                } else {
                    callback.onLoginResult(dataSnapshot, LoginResponseCallback.FAILED);
                }
            }
        });

    }

    private static void checkIfUserExist(DatabaseReference dbRef, final String userId, final UserExistCheckCallback callback) {
        Log.i(TAG, "INSIDE CHECK IF USER EXIST FUNCTION ");
        dbRef.getRoot();
        dbRef.child("user").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "ON DATA CHANGED LISTENER FROM CHECK IF USER EXIST FUNCTION");
                if (dataSnapshot.exists()) {
                    Log.i(TAG, "USER EXIST");
                    callback.onCheckComplete(dataSnapshot, UserExistCheckCallback.EXIST);
                } else {
                    Log.i(TAG, "USER NOT EXIST");
                    callback.onCheckComplete(dataSnapshot, UserExistCheckCallback.NOT_EXIST);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface UserExistCheckCallback {
        int EXIST = 1;
        int NOT_EXIST = 0;

        void onCheckComplete(DataSnapshot dataSnapshot, int existStatus);
    }

    public interface UserRegistrationCallback {
        int REGISTERED = 1;
        int FAILED = 0;
        int DUPLICATE = -1;

        void onRegistrationComplete(int registrationStatus);
    }

    public interface LoginResponseCallback {
        int SUCCESS = 1;
        int FAILED = 0;

        void onLoginResult(DataSnapshot dataSnapshot, int loginResult);
    }

}
