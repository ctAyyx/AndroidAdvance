package com.ct.apt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ct.apt_annotations.ARouter;
import com.ct.apt_annotations.Event;

/**
 * Android APT
 */


@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}