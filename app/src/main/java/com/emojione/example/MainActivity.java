package com.emojione.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String s = "ab ab ab";
        String regex = "ab";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group(0));
        }

        System.out.println(result.toString());

    }
}
