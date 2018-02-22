package com.example.soumyaagarwal.libraryontipsadmin.AddBook;

import com.example.soumyaagarwal.libraryontipsadmin.Internet.ConnectivityReceiver;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.Book;
import com.example.soumyaagarwal.libraryontipsadmin.MyApplication;
import com.example.soumyaagarwal.libraryontipsadmin.ViewBook.ViewBookAdapter;
import com.example.soumyaagarwal.libraryontipsadmin.ViewBook.tab1;
import com.example.soumyaagarwal.libraryontipsadmin.ViewBook.viewbd;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentIntegrator;
import com.example.soumyaagarwal.libraryontipsadmin.android.IntentResult;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;
import com.google.firebase.database.ValueEventListener;

public class addcopies extends AppCompatActivity
{
    DatabaseReference mDatabase,dbr,db,db2,db3;
    Button add;
    FloatingActionButton scanbarcode;
    EditText barcode,price,dop;
    TextView ISBN;
    String copyISBN, copybarcode, copyprice,copydop;
    private List<CopyBook> bookList;
    public RecyclerView recyclerView;
    private BookAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ChildEventListener childEventListener,childEventListener2;
    ScrollView parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcopies);

        add = (Button) findViewById(R.id.add);
        scanbarcode = (FloatingActionButton) findViewById(R.id.scanbarcode);
        ISBN = (TextView) findViewById(R.id.ISBN);
        ISBN.setText(getIntent().getExtras().getString("ISBN_No"));
        barcode = (EditText) findViewById(R.id.barcode);
        price = (EditText) findViewById(R.id.price);
        dop = (EditText) findViewById(R.id.dop);
        recyclerView = (RecyclerView) findViewById(R.id.copybook_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        parent = (ScrollView) findViewById(R.id.parent);

        scanbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        IntentIntegrator scanIntegrator = new IntentIntegrator(addcopies.this);
                        scanIntegrator.initiateScan();

                    }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                copyISBN = ISBN.getText().toString();
                copydop = dop.getText().toString();
                copyprice = price.getText().toString();
                copybarcode = barcode.getText().toString();

                if (TextUtils.isEmpty(copybarcode) || (TextUtils.isEmpty(copydop)) || TextUtils.isEmpty(copyprice))
                {
                    Snackbar snackbar = Snackbar
                            .make(parent, "Fill in all the details", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

                else
                {
                    boolean isConnected = ConnectivityReceiver.isConnected();
                    showSnack2(isConnected);
                }
            }


        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            barcode.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

        class ChildAddAsync extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(addcopies.this);
            pd.setMessage("Loading...");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(final String... params) {
            try {

                    db = mDatabase.child("CopyBook").getRef();

                    childEventListener = db.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            CopyBook book = dataSnapshot.getValue(CopyBook.class);
                            if(book.getISBN_No().equals(copyISBN))
                            {
                                book.setBarCode(dataSnapshot.getKey());
                                bookList.add(book);
                                mAdapter.notifyDataSetChanged();
                            }
                            pd.dismiss();
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
                            pd.dismiss();
                        }

                    });
            } catch (Exception e) {
                e.printStackTrace();

                pd.dismiss();
                Log.d("Failure", "FAILURE,FAILURE 111111");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String j)
        {

        }
    }

    private void showSnack2(boolean isConnected) {
        String message;
        int color;
        if (isConnected)
        {
            dbr = mDatabase.child("CopyBook").child(copybarcode);

            dbr.child("ISBN_No").setValue(copyISBN);
            dbr.child("DateOfPurchase").setValue(copydop);
            dbr.child("Price").setValue(copyprice);
            dbr.child("DueDate").setValue("null");
            dbr.child("IssuedStatus").setValue("null");
            dbr.child("DateIssued").setValue("null");


            bookList = new ArrayList<>();
            addingrows(bookList);
            new ChildAddAsync().execute();

            db2 = mDatabase.child("Book").getRef();

            childEventListener2 = db2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Book b = dataSnapshot.getValue(Book.class);

                    String k = dataSnapshot.getKey();
                    int a = Integer.parseInt(b.getCopiesNo())+1;
                    int c = Integer.parseInt(b.getAvailableCopies())+1;
                    String A = String.valueOf(a);
                    String C = String.valueOf(c);

                    if(k.equals(copyISBN))
                    {
                        db2.child(copyISBN).child("CopiesNo").setValue(A);
                        db2.child(copyISBN).child("AvailableCopies").setValue(C);
                        db2.child(copyISBN).child("BarCodeCopies").child(copybarcode).setValue(copybarcode);
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

            Toast.makeText(addcopies.this,"copy added",Toast.LENGTH_SHORT).show();
            dop.setText("");
            price.setText("");
            barcode.setText("");

        }
        else
        {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            Snackbar snackbar = Snackbar
                    .make(parent, message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }
    }

    private void addingrows(final List<CopyBook> k)
    {
        mAdapter = new BookAdapter(k);
        mLayoutManager = new LinearLayoutManager(addcopies.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        if(childEventListener2!=null)
        db2.removeEventListener(childEventListener2);
        startActivity(new Intent(addcopies.this,admin_page.class));
        finish();
      //  db.removeEventListener(childEventListener);

    }
}
