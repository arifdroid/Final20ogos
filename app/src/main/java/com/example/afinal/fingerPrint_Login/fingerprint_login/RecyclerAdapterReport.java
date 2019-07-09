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
        listss = lateList;
    }

    @NonNull
    @Override
    public InsideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_late_card,parent,false);

        return new InsideHolder(view) ;


    }

    @Override
    public void onBindViewHolder(@NonNull InsideHolder holder, int position) {

        listss.get(position).getMon_date(); // need to check how data is treated.

        holder.textViewNumber.setText(String.valueOf(position));
        holder.textViewName.setText(listss.get(position).getName());

        if(!listss.get(position).getMon_morning().equals("")){
            holder.textViewClock.setText(listss.get(position).getMon_morning());
        }
        //also if
        holder.textViewDate.setText(listss.get(position).getMon_date());


        holder.textViewLocation.setText(listss.get(position).getLocationStreet());

        //depends on data.
        //holder.textViewMorOrEve



    }

    @Override
    public int getItemCount() {
        return 0;
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
