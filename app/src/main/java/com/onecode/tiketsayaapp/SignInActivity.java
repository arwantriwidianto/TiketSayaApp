package com.onecode.tiketsayaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    TextView btn_new_account;
    EditText xusername, xpassword;
    Button btn_sign_in;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        load elemen atau resource id
        btn_new_account = findViewById(R.id.btn_new_account);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(SignInActivity.this, RegisterOneActivity.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

//                pengecekan usernmae kosong atau tidak

                if (username.isEmpty()){

//                    jika iya maka muncul toast harus di isi
                    Toast.makeText(getApplicationContext(), "Username kosong", Toast.LENGTH_SHORT).show();

//                    jika tidak maka lanjut
                } else {
                    if (password.isEmpty()){
                        //                    jika iya maka muncul toast harus di isi
                        Toast.makeText(getApplicationContext(), "Password kosong", Toast.LENGTH_SHORT).show();

                    } else {
                        btn_sign_in.setEnabled(false);
                        btn_sign_in.setText("Loading...");

                        //                ambil data username dari firebase
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()){

                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    if (password.equals(passwordFromFirebase)){

//                                simpan ke storage local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        Toast.makeText(getApplicationContext(), "Berhasil login", Toast.LENGTH_SHORT).show();

                                        Intent gotohome = new Intent(SignInActivity.this, HomeActivity.class);
                                        startActivity(gotohome);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password salah atau kosong", Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                } else {
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                    Toast.makeText(getApplicationContext(), "Username tidak ada !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
