package com.example.volunteerapp.counselor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.volunteerapp.R;
import com.example.volunteerapp.adapters.VolunteerAdapterEvents;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;

public class Events_fragment_counselor extends Fragment {

    private Spinner filter;
    private ListView listView;
    private User user;

    private ProgressDialog progressDialog;

    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_volonter, container, false);
        filter = (Spinner) v.findViewById(R.id.spinnerFilterSV);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.divisions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);

        UserPreferences userPref = new UserPreferences(this.getActivity());
        user = userPref.getUser();

        listView = v.findViewById(R.id.volunteerListView);

        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Загрузка");
        progressDialog.setCancelable(false);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                progressDialog.show();

                ParseObject userObj;
                ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
                queryUser.whereEqualTo("username", user.getLogin());
                System.out.println(user.getLogin());
                queryUser.findInBackground();
                try {
                    userObj = queryUser.getFirst();
                    //System.out.println(userObj.getString("username"));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                ParseQuery<ParseObject> query = new ParseQuery<>("Event");
                switch (filter.getSelectedItem().toString()) {
                    case "Все мероприятия": {
                        //query.whereEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                System.out.println(adapter.getCount());
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Животные": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Животные");
                        query.whereGreaterThan("date", new Date());
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Спорт": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Спорт");
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        //query.whereEqualTo("volunteers", userObj);
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Культура": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Культура");
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        //query.whereEqualTo("volunteers", userObj);
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Духовные": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Духовные");
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Интелектуальные": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Интелектуальные");
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                    case "Социальные": {
                        progressDialog.show();
                        //query.whereNotEqualTo("volonteers", userObj);
                        query.whereEqualTo("organizer", userObj.getString("username"));
                        query.whereEqualTo("direction_id", "Социальные");
                        query.whereGreaterThan("date", new Date());
                        query.orderByDescending("date");
                        query.findInBackground((objects, e) -> {
                            progressDialog.dismiss();
                            if (e == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });
                        return;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }
}
