package com.example.soumyaagarwal.libraryontipsadmin.Student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.Book;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.Student;
import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentIntegrator;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class BookIssue extends AppCompatActivity
{
    EditText issuebarcode;
    Button scanissuebarcode,issuethis;
    String barcode,sturollno,isbn;
    DatabaseReference mDatabase,db,dbr,dbr2,dbr3;
    int l=0,ac,i,p;
    Calendar cal,calr;
    SimpleDateFormat sdf;
    Book book;
    CopyBook cbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_issue);

        issuebarcode = (EditText)findViewById(R.id.issuebarcode);
        scanissuebarcode = (Button)findViewById(R.id.scanissuebarcode);
        issuethis = (Button)findViewById(R.id.issuethis);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sturollno = getIntent().getExtras().getString("sturollno");

        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        calr = Calendar.getInstance();
        //sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        calr.add(Calendar.DAY_OF_YEAR, +7);


        scanissuebarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(BookIssue.this);
                scanIntegrator.initiateScan();
            }
        });

        issuethis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                barcode = issuebarcode.getText().toString().trim();
                if(!barcode.equals("")) {
                    l = 0;

                    db = mDatabase.child("CopyBook").child(barcode).getRef();

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists())
                            {
                                Map<String, Object> mapcBook = (Map<String, Object>) dataSnapshot.getValue();

                                cbook = new CopyBook();

                                cbook.setIssuedStatus((String) mapcBook.get("IssuedStatus"));
                                cbook.setISBN_No((String) mapcBook.get("ISBN_No"));
                                cbook.setDateIssued((String) mapcBook.get("DateIssued"));
                                cbook.setDueDate((String) mapcBook.get("DueDate"));

                                if (cbook.getIssuedStatus().equals("null")) {
                                    isbn = cbook.getISBN_No();
                                    dbr = mDatabase.child("CopyBook").child(barcode);
                                    dbr.child("IssuedStatus").setValue(sturollno);
                                    dbr.child("DateIssued").setValue(sdf.format(cal.getTime()));
                                    dbr.child("DueDate").setValue(sdf.format(calr.getTime()));

                                    dbr2 = mDatabase.child("Student").child(sturollno).child("CurrentBookList").child(barcode);
                                    dbr2.child("ISBN_No").setValue(cbook.getISBN_No());
                                    dbr2.child("IssuedDate").setValue(sdf.format(cal.getTime()));
                                    dbr2.child("ReturnDate").setValue(sdf.format(calr.getTime()));

                                    dbr3 = mDatabase.child("Book").child(isbn).getRef();

                                    dbr3.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Map<String, Object> mapBook = (Map<String, Object>) dataSnapshot.getValue();

                                            book = new Book();

                                            book.setAvailableCopies((String) mapBook.get("AvailableCopies"));
                                            book.setTitle((String) mapBook.get("Title"));
                                            book.setAuthor((String) mapBook.get("Author"));

                                            ac = Integer.parseInt(book.getAvailableCopies()) - 1;
                                            String m = String.valueOf(ac);
                                            dbr3.child("AvailableCopies").setValue(m);

                                            dbr2.child("Title").setValue(book.getTitle());
                                            dbr2.child("Author").setValue(book.getAuthor());

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    Toast.makeText(BookIssue.this, "Book Issued", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookIssue.this, student.class);
                                    intent.putExtra("sturollno", sturollno);
                                    barcode = "";
                                    startActivity(intent);
                                    finish();
                                }

                                else
                                {
                                    Toast.makeText(BookIssue.this, "Book is already Issued", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else
                            {
                                Toast.makeText(BookIssue.this, "This book do not belong to library", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter BarCode",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            issuebarcode.setText(scanContent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookIssue.this,student.class);
        intent.putExtra("sturollno",sturollno);
        startActivity(intent);
        finish();
    }
}
