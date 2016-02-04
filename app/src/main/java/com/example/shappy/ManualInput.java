package com.example.shappy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ManualInput extends AppCompatActivity {

    private EditText codeEditText;
    private Button codeSendButton;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "companyToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);
        
        codeEditText = (EditText) findViewById(R.id.input_code_editText);
        codeSendButton = (Button) findViewById(R.id.button_send_code);

        sharedpreferences = getSharedPreferences(userPreferences, Context.MODE_PRIVATE);

        codeEditText.setText("9653933649");

        codeSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockCode = codeEditText.getText().toString();
                String token = sharedpreferences.getString("token", "");


                CodeSender codeSender = new CodeSender();
                codeSender.execute(token,stockCode);
            }
        });

    }

}
