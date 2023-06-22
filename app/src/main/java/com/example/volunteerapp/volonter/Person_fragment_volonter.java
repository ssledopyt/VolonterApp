package com.example.volunteerapp.volonter;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volunteerapp.R;
import com.example.volunteerapp.SignInActivity;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person_fragment_volonter extends Fragment {

    private int rating;
    private ParseObject userParse;

    private ImageView profileImage;
    private TextView fullnameView, rateView, ageView, levelView, postView;
    Button logoutButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_person_counselor, container, false);
        rateView = v.findViewById(R.id.rateView);
        UserPreferences ePref = new UserPreferences(this.getActivity());
        User user = ePref.getUser();
        ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
        queryUser.whereEqualTo("username", user.getLogin());
        queryUser.findInBackground();
        levelView = v.findViewById(R.id.levelView);
        levelView.setText(user.getPost());
        try {
            userParse = queryUser.getFirst();
            rating = userParse.getInt("scores");
            rateView.setText(rating+"");
            if (rating<=200){
                levelView.setText("Деревянный");
            } else if (rating>200 && rating<=400) {
                levelView.setText("Бронзовый");
            } else if (rating>400 && rating<=600) {
                levelView.setText("Медный");
            } else if (rating>600 && rating<=800) {
                levelView.setText("Серебрянный");
            } else if (rating>800 && rating<=1000) {
                levelView.setText("Золотой");
            }

//            });
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        postView = v.findViewById(R.id.postView);
        postView.setText(user.getPost());
        String nameString = user.getLastname()+" "+user.getFirstname()+" "+user.getPatronymic();
        fullnameView = v.findViewById(R.id.fullnameView);

        ageView = v.findViewById(R.id.dateView);
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
        return v;
    }
}