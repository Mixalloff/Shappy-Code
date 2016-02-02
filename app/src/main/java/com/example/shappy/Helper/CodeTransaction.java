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
    public static String login(JSONObject jsonObject) throws IOException {

        String response = "could not establish connection";

        try {
            URL url = new URL("http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080/company/authorize");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            //connection.setConnectTimeout(10000);

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            String b = jsonObject.toString();
            osw.write(b);
            osw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            response = br.readLine();
            Log.v("LOGIN", response);
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
