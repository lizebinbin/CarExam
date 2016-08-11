package com.lzb.carexam.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzb.carexam.R;
import com.lzb.carexam.bean.Question;
import com.lzb.carexam.util.StringUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class ExerciseBaseActivity extends BaseActivity implements View.OnClickListener {
    TextView mTvQuestion, mTvLast, mTvNext, mTvGotoClick;
    EditText mEtGotoNum;
    ImageView mIvImage;
    RadioGroup mRgAnswers;
    RadioButton mRbAnswerA, mRbAnswerB, mRbAnswerC, mRbAnswerD;

    List<Question> mQuestions;

    int mCurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mTvQuestion = (TextView) findViewById(R.id.exercise_tv_question);
        mTvLast = (TextView) findViewById(R.id.exercise_tv_last);
        mTvNext = (TextView) findViewById(R.id.exercise_tv_next);
        mTvGotoClick = (TextView) findViewById(R.id.exercise_tv_gotoClick);
        mEtGotoNum = (EditText) findViewById(R.id.exercise_et_gotoNum);
        mIvImage = (ImageView) findViewById(R.id.exercise_iv_img);
        mRgAnswers = (RadioGroup) findViewById(R.id.exercise_rp_answers);
        mRbAnswerA = (RadioButton) findViewById(R.id.exercise_rb_answerA);
        mRbAnswerB = (RadioButton) findViewById(R.id.exercise_rb_answerB);
        mRbAnswerC = (RadioButton) findViewById(R.id.exercise_rb_answerC);
        mRbAnswerD = (RadioButton) findViewById(R.id.exercise_rb_answerD);

        mTvLast.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvGotoClick.setOnClickListener(this);
    }

    /**
     * 显示题目
     *
     * @param position
     */
    public void showQuestion(int position) {
        if (position >= 0 && position < mQuestions.size()) {
            Question question = mQuestions.get(position);
            if (question != null) {
                String index = (mCurrentPosition + 1) + "/" + mQuestions.size() + "、";
                mTvQuestion.setText(index + question.getTitle());
                mRbAnswerA.setText(question.getAnswerA());
                mRbAnswerB.setText(question.getAnswerB());
                if (StringUtil.isEmpty(question.getAnswerC())) {
                    mRbAnswerC.setVisibility(View.GONE);
                } else {
                    mRbAnswerC.setVisibility(View.VISIBLE);
                    mRbAnswerC.setText(question.getAnswerC());
                }
                if (StringUtil.isEmpty(question.getAnswerD())) {
                    mRbAnswerD.setVisibility(View.GONE);
                } else {
                    mRbAnswerD.setVisibility(View.VISIBLE);
                    mRbAnswerD.setText(question.getAnswerD());
                }
                if (StringUtil.isNotEmpty(question.getImgPath())) {
                    mIvImage.setVisibility(View.VISIBLE);
                    Log.e("AAA","path:"+question.getImgPath());
                    Picasso.with(this).load(question.getImgPath()).error(R.drawable.exercise_default).into(mIvImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("AAA", "success");
                        }

                        @Override
                        public void onError() {
                            Log.e("AAA", "error");
                        }
                    });
                } else {
                    mIvImage.setVisibility(View.GONE);
                }
            }
        }
    }

    private void goToPointPosition() {
        String inputNum = mEtGotoNum.getText().toString();
        //输入为空
        if (StringUtil.isEmpty(inputNum)) {
            //Toast
            return;
        }
        //输入不是数字
        if (!StringUtil.isNumber(inputNum)) {
            //Toast
            return;
        }
        int getPosition = mCurrentPosition;
        try {
            getPosition = Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            //转换失败
            getPosition = mCurrentPosition;
            //Toast
            return;
        }
        //判断是否超过范围
        if (getPosition < 1 || getPosition > mQuestions.size()) {

            return;
        }
        mCurrentPosition = getPosition-1;
        showQuestion(mCurrentPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_tv_last:
                if (mCurrentPosition == 0) {
                    Log.e("base", "first one");
                    return;
                } else {
                    mCurrentPosition--;
                }
                break;
            case R.id.exercise_tv_next:
                if (mCurrentPosition == mQuestions.size() - 1) {
                    Log.e("base", "last one");
                    return;
                } else {
                    mCurrentPosition++;
                }
                break;
            case R.id.exercise_tv_gotoClick:
                goToPointPosition();
                break;
        }
        showQuestion(mCurrentPosition);
    }
}
