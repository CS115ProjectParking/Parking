package com.example.shreyvalia.parking;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class OverviewActivity extends ActionBarActivity {

    int num_lots = 6;
    String lot_names[] = {"Core West", "North Remote", "East Remote", "College 10", "Crown", "Health Center"};

    private class ParkingLot {
        int id, capacity = 0, population = 0;
        TextView name, space;
        ProgressBar progress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        LinearLayout lots_layout = (LinearLayout) findViewById(R.id.lots_layout);

        for (int i = 0; i < num_lots; i++) {
            LinearLayout lot_layout = new LinearLayout(this);
            lot_layout.setOrientation(LinearLayout.HORIZONTAL);
            
        }

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
