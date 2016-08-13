package com.lzb.carexam.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzb.carexam.R;
import com.lzb.carexam.base.Config;

/**
 * 选择章节
 * Created by MooreLi on 2016/8/13.
 */
public class SelectClassActivity extends BaseActivity implements View.OnClickListener, Animation.AnimationListener {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvClassOne, mTvClassTwo, mTvClassThree, mTvClassFour;

    Animation cardMove_0;
    Animation cardMove_1;
    Animation cardMove_2;
    Animation cardMove_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        initViews();
        mTvTitle.setText(getResources().getString(R.string.select_classNum));
        /**
         * 设置进入动画
         */
        cardMove_0 = AnimationUtils.loadAnimation(this, R.anim.card_in);
        cardMove_1 = AnimationUtils.loadAnimation(this, R.anim.card_in);
        cardMove_2 = AnimationUtils.loadAnimation(this, R.anim.card_in);
        cardMove_3 = AnimationUtils.loadAnimation(this, R.anim.card_in);
        cardMove_0.setAnimationListener(this);
        cardMove_1.setAnimationListener(this);
        cardMove_2.setAnimationListener(this);
        cardMove_3.setAnimationListener(this);
        mTvClassOne.setAnimation(cardMove_0);
    }

    private void initViews() {
        mIvBack = (ImageView) findViewById(R.id.topBar_iv_back);
        mTvTitle = (TextView) findViewById(R.id.topBar_tv_title);
        mTvClassOne = (TextView) findViewById(R.id.SelectClass_tv_One);
        mTvClassTwo = (TextView) findViewById(R.id.SelectClass_tv_Two);
        mTvClassThree = (TextView) findViewById(R.id.SelectClass_tv_Three);
        mTvClassFour = (TextView) findViewById(R.id.SelectClass_tv_Four);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvClassOne.setOnClickListener(this);
        mTvClassTwo.setOnClickListener(this);
        mTvClassThree.setOnClickListener(this);
        mTvClassFour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("exerciseType", Config.EXERCISE_TYPE_CLASS);
        switch (v.getId()) {
            case R.id.SelectClass_tv_One:
                intent.putExtra("classNum", 1);
                break;
            case R.id.SelectClass_tv_Two:
                intent.putExtra("classNum", 2);
                break;
            case R.id.SelectClass_tv_Three:
                intent.putExtra("classNum", 3);
                break;
            case R.id.SelectClass_tv_Four:
                intent.putExtra("classNum", 4);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation.equals(cardMove_0)) {
            mHandler.sendEmptyMessageDelayed(0, 200);
        } else if (animation.equals(cardMove_1)) {
            mHandler.sendEmptyMessageDelayed(1, 200);
        } else if (animation.equals(cardMove_2)) {
            mHandler.sendEmptyMessageDelayed(2, 200);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mTvClassTwo.setVisibility(View.VISIBLE);
                    mTvClassTwo.setAnimation(cardMove_1);
                    break;
                case 1:
                    mTvClassThree.setVisibility(View.VISIBLE);
                    mTvClassThree.setAnimation(cardMove_2);
                    break;
                case 2:
                    mTvClassFour.setVisibility(View.VISIBLE);
                    mTvClassFour.setAnimation(cardMove_3);
                    break;
            }
        }
    };
}
