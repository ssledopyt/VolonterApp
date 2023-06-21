package com.example.volunteerapp.counselor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.volunteerapp.R;
import com.example.volunteerapp.adapters.AdapterForEventTreatment;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

public class Event_fragment_applications extends AppCompatActivity {

    private ListView listview;
    private TextView title;
    public ParseObject event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_application_counselor);

        //View v = inflater.inflate(R.layout.fragment_application_counselor, container, false);

        title = findViewById(R.id.titleTreatment);
        listview = findViewById(R.id.ListViewTreatment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (ParseObject) extras.get("event");
            title.setText(event.getString("title"));
            ParseObject eventObj;
            ParseQuery<ParseObject> applicationsQuery = new ParseQuery<>("Event");
            System.out.println(event.getString("title"));
            applicationsQuery.whereEqualTo("objectId", event.getObjectId());
            try {
                eventObj = applicationsQuery.getFirst();
                //System.out.println(userObj.getString("username"));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(eventObj.getRelation("applications"));

            applicationsQuery.findInBackground((objects, e) -> {
                if (e == null) {
                    if (objects != null) {

                        AdapterForEventTreatment adapter = new AdapterForEventTreatment(this, eventObj.getList("applications"), event.getObjectId());
                        listview.setAdapter(adapter);
                        listview.setItemsCanFocus(false);
                    }
                }
            });
        }
        //return v;
    }
}