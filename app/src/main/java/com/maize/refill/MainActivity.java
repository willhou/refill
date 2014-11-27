package com.maize.refill;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    private static final float BONUS = 0.05f;
    private static final float FARE = 2.50f;
    private static final float THRESHOLD = 5.00f;

    private TextView tvRefillAmount;
    private TextView tvNewBalance;
    private EditText etRides;
    private EditText etBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRefillAmount = (TextView) findViewById(R.id.tvRefillAmount);
        tvNewBalance = (TextView) findViewById(R.id.tvNewBalance);
        etRides = (EditText) findViewById(R.id.etRides);
        etBalance = (EditText) findViewById(R.id.etBalance);



        etBalance.addTextChangedListener(mTextWatcher);
        etRides.addTextChangedListener(mTextWatcher);
        calculate();
    }

    private void calculate() {
        boolean emptyBalance = TextUtils.isEmpty(etBalance.getText());
        boolean emptyRides = TextUtils.isEmpty(etRides.getText());

        float balance = emptyBalance ? 0 : Float.parseFloat(etBalance.getText().toString());
        int rides = emptyRides ? 1 : Integer.parseInt(etRides.getText().toString());
        float amountNeeded = rides * FARE;
        float amountRefill = amountNeeded;

        if (balance >= amountNeeded) {
            // Enough balance, no need to refill.
            amountNeeded = balance;
        }

        if (amountNeeded >= THRESHOLD) {
            // Refill amount big enough to trigger the bonus
            amountRefill = (amountNeeded - balance) / (1 + BONUS);
        } else {
            amountRefill = amountNeeded;
        }

        DecimalFormat df = new DecimalFormat("#0.00");
        tvRefillAmount.setText("Refill: $" + df.format(amountRefill));
        tvNewBalance.setText("New Balance: $" + df.format(balance + amountRefill));
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
