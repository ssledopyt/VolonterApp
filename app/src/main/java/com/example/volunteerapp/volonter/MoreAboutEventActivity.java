package com.example.volunteerapp.volonter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.volunteerapp.R;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MoreAboutEventActivity extends AppCompatActivity {

    TextView title, venue, organizer, date, quantity, description;
    ProgressBar progress;
    Button applyButton, applicationButton, reportButton;
    ParseObject event;
    ProgressDialog progressDialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_about_event_volonter);

        UserPreferences uPref = new UserPreferences(this);
        user = uPref.getUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Загрузка");
        progressDialog.setCancelable(false);

        title = findViewById(R.id.textViewTitleOfEvent);
        venue = findViewById(R.id.textViewVenueOfEvent);
        organizer = findViewById(R.id.textViewOrganizerOfEvent);
        date = findViewById(R.id.textViewDateOfEvent);
        quantity = findViewById(R.id.textViewQuantityOfEvent);
        description = findViewById(R.id.descriptionView);
        applyButton = findViewById(R.id.buttonApply);
        //applicationButton = findViewById(R.id.buttonApplication);
        //reportButton = findViewById(R.id.buttonReport);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            event = (ParseObject) extras.get("event");
            title.setText(event.getString("title"));
            venue.setText(event.getString("venue"));
            organizer.setText(event.getString("organizer"));
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            date.setText(format.format(event.getDate("date")));
            description.setText(event.getString("description"));
            String quantityText = event.getInt("quantity_current") + "/" + event.getInt("quantity_max");
            quantity.setText(quantityText);

            if(event.getDate("date").before(new Date())){
                applyButton.setVisibility(View.GONE);
            }
            /*ParseObject eveObj;
            ParseQuery<ParseObject> queryUser = new ParseQuery<>("Event");
            queryUser.whereEqualTo("volonteers", user.getLogin());
            queryUser.findInBackground();
            try {
                eveObj = queryUser.getFirst();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            if (eveObj==null){applyButton.setVisibility(View.GONE);}*/
        }
        applyButton.setOnClickListener(view -> {
            progressDialog.show();
            ParseQuery<ParseObject> parseEvent = ParseQuery.getQuery("Event");
            parseEvent.getInBackground(event.getObjectId(), (ev, e) -> {
                if (e == null) {
                    ParseObject userObj;
                    if(ev.getInt("quantity_max") > ev.getInt("quantity_current")){
                        ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
                        queryUser.whereEqualTo("username", user.getLogin());
                        queryUser.findInBackground();
                        try {
                            userObj = queryUser.getFirst();
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        ParseRelation<ParseObject> relation = ev.getRelation("applications");
                        relation.add(userObj);
                        ev.saveInBackground(e1 -> {
                            progressDialog.dismiss();
                            if (e1 == null) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                alert.setTitle("Заявка отправлена");
                                alert.setMessage("После одоборения заявки мероприятие появится в разделе 'Мои мероприятия'");
                                alert.setPositiveButton("Ок", (dialogInterface, i) -> dialogInterface.dismiss());
                                alert.show();
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                alert.setTitle("Заявка не отправлена!");
                                alert.setMessage("Вы уже отправляли заявку");
                                alert.setPositiveButton("Ок", (dialogInterface, i) -> dialogInterface.dismiss());
                                alert.show();
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                        alert.setTitle("Заявка не отправлена!");
                        alert.setMessage("Данное мероприятие уже имеет максимальное количество участников");
                        alert.setPositiveButton("Ок", (dialogInterface, i) -> dialogInterface.dismiss());
                        alert.show();
                    }

                } else {
                    progressDialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Ошибка");
                    alert.setPositiveButton("Ок", (dialogInterface, i) -> dialogInterface.dismiss());
                    alert.show();
                }
            });
        });


/*        applicationButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EventTreatmentActivity.class);
            intent.putExtra("event", event);
            view.getContext().startActivity(intent);
        });
        reportButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EventReportActivity.class);
            intent.putExtra("event", event);
            view.getContext().startActivity(intent);
        });



        ParseQuery<ParseObject> queryPart = new ParseQuery<>("Participant");
        queryPart.whereEqualTo("user", ParseUser.getCurrentUser());
        queryPart.whereEqualTo("event", event);
        try {
            ParseObject currentPart = queryPart.getFirst();
            if(currentPart.getBoolean("application")){
                applyButton.setText("Заявка отправлена");
                applyButton.setClickable(false);
            } else if(currentPart.getBoolean("participant")){
                applyButton.setText("Заявка принята");
                applyButton.setClickable(false);
            } else if(!currentPart.getBoolean("application") && !currentPart.getBoolean("participant")){
                applyButton.setText("Заявка отклонена");
                applyButton.setClickable(false);
            }
        } catch (ParseException ignored) {

        }*/

    }
}