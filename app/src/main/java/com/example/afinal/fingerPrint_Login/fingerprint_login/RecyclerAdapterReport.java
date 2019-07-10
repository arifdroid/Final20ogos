package com.example.afinal.fingerPrint_Login.fingerprint_login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.fingerPrint_Login.oop.TestTimeStamp;

import java.util.ArrayList;

public class RecyclerAdapterReport extends RecyclerView.Adapter<RecyclerAdapterReport.InsideHolder> {


    private Context mContext;
    private ArrayList<TestTimeStamp> listss;

    public RecyclerAdapterReport(Context context, ArrayList<TestTimeStamp> lateList) {

        this.mContext = context;
        this.listss = lateList;
    }

    @NonNull
    @Override
    public InsideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_late_card,parent,false);

        return new InsideHolder(view) ;


    }

    @Override
    public void onBindViewHolder(@NonNull InsideHolder holder, int position) {

        //listss.get(position).getMon_date(); // need to check how data is treated.

        if (listss.get(position).getName() == null) {


        } else {


        if (listss.get(position).isProb_mon_morning()) { //we can get date, assuming, pull this week data,


            holder.textViewDate.setText(listss.get(position).getMon_date());
            holder.textViewClock.setText(listss.get(position).getMon_morning());
            holder.textViewMorOrEve.setText("AM");
            holder.textViewLocation.setText(listss.get(position).getLocationMon());


        } else if (listss.get(position).isProb_tue_morning()) {


            holder.textViewDate.setText(listss.get(position).getTue_date());
            holder.textViewClock.setText(listss.get(position).getTue_morning());
            holder.textViewMorOrEve.setText("AM");
            holder.textViewLocation.setText(listss.get(position).getLocationTue());


        } else if (listss.get(position).isProb_wed_morning()) {


            holder.textViewDate.setText(listss.get(position).getWed_date());
            holder.textViewClock.setText(listss.get(position).getWed_morning());
            holder.textViewMorOrEve.setText("AM");
            holder.textViewLocation.setText(listss.get(position).getLocationWed());


        } else if (listss.get(position).isProb_thu_morning()) {


            holder.textViewDate.setText(listss.get(position).getThu_date());
            holder.textViewClock.setText(listss.get(position).getThu_morning());
            holder.textViewMorOrEve.setText("AM");
            holder.textViewLocation.setText(listss.get(position).getLocationThu());

        } else if (listss.get(position).isProb_fri_morning()) {


            holder.textViewDate.setText(listss.get(position).getFri_date());
            holder.textViewClock.setText(listss.get(position).getFri_morning());
            holder.textViewMorOrEve.setText("AM");
            holder.textViewLocation.setText(listss.get(position).getLocationFri());

        } else if (listss.get(position).isProb_mon_evening()) { //we can get date, assuming, pull this week data,


            holder.textViewDate.setText(listss.get(position).getMon_date());
            holder.textViewClock.setText(listss.get(position).getMon_evening());
            holder.textViewMorOrEve.setText("PM");
            holder.textViewLocation.setText(listss.get(position).getLocationMon_evening());


        } else if (listss.get(position).isProb_tue_evening()) {


            holder.textViewDate.setText(listss.get(position).getTue_date());
            holder.textViewClock.setText(listss.get(position).getTue_evening());
            holder.textViewMorOrEve.setText("PM");
            holder.textViewLocation.setText(listss.get(position).getLocationTue_evening());

        } else if (listss.get(position).isProb_wed_evening()) {


            holder.textViewDate.setText(listss.get(position).getWed_date());
            holder.textViewClock.setText(listss.get(position).getWed_evening());
            holder.textViewMorOrEve.setText("PM");
            holder.textViewLocation.setText(listss.get(position).getLocationWed_evening());


        } else if (listss.get(position).isProb_thu_evening()) {


            holder.textViewDate.setText(listss.get(position).getThu_date());
            holder.textViewClock.setText(listss.get(position).getThu_evening());
            holder.textViewMorOrEve.setText("PM");
            holder.textViewLocation.setText(listss.get(position).getLocationThu_evening());

        } else if (listss.get(position).isProb_fri_evening()) {


            holder.textViewDate.setText(listss.get(position).getFri_date());
            holder.textViewClock.setText(listss.get(position).getFri_evening());
            holder.textViewMorOrEve.setText("PM");
            holder.textViewLocation.setText(listss.get(position).getLocationFri_evening());

        }


        holder.textViewNumber.setText(String.valueOf(position));
        holder.textViewName.setText(listss.get(position).getName());

        if (!listss.get(position).getMon_morning().equals("")) {
            holder.textViewClock.setText(listss.get(position).getMon_morning());
        }
        //also if
        holder.textViewDate.setText(listss.get(position).getMon_date());


        holder.textViewLocation.setText(listss.get(position).getLocationStreet());

        //depends on data.
        //holder.textViewMorOrEve

    }

    }

    @Override
    public int getItemCount() {
        return listss.size();
    }

    public class InsideHolder extends RecyclerView.ViewHolder{

        public TextView textViewNumber, textViewName, textViewDate, textViewClock,textViewLocation,textViewMorOrEve;

        public InsideHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumber = itemView.findViewById(R.id.textView_report_numberlistID);
            textViewName = itemView.findViewById(R.id.textView_report_nameID);
            textViewDate = itemView.findViewById(R.id.textView_report_dateId);
            textViewClock = itemView.findViewById(R.id.textView_report_clock);
            textViewLocation = itemView.findViewById(R.id.textView_report_location);
            textViewMorOrEve = itemView.findViewById(R.id.textView_report_mor_or_eve);

        }
    }
}
