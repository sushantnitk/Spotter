package com.findmybus.spotter.Spotter;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
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
import com.findmybus.spotter.Utilities.OnOptionSelectedListener;

/**
 * Created by sushantkumar on 19/4/16.
 */
public class SpotterMain extends Activity implements FetchFromServerUser {

    TextView uproute;
    TextView downroute;
    TextView busRoute;
    Button share;
    RadioButton up;
    RadioButton down;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buses_list);
        uproute = (TextView)findViewById(R.id.UpRoute);
        downroute = (TextView)findViewById(R.id.DownRoute);
        up =(RadioButton)findViewById(R.id.Up);
        down = (RadioButton)findViewById(R.id.down);
        share = (Button)findViewById(R.id.Share);
        busRoute = (TextView)findViewById(R.id.busnumber);
        String UpStop = getIntent().getStringExtra("Upstop");
        String DownStop = getIntent().getStringExtra("DownStop");
        final String bus = getIntent().getStringExtra("BuseInfo");
        busRoute.setText(bus);
        uproute.setText(UpStop);
        downroute.setText(DownStop);
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroups);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                switch (selectedId){
                    case(R.id.Up) :
                        String directionUp= "Up";
                        new FetchFromServerTask(SpotterMain.this,0).execute("http://" + Constants.SERVER_URL + "/" + "routes" +"/"+"spotping"+"/"+":id?"+ Constants.ID + directionUp + location.getLongitude() + location.getLatitude()+Constants.SPOTTERID + System.currentTimeMillis());


                    case(R.id.down) :
                        String directionDown="Down";
                        new FetchFromServerTask(SpotterMain.this,0).execute("http://" + Constants.SERVER_URL + "/" + "routes" +"/"+"spotping"+"/"+":id?"+ Constants.ID + directionDown + location.getLongitude() + location.getLatitude()+Constants.SPOTTERID + System.currentTimeMillis());


                }
            }
        });
    }


    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {

    }
}
