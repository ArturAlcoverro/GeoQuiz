package com.androidprog2.geoquiz;

import android.os.Parcelable;

import java.io.Serializable;

public class Question implements Serializable {
    private final String questionText;
    private final boolean answer;
    private boolean isAnswered;

    public Question(String questionText, boolean answer) {
        this.questionText = questionText;
        this.answer = answer;
        this.isAnswered = false;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean isCorrect(boolean answer) {
        isAnswered = true;
        return this.answer == answer;
    }

    public boolean getAnswer(){
        return this.answer;
    }

    public boolean isAnswered() {
        return this.isAnswered;
    }

}
