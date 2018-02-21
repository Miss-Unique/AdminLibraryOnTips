package com.example.soumyaagarwal.libraryontipsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class admin_page extends AppCompatActivity {

    LinearLayout addbook, registerstudent, viewbook, student, returnbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_page);

        addbook = (LinearLayout) findViewById(R.id.addbook);
        viewbook = (LinearLayout) findViewById(R.id.viewbook);
        registerstudent = (LinearLayout) findViewById(R.id.registerstudent);
        student = (LinearLayout) findViewById(R.id.student);
        returnbook = (LinearLayout) findViewById(R.id.returnbook);

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