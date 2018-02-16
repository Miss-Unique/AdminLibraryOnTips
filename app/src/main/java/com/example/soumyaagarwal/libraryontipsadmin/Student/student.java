package com.example.soumyaagarwal.libraryontipsadmin.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;

public class student extends AppCompatActivity {
    Button bookissue, bookreissue;
    String sturollno;
    TextView verifiedrollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        bookissue = (Button)findViewById(R.id.bookissue);
        bookreissue = (Button)findViewById(R.id.bookreissue);
        verifiedrollno = (TextView)findViewById(R.id.verifiedrollno);

        sturollno = getIntent().getExtras().getString("sturollno");
        verifiedrollno.setText(sturollno);

        bookissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (student.this,BookIssue.class);
                intent.putExtra("sturollno",sturollno);
                startActivity(intent);
            }
        });

        bookreissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (student.this,BookReissue.class);
                intent.putExtra("sturollno",sturollno);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (student.this,admin_page.class);
        startActivity(intent);
        finish();
    }
}
