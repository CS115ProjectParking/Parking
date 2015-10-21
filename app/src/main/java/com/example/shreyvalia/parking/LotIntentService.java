package com.example.shreyvalia.parking;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class LotIntentService extends IntentService {


    public LotIntentService() {
        super("LotIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int lot_number = intent.getIntExtra("lot", 0);
            //TODO: get data from server instead of mock data from lots.xml
            //but for now
            Intent statusIntent = new Intent("BROADCAST_LOTDATA");
            //put capacity and population in intent extras
            Resources res = getApplicationContext().getResources();
            int cap = 0, pop = 0;

            //get data corresponding to lot number
            switch (lot_number) {
                case 1:
                    cap = res.getInteger(R.integer.corewest_cap);
                    pop = res.getInteger(R.integer.corewest_pop);
                    break;
                case 2:
                    cap = res.getInteger(R.integer.northremote_cap);
                    pop = res.getInteger(R.integer.northremote_pop);
                    break;
                case 3:
                    cap = res.getInteger(R.integer.eastremote_cap);
                    pop = res.getInteger(R.integer.eastremote_pop);
                    break;
                case 4:
                    cap = res.getInteger(R.integer.c10_cap);
                    pop = res.getInteger(R.integer.c10_pop);
                    break;
                case 5:
                    cap = res.getInteger(R.integer.crown_cap);
                    pop = res.getInteger(R.integer.crown_pop);
                    break;
                case 6:
                    cap = res.getInteger(R.integer.healthcenter_cap);
                    pop = res.getInteger(R.integer.healthcenter_pop);
                    break;
            }
            statusIntent.putExtra("cap",
                    cap);
            statusIntent.putExtra("pop",
                    pop);
            //broadcast data so it can be read from the activity that created the intent
            LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);
        }
    }

}
