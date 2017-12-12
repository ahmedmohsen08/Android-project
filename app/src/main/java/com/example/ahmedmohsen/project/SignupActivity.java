package com.example.ahmedmohsen.project;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    final Calendar mycalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText email = (EditText) findViewById(R.id.editText_email);
        final EditText name = (EditText) findViewById(R.id.editText_name);
        final EditText username = (EditText) findViewById(R.id.editText_username);
        final EditText password = (EditText) findViewById(R.id.editText_password);
        EditText repassword = (EditText) findViewById(R.id.editText_repassword);
        final EditText job = (EditText) findViewById(R.id.editText_job);
        final EditText birthdate = (EditText) findViewById(R.id.editText_birthday);
        RadioButton gender_male = (RadioButton) findViewById(R.id.radioButton_male);
        RadioButton gender_female = (RadioButton) findViewById(R.id.radioButton_female);
        String gender = "";
        Button signup = (Button) findViewById(R.id.button_signup);
        final DBHelper databaseobject = new DBHelper(this);
        //String[] fieldsarr = {String.valueOf(email),String.valueOf(name),String.valueOf(username),String.valueOf(password),String.valueOf(repassword),String.valueOf(job),String.valueOf(birthdate),String.valueOf(gender_male),String.valueOf(gender_female)};
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalendar.set(Calendar.YEAR,i);
                mycalendar.set(Calendar.MONTH,i1);
                mycalendar.set(Calendar.DAY_OF_MONTH,i2);
                updatelabel();
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignupActivity.this,date,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(gender_male.isChecked()) {
            gender = "male";
        }
        else if(gender_female.isChecked()) {
            gender = "female";
        }

        final String finalGender = gender;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateFields()) {
                    databaseobject.createNewCustomer(email.getText().toString(), name.getText().toString(), username.getText().toString(), password.getText().toString(), finalGender, birthdate.getText().toString(), job.getText().toString());
                    Toast.makeText(getApplicationContext(), "signed up successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean ValidateFields() {
        final EditText email = (EditText) findViewById(R.id.editText_email);
        final EditText name = (EditText) findViewById(R.id.editText_name);
        final EditText username = (EditText) findViewById(R.id.editText_username);
        final EditText password = (EditText) findViewById(R.id.editText_password);
        EditText repassword = (EditText) findViewById(R.id.editText_repassword);
        final EditText job = (EditText) findViewById(R.id.editText_job);
        final EditText birthdate = (EditText) findViewById(R.id.editText_birthday);
        RadioButton gender_male = (RadioButton) findViewById(R.id.radioButton_male);
        RadioButton gender_female = (RadioButton) findViewById(R.id.radioButton_female);
        RadioGroup gender = (RadioGroup) findViewById(R.id.radiogroup_gender);
        boolean errorfound = false;
        if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(name.getText().toString())) {
            name.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(username.getText().toString())) {
            username.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(password.getText().toString())) {
            password.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(repassword.getText().toString())) {
            repassword.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(job.getText().toString())) {
            job.setError("This field is required");
            errorfound = true;
        }
        if(TextUtils.isEmpty(birthdate.getText().toString())) {
            birthdate.setError("This field is required");
            errorfound = true;
        }
        if(!(gender_male.isChecked()) && !(gender_female.isChecked())) {
            gender_male.setError("Select at least on item");
            errorfound = true;
        }
        if(!(password.getText().toString().equals(repassword.getText().toString()))) {
            repassword.setError("Please enter the same value as in password field");
            errorfound = true;
        }
        return !errorfound;
    }

    private void updatelabel() {
        String myformat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);
        EditText birthdate = (EditText) findViewById(R.id.editText_birthday);
        birthdate.setText(sdf.format(mycalendar.getTime()));
    }
}
