package com.example.shappy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shappy.Helper.CodeTransaction;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText loginfield;
    private EditText passwordfield;
    private Button loginButton;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "companyToken" ;

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

        sharedpreferences = getSharedPreferences(userPreferences,Context.MODE_PRIVATE );

        // тестовые данные
        loginfield.setText("blahblah");
        passwordfield.setText("blahblah");

        // инициализация подгрузки картинок
        initImageLoader(getApplicationContext());

        // check if token is saved somehow in shared preferences
        boolean isTokenReal =  checkTokenIsReal();
        if (isTokenReal) {
            // if it is saved - launch new intent
            Intent intent = new Intent(getApplicationContext(),ChooseActivity.class);
            startActivity(intent);
        }

        // else fill fields and provide login credentials
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    new DataSender().execute(loginfield.getText().toString(), passwordfield.getText().toString());

                }
            }
        });

    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 512); // 25 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public boolean checkTokenIsReal() {
        String token = sharedpreferences.getString("token", "");
        if (token.equals("")) {
            return false; }
        else return true;
    }

    protected class DataSender extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String login = params[0];
            String password = params[1];
            String result = "";
            try {
                result = CodeTransaction.login(login,password);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                //парсим пришедший json
                String token = "";
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    token = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("token", token);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(),ChooseActivity.class);
                startActivity(intent);
            }
        }
    }
}
