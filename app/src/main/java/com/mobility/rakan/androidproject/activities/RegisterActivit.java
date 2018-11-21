package com.mobility.rakan.androidproject.activities;

import android.content.Context;
import android.content.Intent;
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

public class RegisterActivit extends AppCompatActivity {
    EditText Et_Email, Et_Pws, Et_Name;
    Button Register, Login;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = findViewById(R.id.buttonRegister);
        Login = findViewById(R.id.buttonLogin);

        Et_Email = findViewById(R.id.editEmail);
        Et_Pws = findViewById(R.id.editPassword);
        Et_Name = findViewById(R.id.editName);
        sqLiteHelper = new SQLiteHelper(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToReg();
            }
        });
        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Creating SQLite table if dose n't exists.
                if (EditTextEmptyHolder) {
                    SQLiteTableBuild();

                    // Method to check Email is already exists or not.
                    CheckingEmailAlreadyExistsOrNot();

                    // Empty EditText After done inserting process.
                    EmptyEditTextAfterDataInsert();
                }else{
                    //If any of Reg EditText empty then this block will be executed.
                    Toast.makeText(RegisterActivit.this, "Please Enter all fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild() {

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " + SQLiteHelper.Table_Column_3_Password + " VARCHAR);");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase() {

        // If editText is not empty then this block will executed.
        if (EditTextEmptyHolder) {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (name,email,password) VALUES('" + NameHolder + "', '" + EmailHolder + "', '" + PasswordHolder + "');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(RegisterActivit.this, "User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegisterActivit.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert() {

        Et_Name.getText().clear();

        Et_Email.getText().clear();

        Et_Pws.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Et_Name.getText().toString();
        EmailHolder = Et_Email.getText().toString();
        PasswordHolder = Et_Pws.getText().toString();

        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot() {

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult() {

        // Checking whether email is already exists or not.
        if (F_Result.equalsIgnoreCase(Constants.Email_Found)) {

            // If email is exists then toast msg will display.
            Toast.makeText(RegisterActivit.this, "Email Already Exists", Toast.LENGTH_LONG).show();

        } else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found";

    }

    public void sendToReg() {
        Intent myIntent = new Intent(RegisterActivit.this, LogingActivity.class);
        startActivity(myIntent);
        finish();
    }

}

