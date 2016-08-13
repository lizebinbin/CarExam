package com.lzb.carexam.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzb.carexam.R;
import com.lzb.carexam.bean.Question;
import com.lzb.carexam.cache.QuestionDBAccess;
import com.lzb.carexam.util.LogUtil;
import com.lzb.carexam.util.SPUtil;
import com.lzb.carexam.util.StringUtil;
import com.lzb.carexam.util.ToastUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class ExerciseBaseActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    ImageView mIvBack, mIvCollect, mIvDelete;
    TextView mTvTitle;
    RelativeLayout mLlContent;
    TextView mTvQuestion, mTvLast, mTvNext, mTvGotoClick;
    ImageView mIvLast,mIvNext;
    EditText mEtGotoNum;
    ImageView mIvImage;
    RadioGroup mRgAnswers;
    RadioButton mRbAnswerA, mRbAnswerB, mRbAnswerC, mRbAnswerD;
    RadioButton[] mAnswerArray;

    List<Question> mQuestions;
    QuestionDBAccess mQuestionAccess;

    int mCurrentPosition;
    int mRightAnswerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        mIvCollect.setVisibility(View.VISIBLE);
        mAnswerArray = new RadioButton[]{mRbAnswerA, mRbAnswerB, mRbAnswerC, mRbAnswerD};
        mQuestionAccess = new QuestionDBAccess(this);
    }

    private void initViews() {
        mIvBack = (ImageView) findViewById(R.id.topBar_iv_back);
        mIvCollect = (ImageView) findViewById(R.id.topBar_iv_collect);
        mIvDelete = (ImageView) findViewById(R.id.topBar_iv_delete);
        mTvTitle = (TextView) findViewById(R.id.topBar_tv_title);
        mLlContent = (RelativeLayout) findViewById(R.id.exercise_ll_content);
        mTvQuestion = (TextView) findViewById(R.id.exercise_tv_question);
//        mTvLast = (TextView) findViewById(R.id.exercise_tv_last);
//        mTvNext = (TextView) findViewById(R.id.exercise_tv_next);
        mIvLast = (ImageView) findViewById(R.id.exercise_iv_last);
        mIvNext = (ImageView) findViewById(R.id.exercise_iv_next);
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
        mIvCollect.setOnClickListener(this);
        mIvDelete.setOnClickListener(this);
//        mTvLast.setOnClickListener(this);
//        mTvNext.setOnClickListener(this);
        mIvLast.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mTvGotoClick.setOnClickListener(this);
        mRgAnswers.setOnCheckedChangeListener(this);

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
                try {
                    mRightAnswerPosition = Integer.parseInt(question.getRightAnswer());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                String index = (mCurrentPosition + 1) + "/" + mQuestions.size() + "、";
                //如果回答过
                if (question.getUserAnswerNum() > 0) {

                    for (int i = 0; i < mAnswerArray.length; i++) {
                        mAnswerArray[i].setEnabled(false);
                        mAnswerArray[i].setButtonDrawable(R.drawable.exercise_rb_normal);
                    }
                    //回答正确
                    if (question.getUserAnswerNum() == question.getRightAnswerNum()) {
                        LogUtil.e("回答过  回答正确");
                        mAnswerArray[question.getUserAnswerNum() - 1].setButtonDrawable(R.drawable.exercise_rb_checked);
                    } else {
                        LogUtil.e("回答过 回答错误");
                        mAnswerArray[question.getUserAnswerNum() - 1].setButtonDrawable(R.drawable.ic_answer_wrong);
                        mAnswerArray[question.getRightAnswerNum() - 1].setButtonDrawable(R.drawable.exercise_rb_checked);
                    }
                } else {
                    LogUtil.e("没有回答");
                    mRgAnswers.clearCheck();
                    for (int i = 0; i < mAnswerArray.length; i++) {
                        mAnswerArray[i].setEnabled(true);
                        mAnswerArray[i].setButtonDrawable(R.drawable.exercise_rb_normal);
                    }
                }
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

    /**
     * 跳转到指定题目
     */
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
            case R.id.topBar_iv_collect:
                //收藏、取消收藏

                break;
            case R.id.topBar_iv_delete:
                //移除（从收藏夹中进入或者错题集中进入显示）

                break;
            case R.id.exercise_iv_last:
                if (mCurrentPosition == 0) {
                    ToastUtil.showShort(this, "当前为第一题！");
                    return;
                } else {
                    mCurrentPosition--;
                    showQuestion(mCurrentPosition);
                }
                break;
            case R.id.exercise_iv_next:
                if (mCurrentPosition == mQuestions.size() - 1) {
                    ToastUtil.showShort(this, "当前为最后一题！");
                    return;
                } else {
                    mCurrentPosition++;
                    showQuestion(mCurrentPosition);
                }
                break;
            case R.id.exercise_tv_gotoClick:
                goToPointPosition();
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!mRbAnswerA.isChecked() && !mRbAnswerB.isChecked() && !mRbAnswerC.isChecked() && !mRbAnswerD.isChecked()) {
            return;
        }
        int userSelectPosition = -1;
        switch (checkedId) {
            case R.id.exercise_rb_answerA:
                LogUtil.e("A");
                userSelectPosition = 1;
                break;
            case R.id.exercise_rb_answerB:
                LogUtil.e("B");
                userSelectPosition = 2;
                break;
            case R.id.exercise_rb_answerC:
                LogUtil.e("C");
                userSelectPosition = 3;
                break;
            case R.id.exercise_rb_answerD:
                LogUtil.e("D");
                userSelectPosition = 4;
                break;
        }
        LogUtil.e("userSelectPosition:" + userSelectPosition);
        if (userSelectPosition == -1) {
            return;
        }
        mAnswerArray[userSelectPosition - 1].setButtonDrawable(R.drawable.exercise_rb_checked);
        boolean isAnswerRight = userSelectPosition == mRightAnswerPosition;
        mQuestions.get(mCurrentPosition).setRightAnswerNum(mRightAnswerPosition);
        mQuestions.get(mCurrentPosition).setUserAnswerNum(userSelectPosition);
        if (isAnswerRight) {
            //最后一题
            if (mCurrentPosition == mQuestions.size() - 1) {
                ToastUtil.showShort(this, "恭喜您完成练习！");
            }
            //下一题
            else {
                LogUtil.e("before ++" + mCurrentPosition);
                mCurrentPosition++;
                LogUtil.e("after :" + mCurrentPosition);
                showQuestion(mCurrentPosition);
            }
        } else {
            /**
             * 回答错误
             * 从记录中读取该题错误次数，如果没有错过，则错误次数置为1，否则，错误次数+1，重新保存
             */
            int wrongCount = SPUtil.getIntValue(this, "Question" + mQuestions.get(mCurrentPosition).getId());
            if (wrongCount != -1) {
                wrongCount++;
                SPUtil.putIntValue(this, "Question" + mQuestions.get(mCurrentPosition).getId(), wrongCount);
            } else {
                SPUtil.putIntValue(this, "Question" + mQuestions.get(mCurrentPosition).getId(), 1);
            }
            ToastUtil.showShort(this, "已添加到错题集！");
            if (mRightAnswerPosition > 0 && mRightAnswerPosition < 5) {
                for (int i = 0; i < mAnswerArray.length; i++) {
                    mAnswerArray[i].setEnabled(false);
                    mAnswerArray[i].setButtonDrawable(R.drawable.exercise_rb_bg);
                }
                mAnswerArray[mRightAnswerPosition - 1].setButtonDrawable(R.drawable.exercise_rb_checked);
                mAnswerArray[userSelectPosition - 1].setButtonDrawable(R.drawable.ic_answer_wrong);
            }

        }
    }
}
