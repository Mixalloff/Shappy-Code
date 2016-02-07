package com.example.shappy;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * Created by Ivan on 2/4/2016.
 */
public class DiscountDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;
    public ImageButton no;
    public ProgressBar spinner;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                spinner.setVisibility(View.VISIBLE);
                yes.setVisibility(View.INVISIBLE);
                //c.finish();
                break;
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

    public DiscountDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.discount_dialog_info);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (ImageButton) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


    }


}
