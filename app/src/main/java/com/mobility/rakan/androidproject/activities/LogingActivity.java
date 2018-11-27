package com.mobility.rakan.androidproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobility.rakan.androidproject.R;
import com.mobility.rakan.androidproject.activities.sql.SQLiteHelper;
import com.mobility.rakan.androidproject.models.Constants;


public class LogingActivity extends AppCompatActivity {

    EditText EtName, EtPwd;
    Button btnSginIn, btnSingUp;
    String TempPassword = "NOT_FOUND";
    String NameHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region
        EtName = findViewById(R.id.et_name);
        EtPwd = findViewById(R.id.et_pwd);
        btnSginIn = findViewById(R.id.btn_sign_in);
        btnSingUp = findViewById(R.id.btn_sign_up);
        sqLiteHelper = new SQLiteHelper(this);
        //endregion

        btnSginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendToReg();
            }
        });
    }
    public void sendToReg() {
        Intent myIntent = new Intent(LogingActivity.this, RegisterActivit.class);
        startActivity(myIntent);
    }
    public void LoginFunction() {

        if (EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{NameHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(LogingActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();

        }

    }

    public void CheckFinalResult() {

        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {

            Toast.makeText(LogingActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

            // Going to MainActivity after login success message.
            Intent intent = new Intent(LogingActivity.this, MainActivity.class);

            // Sending Email to Dashboard Activity using intent.
            // intent.putExtra(UserEmail, EmailHolder);
            saveWord(Constants.login, true);
            saveWord(Constants.UserName, EtName.getText().toString());
            startActivity(intent);


        } else {

            Toast.makeText(LogingActivity.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND";

    }

    public void saveWord(String key, Boolean saveThisWord) {
        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));

        editor.commit();
    }

    public void saveWord(String key, String saveThisWord) {
        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(Constants.Mypref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(key, String.valueOf(saveThisWord));

        editor.commit();
    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        NameHolder = EtName.getText().toString();
        PasswordHolder = EtPwd.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }
}