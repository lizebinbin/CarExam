package com.lzb.carexam.cache;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzb.carexam.bean.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class QuestionDBAccess {
    private SQLiteDatabase mDatabase;
    private Context context;
    private String mDbPath = "data/data/com.lzb.carexam/questions.db";
    private String mDbDir = "data/data/com.lzb.carexam";

    public QuestionDBAccess(Context context) {
        this.context = context;
    }

    private SQLiteDatabase openDatabase(Context context) {
        File dbFile = new File(mDbPath);
        if (dbFile.exists()) {
            return SQLiteDatabase.openDatabase(mDbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            File dbDir = new File(mDbDir);
            dbDir.mkdir();
            AssetManager manager = context.getAssets();
            try {
                InputStream inputStream = manager.open("questions.db");
                FileOutputStream fos = new FileOutputStream(mDbPath);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return openDatabase(context);
        }
    }

    public Question queryById(int id) {
        openDB();
        Question question = null;
        Cursor cursor = mDatabase.rawQuery("select * from testOne_tb where _id=?", new String[]{"" + id});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            question = new Question();
            question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            question.setClassNum(cursor.getInt(cursor.getColumnIndex("class")));
            question.setTitle(cursor.getString(cursor.getColumnIndex("question")));
            question.setAnswerA(cursor.getString(cursor.getColumnIndex("answerA")));
            question.setAnswerB(cursor.getString(cursor.getColumnIndex("answerB")));
            question.setAnswerC(cursor.getString(cursor.getColumnIndex("answerC")));
            question.setAnswerD(cursor.getString(cursor.getColumnIndex("answerD")));
            question.setRightAnswer(cursor.getString(cursor.getColumnIndex("rightAnswer")));
            question.setImgPath(cursor.getString(cursor.getColumnIndex("imgPath")));
            question.setImgContent(cursor.getBlob(cursor.getColumnIndex("imgContent")));
            question.setType(cursor.getInt(cursor.getColumnIndex("qusType")));
        }
        cursor.close();
        closeDB();
        return question;
    }

    /**
     * 根据章节查询
     *
     * @param num
     * @return
     */
    public List<Question> queryByClassNum(int num) {
        openDB();
        List<Question> questions = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from testOne_tb where class=?", new String[]{"" + num});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                question.setClassNum(cursor.getInt(cursor.getColumnIndex("class")));
                question.setTitle(cursor.getString(cursor.getColumnIndex("question")));
                question.setAnswerA(cursor.getString(cursor.getColumnIndex("answerA")));
                question.setAnswerB(cursor.getString(cursor.getColumnIndex("answerB")));
                question.setAnswerC(cursor.getString(cursor.getColumnIndex("answerC")));
                question.setAnswerD(cursor.getString(cursor.getColumnIndex("answerD")));
                question.setRightAnswer(cursor.getString(cursor.getColumnIndex("rightAnswer")));
                question.setImgPath(cursor.getString(cursor.getColumnIndex("imgPath")));
                question.setImgContent(cursor.getBlob(cursor.getColumnIndex("imgContent")));
                question.setType(cursor.getInt(cursor.getColumnIndex("qusType")));

                questions.add(question);
                cursor.moveToNext();
            }
            cursor.close();
        }
        closeDB();
        return questions;
    }

    private void openDB() {
        mDatabase = openDatabase(context);
    }

    private void closeDB() {
        if (null != mDatabase && mDatabase.isOpen()) {
            mDatabase.close();
        }
    }
}
