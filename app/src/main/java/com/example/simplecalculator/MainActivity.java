package com.example.simplecalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    public void onBtnClicked(View view) {
        // Get view handle using the view id
        EditText et1 = findViewById(R.id.Num1);

        // Get the view’s text
        String et1Text = et1.getText().toString();

        // Cast the String into Integer
        Integer num1 = Integer.valueOf(et1Text);

        // Get view handle using the view id
        EditText et2 = findViewById(R.id.Num2);

        // Get the view’s text
        String et2Text = et2.getText().toString();

        // Cast the String into Integer
        Integer num2 = Integer.valueOf(et2Text);
        Log.e("num1=", num1.toString());
        Log.e("num2=", num2.toString());

         Integer result = null;
        if (view.getId() == R.id.btnPlus) {
            result = num1 + num2;
            Log.e("result=",result.toString());

        }
        if (view.getId() == R.id.btnMinus)
            result = num1 - num2;
        if (view.getId() == R.id.btnMult)
            result = num1 * num2;
        if (view.getId() == R.id.btnDiv)
            result = num1 / num2;
        if (result != null) {
            Log.e("show result=", result.toString());

            TextView tvRes = findViewById(R.id.tvResult);
            tvRes.setText(result.toString());
        }

    }
}