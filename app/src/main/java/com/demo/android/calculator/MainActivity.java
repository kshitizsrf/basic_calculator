package com.demo.android.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView txtBox1, txtBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBox1 = findViewById(R.id.current_view);
        txtBox2 = findViewById(R.id.history_view);

        assignId(R.id.btn_plus);
        assignId(R.id.btn_minus);
        assignId(R.id.btn_multiply);
        assignId(R.id.btn_divide);
        assignId(R.id.btn_equals);
        assignId(R.id.btn_del);
        assignId(R.id.btn_0);
        assignId(R.id.btn_1);
        assignId(R.id.btn_2);
        assignId(R.id.btn_3);
        assignId(R.id.btn_4);
        assignId(R.id.btn_5);
        assignId(R.id.btn_6);
        assignId(R.id.btn_7);
        assignId(R.id.btn_8);
        assignId(R.id.btn_9);
        assignId(R.id.btn_AC);
        assignId(R.id.btn_clear);
        assignId(R.id.btn_dot);

    }
    void assignId(int id){
        Button btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    double expression1 = 0, expression2 = 0;
    int operator, temp = 0; boolean flag = true;
    StringBuilder result = new StringBuilder();
    StringBuilder historyView = new StringBuilder();
    StringBuilder currentView = new StringBuilder();
    StringBuilder currentExpression = new StringBuilder();

    // main function - what happens on each button click
    @Override
    public void onClick(View view) {

        Button button = (Button) view;
        String btnText = button.getText().toString();

        switch (btnText) {

            // all clear text boxes - current as well as history
            case "AC":
                if(currentExpression.length() > 0) clearString(currentExpression);
                if(currentView.length() > 0) clearString(currentView);
                if(historyView.length() > 0){
                    clearString(historyView);
                    txtBox2.setText("");
                }

                txtBox1.setText("0");
                return;

            // clear current text box
            case "C":
                if(currentExpression.length() > 0) clearString(currentExpression);
                if(currentView.length() > 0) clearString(currentView);

                txtBox1.setText("0");
                return;

            // deleting the last index element in the current view expression
            case "DEL":
                int len = currentView.length();
                if(len > 0) {
                    if(currentView.charAt(len - 1) == ' ') currentView = currentView.delete(len - 3,len);
                    else currentView = currentView.deleteCharAt(len-1);
                    txtBox1.setText(currentView);
                }
                if(currentExpression.length()>0) currentExpression = currentExpression.deleteCharAt(currentExpression.length()-1);
                return;

            case ".":
                if(currentExpression.indexOf(".")==-1){
                    currentExpression = currentExpression.append(btnText);
                    flag = true;
                    break;
                }
                flag = false;
                break;

            case " + ":
                temp = 1; break;

            case " - ":
                temp = 2; break;

            case " x ":
                temp = 3; break;

            case " / ":
                temp = 4; break;

            case " = ":
                if(currentExpression.length()==0) return;
                if(expression1!=0) {
                    expression2 = Double.parseDouble(currentExpression.toString()); // converting the current expression to double for calculation
                    result.append(getResult()); // calculating the output
                    isInteger(); // checks if there is something after the decimal
                    txtBox1.setText(result); // displaying the output in the current view

                    shiftCurrentView(); // shifting to history view
                }
                return;

            // concatenating the button strings to the main expression which is to be displayed in the current view
            default:
                if(result.length()!=0) clearString(result);
                currentExpression = currentExpression.append(btnText);
                flag = true;
        }

        // (+) (-) (*) (/)
        if(temp!=0) {
            if(currentExpression.length()==0) {
                if(result.length()==0) return;
                else {
                    expression1 = Double.parseDouble(result.toString());
                    currentView = currentView.append(result);
                    clearString(result);
                }
            }
            else {
                expression1 = Double.parseDouble(currentExpression.toString()); // contains the expression till now (before operator)
                clearString(currentExpression); // clears the currentExpression
            }
            operator = temp;
            temp = 0;
        }

        // setting current view at every input
        if(flag) {
            currentView = currentView.append(btnText);
        }
        txtBox1.setText(currentView);
    }
    String getResult() {
        try {
            switch (operator) {
                case 1:
                    return Double.toString(expression1 + expression2);
                case 2:
                    return Double.toString(expression1 - expression2);
                case 3:
                    return Double.toString(expression1 * expression2);
                case 4:
                    return Double.toString(expression1 / expression2);
                default:
                    return "Err";
            }
        } catch (Exception e) {
            return "Err";
        }
    }
    void clearString(@NonNull StringBuilder str) {
        str.delete(0, str.length());
    }
    void isInteger() {
        double fraction = Double.parseDouble(result.substring(result.indexOf(".")));
        System.out.println(fraction);
        if(fraction==0){
            result = result.delete(result.indexOf("."), result.length());
        }
    }
    void shiftCurrentView() {
        // shifting the result from current view(if any) to the history view
        historyView = historyView.append(currentView);
        historyView = historyView.append(" = ");
        historyView = historyView.append(result);
        historyView = historyView.append("\n\n");
        txtBox2.setText(historyView);

        expression1 = expression2 = 0;
        clearString(currentExpression);
        clearString(currentView);
    }
}
