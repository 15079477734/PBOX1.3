package com.planboxone.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;
import android.widget.TextView;

import com.library.googledatetimepicker.time.RadialPickerLayout;
import com.library.googledatetimepicker.time.TimePickerDialog;
import com.library.googledatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.library.googledatetimepicker.tool.SwipeDismissTouchListener;
import com.planboxone.R;

import java.util.Calendar;

public class DateTimePickActivity extends Activity {


    private TextView tvDisplayTime;

    private final Calendar mCalendar = Calendar.getInstance();

    private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);

    private int minute = mCalendar.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_pick);
        tvDisplayTime = (TextView) findViewById(R.id.tvTime);
        resetTime();



        final TimePickerDialog timePickerDialog12h = TimePickerDialog.newInstance(new OnTimeSetListener() {

            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay,
                                  int minute) {

                Object c = pad3(hourOfDay);

                tvDisplayTime.setText(
                        new StringBuilder().append(pad2(hourOfDay))
                                .append(":").append(pad(minute)).append(c)
                );
                tvDisplayTime.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false);


        findViewById(R.id.btnChangeTime).setOnClickListener(new OnClickListener() {

            private String tag;

            @Override
            public void onClick(View v) {
                timePickerDialog12h.show(getFragmentManager(), tag);
            }
        });
    }

    private void resetTime() {
        tvDisplayTime.setText(new StringBuilder().append(pad(hourOfDay))
                .append(":").append(pad(minute)));
        tvDisplayTime.setTextColor(getResources().getColor(android.R.color.darker_gray));

    }


    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private static String pad2(int c) {
        if (c == 12)
            return String.valueOf(c);
        if (c == 00)
            return String.valueOf(c + 12);
        if (c > 12)
            return String.valueOf(c - 12);
        else
            return String.valueOf(c);
    }

    private static String pad3(int c) {
        if (c == 12)
            return " PM";
        if (c == 00)
            return " AM";
        if (c > 12)
            return " PM";
        else
            return " AM";
    }

}
