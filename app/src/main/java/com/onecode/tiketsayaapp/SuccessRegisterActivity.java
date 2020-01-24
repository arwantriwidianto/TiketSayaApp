package com.onecode.tiketsayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterActivity extends AppCompatActivity {

    Animation bottomtotop, toptobottom, app_splsh;
    Button btn_explore;
    ImageView icon_succes;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //        load elemen
        btn_explore = findViewById(R.id.btn_explore);
        icon_succes = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

//        load animation
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        app_splsh = AnimationUtils.loadAnimation(this, R.anim.app_splash);

//        run animation
        btn_explore.startAnimation(bottomtotop);
        icon_succes.startAnimation(app_splsh);
        app_title.startAnimation(toptobottom);
        app_subtitle.startAnimation(toptobottom);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoexplore = new Intent(SuccessRegisterActivity.this, HomeActivity.class);
                startActivity(gotoexplore);
            }
        });


    }
}
