package com.example.shreyvalia.parking;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class LotIntentService extends IntentService {

    Firebase myFB;

    public LotIntentService() {
        super("LotIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Firebase.setAndroidContext(this);
        myFB = new Firebase("https://torrid-heat-8415.firebaseio.com/");

        if (intent != null) {
            int lot_number = intent.getIntExtra("lot", 0);
            //TODO: get data from server instead of mock data from lots.xml
            //but for now
            Intent statusIntent = new Intent("BROADCAST_LOTDATA");
            //put capacity and population in intent extras
            Resources res = getApplicationContext().getResources();
            int cap = 0, pop = 0;

            final int [] popTemp = {0};
            //get data corresponding to lot number
            switch (lot_number) {
                case 0:
                    myFB.child("Count").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String a = snapshot.getValue().toString();
                            
                            String b = a.substring(0).toString();
                            popTemp[0] = Integer.parseInt(b);

                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                        }

                    });

                    cap = res.getInteger(R.integer.corewest_cap);
                    pop = popTemp[0];
                    break;
                case 1:
                    cap = res.getInteger(R.integer.northremote_cap);
                    pop = res.getInteger(R.integer.northremote_pop);
                    break;
                case 2:
                    cap = res.getInteger(R.integer.eastremote_cap);
                    pop = res.getInteger(R.integer.eastremote_pop);
                    break;
                case 3:
                    cap = res.getInteger(R.integer.c10_cap);
                    pop = res.getInteger(R.integer.c10_pop);
                    break;
                case 4:
                    cap = res.getInteger(R.integer.crown_cap);
                    pop= res.getInteger(R.integer.crown_pop);
                    break;
                case 5:
                    cap = res.getInteger(R.integer.healthcenter_cap);
                    pop = res.getInteger(R.integer.healthcenter_pop);
                    break;
            }
            statusIntent.putExtra("cap",
                    cap);
            statusIntent.putExtra("pop",
                    pop);
            statusIntent.putExtra("id",
                    lot_number);
            //broadcast data so it can be read from the activity that created the intent
            LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);
        }
    }

//    private int stringToInt(String a){
//        Log.e("Main Change Method: '", a);
////        ": 0202"
//        String b  = new String();
//        for(int i = 2; i < a.length(); i++)
//        {
////            Log.e("a value i is : ", a.valueOf(i));
//            b.concat(String.valueOf(a.charAt(i)));
//        }
//        Log.e("Main Change b is: '", b);
//        return 0;
//
//
//    }

}
