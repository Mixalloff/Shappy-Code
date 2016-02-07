package com.example.shappy.Helper;

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
 * Created by Ivan on 2/4/2016.
 */
public class CodeSend {

    public static String createCheckCodeRequest(String token, String code) {
        return "token="+token+"&code="+code;
    }

    public static String checkAction(String... params) throws IOException {
        String token = params[0];
        String code = params[1];
        String response = "";
        try {
            URL url = new URL("http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080/company/stocks/apply");

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
            String request = createCheckCodeRequest(token, code);
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return response;
        }

    }

}
