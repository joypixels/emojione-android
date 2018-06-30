package com.emojione.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emojione.tools.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Client client = new Client();

        // Convert native unicode emoji to shortnames
        String result1 = client.toShort("Hello world! \uD83D\uDE04");

        // Convert native unicode emoji and shortnames directly to images
        String result2 = client.toImage("Hello world! :smile: \uD83D\uDE04");

        // Convert native unicode emoji directly to images
        String result3 = client.unicodeToImage("Hello world! \uD83D\uDE04");

        // Convert shortnames to images
        String result4 = client.shortnameToImage("Hello world! :smile:");

        // Convert shortnames to native unicode
        String result5 = client.shortnameToUnicode(" <3 Hello world! :smile:");

    }
}
