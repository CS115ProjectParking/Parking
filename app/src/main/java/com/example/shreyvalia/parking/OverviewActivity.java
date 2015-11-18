package com.example.shreyvalia.parking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class OverviewActivity extends ActionBarActivity {

    private int num_lots = 6;
    private String lot_names[] = {"Core West", "North Remote", "East Remote", "College 10", "Crown", "Health Center"};
    private ParkingLot lots[] = new ParkingLot[num_lots];
    private Timer updater;
    private final int refresh_duration = 15;
    int popu[] = new int[num_lots]; //population for the the number of the lot. instance here and pass to lot intent service.

    //TODO: checkout the keepSynced method

    private class ParkingLot {
        int id;
        TextView name, space;
        ProgressBar progress;
    }

    private class LotReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            int capacity = intent.getIntExtra("cap", 0);
            int population = intent.getIntExtra("pop", 0);
            int id = intent.getIntExtra("id", 0);
            TextView capacity_text = lots[id].space;
            capacity_text.setText(population + "/" + capacity);
            ProgressBar progress = lots[id].progress;
            progress.setProgress(100 * population / capacity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        setTitle("Overview");

        LinearLayout lots_layout = (LinearLayout) findViewById(R.id.lots_layout);

        for (int i = 0; i < num_lots; i++) {
            ParkingLot new_lot = new ParkingLot();
            new_lot.id = i;

            LinearLayout lot_layout = new LinearLayout(this);
            lot_layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout text_layout = new LinearLayout(this);
            text_layout.setOrientation(LinearLayout.HORIZONTAL);
            text_layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView name = new TextView(this);
            name.setText(lot_names[i]);
            text_layout.addView(name);
            new_lot.name = name;


            TextView space = new TextView(this);
            space.setText("n/a");
            space.setGravity(Gravity.RIGHT);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            text_layout.addView(space);
            new_lot.space = space;

            lot_layout.addView(text_layout);

            ProgressBar progress = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            progress.setProgress(0);
            progress.setScaleY(3f);
            lot_layout.addView(progress);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
            progress.setLayoutParams(layoutParams);
            new_lot.progress = progress;

            final Intent intent = new Intent(OverviewActivity.this, ParkActivity.class);
            intent.putExtra("lot_id", i);
            lot_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });

            lots_layout.addView(lot_layout);
            lots[i] = new_lot;
        }

        IntentFilter intentFilter = new IntentFilter("BROADCAST_LOTDATA");
        LocalBroadcastManager.getInstance(this).registerReceiver(new LotReceiver(), intentFilter);

        refresh_all();
    }

    private void refresh_all() {
        for (int i = 0; i < num_lots; i++) {
            refresh_lot(i);
        }
    }

    private void refresh_lot(int lot_number) {
        //deliver intent to lot service
        Intent serviceIntent = new Intent(getApplicationContext(), LotIntentService.class);
        serviceIntent.putExtra("lot", lot_number);
//        serviceIntent.putExtra("population", popu[lotnumber]);
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updater = new Timer();
        updater.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refresh_all();
                    }
                });
            }
        }, 0, refresh_duration * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        updater.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
