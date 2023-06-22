package com.example.volunteerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volunteerapp.volonter.MoreAboutEventActivity;
import com.example.volunteerapp.R;
import com.example.volunteerapp.counselor.MoreAboutEventActivityCounselor;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.google.android.material.card.MaterialCardView;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class VolunteerAdapterEvents extends BaseAdapter {

    private List<ParseObject> list;

    private Context context;
    private LayoutInflater lInflater;

    Activity activity;
    public VolunteerAdapterEvents(Activity activity, List<ParseObject> list){
        this.activity = activity;
        this.list = list;
        lInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ParseObject getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = lInflater.inflate(R.layout.custom_item_for_events, viewGroup, false);
        }

        Resources res = v.getResources();

        UserPreferences uPref = new UserPreferences(v.getContext());
        User user = uPref.getUser();

        MaterialCardView cardView = v.findViewById(R.id.cardMaterial);
        TextView title = v.findViewById(R.id.titleOfEvent);
        TextView date = v.findViewById(R.id.dateOfEvent);
        Button more = v.findViewById(R.id.buttonMoreAboutEvent);

/*        if (getItem(i).getDate("date").before(new Date())){
            System.out.println(i+"ok1");
            System.out.println(getItem(i).getDate("date") +" ---- " + new Date() +"-=-  "+ getItem(i).getString("title"));
            more.setBackgroundColor(v.getResources().getColor(R.color.gray,null));
            //cardView.setStrokeColor(v.getResources().getColor(R.color.gray,null));
            more.setEnabled(false);
            System.out.println(i+"ok2");
        }*/
//        else{
        if (user.getPost().equals("volonter")){
            more.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, MoreAboutEventActivity.class);
                intent.putExtra("event", getItem(i));
                activity.startActivity(intent);
            });
        }else{
            more.setOnClickListener(view1 -> {
                Intent intent = new Intent(activity, MoreAboutEventActivityCounselor.class);
                intent.putExtra("event", getItem(i));
                activity.startActivity(intent);
            });
        }

        ImageView img = v.findViewById(R.id.imageOfEvent);
        title.setText(getItem(i).getString("title"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        date.setText(format.format(getItem(i).getDate("date")));



//        ParseRelation<ParseObject> relation = getItem(i).getRelation("volunteers");
//        ParseQuery<ParseObject> query = relation.getQuery();
//        query.findInBackground((objects, e) -> {
//            if (e == null) {
//                if(objects != null){
//
//                }
//            }
//        });



        return v;
    }
}
