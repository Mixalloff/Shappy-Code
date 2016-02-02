package com.example.shappy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shappy.Helper.CodeTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText loginfield;
    private EditText passwordfield;
    private Button loginButton;

    static {
        System.loadLibrary("iconv");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CodeScanner");
        loginfield = (EditText) findViewById(R.id.login_field);
        passwordfield = (EditText) findViewById(R.id.password_field);
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    new DataSender().execute(loginfield.getText().toString(), passwordfield.getText().toString());

                    //Intent intent = new Intent(getApplicationContext(),ChooseActivity.class);
                    //startActivity(intent);
                }
            }
        });

    }
    protected class DataSender extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String login = params[0];
            String password = params[1];
            String result = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("login", login);
                jsonObject.put("password", password);
                result = CodeTransaction.login(jsonObject);
            }
            catch (JSONException j) {

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),ChooseActivity.class);
                startActivity(intent);
            }
        }
    }
}
