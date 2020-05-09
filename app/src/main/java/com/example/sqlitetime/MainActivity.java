package com.example.sqlitetime;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText userName, password, currentName, newName, deleteName;
    Button addUser, viewData, update, delete;
    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        currentName = findViewById(R.id.currname);
        newName = findViewById(R.id.newname);
        deleteName = findViewById(R.id.namedelete);
        addUser = findViewById(R.id.adduser);
        viewData = findViewById(R.id.viewdata);
        update = findViewById(R.id.updatename);
        delete = findViewById(R.id.delete);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();

                boolean isInserted = mydb.InsertData(userName.getText().toString().trim(), password.getText().toString().trim());

                if(isInserted == true){
                    Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data not added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currName = currentName.getText().toString().trim();
                String nName = newName.getText().toString().trim();

                if(currName.isEmpty()){
                    currentName.setError("enter current name");
                    currentName.requestFocus();
                    return;
                }

                if(nName.isEmpty()){
                    newName.setError("enter the new Name");
                    newName.requestFocus();
                    return;
                }

                boolean isUpdate = mydb.updateData(nName, currName);

                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, " data updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "data not updated", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameDel  = deleteName.getText().toString().trim();

                if(nameDel.isEmpty()){
                    deleteName.setError("enter the name to be deleted");
                    deleteName.requestFocus();
                    return;
                }

                int isdeleted = mydb.delete(nameDel);

                if(isdeleted > 0){
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(MainActivity.this, "unable to delete", Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();

                if(res.getCount() == 0){
                    Toast.makeText(MainActivity.this, "no data found" ,Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("name: " + res.getString(0) + "\n");
                    buffer.append("password: " + res.getString(1) + "\n\n\n");
                }

                Toast.makeText(MainActivity.this, buffer, Toast.LENGTH_LONG).show();
                Log.d("BUFFER" , String.valueOf(buffer));
            }
        });

    }

    public void validate(){

        String UserName  = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();


        if(UserName.isEmpty()){
            userName.setError("Fill username");
            userName.requestFocus();
            return;
        }


        if(pass.isEmpty()){
            password.setError("enter a password");
            password.requestFocus();
            return;
        }

    }
}
