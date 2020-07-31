package com.example.professt.surveyapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SurveyActivity4 extends AppCompatActivity {

    ImageView BackId;
    TextView nextId,questionId,timestamp;
    Spinner useProducts;
    CheckBox yescheckId,nocheckId;
    EditText address,contactNumber;
    RadioGroup radioGrroup;
    RadioButton feelings;

    RadioButton verygood;
    RadioButton good;
    RadioButton neutral;
    RadioButton bad;
    RadioButton very_bad;

    RelativeLayout relativeLayout,relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4;

    String question,type,timestamps;

    String[] userproduct;
    ArrayList<String> user_product = new ArrayList<String>();


    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey4);

        BackId = (ImageView)findViewById(R.id.BackId);
        nextId = (TextView) findViewById(R.id.nextId);
        questionId = (TextView)findViewById(R.id.questionId);
        timestamp = (TextView)findViewById(R.id.timestamp);

        useProducts = (Spinner)findViewById(R.id.useProducts);

        yescheckId = (CheckBox)findViewById(R.id.yescheckId);
        nocheckId = (CheckBox)findViewById(R.id.nocheckId);

        address = (EditText)findViewById(R.id.address);
        contactNumber = (EditText)findViewById(R.id.contactNumber);

        radioGrroup = (RadioGroup)findViewById(R.id.radioGroupId);
        verygood = (RadioButton)findViewById(R.id.verygood);
        good = (RadioButton)findViewById(R.id.good);
        neutral = (RadioButton)findViewById(R.id.neutral);
        bad = (RadioButton)findViewById(R.id.bad);
        very_bad = (RadioButton)findViewById(R.id.very_bad);

        Information information2 = Information.getInstance();
        timestamps = information2.getTimestamp();

        timestamp.setText(timestamps);

        userproduct = getResources().getStringArray(R.array.user_product);

        for (int i=0;i<userproduct.length;i++)
        {
            user_product.add(userproduct[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SurveyActivity4.this,R.layout.sample_view,R.id.textViewId,userproduct); //creating object of ArrayAdapter....thre are 4 parameters in ArrayAdapter
        useProducts.setAdapter(adapter);   //setting the adapter in spinner

        relativeLayout = (RelativeLayout)findViewById(R.id.radioButtons);
        relativeLayout1 = (RelativeLayout)findViewById(R.id.dropdown);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.numbers);
        relativeLayout3 = (RelativeLayout)findViewById(R.id.texts);
        relativeLayout4 = (RelativeLayout)findViewById(R.id.checkboxs);

        Information information = Information.getInstance();
        question = information.getQuestions().get(3);
        type = information.getTypes().get(3);

        if(!(type.equalsIgnoreCase("multiple choice")))
        {
            relativeLayout.setVisibility(View.GONE);
        }

        if(!(type.equalsIgnoreCase("dropdown")))
        {
            relativeLayout1.setVisibility(View.GONE);
        }

        if(!(type.equalsIgnoreCase("number")))
        {
            relativeLayout2.setVisibility(View.GONE);
        }

        if(!(type.equalsIgnoreCase("text")))
        {
            relativeLayout3.setVisibility(View.GONE);
        }

        if(!(type.equalsIgnoreCase("Checkbox")))
        {
            relativeLayout4.setVisibility(View.GONE);
        }

        questionId.setText(question);

        yescheckId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    nocheckId.setChecked(false);
                }
            }
        });

        nocheckId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    yescheckId.setChecked(false);
                }
            }
        });

        nextId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equalsIgnoreCase("multiple choice"))
                {
                    try {
                        int selectedId = radioGrroup.getCheckedRadioButtonId();
                        feelings = (RadioButton) findViewById(selectedId);

                        String answer = feelings.getText().toString();
                        String question = questionId.getText().toString();

                        Information information1 = Information.getInstance();
                        information1.setQuestion4(question);
                        information1.setAnswer4(answer);

                        Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                        startActivity(intent);

                    }
                    catch(Exception e){
                        Toast.makeText(SurveyActivity4.this,"Please select anything",Toast.LENGTH_SHORT).show();

                    }
                }

                if(type.equalsIgnoreCase("dropdown"))
                {
                    String answer = useProducts.getSelectedItem().toString();
                    String question = questionId.getText().toString();

                    if(answer.equalsIgnoreCase("Select"))
                    {
                        Toast.makeText(SurveyActivity4.this, "Please select from the drop down", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Information information1 = Information.getInstance();
                        information1.setQuestion4(question);
                        information1.setAnswer4(answer);

                        Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                        startActivity(intent);
                    }


                }

                if(type.equalsIgnoreCase("number"))
                {
                    String answer = contactNumber.getText().toString();
                    String question = questionId.getText().toString();

                    Information information1 = Information.getInstance();
                    information1.setQuestion4(question);
                    information1.setAnswer4(answer);

                    Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                    startActivity(intent);

                }

                if(type.equalsIgnoreCase("text"))
                {
                    String answer = address.getText().toString();
                    String question = questionId.getText().toString();

                    if(answer.isEmpty())
                    {
                        address.setError("Please Enter Address");
                        address.requestFocus();
                    }
                    else
                    {
                        Information information1 = Information.getInstance();
                        information1.setQuestion4(question);
                        information1.setAnswer4(answer);

                        Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                        startActivity(intent);
                    }



                }

                if(type.equalsIgnoreCase("Checkbox"))
                {

                    if(yescheckId.isChecked())
                    {
                        String answer = yescheckId.getText().toString();
                        String question = questionId.getText().toString();

                        Information information1 = Information.getInstance();
                        information1.setQuestion4(question);
                        information1.setAnswer4(answer);

                        Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                        startActivity(intent);
                    }
                    else
                    {
                        if(nocheckId.isChecked())
                        {
                            String answer = nocheckId.getText().toString();
                            String question = questionId.getText().toString();

                            Information information1 = Information.getInstance();
                            information1.setQuestion4(question);
                            information1.setAnswer4(answer);

                            Intent intent = new Intent(SurveyActivity4.this,SurveyActivity5.class);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(SurveyActivity4.this, "Please choose", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

        getData();

        BackId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(SurveyActivity4.this,SurveyActivity3.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        Information information = Information.getInstance();
        String question = information.getQuestion4();
        String answer = information.getAnswer4();

        if(!(answer.isEmpty()))
        {
            if(type.equalsIgnoreCase("dropdown"))
            {
                useProducts.setSelection(user_product.indexOf(answer),true);
            }

            if(type.equalsIgnoreCase("multiple choice"))
            {
                if(answer.equalsIgnoreCase("Very Good"))
                {
                    verygood.setChecked(true);
                }
                if(answer.equalsIgnoreCase("Good"))
                {
                    good.setChecked(true);
                }
                if(answer.equalsIgnoreCase("Neutral"))
                {
                    neutral.setChecked(true);
                }
                if(answer.equalsIgnoreCase("Bad"))
                {
                    bad.setChecked(true);
                }
                if(answer.equalsIgnoreCase("Very Bad"))
                {
                    very_bad.setChecked(true);
                }
            }

            if(type.equalsIgnoreCase("number"))
            {
                contactNumber.setText(answer);
            }

            if(type.equalsIgnoreCase("text"))
            {
                address.setText(answer);
            }

            if(type.equalsIgnoreCase("Checkbox"))
            {
                if(answer.equalsIgnoreCase("Yes"))
                {
                    yescheckId.setChecked(true);
                }
                if(answer.equalsIgnoreCase("No"))
                {
                    nocheckId.setChecked(true);
                }
            }

        }


    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(SurveyActivity4.this,SurveyActivity3.class);
        startActivity(intent);

    }
}
