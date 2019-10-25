package com.example.flyhighadminchat.adapters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.date_package.DateUtils;


public class DataBindingAdapters {
    private static final String TAG = "DataBindingAdapters";

    @BindingAdapter("intHours")
    public static void setHours(TextView textView, String _time){


        String hours = _time.substring(0,2);

        textView.setText(hours);

    }

    @BindingAdapter("intMinutes")
    public static void setMinutes(TextView textView, String _time){

        String minutes = _time.substring(2);

        textView.setText(minutes);
    }

    @BindingAdapter("longParserRequest")
    public static void setDateOfRequest(TextView textView, long timestamp){
        DateUtils dateUtils = new DateUtils();

        String month = dateUtils.getMonthFromLongTimeStamp(timestamp);
        String day = String.valueOf(dateUtils.getDayOfMonthFromLongTimeStamp(timestamp));
        String year = String.valueOf(dateUtils.getYearFromLongTimeStamp(timestamp));
        String request = textView.getResources().getString(R.string.help_requested_at);

        String hours = String.valueOf(dateUtils.getHoursFromLongTimeStamp(timestamp));
        String minutes = String.valueOf(dateUtils.getMinutesFromLongTimeStamp(timestamp));

        textView.setText(request + month + " " + day + ", " + year + " at " + hours + ":" + minutes);

    }

    @BindingAdapter("longParserOpen")
    public static void setDateOfOpen(TextView textView, long timestamp){
        DateUtils dateUtils = new DateUtils();

        String month = dateUtils.getMonthFromLongTimeStamp(timestamp);
        String day = String.valueOf(dateUtils.getDayOfMonthFromLongTimeStamp(timestamp));
        String year = String.valueOf(dateUtils.getYearFromLongTimeStamp(timestamp));
        String firstString = textView.getResources().getString(R.string.session_opened);

        String hours = String.valueOf(dateUtils.getHoursFromLongTimeStamp(timestamp));
        String minutes = String.valueOf(dateUtils.getMinutesFromLongTimeStamp(timestamp));

        textView.setText(firstString + month + " " + day + ", " + year + " at " + hours + ":" + minutes);

    }

    @BindingAdapter("longParserClosed")
    public static void setDateOfClosed(TextView textView, long timestamp){
        DateUtils dateUtils = new DateUtils();

        String month = dateUtils.getMonthFromLongTimeStamp(timestamp);
        String day = String.valueOf(dateUtils.getDayOfMonthFromLongTimeStamp(timestamp));
        String year = String.valueOf(dateUtils.getYearFromLongTimeStamp(timestamp));
        String firstString = textView.getResources().getString(R.string.session_closed);

        String hours = String.valueOf(dateUtils.getHoursFromLongTimeStamp(timestamp));
        String minutes = String.valueOf(dateUtils.getMinutesFromLongTimeStamp(timestamp));

        textView.setText(firstString + month + " " + day + ", " + year + " at " + hours + ":" + minutes);

    }

}
