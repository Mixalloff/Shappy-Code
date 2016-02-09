package com.example.shappy.Helper;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ivan on 2/2/2016.
 */
public class CodeTransaction {

    public static String createLoginRequest(String login, String password) {
        return "login="+login+"&password="+password;
    }



    public static String login(String... params) throws IOException {
        String login = params[0];
        String password = params[1];
        String response = "";
        try {
            URL url = new URL("http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080/company/authorize");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            //connection.setConnectTimeout(10000);

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);

            //String b = jsonObject.toString();
            String request = createLoginRequest(login,password);
            osw.write(request);
            osw.flush();

            String line ;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }

            //Log.v("LOGIN", response);
            br.close();

            osw.flush();
            osw.close();

        }
        catch (MalformedURLException | ProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return response;
        }

    }
}
