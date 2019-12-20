package sanchous19.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView expressionTextView, answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expressionTextView = findViewById(R.id.expressionTextView);
        answerTextView = findViewById(R.id.answerTextView);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("expression", expressionTextView.getText().toString());
        savedInstanceState.putString("answer", answerTextView.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        String expression = savedInstanceState.getString("expression");
        String answer = savedInstanceState.getString("answer");
        expressionTextView.setText(expression);
        answerTextView.setText(answer);
    }
}
