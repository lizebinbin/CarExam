package com.lzb.carexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lzb.carexam.R;
import com.lzb.carexam.bean.Question;

import java.util.List;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class ExerciseActivity extends ExerciseBaseActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exercise);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mQuestions = (List<Question>) intent.getSerializableExtra("testQuestions");
        if (mQuestions != null) {
            if (mQuestions.size() == 0) {
                Log.e(TAG, "size == 0");
            } else {
                showQuestion(mCurrentPosition);
            }
        }
    }


}
