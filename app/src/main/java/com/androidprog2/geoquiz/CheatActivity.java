package com.androidprog2.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "answer";
    private static final String EXTRA_SHOWN = "shown";
    boolean answer;

    private Button showAnswerBtn;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answer = getIntent().getBooleanExtra(EXTRA_ANSWER, false);

        answerTextView = (TextView) findViewById(R.id.answer_text);

        showAnswerBtn = (Button) findViewById(R.id.show_btn);
        showAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setText(String.valueOf(answer));
                showAnswerBtn.setEnabled(false);
                Intent data = new Intent();
                data.putExtra(EXTRA_SHOWN , true);
                setResult(RESULT_OK, data);
            }
        });

    }

    public static Intent newIntent(Context packageContext, boolean answer){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, answer);
        return intent;
    }

    public static boolean wasAnswerShown(Intent data){
        return data.getBooleanExtra(EXTRA_SHOWN, false);
    }

}