package kth.jjve.fancyweatherapp.networking;
    /*
    This class is responsible for downloading of the data
    given the provided URL's.
    */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import kth.jjve.fancyweatherapp.datahandling.Parser;
import kth.jjve.fancyweatherapp.datahandling.Weather;

public class Downloader {
    private Parser parser;
    public String mCityURL;
    private String mWeatherURL;
    private String[] mCoordinates;
    public List<Weather> weatherList;
    private RequestQueue mQueue;
    private final Context mCtx;

    public Downloader (Context ctx){
        mQueue = VolleySingleton.getInstance(ctx).getRequestQueue();
        mCtx = ctx;
        parser = new Parser();
    }

    public RequestQueue getQueue(){
        return mQueue;
    }

    public void postVolleyRequest(String cityName) {
        setCityURL(cityName);
        JsonArrayRequest cityRequest = new JsonArrayRequest(Request.Method.GET,
                mCityURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            mCoordinates = parser.parseCityArray(response);
                            setWeatherURL(mCoordinates);
                            JsonObjectRequest weatherRequest = new JsonObjectRequest(Request.Method.GET,
                                    mWeatherURL,
                                    null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                List<Weather> newWeather = parser.getWeather(response);
                                                if (weatherList != null) {
                                                    weatherList.clear();
                                                    weatherList.addAll(newWeather);
                                                }else{
                                                    weatherList = newWeather;
                                                }
                                                Toast toast = Toast.makeText(mCtx, "Download complete",
                                                        Toast.LENGTH_SHORT);
                                                toast.show();

                                            } catch (Exception e) {
                                                Log.i("error whilst parsing", e.toString());
                                            }
                                        }
                                    },
                                    errorListener);
                            weatherRequest.setTag(this);
                            mQueue.add(weatherRequest);
                        } catch (Exception e) {
                            Log.i("error whilst parsing", e.toString());
                        }
                    }
                },
                errorListener);
        cityRequest.setTag(this);
        mQueue.add(cityRequest);


    }

      private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Volley error", error.toString());
        }
    };


    public void setCityURL(String cityName){
        mCityURL = "https://maceo.sth.kth.se/weather/search?location=Sigfridstorp";
        //mCityURL = "https://www.smhi.se/wpt-a/backend_solr/autocomplete/search/" + cityName;
    }

    public void setWeatherURL(String[] coordinates){
        mWeatherURL = "https://maceo.sth.kth.se/weather/forecast?lonLat=lon/14.333/lat/60.383";
//        mWeatherURL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/"
//                      + coordinates[0] + "/lat/"+ coordinates[1] + "/data.json";
    }
}
