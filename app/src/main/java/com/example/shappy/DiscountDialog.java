package com.example.shappy;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shappy.Helper.CodeTransaction;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

/**
 * Created by Ivan on 2/4/2016.
 */
public class DiscountDialog extends Dialog implements android.view.View.OnClickListener {

    public static Activity c;
    public Dialog d;
    public Button yes;
    public ImageButton no;
    public static ProgressBar spinner;
    public static ImageButton success;
    public String token;
    public String code;

    private static TextView headerEditText;

    public static JSONObject data;
    public static String stockname;
    public static String description;
    public static String imageAddress;
    public static ImageLoader stockImage;
    public static ImageView stockImageView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                spinner.setVisibility(View.VISIBLE);
                yes.setVisibility(View.INVISIBLE);

                CodeSender codeSender = new CodeSender();
                codeSender.execute(token,code,"apply");

                break;
                //c.finish();
            case R.id.btn_no:
                //cancel();
                c.recreate();
                dismiss();
                break;
            default:
                break;
        }
        //dismiss();
    }

    public DiscountDialog(Activity a, String token, String code) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.token = token;
        this.code = code;
    }

    public static void animateSuccess() {
        spinner.setVisibility(View.INVISIBLE);
        success.setVisibility(View.VISIBLE);
        Animation shake = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.anim_alpha);
        success.startAnimation(shake);

    }

    public static void setData() {

                try {
                    JSONObject stock = data.getJSONObject("data").getJSONObject("stock");
                    headerEditText.setText(stock.getString("name"));
                    description = stock.getString("description");
                    imageAddress = stock.getString("thumb");
                    ImageLoader imageLoader = ImageLoader.getInstance();

                    imageLoader.displayImage("http://ec2-54-200-218-253.us-west-2.compute.amazonaws.com:8080" + imageAddress, stockImageView);
                    }

                    catch (JSONException e) {
                    e.printStackTrace();
                    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);*/

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.discount_dialog_info);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (ImageButton) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        success = (ImageButton) findViewById(R.id.dialog_success_button);
        success.setVisibility(View.INVISIBLE);

        stockImageView = (ImageView) findViewById(R.id.dialog_image_view);

        headerEditText = (TextView) findViewById(R.id.discount_header_text);

        CodeSender codeSender = new CodeSender();
        // вставить сюда токен и код
        codeSender.execute(token,code,"check");

    }


}
