package com.lzb.carexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lzb.carexam.R;
import com.lzb.carexam.base.Config;
import com.lzb.carexam.bean.Question;
import com.lzb.carexam.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 答题界面
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
                    int classNum = intent.getIntExtra("classNum", -1);
                    mQuestions = mQuestionAccess.queryByClassNum(classNum);
                    break;
                case Config.EXERCISE_TYPE_ONEBYONE:
                    mTvTitle.setText(getResources().getText(R.string.exerciseType_oneByOne));
                    mQuestions = mQuestionAccess.queryAllQuestions();
                    break;
                case Config.EXERCISE_TYPE_ATRANDOM:
                    mTvTitle.setText(getResources().getText(R.string.exerciseType_atRandom));
                    break;
                case Config.EXERCISE_TYPE_MYWRONG:
                    mTvTitle.setText(getResources().getText(R.string.myWrongQuestions));
                    break;
                case Config.EXERCISE_TYPE_MYCOLLECT:
                    mTvTitle.setText(getResources().getText(R.string.myCollect));
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

//    private List<Question> getMyWrongRuestions() {
//        List<Question> wrongQuestions = new ArrayList<>();
//        for (int i = 0; i < Config.totalQuestionCount; i++) {
//            if (SPUtil.contains(this, "Question" + (i + 1));
//        }
//        return wrongQuestions;
//    }

}
