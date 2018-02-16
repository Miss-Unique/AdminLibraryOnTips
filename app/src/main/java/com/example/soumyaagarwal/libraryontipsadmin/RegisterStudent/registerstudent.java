package com.example.soumyaagarwal.libraryontipsadmin.RegisterStudent;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerstudent extends AppCompatActivity {

    Button studentsignup;
    EditText rollno,password,name;
    TextInputLayout input_rollno,input_password,input_name;
    DatabaseReference mDatabase,db;
    int l=0,p=0,i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerstudent);

        input_rollno= (TextInputLayout)findViewById(R.id.input_rollno);
        input_password= (TextInputLayout)findViewById(R.id.input_stupassword);
        input_name= (TextInputLayout)findViewById(R.id.input_name);
        rollno = (EditText)findViewById(R.id.rollno);
        password = (EditText)findViewById(R.id.stupassword);
        name = (EditText)findViewById(R.id.name);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        studentsignup = (Button)findViewById(R.id.studentsignup);
        studentsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPost();

            }
        });
    }

    private void startPost() {
        final String stuname = name.getText().toString().trim();
        final String sturollno = rollno.getText().toString().trim();
        final String stupassword = password.getText().toString().trim();
        l=0;
        p = 0;
        i=0;

        if (TextUtils.isEmpty(stuname) || TextUtils.isEmpty(sturollno)||TextUtils.isEmpty(stupassword)) {
            if (stuname.isEmpty()) {
                input_name.setError("Field cannot be empty");
                if(name.requestFocus())
                {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
             }   else
            {
                input_name.setErrorEnabled(false);
            }


            if (sturollno.isEmpty()) {
                input_rollno.setError("Enter a valid Roll Number");
                if(rollno.requestFocus())
                {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }else
            {
                input_rollno.setErrorEnabled(false);
            }


            if (stupassword.isEmpty()) {
                input_password.setError("Field cannot be empty");
                if(password.requestFocus())
                {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }   else
            {
                input_password.setErrorEnabled(false);
            }

            Toast.makeText(registerstudent.this, "Field cannot be Left Empty", Toast.LENGTH_SHORT).show();
        }

        else if(l==0)
        {
            db = mDatabase.child("RollNo").getRef();
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    i = (int) dataSnapshot.getChildrenCount();
                    //Toast.makeText(registerstudent.this, i+"", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    String rn = dataSnapshot.getValue(String.class);
                    String k = dataSnapshot.getKey();
                    String j = rn;
                    p +=1;
                    if (k.equals(sturollno)&&j.equals("no"))
                    {
                        DatabaseReference dbr = mDatabase.child("Student").child(sturollno);

                        dbr.child("Name").setValue(stuname);
                        dbr.child("Password").setValue(stupassword);
                        dbr.child("TotalFine").setValue("0");

                        Toast.makeText(registerstudent.this,"Student Registered Succesfully",Toast.LENGTH_SHORT).show();
                        DatabaseReference db = mDatabase.child("RollNo").child(sturollno);
                        db.setValue("yes");
                        l=1;
                        startActivity(new Intent(registerstudent.this,admin_page.class));
                        finish();
                    }
                    if (p==i&&l!=1)
                    {
                        input_rollno.setError("Enter a valid Roll Number");
                        if(rollno.requestFocus())
                        {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                        rollno.setText("");
                        password.setText("");
                        name.setText("");
                    }
                    else
                    {
                        input_rollno.setErrorEnabled(false);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
              startActivity(new Intent(registerstudent.this,admin_page.class));
                finish();;
    }
}

