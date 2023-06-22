package com.example.volunteerapp.counselor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteerapp.R;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MoreAboutEventActivityCounselor extends AppCompatActivity {

    TextView title, venue, organizer, date, quantity, description;
    Button applyButton, applicationButton, reportButton;
    ParseObject event;
    ProgressDialog progressDialog;
    User user;

    Event_fragment_applications eventFragmenttreatment = new Event_fragment_applications();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_about_event_counseler);

        UserPreferences uPref = new UserPreferences(this);
        user = uPref.getUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Загрузка");
        progressDialog.setCancelable(false);

        title = findViewById(R.id.textViewTitleOfEvent);
        venue = findViewById(R.id.place_about);
        organizer = findViewById(R.id.textViewOrganizerOfEvent);
        date = findViewById(R.id.date_about);
        quantity = findViewById(R.id.textViewQuantityOfEvent);
        applicationButton = findViewById(R.id.buttonApplication);
        reportButton = findViewById(R.id.buttonReport);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (ParseObject) extras.get("event");
            title.setText(event.getString("title"));
            venue.setText(event.getString("venue"));
            organizer.setText(event.getString("organizer"));
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            date.setText(format.format(event.getDate("date")));
            String quantityText = event.getInt("quantity_current") + "/" + event.getInt("quantity_max");
            quantity.setText(quantityText);
/*            if (event.getString("description")){
                description.setText(event.getString("description"));
            }*/
            if(event.getDate("date").before(new Date())){
                applyButton.setVisibility(View.GONE);
            }
        }

        applicationButton.setOnClickListener(view ->{
            Intent intent = new Intent(view.getContext(), Event_fragment_applications.class);
            intent.putExtra("event", event);
            view.getContext().startActivity(intent);
        }
        );
        reportButton.setOnClickListener(view -> {
        });
    }
}