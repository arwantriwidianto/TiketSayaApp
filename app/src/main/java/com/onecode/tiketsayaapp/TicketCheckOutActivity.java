package com.onecode.tiketsayaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckOutActivity extends AppCompatActivity {

    ImageView notice_uang;
    Button btn_buy_ticket, btnplus, btnmines;
    LinearLayout btn_back;
    TextView textjumlahtiket, textmybalance, texttotalharga, nama_wisata, lokasi, ketentuan;
    Integer valueJumlahTiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance = 0;

//    membuat nomor transaksi secara random
    Integer nomor_transaksi = new Random().nextInt();

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);

        getUsernameLocal();

//        mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

//        load elemen, harus di deklarasi terlebih dahulu
        notice_uang = findViewById(R.id.notice_uang);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_back = findViewById(R.id.btn_back);
        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        textmybalance = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

//        setting value baru untuk beberapa komponen
        textjumlahtiket.setText(valueJumlahTiket.toString());

//        animation default
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.animate().alpha(0).setDuration(300).start();

        //        mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();
                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                texttotalharga.setText("US$"+valuehargatiket+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        mengambil mybalance dari user di firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                sisa_balance = mybalance - valuetotalharga;
                textmybalance.setText("US$ "+sisa_balance+"");
//                textmybalance.setText("US$ "+sisa_balance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        saat di klik
        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket -= 1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket <= 1 ){
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }

                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$"+valuetotalharga+"");

                sisa_balance = mybalance - valuetotalharga;
                textmybalance.setText("US$ "+sisa_balance+"");


                if (valuetotalharga < mybalance){
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_buy_ticket.setEnabled(true);

                    textmybalance.setTextColor(Color.parseColor("#203DD1"));

                    notice_uang.animate().alpha(0).setDuration(300).start();
                }
            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket +=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket > 1 ){
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }

                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$"+valuetotalharga+"");

//                update mybalance
                sisa_balance = mybalance - valuetotalharga;
                textmybalance.setText("US$ "+sisa_balance+"");

                if (valuetotalharga > mybalance){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_buy_ticket.setEnabled(false);

//                    ketika balance tak cukup
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.animate().alpha(1).setDuration(300).start();
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //        menyimpan data user ke firebase dan membuat table baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference()
                        .child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("total_harga").setValue(valuetotalharga);
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                reference4 = FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference4.getRef().child("user_balance").setValue(sisa_balance.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                        Intent gotosuccessticket = new Intent(TicketCheckOutActivity.this, SuccessBuyTicketActivity.class);
                        startActivity(gotosuccessticket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

//        kembali
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
