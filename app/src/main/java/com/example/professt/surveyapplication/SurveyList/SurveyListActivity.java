package com.example.professt.surveyapplication.SurveyList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.professt.surveyapplication.Information;
import com.example.professt.surveyapplication.Key;
import com.example.professt.surveyapplication.ProfileActivity;
import com.example.professt.surveyapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyListActivity extends AppCompatActivity {

    ListView CustomList;
    String email;
    ImageView imgNoData;
    private ProgressDialog loading;

    int MAX_SIZE=999;
    public String userID[]=new String[MAX_SIZE];
    public String Question1[]=new String[MAX_SIZE];
    public String Question2[]=new String[MAX_SIZE];
    public String Question3[]=new String[MAX_SIZE];
    public String Question4[]=new String[MAX_SIZE];
    public String Question5[]=new String[MAX_SIZE];
    public String Answer1[]=new String[MAX_SIZE];
    public String Answer2[]=new String[MAX_SIZE];
    public String Answer3[]=new String[MAX_SIZE];
    public String Answer4[]=new String[MAX_SIZE];
    public String Answer5[]=new String[MAX_SIZE];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);

        CustomList=(ListView)findViewById(R.id.survey_list);

        Information information = Information.getInstance();
        email = information.getEmail();

        getData();
    }

    private void getData() {

        loading = new ProgressDialog(SurveyListActivity.this);
        loading.setIcon(R.drawable.load);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Key.GET_ALL_DATA+email;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(SurveyListActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(SurveyListActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Key.JSON_ARRAY);


            if (result.length()==0)
            {
                Toast.makeText(SurveyListActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SurveyListActivity.this, ProfileActivity.class);

                startActivity(intent);
                //imgNoData.setImageResource(R.drawable.nodata);
            }

            else {
                for (int i = 0; i < result.length(); i++) {

                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(Key.KEY_ID);
                    String question1 = jo.getString(Key.KEY_QUESTION1);
                    String question2 = jo.getString(Key.KEY_QUESTION2);
                    String question3 = jo.getString(Key.KEY_QUESTION3);
                    String question4 = jo.getString(Key.KEY_QUESTION4);
                    String question5 = jo.getString(Key.KEY_QUESTION5);
                    String answer1 = jo.getString(Key.KEY_ANSWER1);
                    String answer2 = jo.getString(Key.KEY_ANSWER2);
                    String answer3 = jo.getString(Key.KEY_ANSWER3);
                    String answer4 = jo.getString(Key.KEY_ANSWER4);
                    String answer5 = jo.getString(Key.KEY_ANSWER5);
                    String date = jo.getString(Key.KEY_DATE);
                    String time = jo.getString(Key.KEY_TIME);

                    //insert data into array for put extra
                    userID[i] = id;
                    Question1[i] = question1;
                    Question2[i] = question2;
                    Question3[i] = question3;
                    Question4[i] = question4;
                    Question5[i] = question5;
                    Answer1[i] = answer1;
                    Answer2[i] = answer2;
                    Answer3[i] = answer3;
                    Answer4[i] = answer4;
                    Answer5[i] = answer5;

                    //put value into Hashmap
                    HashMap<String, String> user_data = new HashMap<>();
                    user_data.put(Key.KEY_DATE, date);
                    user_data.put(Key.KEY_TIME, time);

                    list.add(user_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                SurveyListActivity.this, list, R.layout.survey_list,
                new String[]{Key.KEY_DATE, Key.KEY_TIME}, //,Key.KEY_IMAGE
                new int[]{R.id.txt_date, R.id.txt_time}); //,R.id.list_icon
        CustomList.setAdapter(adapter);



        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Intent intent=new Intent(SurveyListActivity.this,SurveyDetailsActivity.class);
                intent.putExtra("id",userID[position]);
                intent.putExtra("question1",Question1[position]);
                intent.putExtra("question2",Question2[position]);
                intent.putExtra("question3",Question3[position]);
                intent.putExtra("question4",Question4[position]);
                intent.putExtra("question5",Question5[position]);
                intent.putExtra("answer1",Answer1[position]);
                intent.putExtra("answer2",Answer2[position]);
                intent.putExtra("answer3",Answer3[position]);
                intent.putExtra("answer4",Answer4[position]);
                intent.putExtra("answer5",Answer5[position]);


                startActivity(intent);



            }
        });



    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(SurveyListActivity.this,ProfileActivity.class);
        startActivity(intent);

    }
}
