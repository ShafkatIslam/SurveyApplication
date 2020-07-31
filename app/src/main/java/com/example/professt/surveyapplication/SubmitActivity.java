package com.example.professt.surveyapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SubmitActivity extends AppCompatActivity {

    Button submitSurvey;

    String question1,answer1;
    String question2,answer2;
    String question3,answer3;
    String question4,answer4;
    String question5,answer5;
    String email;

    String date,time,timestamp;

    ProgressDialog loading;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        submitSurvey = (Button)findViewById(R.id.submitSurvey);

        Information information = Information.getInstance();
        question1 = information.getQuestion1();
        question2 = information.getQuestion2();
        question3 = information.getQuestion3();
        question4 = information.getQuestion4();
        question5 = information.getQuestion5();
        answer1 = information.getAnswer1();
        answer2 = information.getAnswer2();
        answer3 = information.getAnswer3();
        answer4 = information.getAnswer4();
        answer5 = information.getAnswer5();
        email = information.getEmail();
        timestamp = information.getTimestamp();

        String[] date_time = timestamp.split(" ");
        date = date_time[0];
        time = date_time[1];

        Log.d("question1", "question1: "+question1);
        Log.d("question2", "question2: "+question2);
        Log.d("question3", "question3: "+question3);
        Log.d("question4", "question4: "+question4);
        Log.d("question5", "question5: "+question5);
        Log.d("answer1", "answer1: "+answer1);
        Log.d("answer2", "answer2: "+answer2);
        Log.d("answer3", "answer3: "+answer3);
        Log.d("answer4", "answer4: "+answer4);
        Log.d("answer5", "answer5: "+answer1);

        submitSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading = new ProgressDialog(SubmitActivity.this);
                loading.setIcon(R.drawable.load);
                loading.setTitle("Submit");
                loading.setMessage("Please wait...");
                loading.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Key.SUBMIT_SURVEY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //for track response in Logcat
                        Log.d("RESPONSEs", "" + response);

                        //if we are getting success from server
                        if (response.equals("success")) {
                            //creating a shared preference
                            loading.dismiss();
                            //starting profile activity
                            Intent intent = new Intent(SubmitActivity.this, ProfileActivity.class);
//                            intent.putExtra("email",emails);
                            startActivity(intent);

                            Toast.makeText(SubmitActivity.this, "Submission Successfull", Toast.LENGTH_SHORT).show();


                        }
                        else if (response.equals("failure")) {
                            Toast.makeText(SubmitActivity.this, "Submission failed", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SubmitActivity.this, "There is an error", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SubmitActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                loading.dismiss();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //return super.getParams();

                        Map<String,String> params = new HashMap<>();

                        //Ading parameters to request
                        params.put(Key.KEY_QUESTION1,question1);
                        params.put(Key.KEY_QUESTION2,question2);
                        params.put(Key.KEY_QUESTION3,question3);
                        params.put(Key.KEY_QUESTION4,question4);
                        params.put(Key.KEY_QUESTION5,question5);
                        params.put(Key.KEY_ANSWER1,answer1);
                        params.put(Key.KEY_ANSWER2,answer2);
                        params.put(Key.KEY_ANSWER3,answer3);
                        params.put(Key.KEY_ANSWER4,answer4);
                        params.put(Key.KEY_ANSWER5,answer5);
                        params.put(Key.KEY_EMAIL,email);
                        params.put(Key.KEY_DATE,date);
                        params.put(Key.KEY_TIME,time);

                        //returning parameter
                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(SubmitActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(SubmitActivity.this,SurveyActivity5.class);
        startActivity(intent);

    }
}
