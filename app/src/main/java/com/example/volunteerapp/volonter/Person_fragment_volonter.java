package com.example.volunteerapp.volonter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.volunteerapp.R;
import com.example.volunteerapp.SignInActivity;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.example.volunteerapp.volonter.VolonterMainActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person_fragment_volonter extends Fragment {

    private int rating;
    private ParseObject userParse;
    private TextView fullnameView, rateView, ageView;
    private ProgressBar barView;
    Button toEventButton;
    Button logoutButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_person_volonter, container, false);
        rateView = v.findViewById(R.id.mainView_rate);
        barView = v.findViewById(R.id.progressBar2);
        UserPreferences ePref = new UserPreferences(this.getActivity());
        User user = ePref.getUser();
        ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
        queryUser.whereEqualTo("username", user.getLogin());
        queryUser.findInBackground();
        try {
            userParse = queryUser.getFirst();
/*            ParseQuery<ParseObject> pointsQuery = new ParseQuery<>("Rating");
            pointsQuery.whereEqualTo("user", userParse);
            pointsQuery.getFirstInBackground((object, e) -> {*/
            rating = userParse.getInt("scores");
            barView.setMax(1000);
            barView.setProgress(rating);
            String rateString ="Баллов активности: " + rating;
            rateView.setText(rateString);
            if (rating<=200){
                barView.getProgressDrawable().setColorFilter(Color.rgb(255, 155, 82), android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (rating>200 && rating<=400) {
                barView.getProgressDrawable().setColorFilter(Color.rgb(142, 85, 0), android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (rating>400 && rating<=600) {
                barView.getProgressDrawable().setColorFilter(Color.rgb(114, 114, 114), android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (rating>600 && rating<=800) {
                barView.getProgressDrawable().setColorFilter(Color.rgb(255, 206, 82 ), android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (rating>800 && rating<=1000) {
                barView.getProgressDrawable().setColorFilter(Color.rgb(111, 199, 199), android.graphics.PorterDuff.Mode.SRC_IN);
            }


//            });
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String nameString = user.getLastname()+" "+user.getFirstname()+" "+user.getPatronymic();
        fullnameView = v.findViewById(R.id.fullnameView);

        ageView = v.findViewById(R.id.mainView_age);
        fullnameView.setText(nameString);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday;
        try {
            birthday = myFormat.parse(userParse.getString("date_of_birthday"));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        Long time = new Date().getTime() / 1000 - birthday.getTime() / 1000;
        int years = Math.round(time) / 31536000;
        ageView.setText("Возраст: "+years);

        logoutButton = v.findViewById(R.id.imageButtonExit);
        logoutButton.setOnClickListener(view -> {
            ParseUser.logOutInBackground();
            UserPreferences uPref = new UserPreferences(getActivity());
            uPref.setEntered(false);
            Intent intent = new Intent(this.getActivity(), SignInActivity.class);
            startActivity(intent);
        });
        /*toEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(this.getActivity(), VolunteerEventsActivity.class);
            startActivity(intent);
        });*/
        return v;
    }
}