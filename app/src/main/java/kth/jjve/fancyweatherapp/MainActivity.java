package kth.jjve.fancyweatherapp;
/*
Jitse van Esch
Assignment 1, Android Studio
Mobile Sports Applications and Data Mining
Master Sports Technology 2021
KTH- Royal Institute of Technology
Fancy weather app
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kth.jjve.fancyweatherapp.datahandling.Weather;
import kth.jjve.fancyweatherapp.datahandling.WeatherList;
import kth.jjve.fancyweatherapp.networking.Downloader;
import kth.jjve.fancyweatherapp.viewing.WeatherItem;
import kth.jjve.fancyweatherapp.viewing.WeatherItemAdapter;

public class MainActivity extends AppCompatActivity {
    // Datahandling variables
    private String mCityName;
    private List<Weather> weatherList;

    // Viewing variables
    private AutoCompleteTextView inputCityName;
    private TextView textViewUpdatedTime;
    private TextView textViewConnection;
    private RecyclerView recyclerView;
    private String mApprovedTime;

    // Other variables
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Networking variables
    public Downloader downloader;
    private RequestQueue mRequestQueue;
    private static long lastDownload;
    private static boolean isConnected;
    private static int DOWNLOAD_UPDATE_INTERVAL = 60000; //Updates every 10 minutes //Todo make this alterable by user
    private static final int NETWORK_CHECK_INTERVAL = 4000; //check the network every 4 seconds //Todo implement!
    private final Handler timerHandler = new Handler();
    private final Runnable timerRunnable = new Runnable() {
        // Check internet connection
        @Override
        public void run() {
            ConnectivityManager cm = (ConnectivityManager) getApplication() //Returns the application that owns the activity //Todo, see if this also returns the connectivity type
                    .getApplicationContext()                                //Returns the context of the application
                    .getSystemService(Context.CONNECTIVITY_SERVICE);        //Uses the context of the application to find the connectivity service
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();              //Deprecated for API29 and higher (however, still recommended by android) --> //Todo, see if I can find an alternative
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (!isConnected){
                textViewConnection.setText(R.string.mssg_notconnected);
            }else{
                textViewConnection.setText(R.string.mssg_connected);
            }
            String cityNameOld = inputCityName.getText().toString();
            if (!cityNameOld.isEmpty()){ //Only executes if there is a previous cityName
                mCityName = cityNameOld;
                if (isConnected && ((System.currentTimeMillis() - lastDownload) > DOWNLOAD_UPDATE_INTERVAL)){ //Only executes if interval>10min and sys is connected to internet
                    String updatedTimeOld = textViewUpdatedTime.getText().toString();
                    downloader.postVolleyRequest(mCityName);  //download new data //Todo check if this is how I really want to do it
                    weatherList = downloader.weatherList;
                    fillRecyclerView(weatherList);
                    lastDownload = System.currentTimeMillis();  //update the lastDownload time
                    if (!textViewUpdatedTime.getText().toString().equals(updatedTimeOld)){
                        //Todo do something
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        textViewConnection = findViewById(R.id.textViewConnection);
        inputCityName = findViewById(R.id.inputCityName);
        textViewUpdatedTime = findViewById(R.id.textViewUpdatedTime);
        recyclerView = findViewById(R.id.recyclerView);

        mRequestQueue = Volley.newRequestQueue(this);
        weatherList = WeatherList.getInstance(); //gets the singleton list
        downloader = new Downloader(this);
    };

    @Override
    protected void onStart(){
        super.onStart();
        timerHandler.postDelayed(timerRunnable, 0); //Todo find out what this does
    }

    @Override
    protected void onPause(){
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        mRequestQueue.cancelAll(this);
    }

    public void onSearch(View view) {
        if (isConnected) {
            lastDownload = System.currentTimeMillis();
            mCityName = inputCityName.getText().toString(); //update the cityname
            if(mCityName.isEmpty()){
                Toast toast = Toast.makeText(this, "No location entered",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String updatedTimeOld = textViewUpdatedTime.getText().toString();
                String cityNameOld = mCityName;
                downloader.postVolleyRequest(mCityName);
            }
        } else {
            Toast toast = Toast.makeText(this, "No internet connection",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void fillRecyclerView(List<Weather> weatherList){
        // For each single element in the list a weather item
        // in the recycler view is filled
        ArrayList<WeatherItem> itemList = new ArrayList<>();
        for (Weather weatherAtTime : weatherList){
            int weatherSymbol = weatherAtTime.getWeatherSymbol();
            String dateTime = weatherAtTime.getTimeData();
            String temperature = weatherAtTime.getTemperature();
            String windspeed = weatherAtTime.getWindSpeed();
            String winddirection = weatherAtTime.getWindDirection();
            String rainintensity = weatherAtTime.getRainIntensity();
            String cloudcoverage = weatherAtTime.getCloudCoverage();
            mApprovedTime = "Last updated at: " + weatherAtTime.getApprovedTime();
            itemList.add(new WeatherItem(weatherSymbol, dateTime, temperature,
                    windspeed, winddirection, rainintensity, cloudcoverage)); //Fill the item and add it to the list
        }
        RecyclerView.Adapter<WeatherItemAdapter.WeatherItemsViewHolder> adapter =
        new WeatherItemAdapter(itemList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d(LOG_TAG, "...filled recycler view");
    }

    public void display(View view) {
        // todo mke sure that this doesn't require an extra button
        // todo mke sure that the view items are a bit smaller.

        if (weatherList != null) {
            weatherList = downloader.weatherList;
            fillRecyclerView(weatherList);
            textViewUpdatedTime.setText(mApprovedTime);
        }
        else{
            Log.i(LOG_TAG, "no weatherList");
        }
    }
}