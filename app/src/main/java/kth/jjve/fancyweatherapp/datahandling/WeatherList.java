package kth.jjve.fancyweatherapp.datahandling;

import java.util.ArrayList;
import java.util.List;

/*
    This is a class that takes multiple WeatherObjects and
    groups them together in a list that can be handed to the
    UIAdapter
     */
public class WeatherList {
    private static List<Weather> theWeather;

    //private constructor to force the use of getInstance() to get an object
    public WeatherList(){

    }

    public static List<Weather> getInstance(){
        if (theWeather == null)
            theWeather = new ArrayList<>();
        return theWeather;
    }
}
