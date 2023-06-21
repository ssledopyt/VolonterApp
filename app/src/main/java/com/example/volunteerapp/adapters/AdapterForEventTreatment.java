package com.example.volunteerapp.adapters;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.volunteerapp.R;
import com.example.volunteerapp.SignInActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class AdapterForEventTreatment extends BaseAdapter  {
    private ProgressDialog progressDialog;
    private String id;
    private List<ParseObject> list;
    private LayoutInflater inflater ;
    private Context context;
    private ParseObject userPoints;

    public AdapterForEventTreatment (Context context, List<ParseObject> list, String id){
        this.context = context;
        this.list = list;
        this.id = id;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void remove(int position){
        list.remove(list.get(position));
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public ParseObject getItem(int pos) {
        return list.get(pos);
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = inflater.inflate(R.layout.custom_lv_treatment_item, viewGroup, false);
        }
        TextView userName = v.findViewById(R.id.user_name);
        ImageButton acceptButton = v.findViewById(R.id.acceptButtonC);
        ImageButton cancelButton = v.findViewById(R.id.cancelButtonC);

/*        ParseObject user = getItem(i).getParseObject("user");
        try {
            user.fetchIfNeeded();
        } catch (com.parse.ParseException e) {
            throw new RuntimeException(e);
        }*/

/*        ParseQuery<ParseObject> userRatingQuery = new ParseQuery<>("Rating");
        userRatingQuery.whereEqualTo("user", user);
        userRatingQuery.getFirstInBackground((object, e) -> {
            userRating.setText("Рейтинг: " + object.getInt("points"));
        });*/

        //ParseRelation<ParseObject> relationVolunteers = getItem(i).getRelation("application");
        userName.setText(getItem(i).getString("lastname")+ " "+ getItem(i).getString("firstname")+ " "+ getItem(i).getString("patronymic"));
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Загрузка");
        progressDialog.setCancelable(false);
        System.out.println(list.size());
        if (getItem(i).getObjectId()==null){

            userName.setVisibility(view.GONE);
            acceptButton.setVisibility(view.GONE);
            cancelButton.setVisibility(view.GONE);
        }
        acceptButton.setOnClickListener(view1 -> {
            progressDialog.show();
            ParseQuery<ParseObject> parseEvent = ParseQuery.getQuery("Event");
            parseEvent.getInBackground(id, (ev1, e) -> {
                if (e == null) {
/*                    ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
                    queryUser.whereEqualTo("username", getItem(i).getString("login"));
                    queryUser.findInBackground();
                    ParseObject userObj;
                    try {
                        userObj = queryUser.getFirst();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }*/
//                    ParseQuery<ParseObject> queryUser = new ParseQuery<>("_User");
//                    queryUser.findInBackground();
//                    queryUser.getInBackground(id, (ev2, e2) -> {
                        if(ev1.getInt("quantity_current")<ev1.getInt("quantity_max")){
                            ev1.put("quantity_current", ev1.getInt("quantity_current")+1);
                            ev1.saveInBackground();
                            ParseRelation<ParseObject> relationVolunteers = ev1.getRelation("volunteers");
                            relationVolunteers.add(getItem(i));
                            ev1.saveInBackground();
                            ParseRelation<ParseObject> relationVolunteers2 = ev1.getRelation("applications");
                            relationVolunteers2.remove(getItem(i));
                            ev1.saveInBackground(e1 -> {
                                if (e1 == null) {
                                    progressDialog.dismiss();
                                    Toast.makeText(view1.getContext(), "Заявка принята", Toast.LENGTH_LONG).show();
                                    remove(i);
                                    notifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(view1.getContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(view1, "На мероприятии максимальное количество участников", Snackbar.LENGTH_SHORT).show();
                        }

//                    });

                } else {
                    Toast.makeText(view1.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        cancelButton.setOnClickListener(view1 -> {
            ParseQuery<ParseObject> parseV = ParseQuery.getQuery("Event");
            parseV.getInBackground(id, (event, e) -> {
                if (e == null) {
                    ParseRelation<ParseObject> relationApplication = event.getRelation("applications");
                    relationApplication.remove(getItem(i));
                    event.saveInBackground(e1 -> {
                        if (e1 == null) {
                            Log.d(TAG, "sucess" );
                            remove(i);
                            notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "not sucess"+ e1);
                        }
                    });
                } else {
                    Log.d(TAG, "not sucess" );
                }
            });
        });
        return v;
    }
}