package com.example.soumyaagarwal.libraryontipsadmin.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import static java.lang.System.exit;


public class studentverify extends AppCompatActivity {

    EditText verifyrollno;
    DatabaseReference mDatabase,db;
    int l=0,i,p;
    String sturollno;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentverify);

        verifyrollno = (EditText)findViewById(R.id.verifyrollno);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        check = (Button)findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sturollno = verifyrollno.getText().toString();

                db = mDatabase.child("Student").child(sturollno).getRef();

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Intent intent = new Intent(studentverify.this,student.class);
                            intent.putExtra("sturollno",sturollno);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(studentverify.this, "The Student is not REGISTERED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),admin_page.class));
        finish();
    }
}
