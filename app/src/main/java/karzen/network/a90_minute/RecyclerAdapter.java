package karzen.network.a90_minute;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Date> displayDates = new ArrayList<>();
    private ArrayList<Date> selectedTime;

    private Context mContext;

    public RecyclerAdapter(Context context, ArrayList<Date> displayDates, ArrayList<Date> selectedTime  ) {

        this.displayDates = displayDates;
        this.selectedTime = selectedTime;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Log.d("SelectedDate", "" + displayDates.get(position).getTime());
        Log.d("CurrentDate", "" +  new Date().getTime());

        final long sleepTime = (displayDates.get(position).getTime() - selectedTime.get(0).getTime()) / (1000*60);

        holder.timeView.setText(String.format(Locale.getDefault(), "%02d : %02d | %02d:%02d h Sleep Time", displayDates.get(position).getHours(), displayDates.get(position).getMinutes(), sleepTime/60, sleepTime%60));

    }

    @Override
    public int getItemCount() {
        return displayDates.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView timeView;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);

            timeView = itemView.findViewById(R.id.timeDisplay);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }

    }


}
