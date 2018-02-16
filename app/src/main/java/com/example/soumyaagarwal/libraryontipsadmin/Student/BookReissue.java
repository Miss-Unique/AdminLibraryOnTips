package com.example.soumyaagarwal.libraryontipsadmin.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.HistoryBook;
import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentIntegrator;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentResult;
import com.google.firebase.database.ChildEventListener;
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
 * Created by RajK on 13-03-2017.
 *
 * increase total fine
 * change the due date in current book and copy book
 * change the issue date in current book and copy book
 *
 */
public class BookReissue extends AppCompatActivity{
    EditText reissueBarCode;
    Button scanreissueBarCode,reissueBookButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStudent,mCopyBook,mBook;
    String copyBookBarCode="",RollNo;
    CopyBook copyBook;
    Integer FinePerDay=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RollNo = getIntent().getExtras().getString("sturollno");

        setContentView(R.layout.activity_book_reissue);

        reissueBarCode = (EditText)findViewById(R.id.reissueBarCode);
        scanreissueBarCode = (Button)findViewById(R.id.scanreissueBarCode) ;
        reissueBookButton = (Button) findViewById(R.id.reissueBookButton) ;

        scanreissueBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(BookReissue.this);
                scanIntegrator.initiateScan();
            }
        });

        reissueBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                copyBookBarCode = reissueBarCode.getText().toString();

                if (!TextUtils.isEmpty(copyBookBarCode))
                {

                    mCopyBook = mDatabase.child("CopyBook").child(copyBookBarCode).getRef();

                    mCopyBook.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                Map<String,Object> copyBookMap = (Map<String,Object>) dataSnapshot.getValue();

                                //retreive CopyBook
                                copyBook = new CopyBook();

                                copyBook.setBarCode((String) copyBookMap.get("BarCode"));
                                copyBook.setDateIssued((String) copyBookMap.get("DateIssued"));
                                copyBook.setISBN_No((String) copyBookMap.get("ISBN_No"));
                                copyBook.setIssuedStatus((String) copyBookMap.get("IssuedStatus"));
                                copyBook.setDueDate((String) copyBookMap.get("DueDate"));
                                //retreive CopyBook
                                if(copyBook.getIssuedStatus().equals("null")) // Book is not issued
                                {

                                    Toast.makeText(getApplicationContext(),"Book is not issued yet",Toast.LENGTH_SHORT).show();
                                }
                                else if(!copyBook.getIssuedStatus().equals(RollNo)) // Book is not issued to the given rollNo
                                {

                                    Toast.makeText(getApplicationContext(),"Book not issued to "+RollNo,Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    RollNo = copyBook.getIssuedStatus();
                                    mStudent = mDatabase.child("Student").child(RollNo).getRef();
                                    mStudent.child("CurrentBookList").child(copyBookBarCode).addValueEventListener(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Map<String,Object> currentbookMap = (Map<String,Object>) dataSnapshot.getValue();
                                            HistoryBook hb = new HistoryBook();
                                            hb.setReturnDate((String) currentbookMap.get("ReturnDate"));
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                            Calendar calToday = Calendar.getInstance();
                                            String rdate = hb.getReturnDate();
                                            String tdate = sdf.format(calToday.getTime());


                                            try {

                                                Date rd = sdf.parse(rdate);  // get due date
                                                Date td = sdf.parse(tdate);

                                                long diff = td.getTime() - rd.getTime();
                                                final Long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                                                mCopyBook.child("DateIssued").setValue(tdate);

                                                Calendar calr = Calendar.getInstance();
                                                calr.add(Calendar.DAY_OF_YEAR, +7);

                                                mCopyBook.child("DueDate").setValue(sdf.format(calr.getTime()));

                                                mStudent.child("CurrentBookList").child(copyBookBarCode).child("ReturnDate").setValue(sdf.format(calr.getTime()));
                                                mStudent.child("CurrentBookList").child(copyBookBarCode).child("IssuedDate").setValue(tdate);

                                                if(daysDiff>0) {
                                                    mStudent.child("TotalFine").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                            } catch (ParseException ex) {
                                            }
                                            mStudent.child("CurrentBookList").child(copyBookBarCode).removeEventListener(this);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError)
                                        {

                                        }
                                    });
                                    Toast.makeText(getApplicationContext(),"Book Reissued",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookReissue.this, student.class);
                                    intent.putExtra("sturollno", RollNo);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Book Does Not Belong to Library",Toast.LENGTH_SHORT).show();
                            }
                            //copyBookBarCode = "";
                            //reissueBarCode.setText("");
                            //mCopyBook.removeEventListener(this);
                            //reissueBookButton.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }


    private void updateTotalFine(String totalFine, long daysDiff) {
        long fine = Long.parseLong(totalFine)+daysDiff;
        mStudent.child("TotalFine").setValue(fine+"");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            reissueBarCode.setText(scanContent);
            reissueBookButton.setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(BookReissue.this,student.class);
        intent.putExtra("sturollno",RollNo);
        startActivity(intent);
        finish();
    }
}
