package com.example.professt.surveyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.professt.surveyapplication.SurveyList.SurveyListActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView userName,userEmail;
    CircleImageView profileImage;
    Button startSurvey,surveyList;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> types = new ArrayList<String>();
    ArrayList<String> optionss = new ArrayList<String>();
    ArrayList<String> requireds = new ArrayList<String>();

    private ProgressDialog loading;

    String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName=(TextView)findViewById(R.id.name);
        userEmail=(TextView)findViewById(R.id.email);
        profileImage=(CircleImageView) findViewById(R.id.profileImage);

        startSurvey = (Button)findViewById(R.id.startSurvey);
        surveyList = (Button)findViewById(R.id.surveyList);

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        startSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,SurveyActivity.class);
                startActivity(intent);
            }
        });
        surveyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,SurveyListActivity.class);
                startActivity(intent);
            }
        });

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        timestamp = formatter.format(date);
        Information information = Information.getInstance();
        information.setTimestamp(timestamp);

        getData();
    }

    private void getData() {

        //for showing progress dialog
        loading = new ProgressDialog(ProfileActivity.this);
        loading.setIcon(R.drawable.load);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Key.GET_DATA;

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
                        Toast.makeText(ProfileActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
//            jsonObject = new JSONObject(response);
//            JSONArray result = jsonObject.getJSONArray(Key.JSON_ARRAY);

            JSONArray jsonarray = new JSONArray(response);

            for(int i=0; i < jsonarray.length(); i++) {
                JSONObject jo = jsonarray.getJSONObject(i);
                String question = jo.getString(Key.KEY_QUESTION);
                String type = jo.getString(Key.KEY_TYPE);
                String options = jo.getString(Key.KEY_OPTIONS);
                String required = jo.getString(Key.KEY_REQUIRED);

                questions.add(question);
                types.add(type);
                optionss.add(options);
                requireds.add(required);

                //Log.d("question", "question: "+question);



            }

            Information information = Information.getInstance();
            information.setQuestions(questions);
            information.setTypes(types);
            information.setOptions(optionss);
            information.setRequired(requireds);

            Log.d("question", "question: "+questions);
            Log.d("types", "types: "+types);
            Log.d("optionss", "optionss: "+optionss);
            Log.d("requireds", "requireds: "+requireds);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    //this method takes the "menu_layout.xml" file in java file

        MenuInflater menuInflater = getMenuInflater();   //"MenuInflater" is a service which coverts xml file into java file
        menuInflater.inflate(R.menu.menu_layout,menu);    //"Inflate" method turns the xml file into java file

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.exit)        //compare with the selected item id with item by "getItemId" method
        {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.isSuccess()){
                                gotoMainActivity();
                            }else{
                                Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            return true;      //Because the method returns a boolean value
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
            Information information = Information.getInstance();
            information.setEmail(account.getEmail());

            try{
                if(account.getPhotoUrl().toString().isEmpty())
                {

                }
                else
                {
                    Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
                }
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        }else{
            gotoMainActivity();
        }
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to stay here!")
                .setConfirmText("Exit")
                .setCancelText("Cancel")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

    }
}
