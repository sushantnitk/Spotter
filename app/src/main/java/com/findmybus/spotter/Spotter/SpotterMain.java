package com.findmybus.spotter.Spotter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.findmybus.spotter.R;
import com.findmybus.spotter.Utilities.Constants;
import com.findmybus.spotter.Utilities.FetchFromServerTask;
import com.findmybus.spotter.Utilities.FetchFromServerUser;
import com.findmybus.spotter.Utilities.FindCurrentLocationTask;
import com.findmybus.spotter.Utilities.FindCurrentLocationUser;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sushantkumar on 19/4/16.
 */
public class SpotterMain extends AppCompatActivity implements FetchFromServerUser,FindCurrentLocationUser{

    TextView uproute;
    TextView downroute;
    TextView busRoute;
    Button share;
    RadioButton up;
    RadioButton down;
    RadioGroup radioGroup;
    String UpStop;
    String DownStop;
    String bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_infolist);
        uproute = (TextView)findViewById(R.id.UpRoute);
        downroute = (TextView)findViewById(R.id.DownRoute);
        up =(RadioButton)findViewById(R.id.Up);
        down = (RadioButton)findViewById(R.id.down);
        share = (Button)findViewById(R.id.PingLocation);
        busRoute = (TextView)findViewById(R.id.busnumber);
        UpStop = getIntent().getStringExtra("Upstop");
        DownStop = getIntent().getStringExtra("Downstop");
        bus = getIntent().getStringExtra("BusInfo");
        busRoute.setText(bus);
        uproute.setText(UpStop);
        downroute.setText(DownStop);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroups);
        new FindCurrentLocationTask(this, this).execute();
    }


    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        new FindCurrentLocationTask(this, this).execute();

                try {
                    Log.i("String Fetchd", string);
                    JSONObject obj = new JSONObject(string);
                    String segmentFound = obj.getString("SegmentFound");
                    if(segmentFound == "false"){
                        Toast.makeText(SpotterMain.this, "Segment not added,Please Move near to BusStops ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SpotterMain.this, "Congrats!!Segment added", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Log.e("MainActivity", e.toString());
                }
            }



    @Override
    public void onFindLocationCompletion(final Location l) {
        if(up.isChecked()){
            down.setChecked(false);
        }
        else if(down.isChecked()){
            up.setChecked(false);
        }
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                switch (selectedId) {
                    case (R.id.Up):
                        String directionUp = "1";
                        new FetchFromServerTask(SpotterMain.this, 0).execute("http://" + Constants.SERVER_URL + "/" + "routes" + "/" + "spotping" + "/" + bus + "?" + "direction=" + directionUp + l.getLongitude() + l.getLatitude() + Constants.SPOTTERID + System.currentTimeMillis());


                    case (R.id.down):
                        String directionDown = "2";
                        Log.d("SpotterMAin", "http://" + Constants.SERVER_URL + "/" + "routes" + "/" + "spotping" + "/" + bus + "?" + "direction=" + directionDown + "&latitude=" + l.getLatitude() + "&longitude=" + l.getLongitude() + "&spotterid=" + Constants.SPOTTERID + "&utime=" + System.currentTimeMillis());
                        new FetchFromServerTask(SpotterMain.this, 0).execute("http://" + Constants.SERVER_URL + "/" + "routes" + "/" + "spotping" + "/" + bus + "?" + "direction=" + directionDown + "&latitude=" + l.getLatitude() + "&longitude=" + l.getLongitude() + "&spotterid=" + Constants.SPOTTERID + "&utime=" + System.currentTimeMillis());


                }
            }
        });
    }
}
