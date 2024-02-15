package karzen.network.a90_minute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    Button timeButton;

    int hour, minute;
    Date selected;
    Calendar calendar;


    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    ArrayList<Date> selectedDate = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeButton = findViewById(R.id.timeSelect);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        selectedDate.add(new Date());

        timeButton.setText(String.format(Locale.getDefault(), "%02d : %02d", hour, minute));


        initRecyclerView();
        updateList();



    }

    public void pickTime(View view){
        final Context mainContext = this;
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d : %02d", hour, minute));
                updateList();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Keep it up");
        timePickerDialog.show();


    }

    public void updateList(){

        Date now = new Date();
        int minutesFromNow = 0;

        dates.clear();
        selectedDate.clear();

        if(now.getMinutes() > minute) {
            minutesFromNow += 60 - now.getMinutes() + minute;
            hour += -1;
        }
        else {
            minutesFromNow += minute - now.getMinutes();

        }

        if(now.getHours() > hour)
            minutesFromNow += (24-now.getHours() + hour ) * 60;
        else
            minutesFromNow += (hour-now.getHours()) * 60;




        for(int i = 1;  i <= 10; i++) {
            dates.add(new Date(Calendar.getInstance().getTimeInMillis() + 60 * 1000 * minutesFromNow + 90*i * 60 * 1000));
           // dates.add(new Date(Calendar.getInstance().getTimeInMillis() + (90*i * 60 * 1000)));
        }

        selectedDate.add(new Date(Calendar.getInstance().getTimeInMillis() + 60 * 1000 * minutesFromNow));
        adapter.notifyDataSetChanged();
    }


    public void openAlarm(View view){ startActivity(new Intent(AlarmClock.ACTION_SET_ALARM)); }


    private void initRecyclerView(){
        recyclerView = findViewById(R.id.TimeView);
        adapter = new RecyclerAdapter(this, dates, selectedDate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}