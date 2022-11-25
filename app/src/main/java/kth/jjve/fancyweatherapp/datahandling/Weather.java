package kth.jjve.fancyweatherapp.datahandling;
    /*
    This is a class that takes the weather data from the parser
    and spreads it out over multiple WeatherObjects according to the time
    instances
     */

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kth.jjve.fancyweatherapp.R;

public class Weather {
    private static final String LOG_TAG = Weather.class.getSimpleName();
    private String mTime;
    private String mDate;
    private String mDateTime;
    private String mTemperature;
    private String mCloudCoverage;
    private String mWindSpeed;
    private String mWindDirection;
    private int mWeatherSymbol;
    private String mRain;
    private String mApprovedTime;

    public void setTimeDate(String timestamp) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdFormat.parse(timestamp.substring(0,10));
        sdFormat.applyPattern("EEE, dd MMM");
        mDate = sdFormat.format(date);
        mTime = timestamp.substring(11,16);
        mDateTime = mDate + "\n" + mTime;
    }

    public void setTemperature(double temp){
        Log.d(LOG_TAG, "Temperature: " +temp);
        mTemperature = Double.toString(temp)+" ℃";
    }

    public void setCloudCoverage(int cov){
        Log.d(LOG_TAG, "Cloud coverage: " +cov);
        mCloudCoverage = Integer.toString(cov) + "octas";
    }

    public void setWindSpeed(double ws){
        Log.d(LOG_TAG, "Windspeed: " + ws);
        mWindSpeed = Double.toString(ws) + "m/s";
    }

    public void setWindDirection(int wd){
        if (wd>337 || wd<=22){
            mWindDirection = "N";
        }else if (wd<=67){
            mWindDirection = "NE";
        }else if (wd<=112){
            mWindDirection = "E";
        }else if (wd<=157){
            mWindDirection = "SE";
        }else if (wd<=202){
            mWindDirection = "S";
        }else if (wd<=247){
            mWindDirection = "SW";
        }else if (wd<=292){
            mWindDirection = "W";
        }else{
            mWindDirection = "NW";}
    }

    public void setSymbol(int sym){
        Log.d(LOG_TAG, "symbolnumber:" + Integer.toString(sym));
        boolean isDay = Integer.parseInt(mTime.substring(0,2)) > 6 &&
                Integer.parseInt(mTime.substring(0,2)) < 18;
        if (isDay) { //Todo see why this only gives default now
            switch (sym) {
                case 1:
                    mWeatherSymbol = R.drawable.d1;
                    break;
                case 2:
                    mWeatherSymbol = R.drawable.d2;
                    break;
                case 3:
                    mWeatherSymbol = R.drawable.d3;
                    break;
                case 4:
                    mWeatherSymbol = R.drawable.d4;
                    break;
                case 5:
                    mWeatherSymbol = R.drawable.d5;
                    break;
                case 6:
                    mWeatherSymbol = R.drawable.d6;
                    break;
                case 7:
                    mWeatherSymbol = R.drawable.d7;
                    break;
                case 8:
                    mWeatherSymbol = R.drawable.d8;
                    break;
                case 9:
                    mWeatherSymbol = R.drawable.d9;
                    break;
                case 10:
                    mWeatherSymbol = R.drawable.d10;
                    break;
                case 11:
                    mWeatherSymbol = R.drawable.d11;
                    break;
                case 12:
                    mWeatherSymbol = R.drawable.d12;
                    break;
                case 13:
                    mWeatherSymbol = R.drawable.d13;
                    break;
                case 14:
                    mWeatherSymbol = R.drawable.d14;
                    break;
                case 15:
                    mWeatherSymbol = R.drawable.d15;
                    break;
                case 16:
                    mWeatherSymbol = R.drawable.d16;
                    break;
                case 17:
                    mWeatherSymbol = R.drawable.d17;
                    break;
                case 18:
                    mWeatherSymbol = R.drawable.d18;
                    break;
                case 19:
                    mWeatherSymbol = R.drawable.d19;
                    break;
                case 20:
                    mWeatherSymbol = R.drawable.d20;
                    break;
                case 21:
                    mWeatherSymbol = R.drawable.d21;
                    break;
                case 22:
                    mWeatherSymbol = R.drawable.d22;
                    break;
                case 23:
                    mWeatherSymbol = R.drawable.d23;
                    break;
                case 24:
                    mWeatherSymbol = R.drawable.d24;
                    break;
                case 25:
                    mWeatherSymbol = R.drawable.d25;
                    break;
                case 26:
                    mWeatherSymbol = R.drawable.d26;
                    break;
                case 27:
                    mWeatherSymbol = R.drawable.d27;
                    break;
                default:
                    mWeatherSymbol = R.drawable.nodata;
            }
        }else {
                switch (sym) {
                    case 1:
                        mWeatherSymbol = R.drawable.n1;
                        break;
                    case 2:
                        mWeatherSymbol = R.drawable.n2;
                        break;
                    case 3:
                        mWeatherSymbol = R.drawable.n3;
                        break;
                    case 4:
                        mWeatherSymbol = R.drawable.n4;
                        break;
                    case 5:
                        mWeatherSymbol = R.drawable.n5;
                        break;
                    case 6:
                        mWeatherSymbol = R.drawable.n6;
                        break;
                    case 7:
                        mWeatherSymbol = R.drawable.n7;
                        break;
                    case 8:
                        mWeatherSymbol = R.drawable.n8;
                        break;
                    case 9:
                        mWeatherSymbol = R.drawable.n9;
                        break;
                    case 10:
                        mWeatherSymbol = R.drawable.n10;
                        break;
                    case 11:
                        mWeatherSymbol = R.drawable.n11;
                        break;
                    case 12:
                        mWeatherSymbol = R.drawable.n12;
                        break;
                    case 13:
                        mWeatherSymbol = R.drawable.n13;
                        break;
                    case 14:
                        mWeatherSymbol = R.drawable.n14;
                        break;
                    case 15:
                        mWeatherSymbol = R.drawable.n15;
                        break;
                    case 16:
                        mWeatherSymbol = R.drawable.n16;
                        break;
                    case 17:
                        mWeatherSymbol = R.drawable.n17;
                        break;
                    case 18:
                        mWeatherSymbol = R.drawable.n18;
                        break;
                    case 19:
                        mWeatherSymbol = R.drawable.n19;
                        break;
                    case 20:
                        mWeatherSymbol = R.drawable.n20;
                        break;
                    case 21:
                        mWeatherSymbol = R.drawable.n21;
                        break;
                    case 22:
                        mWeatherSymbol = R.drawable.n22;
                        break;
                    case 23:
                        mWeatherSymbol = R.drawable.n23;
                        break;
                    case 24:
                        mWeatherSymbol = R.drawable.n24;
                        break;
                    case 25:
                        mWeatherSymbol = R.drawable.n25;
                        break;
                    case 26:
                        mWeatherSymbol = R.drawable.n26;
                        break;
                    case 27:
                        mWeatherSymbol = R.drawable.n27;
                        break;
                    default:
                        mWeatherSymbol = R.drawable.nodata;
            }
        }
    }

    public void setRainIntensity(double rain){
        Log.d(LOG_TAG, "Rainintensity: " +rain + "mm/h");
        mRain = Double.toString(rain) + "mm/h";
    }

    public void setApprovedTime(String approvedTime) throws ParseException{
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdFormat.parse(approvedTime.substring(0,10));
        sdFormat.applyPattern("EEE, dd MMM");
        String UpdatedDate = sdFormat.format(date);
        String UpdatedTime = approvedTime.substring(11,16);
        mApprovedTime = UpdatedTime + " | " + UpdatedDate;
    }

    public String getApprovedTime(){return mApprovedTime;}

    public String getTimeData(){return mDateTime;}

    public String getTemperature(){return mTemperature;}

    public String getCloudCoverage(){return mCloudCoverage;}

    public String getWindSpeed(){return mWindSpeed;}

    public String getWindDirection(){return mWindDirection;}

    public int getWeatherSymbol(){return mWeatherSymbol;}

    public String getRainIntensity(){return mRain;}


}
