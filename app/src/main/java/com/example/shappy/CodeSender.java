package com.example.shappy;

import android.os.AsyncTask;

import com.example.shappy.Helper.CodeSend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ivan on 2/4/2016.
 * Class is responsible for checking whether STOCK is USED or NOT USED on SERVER
 * makes an async call to :
 * http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080/company/authorize
 * if the stock is presented on server -> marks it as used and activates (?),
 * if the stock is not presented (meaning code is not valid) returns negative result
 *
 */
public class CodeSender extends AsyncTask<String,Void,String> {
    static String mode = "";

    @Override
    protected String doInBackground(String... params) {
        String result ="";
        try {
            String token = params[0];
            String code = params[1];
            mode = params[2];

            if (mode.equals("check")) {
                result = CodeSend.requestDiscountData(token, code);
            }
            else if (mode.equals("apply")) {
                result = CodeSend.applyAction(token,code);
            }
            String s = "";

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String result = s;
        try {
            DiscountDialog.data = new JSONObject(s);
            DiscountDialog.setData();
            DiscountDialog.animateSuccess();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
