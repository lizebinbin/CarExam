package com.lzb.carexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lzb.carexam.R;
import com.lzb.carexam.base.Config;
import com.lzb.carexam.bean.Question;
import com.lzb.carexam.cache.QuestionDBAccess;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MooreLi on 2016/8/12.
 */
public class SelectExerciseTypeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlExerciseByClass, mLlExerciseOneByOne, mLlExerciseAtRandom, mLlExerciseTakeExam;
    private LinearLayout mLlMyWrongQuestions, mLlMyCollect, mLlMyRecords;

    private QuestionDBAccess mQuestionDbAccess;
    private List<Question> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise_type);
        initViews();
        mQuestionDbAccess = new QuestionDBAccess(this);
    }

    private void initViews() {
        mLlExerciseByClass = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_class);
        mLlExerciseOneByOne = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_oneByOne);
        mLlExerciseAtRandom = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_atRandom);
        mLlExerciseTakeExam = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_takeExam);
        mLlMyWrongQuestions = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_MyWrong);
        mLlMyCollect = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_MyCollect);
        mLlMyRecords = (LinearLayout) findViewById(R.id.SelectExerciseType_ll_MyRecord);

        mLlExerciseByClass.setOnClickListener(this);
        mLlExerciseOneByOne.setOnClickListener(this);
        mLlExerciseAtRandom.setOnClickListener(this);
        mLlExerciseTakeExam.setOnClickListener(this);
        mLlMyWrongQuestions.setOnClickListener(this);
        mLlMyCollect.setOnClickListener(this);
        mLlMyRecords.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.SelectExerciseType_ll_class:
                intent = new Intent(this,SelectClassActivity.class);
                break;
            case R.id.SelectExerciseType_ll_oneByOne:
                intent = new Intent(this, ExerciseActivity.class);
//                mQuestions = mQuestionDbAccess.queryAllQuestions();
                intent.putExtra("exerciseType", Config.EXERCISE_TYPE_ONEBYONE);
                break;
            case R.id.SelectExerciseType_ll_atRandom:

                break;
            case R.id.SelectExerciseType_ll_takeExam:

                break;
            case R.id.SelectExerciseType_ll_MyWrong:

                break;
            case R.id.SelectExerciseType_ll_MyCollect:

                break;
            case R.id.SelectExerciseType_ll_MyRecord:

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
