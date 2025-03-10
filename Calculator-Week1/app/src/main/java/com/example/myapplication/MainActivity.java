package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private double firstNumber = 0;
    private String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        input=findViewById(R.id.input);
        Button btn0=findViewById(R.id.btn0);
        Button btn1=findViewById(R.id.btn1);
        Button btn2=findViewById(R.id.btn2);
        Button btn3=findViewById(R.id.btn3);
        Button btn4=findViewById(R.id.btn4);
        Button btn5=findViewById(R.id.btn5);
        Button btn6=findViewById(R.id.btn6);
        Button btn7=findViewById(R.id.btn7);
        Button btn8=findViewById(R.id.btn8);
        Button btn9=findViewById(R.id.btn9);
        Button btnarti=findViewById(R.id.btnarti);
        Button btnclear=findViewById(R.id.btnclear);
        Button btneksi=findViewById(R.id.btneksi);
        Button btnus=findViewById(R.id.btnus);
        Button btncarpi=findViewById(R.id.btncarpi);
        Button btnfakt=findViewById(R.id.btnfakt);
        Button btnnokta=findViewById(R.id.btnnokta);
        Button btnesittir=findViewById(R.id.btnesittir);
        Button btnbolum=findViewById(R.id.btnbolum);
        Button btnkok=findViewById(R.id.btnkok);

        View.OnClickListener numberClickListener = v -> {
            Button clickedButton = (Button) v;
            input.append(clickedButton.getText().toString());
        };

        btn0.setOnClickListener(numberClickListener);
        btn1.setOnClickListener(numberClickListener);
        btn2.setOnClickListener(numberClickListener);
        btn3.setOnClickListener(numberClickListener);
        btn4.setOnClickListener(numberClickListener);
        btn5.setOnClickListener(numberClickListener);
        btn6.setOnClickListener(numberClickListener);
        btn7.setOnClickListener(numberClickListener);
        btn8.setOnClickListener(numberClickListener);
        btn9.setOnClickListener(numberClickListener);
        btnnokta.setOnClickListener(numberClickListener);

        btnarti.setOnClickListener(v -> setOperation("+"));
        btneksi.setOnClickListener(v -> setOperation("-"));
        btncarpi.setOnClickListener(v -> setOperation("*"));
        btnbolum.setOnClickListener(v -> setOperation("/"));
        btnkok.setOnClickListener(v -> calculateSingle("√"));
        btnus.setOnClickListener(v -> setOperation("^"));
        btnfakt.setOnClickListener(v -> calculateSingle("!"));
        btnesittir.setOnClickListener(v -> calculate(v));
        btnclear.setOnClickListener(v -> clear());




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void clear() {
        input.setText("");
        firstNumber = 0;
        operation = "";
    }

    private void setOperation(String op) {
        String inp = input.getText().toString();
        if (!inp.isEmpty()) {
            firstNumber = Double.parseDouble(inp);
            operation = op;
            input.setText("");
        }
    }

    private void calculateSingle(String op) {
        String inp = input.getText().toString();
        if (!inp.isEmpty()) {
            double number = Double.parseDouble(inp);
            double result = 0;

            if (op.equals("√")) {
                result = Math.sqrt(number);
            } else if (op.equals("!")) {
                result = factorial((int) number);
            }

            input.setText(String.valueOf(result));
            input.setText("");
        }
    }

    private double factorial(int number) {
        if (number == 0 || number == 1) return 1;
        int fact = 1;
        for (int i = 2; i <= number; i++) {
            fact *= i;
        }
        return fact;
    }

    public void calculate(View view) {
        String inp = input.getText().toString();
        if (!inp.isEmpty() && !operation.isEmpty()) {
            double secondNumber = Double.parseDouble(inp);
            double result = 0;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        input.setText("Hata: Bölme sıfır olamaz!"); //

                        return;
                    }
                    break;
                case "^":
                    result = Math.pow(firstNumber, secondNumber);
                    break;
            }

            input.setText(String.valueOf(result));
            operation = "";
        }
    }


}