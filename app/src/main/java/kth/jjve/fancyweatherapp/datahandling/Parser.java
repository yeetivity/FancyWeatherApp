package kth.jjve.fancyweatherapp.datahandling;
    /*
    This is a class that parses the data provided by the downloader
        - parse coordinates based upon cityname from city info download
        - parse weather data based upon parsed coordinates from weather download
    */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String
            LOG_TAG = Parser.class.getSimpleName(),
            LONGITUDE = "lon",
            LATITUDE = "lat",
            VALID_TIME = "validTime",
            REFERENCE_TIME = "referenceTime",
            APPROVED_TIME = "approvedTime",
            TIME_SERIES = "timeSeries",
            PARAMETERS = "parameters",
            NAME = "name",
            VALUES = "values",
            TEMPERATURE = "t",
            CLOUDCOV = "tcc_mean",
            WINDSPEED = "ws",
            WINDDIR = "wd",
            SYMBOL = "Wsymb2",
            RAIN = "pmean";



    public String[] parseCityArray(JSONArray cityArray) throws JSONException{
        String[] coordinates = new String[2];
        JSONObject cityObject = cityArray.getJSONObject(1); //i=0 gives long latitude and longitude, i=1 gives shortened
        coordinates[0] = String.valueOf(cityObject.getDouble(LONGITUDE));
        coordinates[1] = String.valueOf(cityObject.getDouble(LATITUDE));
        return coordinates;
    }

    public List<Weather> getWeather(JSONObject weatherObj) throws JSONException, ParseException {
        String referencetime = weatherObj.getString(REFERENCE_TIME);
        String approvedtime = weatherObj.getString(APPROVED_TIME);
        Log.i("Parser", "parsed the ref time " + referencetime
                + " that has been approved at "+ approvedtime);

        JSONArray timeSeries = weatherObj.getJSONArray(TIME_SERIES);

        List<Weather> weatherList = new ArrayList<>();
        Log.d(LOG_TAG, "parser initialised");

        for (int i = 0; i < timeSeries.length(); i++) {
            JSONObject WeatherobjAtTimeInstance = timeSeries.getJSONObject(i);
            String validTime = WeatherobjAtTimeInstance.getString(VALID_TIME);
            Log.d(LOG_TAG, "valid time: "+ validTime);

            JSONArray parAtTimeInstance = WeatherobjAtTimeInstance.getJSONArray(PARAMETERS);

            Weather weatherInstance = new Weather();
            weatherList.add(weatherInstance);
            weatherInstance.setTimeDate(validTime);
            weatherInstance.setApprovedTime(approvedtime);

            for (int j = 0; j < parAtTimeInstance.length(); j++) {
                JSONObject parameter = parAtTimeInstance.getJSONObject(j);
                String name = parameter.getString(NAME);
                JSONArray valueArray = parameter.getJSONArray(VALUES);

                if (TEMPERATURE.equals(name)) {
                    weatherInstance.setTemperature(valueArray.getDouble(0));}
                else if (CLOUDCOV.equals(name)) {
                    weatherInstance.setCloudCoverage(valueArray.getInt(0));}
                else if (WINDSPEED.equals(name)) {
                    weatherInstance.setWindSpeed(valueArray.getDouble(0));}
                else if (WINDDIR.equals(name)){
                    weatherInstance.setWindDirection(valueArray.getInt(0));}
                else if (SYMBOL.equals(name)){
                    weatherInstance.setSymbol(valueArray.getInt(0));}
                else if (RAIN.equals(name)){
                    weatherInstance.setRainIntensity(valueArray.getDouble(0));
                } else{
                    Log.i(LOG_TAG, "Name was not in the list");
                }
            }
        }
        return weatherList;
    }
}
