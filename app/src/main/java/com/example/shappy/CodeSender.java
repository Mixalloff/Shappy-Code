package com.example.shappy;

import android.os.AsyncTask;

import com.example.shappy.Helper.CodeSend;

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

    private URL url;
    private static String address = "http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080/company/stocks/apply";


    @Override
    protected String doInBackground(String... params) {
        try {
            String token = params[0];
            String code = params[1];

            url = new URL(address);
            String result = CodeSend.checkAction(token,code);

            String s = "";

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
