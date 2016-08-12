package com.lzb.carexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lzb.carexam.R;
import com.lzb.carexam.base.Config;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class ExerciseActivity extends ExerciseBaseActivity {
    private String TAG = getClass().getSimpleName();

    private int sourceType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exercise);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        sourceType = intent.getIntExtra("exerciseType", -1);
        if (sourceType != -1) {
            switch (sourceType) {
                case Config.EXERCISE_TYPE_CLASS:
                    mTvTitle.setText(getResources().getText(R.string.exerciseType_class));
                    break;
                case Config.EXERCISE_TYPE_ONEBYONE:
                    mTvTitle.setText(getResources().getText(R.string.exerciseType_oneByOne));
                    mQuestions = mQuestionAccess.queryAllQuestions();
                    break;
                case Config.EXERCISE_TYPE_ATRANDOM:
                    mTvTitle.setText(getResources().getText(R.string.exerciseType_atRandom));

                    break;
            }

            if (mQuestions != null) {
                if (mQuestions.size() == 0) {
                    Log.e(TAG, "size == 0");
                } else {
                    showQuestion(mCurrentPosition);
                }
            }

        }

    }


}
