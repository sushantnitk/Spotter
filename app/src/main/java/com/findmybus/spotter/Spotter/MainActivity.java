package com.findmybus.spotter.Spotter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.findmybus.spotter.R;

import java.util.ArrayList;
import java.util.List;

import com.findmybus.spotter.Utilities.Constants;
import com.findmybus.spotter.Utilities.FetchFromServerTask;
import com.findmybus.spotter.Utilities.FetchFromServerUser;
import com.findmybus.spotter.Utilities.FindCurrentLocationUser;
import com.findmybus.spotter.Utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FetchFromServerUser,FindCurrentLocationUser {

    List<SpotterBean> busInfo;
    GridView busInfoView;
    WifiManager wifiManager;
    LocationManager locationManager;
    ProgressDialog dialog;
    ErrorFragment errorFragment;
    BusAdapter adapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Utils.createUniqId(getApplicationContext());
        setContentView(R.layout.activity_main);
        busInfoView=(GridView)findViewById(R.id.gridview);
        busInfoView.setAdapter(new BusAdapter(this,busInfo));

    }
    @Override
    public void onFetchCompletion(String string, int id) {
        if(id == 1){
            if(string!=null){
                Toast.makeText(this,"Bus List added Successfully",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"An error Occured,Please Try Again",Toast.LENGTH_SHORT).show();
            }
        }
        if(dialog != null){
            dialog.dismiss();
            if(errorFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(errorFragment).commit();
            }
        }
        if(id == 0){
            if(string == null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                errorFragment = new ErrorFragment();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "No or poor internet connection.");
                errorFragment.setArguments(bundle);
                transaction.replace(R.id.container, errorFragment).commit();
            }
            else{
                JSONObject array;
                try{
                    array = new JSONObject(string);
                    busInfo=new ArrayList<>();
                    String route= array.getString("Route");
                    JSONObject stops= new JSONObject("Stops");
                    String UpStops = stops.getString("UpStop");
                    String DownStops = stops.getString("DownStop");
                    SpotterBean bean = new SpotterBean();
                    bean.setRoute(route);
                    bean.setUpStop(UpStops);
                    bean.setDownStop(DownStops);
                    busInfo.add(bean);
                    adapter=new BusAdapter(this,busInfo);
                    busInfoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(context,SpotterMain.class);
                            intent.putExtra("BuseInfo",busInfo.get(position).getRoute());
                            intent.putExtra("Upstop",busInfo.get(position).getUpStop());
                            intent.putExtra("DownStop",busInfo.get(position).getDownStop());
                        }
                    });
                    busInfoView.setAdapter(adapter);
                }catch (JSONException e) {
                    Log.e("MainActivity", e.toString());
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    errorFragment = new ErrorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "Server Error");
                    errorFragment.setArguments(bundle);
                    transaction.replace(R.id.container, errorFragment).commit();
                }
            }
        }
    }

    @Override
    public void onPreFetch() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Fetching Data");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onFindLocationCompletion(Location l) {
        new FetchFromServerTask(this, 0).execute("http://" + Constants.SERVER_URL + "/" + "spotterroutes?"+l.getLatitude()+l.getLongitude()+"&spotterid="+Constants.SPOTTERID);
    }
}
