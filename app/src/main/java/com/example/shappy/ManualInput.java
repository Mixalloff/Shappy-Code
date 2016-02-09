package com.example.shappy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ManualInput extends AppCompatActivity {

    private EditText codeEditText;
    private Button codeSendButton;
    private ImageView imageView;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "companyToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);

        codeEditText = (EditText) findViewById(R.id.input_code_editText);
        codeSendButton = (Button) findViewById(R.id.button_send_code);
        imageView = (ImageView) findViewById(R.id.imageView);

        sharedpreferences = getSharedPreferences(userPreferences, Context.MODE_PRIVATE);

        codeEditText.setText("7947503631");

        /*ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage("http://townandcountryremovals.com/wp-content/uploads/2013/10/firefox-logo-200x200.png", new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
            }
        });

        imageLoader.displayImage("http://townandcountryremovals.com/wp-content/uploads/2013/10/firefox-logo-200x200.png", imageView);
*/

        codeSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockCode = codeEditText.getText().toString();
                String token = sharedpreferences.getString("token", "");

                DiscountDialog dd = new DiscountDialog(ManualInput.this, token, stockCode);
                dd.show();

                //CodeSender codeSender = new CodeSender();
                //codeSender.execute(token, stockCode);
            }
        });

    }

}
