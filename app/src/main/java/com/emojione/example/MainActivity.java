package com.emojione.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emojione.tools.Callback;
import com.emojione.tools.Client;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.textView) TextView textView;

    Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        // Convert native unicode emoji to shortnames
//        String string1 = client.toShort("Hello world! \uD83D\uDE04");
//
//        // Convert shortnames to native unicode
//        String string2 = client.shortnameToUnicode(" <3 Hello world! :smile:");
//
//        // Convert native unicode emoji and shortnames directly to images
//        SpannableStringBuilder textview1 = client.toImage("Hello world! :smile: \uD83D\uDE04");
//
//        // Convert native unicode emoji directly to images
//        SpannableStringBuilder textview2 = client.unicodeToImage("Hello world! \uD83D\uDE04");
//
//        // Convert shortnames to images
//        SpannableStringBuilder textview3 = client.shortnameToImage("Hello world! :smile:");

        //editText.setText("Hello world! \uD83D\uDE04");
        editText.setText("Hello world! :joy: ");
    }

    @OnClick({R.id.btnToImage, R.id.btnShortnameToImage,R.id.btnShortnameToUnicode,R.id.btnToShort,R.id.btnUnicodeToImage}) public void submit(Button view) {
        client.shortnameToImage("Hello world! :smile:", new com.emojione.tools.Callback() {

            @Override
            public void onFailure(IOException e) {
                String jason = "jason";
            }

            @Override
            public void onSuccess(SpannableStringBuilder ssb) {
                String jason = "jason";
            }
        });
        String result = "";
        switch (view.getId()) {
            case R.id.btnToImage:
                //result = client.toImage(editText.getText().toString());
                break;
            case R.id.btnShortnameToImage:
                //result = client.shortnameToImage(editText.getText().toString());
                break;
            case R.id.btnShortnameToUnicode:
                //result = client.shortnameToUnicode(editText.getText().toString());
                break;
            case R.id.btnToShort:
                //result = client.toShort(editText.getText().toString());
                break;
            case R.id.btnUnicodeToImage:
                //result = client.unicodeToImage(editText.getText().toString());
                break;
        }
        //textView.setText(result);
    }
}
