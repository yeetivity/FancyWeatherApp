package kth.jjve.fancyweatherapp.viewing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kth.jjve.fancyweatherapp.R;

/*
Class that adapts the made weather items to the UI.
 */
public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.WeatherItemsViewHolder> {
    private ArrayList<WeatherItem> mWeatherItemList;

    public static class WeatherItemsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mWeatherSymbol;
        public TextView mTextDate;
        public TextView mTextTemperature;
        public TextView mTextWindspeed;
        public TextView mTextWinddirection;
        public TextView mTextRainIntensity;

        public WeatherItemsViewHolder(@NonNull View itemView){
            super(itemView);
            // Get the references to the view-objects
            mWeatherSymbol = itemView.findViewById(R.id.imageView_weathersymbol);
            mTextDate = itemView.findViewById(R.id.textView_Date);
            mTextTemperature = itemView.findViewById(R.id.textView_temperature);
            mTextWindspeed = itemView.findViewById(R.id.textView_Windspeed);
            mTextWinddirection = itemView.findViewById(R.id.textView_Winddirection);
            mTextRainIntensity = itemView.findViewById(R.id.textView_RainIntensity);

        }
    }

    public WeatherItemAdapter(ArrayList<WeatherItem> weatherItemsList){
        mWeatherItemList = weatherItemsList;
    }

    @NonNull
    @Override
    public WeatherItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // pass layout of weather item layout to adapter
        // returning view holder
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);
        return new WeatherItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherItemsViewHolder holder, int position){
        WeatherItem currentItem = mWeatherItemList.get(position); //Item on a specific position
        holder.mWeatherSymbol.setImageResource(currentItem.getWeatherSymbol());
        holder.mTextDate.setText(currentItem.getTextDate());
        holder.mTextTemperature.setText(currentItem.getTextTemperature());
        holder.mTextWindspeed.setText(currentItem.getTextWindspeed());
        holder.mTextWinddirection.setText(currentItem.getTextWinddirection());
        holder.mTextRainIntensity.setText(currentItem.getTextRainintensity());
    }

    @Override
    public int getItemCount() {
        return mWeatherItemList.size();
    }
}
