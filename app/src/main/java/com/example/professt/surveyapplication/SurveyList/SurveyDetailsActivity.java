package com.example.professt.surveyapplication.SurveyList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.professt.surveyapplication.R;

public class SurveyDetailsActivity extends AppCompatActivity {

    TextView question1,answer1,question2,answer2,question3,answer3,question4,answer4,question5,answer5;

    String questions1,answers1;
    String questions2,answers2;
    String questions3,answers3;
    String questions4,answers4;
    String questions5,answers5;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        question1 = (TextView)findViewById(R.id.question1Id);
        question2 = (TextView)findViewById(R.id.question2Id);
        question3 = (TextView)findViewById(R.id.question3Id);
        question4 = (TextView)findViewById(R.id.question4Id);
        question5 = (TextView)findViewById(R.id.question5Id);
        answer1 = (TextView)findViewById(R.id.answer1Id);
        answer2 = (TextView)findViewById(R.id.answer2Id);
        answer3 = (TextView)findViewById(R.id.answer3Id);
        answer4 = (TextView)findViewById(R.id.answer4Id);
        answer5 = (TextView)findViewById(R.id.answer5Id);

        userId = getIntent().getExtras().getString("id");
        questions1 = getIntent().getExtras().getString("question1");
        questions2 = getIntent().getExtras().getString("question2");
        questions3 = getIntent().getExtras().getString("question3");
        questions4 = getIntent().getExtras().getString("question4");
        questions5 = getIntent().getExtras().getString("question5");
        answers1 = getIntent().getExtras().getString("answer1");
        answers2 = getIntent().getExtras().getString("answer2");
        answers3 = getIntent().getExtras().getString("answer3");
        answers4 = getIntent().getExtras().getString("answer4");
        answers5 = getIntent().getExtras().getString("answer5");

        question1.setText(questions1);
        question2.setText(questions2);
        question3.setText(questions3);
        question4.setText(questions4);
        question5.setText(questions5);
        answer1.setText(answers1);
        answer2.setText(answers2);
        answer3.setText(answers3);
        answer4.setText(answers4);
        answer5.setText(answers5);
    }

    @Override
    public void onBackPressed() {           //creating method of onBackPressed

        Intent intent =  new Intent(SurveyDetailsActivity.this,SurveyListActivity.class);
        startActivity(intent);

    }
}
