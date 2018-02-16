package com.example.soumyaagarwal.libraryontipsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.HistoryBook;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentIntegrator;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * update available copies
 * remove book from current book list
 * put book into history book list
 * calculate total fine
 * put issue status of the book null
 * put due date null
 * put issue date null
 * calculate the book fine
 */
public class BookReturn extends AppCompatActivity
{
    EditText returnBarCode;
    Button scanReturnBarCode,ReturnbookButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStudent,mCopyBook,mBook,mS;
    String copyBookBarCode="";
    CopyBook copyBook;
    Integer FinePerDay=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_return);

        returnBarCode = (EditText) findViewById(R.id.returnBarCode);
        scanReturnBarCode = (Button)findViewById(R.id.scanReturnBarCode) ;
        ReturnbookButton = (Button) findViewById(R.id.ReturnbookButton) ;
        //ReturnbookButton.setVisibility(View.INVISIBLE);

        scanReturnBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(BookReturn.this);
                scanIntegrator.initiateScan();
            }
        });

        ReturnbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    copyBookBarCode = returnBarCode.getText().toString();
                    if(!copyBookBarCode.equals("")) {

                        mCopyBook = mDatabase.child("CopyBook").child(copyBookBarCode).getRef();

                        mCopyBook.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists())
                                {
                                    Map<String, Object> copyBookMap = (Map<String, Object>) dataSnapshot.getValue();

                                    copyBook = new CopyBook();

                                    copyBook.setBarCode((String) copyBookMap.get("BarCode"));
                                    copyBook.setDateIssued((String) copyBookMap.get("DateIssued"));
                                    copyBook.setISBN_No((String) copyBookMap.get("ISBN_No"));
                                    copyBook.setIssuedStatus((String) copyBookMap.get("IssuedStatus"));
                                    copyBook.setDueDate((String) copyBookMap.get("DueDate"));

                                    if (copyBook.getIssuedStatus().equals("null")) {
                                        Toast.makeText(getApplicationContext(), "Book is not issued yet", Toast.LENGTH_SHORT).show();
                                    } else {

                                        String RollNo = copyBook.getIssuedStatus();
                                        String ISBN_No = copyBook.getISBN_No();

                                        mCopyBook.child("IssuedStatus").setValue("null");
                                        mCopyBook.child("DueDate").setValue("null");
                                        mCopyBook.child("DateIssued").setValue("null");

                                        mS = mDatabase.child("Student").child(RollNo).getRef();

                                        mBook = mDatabase.child("Book").child(ISBN_No).child("AvailableCopies").getRef();

                                        mBook.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String Copies = dataSnapshot.getValue(String.class);
                                                updateAvailableCopies(Copies);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        mStudent = mS.child("CurrentBookList").child(copyBookBarCode).getRef();

                                        mStudent.addListenerForSingleValueEvent(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Map<String, Object> currentbookMap = (Map<String, Object>) dataSnapshot.getValue();

                                                HistoryBook hb = new HistoryBook();

                                                hb.setTitle((String) currentbookMap.get("Title"));
                                                hb.setAuthor((String) currentbookMap.get("Author"));
                                                hb.setIssuedDate((String) currentbookMap.get("IssuedDate"));
                                                hb.setReturnDate((String) currentbookMap.get("ReturnDate"));
                                                hb.setISBN_No((String) currentbookMap.get("ISBN_No"));

                                                DatabaseReference dbr = mS.child("HistoryBookList").child(copyBookBarCode);
                                                dbr.child("Title").setValue(hb.getTitle());
                                                dbr.child("Author").setValue(hb.getAuthor());
                                                dbr.child("IssuedDate").setValue(hb.getIssuedDate());
                                                dbr.child("ISBN_No").setValue(hb.getISBN_No());

                                                Calendar cal = Calendar.getInstance();
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                String rdate = hb.getReturnDate();
                                                String tdate = sdf.format(cal.getTime());

                                                dbr.child("ReturnDate").setValue(tdate);

                                                try {
                                                    Date rd = sdf.parse(rdate);
                                                    Date td = sdf.parse(tdate);

                                                    long diff = td.getTime() - rd.getTime();
                                                    final Long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                                    if (daysDiff > 0) {
                                                        dbr.child("Fine").setValue(daysDiff * FinePerDay + "");

                                                        mS.child("TotalFine").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                String totalFine = dataSnapshot.getValue(String.class);
                                                                updateTotalFine(totalFine, daysDiff);
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                    else
                                                        dbr.child("Fine").setValue("0");

                                                } catch (ParseException ex) {
                                                }
                                                Toast.makeText(getApplicationContext(), "Book Returned", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(BookReturn.this, admin_page.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Book Does Not Belong to Library", Toast.LENGTH_SHORT).show();
                                    copyBookBarCode = "";
                                }
                                //copyBookBarCode = "";
                                //returnBarCode.setText("");
                                //mCopyBook.removeEventListener(this);
//                            mStudent.child("CurrentBookList").child(copyBookBarCode).removeEventListener(vel);
//                          mStudent.child("CurrentBookList").child(copyBookBarCode).removeValue();
                                //ReturnbookButton.setVisibility(View.INVISIBLE);
                                mS.child("HistoryBookList").child(copyBookBarCode).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            mS.child("CurrentBookList").child(copyBookBarCode).removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else
                    {
                        Toast.makeText(getApplicationContext(),"Enter BarCode",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void updateTotalFine(String totalFine, long daysDiff) {
        long fine = Long.parseLong(totalFine)+daysDiff;
        mS.child("TotalFine").setValue(fine+"");
    }

    private void updateAvailableCopies(String copies)
    {
        mBook.setValue(String.valueOf(Integer.parseInt(copies)+1));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            returnBarCode.setText(scanContent);
            copyBookBarCode = scanContent;
            //ReturnbookButton.setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (BookReturn.this,admin_page.class);
        startActivity(intent);
        finish();
    }
}
