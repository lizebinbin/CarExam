package com.lzb.carexam.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzb.carexam.R;
import com.lzb.carexam.bean.Question;
import com.lzb.carexam.cache.QuestionDBAccess;
import com.lzb.carexam.util.DeviceUtil;
import com.lzb.carexam.util.StringUtil;
import com.lzb.carexam.util.ToastUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class ExerciseBaseActivity extends BaseActivity implements View.OnClickListener {
    ImageView mIvBack;
    TextView mTvTitle;
    RelativeLayout mLlContent;
    TextView mTvQuestion, mTvLast, mTvNext, mTvGotoClick;
    EditText mEtGotoNum;
    ImageView mIvImage;
    RadioGroup mRgAnswers;
    RadioButton mRbAnswerA, mRbAnswerB, mRbAnswerC, mRbAnswerD;

    List<Question> mQuestions;
    QuestionDBAccess mQuestionAccess;

    int mCurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        mQuestionAccess = new QuestionDBAccess(this);
    }

    private void initViews() {
        mIvBack = (ImageView) findViewById(R.id.topBar_iv_back);
        mTvTitle = (TextView) findViewById(R.id.topBar_tv_title);
        mLlContent = (RelativeLayout) findViewById(R.id.exercise_ll_content);
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

        /**
         * 设置事件监听
         */
        mIvBack.setOnClickListener(this);
        mTvLast.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvGotoClick.setOnClickListener(this);

//        mEtGotoNum.setFocusable(false);

        int screenHeight = DeviceUtil.getScreenHeight(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(30, 20, 30, 30);
        mLlContent.setLayoutParams(params);
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
                    Log.e("AAA", "path:" + question.getImgPath());
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
            ToastUtil.showShort(this, "输入不能为空！");
            return;
        }
        //输入不是数字
        if (!StringUtil.isNumber(inputNum)) {
            ToastUtil.showShort(this, "请输入数字！");
            return;
        }
        int getPosition = mCurrentPosition;
        try {
            getPosition = Integer.parseInt(inputNum);
        } catch (NumberFormatException e) {
            //转换失败
            getPosition = mCurrentPosition;
            ToastUtil.showShort(this, "输入错误");
            return;
        }
        //判断是否超过范围
        if (getPosition < 1 || getPosition > mQuestions.size()) {
            ToastUtil.showShort(this, "输入题号超过范围！");
            return;
        }
        mCurrentPosition = getPosition - 1;
        showQuestion(mCurrentPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topBar_iv_back:
                finish();
                break;
            case R.id.exercise_tv_last:
                if (mCurrentPosition == 0) {
                    ToastUtil.showShort(this, "当前为第一题！");
                    return;
                } else {
                    mCurrentPosition--;
                }
                break;
            case R.id.exercise_tv_next:
                if (mCurrentPosition == mQuestions.size() - 1) {
                    ToastUtil.showShort(this, "当前为最后一题！");
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
