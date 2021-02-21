package com.androidprog2.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_QUIZ = "quiz_code";
    private static final int REQUEST_CODE_CHEAT = 1;

    private Button falseButton;
    private Button trueButton;
    private Button cheatButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private TextView questionTextView;

    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ArrayList<Question> questions;

        if (savedInstanceState != null)
            quiz = (Quiz) savedInstanceState.getSerializable(KEY_QUIZ);
        else{
            questions = new ArrayList<Question>();
            questions.add(new Question(getString(R.string.question_1), true));
            questions.add(new Question(getString(R.string.question_2), false));
            questions.add(new Question(getString(R.string.question_3), true));
            questions.add(new Question(getString(R.string.question_4), false));
            quiz = new Quiz(questions);
        }

        questionTextView = findViewById(R.id.questionText);
        questionTextView.setText(quiz.actualQuestion().getQuestionText());

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuestion(false);
            }
        });

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerQuestion(true);
            }
        });

        nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answered;
                if (!quiz.isLast()) {
                    questionTextView.setText(quiz.nextQuestion().getQuestionText());
                    answered = !quiz.isActualAnswered();
                    trueButton.setEnabled(answered);
                    falseButton.setEnabled(answered);
                    nextButton.setEnabled(!quiz.isLast());
                    prevButton.setEnabled(true);
                }
            }
        });

        prevButton = (ImageButton) findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answered;
                if (!quiz.isFirst()) {
                    questionTextView.setText(quiz.prevQuestion().getQuestionText());
                    answered = !quiz.isActualAnswered();
                    trueButton.setEnabled(answered);
                    falseButton.setEnabled(answered);
                    nextButton.setEnabled(true);
                    prevButton.setEnabled(!quiz.isFirst());
                }
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheatActivity.newIntent(QuizActivity.this, quiz.actualQuestion().getAnswer());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        trueButton.setEnabled(!quiz.isActualAnswered());
        falseButton.setEnabled(!quiz.isActualAnswered());
        nextButton.setEnabled(!quiz.isLast());
        prevButton.setEnabled(!quiz.isFirst());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(KEY_QUIZ, quiz);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return;
            if(CheatActivity.wasAnswerShown(data))quiz.setCheater();
        }
    }

    public void answerQuestion(boolean answer) {
        String feedback = quiz.checkAnswer(answer) ? getString(R.string.correct_toast) : getString(R.string.incorrect_toast);
        if (quiz.isCheater()) feedback = getString(R.string.cheater_msg);

        Toast toast = Toast.makeText(QuizActivity.this,
                feedback + getString(R.string.toast_percentage) + quiz.getPercentage() + "%",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 200);
        toast.show();
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }
}