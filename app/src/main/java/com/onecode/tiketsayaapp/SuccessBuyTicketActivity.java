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

public class SuccessBuyTicketActivity extends AppCompatActivity {

    Animation toptobottom, bottomtotop;
    Button btn_my_dashboard, btn_view_ticket;
    TextView app_title, app_subtitle;
    ImageView icon_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

//        load elemen
        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        icon_success = findViewById(R.id.icon_success);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);

//        load animation
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);

//        start animation
        icon_success.setAnimation(toptobottom);
        app_title.setAnimation(toptobottom);
        app_subtitle.setAnimation(toptobottom);
        btn_view_ticket.setAnimation(bottomtotop);
        btn_my_dashboard.setAnimation(bottomtotop);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomyticketdetail = new Intent(SuccessBuyTicketActivity.this, MyTicketDetailActivity.class);
                startActivity(gotomyticketdetail);
            }
        });


        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodashboard = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);
                startActivity(gotodashboard);
            }
        });
    }
}
