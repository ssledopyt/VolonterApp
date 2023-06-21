package com.example.volunteerapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.volunteerapp.adapters.AdapterForEventTreatment;
import com.example.volunteerapp.model.Event;
import com.example.volunteerapp.model.User;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class EventTreatmentActivity extends AppCompatActivity {

    private ListView listview;
    private List<ParseObject> listParse;
    public User user = new User();

   public Event event = new Event();

    public ArrayList<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_treatment);

        /*TextView textView = findViewById(R.id.textView2);
        listview = findViewById(R.id.ListViewTreatment);


        ArrayList<Event> listEvent = new ArrayList<>();
        listEvent.add(event);



        ParseQuery<ParseObject> queryEvent = new ParseQuery<>("Event");
        queryEvent.findInBackground((objects, e) -> {
            if (e == null) {
                Log.d(TAG, "Objects: " + objects.get(1).getString("title"));
                event.setTitle(objects.get(1).getString("title"));
                textView.setText(event.getTitle());



                ParseRelation<ParseObject> relation = objects.get(0).getRelation("applications");
                        ParseQuery<ParseObject> queryA = relation.getQuery();
        queryA.findInBackground((objectsE, ex) -> {
            if (e == null) {

                if(objectsE != null){

                    AdapterForEventTreatment adapter = new AdapterForEventTreatment(this, objectsE);
                    listview.setAdapter(adapter);
                }
            }
        });
            } else {
                Log.e(TAG, "Parse Error: ", e);
            }

        });

        listview.setItemsCanFocus(false);*/
    }
}
