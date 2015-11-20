package com.example.shreyvalia.parking;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by shreyvalia on 11/18/15.
 */
public class FireBase {
    private static Firebase ourInstance = null;
//    public static int pop = 0;
//    public final int [] popTemp= {0};

    public static Firebase getInstance(Context a) {
        if(ourInstance == null){
            new FireBase(a);
        }

        return ourInstance;
    }

    private FireBase(Context a) {
        Firebase.setAndroidContext(a);
        ourInstance = new Firebase("https://torrid-heat-8415.firebaseio.com/");

    }
}
