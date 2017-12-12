package com.example.ahmedmohsen.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    Button signup;
    CheckBox rememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editText_login_email);
        password = (EditText) findViewById(R.id.editText_login_password);
        login = (Button) findViewById(R.id.button_login);
        signup = (Button) findViewById(R.id.button_login_signup);
        rememberme = (CheckBox) findViewById(R.id.checkBox_rememberme);
        retrieveinfo();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

        final DBHelper dbHelper = new DBHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "please enter your email", Toast.LENGTH_SHORT).show();
                }
                else {
                    String retpass = dbHelper.SearchbyEmail(email.getText().toString());
                    if (dbHelper.SearchforEmailbyEmail(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "this email doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (retpass.equals(password.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "logged in succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,MainPageActivity.class);
                            int ID = dbHelper.SearchforCustomer(email.getText().toString()).getInt(0);
                            intent.putExtra("custID",ID);
                            if(rememberme.isChecked()) {
                                saveinfo();
                            }
                            startActivity(intent);
                            finish();
                            System.exit(0);
                        } else {
                            Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    public void saveinfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.putString("isChecked", "checked");
        editor.apply();
    }

    public void retrieveinfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String str_email = sharedPreferences.getString("email", "");
        String str_password = sharedPreferences.getString("password", "");
        String str_checked = sharedPreferences.getString("isChecked", "");
        if(str_checked.equals("checked")) {
            rememberme.setChecked(true);
        }
        email.setText(str_email);
        password.setText(str_password);
    }
}
