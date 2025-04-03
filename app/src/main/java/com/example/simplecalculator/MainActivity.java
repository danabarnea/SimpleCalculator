package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private TextView resultView;
    private Button btnPlus, btnMinus, btnMult, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // חובה כדי שה-ID של הרכיבים יזוהה

        // איתור רכיבים
        num1 = findViewById(R.id.Num1);
        num2 = findViewById(R.id.Num2);
        resultView = findViewById(R.id.resultTextView);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMult = findViewById(R.id.btnMult);
        btnDiv = findViewById(R.id.btnDiv);

        // מאזין לאירועי לחיצה
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCalculation(v);
            }
        };

        btnPlus.setOnClickListener(operationListener);
        btnMinus.setOnClickListener(operationListener);
        btnMult.setOnClickListener(operationListener);
        btnDiv.setOnClickListener(operationListener);
    }

    private void performCalculation(View view) {
        String num1Text = num1.getText().toString().trim();
        String num2Text = num2.getText().toString().trim();

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            resultView.setText("אנא הזן את שני המספרים");
            return;
        }

        double number1, number2, result;
        try {
            number1 = Double.parseDouble(num1Text);
            number2 = Double.parseDouble(num2Text);
        } catch (NumberFormatException e) {
            resultView.setText("קלט לא חוקי");
            return;
        }

        if (view.getId() == R.id.btnPlus) {
            result = number1 + number2;
        } else if (view.getId() == R.id.btnMinus) {
            result = number1 - number2;
        } else if (view.getId() == R.id.btnMult) {
            result = number1 * number2;
        } else if (view.getId() == R.id.btnDiv) {
            if (number2 == 0) {
                resultView.setText("אי אפשר לחלק באפס");
                return;
            }
            result = number1 / number2;
        } else {
            return;
        }

        // הצגת התוצאה
        resultView.setText("תוצאה: " + result);
    }
}
