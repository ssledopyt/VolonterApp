package com.example.volunteerapp.volonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.volunteerapp.R;
import com.example.volunteerapp.counselor.Events_fragment_counselor;
import com.example.volunteerapp.counselor.Person_fragment_counselor;
import com.example.volunteerapp.counselor.Add_event_counselor;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationBarView;

public class VolonterMainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";


    BottomNavigationView bottomNavigationView, bottomNavigationView2;
    Person_fragment_volonter person_fragment_volonter = new Person_fragment_volonter();
    Search_fragment_volonter search_fragment_volonter = new Search_fragment_volonter();
    Event_fragment_volonter event_fragment_volonter = new Event_fragment_volonter();

    Person_fragment_counselor person_fragment_counselor = new Person_fragment_counselor();
    Add_event_counselor addevent_counselor = new Add_event_counselor();
    Events_fragment_counselor events_fragment_counselor = new Events_fragment_counselor();



    //Button toEventButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserPreferences ePref = new UserPreferences(this);
        User user = ePref.getUser();

        System.out.println(user.getPost());
        if (user.getPost().equals("volonter")) {
            System.out.println("Я волнтёр");
            setContentView(R.layout.volonter_activity_main);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, search_fragment_volonter).commit();

            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.search_fragment_volonter2);
            badgeDrawable.setVisible(false);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.search_fragment_volonter2:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, search_fragment_volonter).commit();
                            return true;
                        case R.id.volonterMainActivity2:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, event_fragment_volonter).commit();
                            return true;
                        case R.id.person_fragment_volonter:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, person_fragment_volonter).commit();
                            return true;
                    }
                    return false;
                }
            });
        }else{
            setContentView(R.layout.counselor_activity_main);
            bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, addevent_counselor).commit();
            BadgeDrawable badgeDrawable = bottomNavigationView2.getOrCreateBadge(R.id.search_fragment_counselor);
            badgeDrawable.setVisible(false);
            System.out.println("Я вожатый");

            bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.search_fragment_counselor:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container2, addevent_counselor).commit();
                            return true;
                        case R.id.event_fragment_counselor:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container2, events_fragment_counselor).commit();
                            return true;
                        case R.id.person_fragment_counselor:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container2, person_fragment_counselor).commit();
                            return true;
                    }
                    return false;
                }
            });
        }

    }
}

/*        String nameString ="Здравствуй, "+ user.getLastname()+" "+user.getFirstname()+" "+user.getPatronymic();
        TextView fullnameView = findViewById(R.id.fullnameView);
        fullnameView.setText(nameString);
        Log.v(TAG, user.getPoints());
        String rateString ="Твой рейтинг: "+user.getPoints();
        TextView rateView = findViewById(R.id.rateView);
        rateView.setText(rateString);
        ImageView img=(ImageView) findViewById(R.id.rateImageView);
        int n = 900;
        if (n>=0 && n<=200) {
            img.setImageResource(R.drawable.wooden);
        } else if (n>=201 && n<=400) {
            img.setImageResource(R.drawable.bronze);
        } else if (n>=401 && n<=600) {
            img.setImageResource(R.drawable.silver);
        } else if (n>=601 && n<=800){
            img.setImageResource(R.drawable.gold);
        } else if (n>=801 && n<=1000){
            img.setImageResource(R.drawable.brilliant);
        }


        toEventButton = findViewById(R.id.toEventButton);

        toEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(VolonterMainActivity.this, VolunteerEventsActivity.class);
            startActivity(intent);
        });*/



//         0 до 200 – Медный
//        201 до 400 – Бронзовый
//        401-600 – Серебряный
//        601-800 – Золотой
//        801 – 1000 – Бриллиантовый