package com.lzb.carexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lzb.carexam.bean.Question;
import com.lzb.carexam.cache.QuestionDBAccess;
import com.lzb.carexam.view.ExerciseActivity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private QuestionDBAccess mQuestionDbAccess;

    private List<Question> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionDbAccess = new QuestionDBAccess(this);

        Question question = mQuestionDbAccess.queryById(20);
        if (question != null) {
            Log.e(TAG, question.getTitle());
        } else {
            Log.e(TAG, "query null");
        }

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestions = mQuestionDbAccess.queryByClassNum(1);
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("testQuestions", (Serializable) mQuestions);
                startActivity(intent);
            }
        });
    }
}
