package com.example.soumyaagarwal.libraryontipsadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.NavDrawer.drawer1;
import com.example.soumyaagarwal.libraryontipsadmin.NavDrawer.snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin_page extends snackbar {

    Button addbook, registerstudent, viewbook, student, returnbook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_admin_page, frame);

        addbook = (Button)findViewById(R.id.addbook);
        viewbook = (Button)findViewById(R.id.viewbook);
        registerstudent = (Button)findViewById(R.id.registerstudent);
        student = (Button)findViewById(R.id.student);
        returnbook = (Button)findViewById(R.id.returnbook);

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, com.example.soumyaagarwal.libraryontipsadmin.AddBook.addbook.class);
                startActivity(intent);
                finish();
            }
        });

        registerstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, com.example.soumyaagarwal.libraryontipsadmin.RegisterStudent.registerstudent.class);
                startActivity(intent);
                finish();
            }
        });

        viewbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, com.example.soumyaagarwal.libraryontipsadmin.ViewBook.viewbook.class);
                startActivity(intent);
                finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, com.example.soumyaagarwal.libraryontipsadmin.Student.studentverify.class);
                startActivity(intent);
                finish();
            }
        });

        returnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_page.this, com.example.soumyaagarwal.libraryontipsadmin.BookReturn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}