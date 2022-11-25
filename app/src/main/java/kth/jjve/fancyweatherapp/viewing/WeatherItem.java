package kth.jjve.fancyweatherapp.viewing;
/*

 */

public class WeatherItem {
    private final int mWeatherSymbol;
    private final String mTextCloudcoverage;
    private final String mDateTime;
    private final String mTextTemperature;
    private final String mTextWindspeed;
    private final String mTextWinddirection;
    private final String mTextRainintensity;

    public WeatherItem(int weatherSymbol, String dateTime, String temperature,
                       String windspeed, String winddirection, String rainintensity,
                       String cloudcoverage){
        //Constructor
        mWeatherSymbol = weatherSymbol;
        mDateTime = dateTime;
        mTextTemperature = temperature;
        mTextWindspeed = windspeed;
        mTextWinddirection = winddirection;
        mTextRainintensity = rainintensity;
        mTextCloudcoverage = cloudcoverage;
        
    }

    public int getWeatherSymbol() {
        return mWeatherSymbol;
    }

    public String getTextDate() {
        return mDateTime;
    }

    public String getTextTemperature() {
        return mTextTemperature;
    }

    public String getTextCloudcoverage() {
        return mTextCloudcoverage;
    }

    public String getTextWindspeed(){return mTextWindspeed;}

    public String getTextWinddirection(){return mTextWinddirection;}

    public String getTextRainintensity(){return mTextRainintensity;}
}
