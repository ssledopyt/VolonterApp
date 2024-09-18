package com.example.volunteerapp.counselor;

import static java.lang.Integer.parseInt;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.volunteerapp.R;
import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.parse.ParseObject;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_event_counselor extends Fragment {
    private User user;


    //private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    private Button addevent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_counselor, container2, false);

        UserPreferences userPref = new UserPreferences(this.getActivity());
        user = userPref.getUser();

        EditText titleField = v.findViewById(R.id.eventEditTextTitle);
        EditText descriptionField = v.findViewById(R.id.eventEditTextDescription);
        EditText quantityField = v.findViewById(R.id.eventEditTextQuantityMax);
        EditText venueField = v.findViewById(R.id.eventEditTextVenue);
        MaskedEditText dateField = v.findViewById(R.id.eventEditTextDate);
        Button addevent = v.findViewById(R.id.buttonAddEvent);

        Spinner spinnerHours = v.findViewById(R.id.spinnerHours);
        ArrayAdapter<CharSequence> adapterHours = ArrayAdapter.createFromResource(getActivity(), R.array.hours, android.R.layout.simple_spinner_dropdown_item);
        //adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(adapterHours);

        Spinner spinnerMinutes = v.findViewById(R.id.spinnerMinutes);
        ArrayAdapter<CharSequence> adapterMinutes = ArrayAdapter.createFromResource(getActivity(), R.array.minutes, android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(adapterMinutes);

        Spinner spinnerDivision = v.findViewById(R.id.spinnerDivision);
        ArrayAdapter<CharSequence> adapterDivision = ArrayAdapter.createFromResource(getActivity(), R.array.division, android.R.layout.simple_spinner_dropdown_item);
        spinnerDivision.setAdapter(adapterDivision);

        System.out.println(user.getLogin());
        addevent.setOnClickListener(view -> {
            UserPreferences uP = new UserPreferences(view.getContext());
            User user = uP.getUser();
            ParseObject event = new ParseObject("Event");
            if((TextUtils.isEmpty(titleField.getText().toString())) ||
                    (TextUtils.isEmpty(dateField.getText().toString())) ||
                    (TextUtils.isEmpty(descriptionField.getText().toString())) ||
                    (TextUtils.isEmpty(quantityField.getText().toString())) ||
                    (TextUtils.isEmpty(venueField.getText().toString()))) {
                Toast.makeText(getActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            } else {
                event.put("title", titleField.getText().toString());
                event.put("description", descriptionField.getText().toString());
                event.put("direction_id", spinnerDivision.getSelectedItem());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date;
                try {
                    date = df.parse(dateField.getText().toString()+" "+spinnerHours.getSelectedItem()+":"+spinnerMinutes.getSelectedItem());
                } catch (java.text.ParseException e) {
                    throw new RuntimeException(e);
                }
                event.put("date", date);
                event.put("quantity_max", Integer.parseInt(quantityField.getText().toString()));
                event.put("organizer", user.getLogin());
                event.put("venue", venueField.getText().toString());
                event.saveInBackground(e -> {
                    if(e == null){
                        Toast.makeText(view.getContext(), "Мероприятие добавлено", Toast.LENGTH_SHORT).show();
/*                        ParseQuery<ParseObject> query = new ParseQuery<>("Event");
                        query.whereGreaterThan("date", new Date());
                        query.findInBackground((objects, ex) -> {
                            progressDialog.dismiss();
                            if (ex == null) {
                                VolunteerAdapterEvents adapter = new VolunteerAdapterEvents(getActivity(), objects);
                                listView.setAdapter(adapter);
                            } else {

                            }
                        });*/
                    } else {
                        Toast.makeText(view.getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return v;
    }
}

    /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySpinner = view.findViewById(R.id.spinnerFilter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.divisions, R.layout.fragment_search_volonter);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);
    }*/
