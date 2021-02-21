package com.androidprog2.geoquiz;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {
    private ArrayList<Question> questions;
    private int index;
    private int total;
    private int points;
    private boolean first;
    private boolean last;
    private boolean isCheater;

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
        this.total = questions.size();
        this.index = 0;
        this.first = true;
        this.last = false;
        this.points = 0;
        this.isCheater = false;
    }

    public Question actualQuestion() {
        return questions.get(index);
    }

    public Question nextQuestion() {
        if (!last) {
            index++;
            last = index >= (questions.size() - 1);
            first = false;
            return questions.get(index);
        }
        return null;
    }

    public Question prevQuestion() {
        if (!first) {
            index--;
            first = index == 0;
            last = false;
            return questions.get(index);
        }
        return null;
    }

    public boolean isActualAnswered() {
        return questions.get(index).isAnswered();
    }

    public boolean checkAnswer(boolean answer) {
        if (questions.get(index).isCorrect(answer)) {
            this.points++;
            return true;
        }
        return false;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public int getPercentage() {
        return (points * 100) / total;
    }

    public boolean isCheater() {
        return isCheater;
    }

    public void setCheater(){
        this.isCheater = true;
    }
}
