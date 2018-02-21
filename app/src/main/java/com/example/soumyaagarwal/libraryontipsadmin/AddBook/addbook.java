package com.example.soumyaagarwal.libraryontipsadmin.AddBook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soumyaagarwal.libraryontipsadmin.Internet.ConnectivityReceiver;
import com.example.soumyaagarwal.libraryontipsadmin.MyApplication;
import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.example.soumyaagarwal.libraryontipsadmin.admin_page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;

public class addbook extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    DatabaseReference mDatabase,dbr;
    EditText booktitle,bookauthor,bookISBN;
    AutoCompleteTextView booksubject,bookshelfnumber,bookbranch;
    Button done;
    String[] subjects,branches,shelves;
    LinearLayout parent;

    String author;
    String title, ISBN, subject, shelfnumber, branch;

    private static final int PICK_IMAGE_REQUEST = 234;

    private ImageButton imageButton;
    private StorageReference mStorageRef;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        bookauthor = (EditText)findViewById(R.id.bookauthor);
        bookISBN = (EditText)findViewById(R.id.bookISBN);
        bookshelfnumber = (AutoCompleteTextView) findViewById(R.id.bookshelfnumber);
        booktitle = (EditText)findViewById(R.id.booktitle);
        booksubject = (AutoCompleteTextView) findViewById(R.id.booksubject);
        bookbranch = (AutoCompleteTextView) findViewById(R.id.bookbranch);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageButton = (ImageButton) findViewById(R.id.imageView);
        done = (Button)findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPost();
            }
        });

        subjects = getResources().getStringArray(R.array.subjects);
        ArrayAdapter<String> adaptersubject = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1,subjects);
        booksubject.setAdapter(adaptersubject);
        booksubject.setThreshold(1);//will start working from first character
        booksubject.setTextColor(Color.BLACK);

        branches = getResources().getStringArray(R.array.branches);
        ArrayAdapter<String> adapterbranch = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1,branches);
        bookbranch.setAdapter(adapterbranch);
        bookbranch.setThreshold(1);//will start working from first character
        bookbranch.setTextColor(Color.BLACK);

        shelves = getResources().getStringArray(R.array.shelves);
        ArrayAdapter<String> adaptershelf = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1,shelves);
        bookshelfnumber.setAdapter(adaptershelf);
        bookshelfnumber.setThreshold(1);//will start working from first character
        bookshelfnumber.setTextColor(Color.BLACK);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

    }

    private void startPost() {

        if (!Arrays.asList(subjects).contains(booksubject.getText().toString().trim()))
        {
            booksubject.setText("");
        }

        if (!Arrays.asList(branches).contains(bookbranch.getText().toString().trim()))
        {
            bookbranch.setText("");
        }

        if (!Arrays.asList(subjects).contains(booksubject.getText().toString().trim()))
        {
            booksubject.setText("");
        }

        author = bookauthor.getText().toString().trim();
        title = booktitle.getText().toString().trim();
        ISBN = bookISBN.getText().toString().trim();
        subject = booksubject.getText().toString().trim();
        shelfnumber = bookshelfnumber.getText().toString().trim();
        branch = bookbranch.getText().toString().trim();

            if (title.isEmpty()&&author.isEmpty()&&ISBN.isEmpty()&&subject.isEmpty()&&shelfnumber.isEmpty()&&branch.isEmpty()&&filePath==null)
            {
                Toast.makeText(addbook.this, "Choose an Image", Toast.LENGTH_SHORT).show();
            }

        if (TextUtils.isEmpty(author) || TextUtils.isEmpty(title)||TextUtils.isEmpty(ISBN) || TextUtils.isEmpty(subject)||TextUtils.isEmpty(shelfnumber)||TextUtils.isEmpty(branch)) {
            Toast.makeText(addbook.this, "Field cannot be Left Empty", Toast.LENGTH_SHORT).show();
        }
         else   {
            boolean isConnected = ConnectivityReceiver.isConnected();
            showSnack(isConnected);

        }
    }


    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected)
        {
            dbr = mDatabase.child("Book").child(ISBN);

            dbr.child("Title").setValue(title);
            dbr.child("Author").setValue(author);
            dbr.child("ShelfNo").setValue(shelfnumber);
            dbr.child("Subject").setValue(subject);
            dbr.child("Ratings").setValue("0");
            dbr.child("RatingPeopleNumber").setValue("0");
            dbr.child("CopiesNo").setValue("0");
            dbr.child("AvailableCopies").setValue("0");
            dbr.child("Branch").setValue(branch);
            uploadFile();

            Toast.makeText(addbook.this,"Book Added Successfully",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(addbook.this,addcopies.class);
            intent.putExtra("ISBN_No",ISBN);
            startActivity(intent);
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

    @Override
    protected void onResume()
    {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected)
    {
        showSnack(isConnected);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            //data.getType()
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageButton.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = mStorageRef.child("image").child(ISBN);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(addbook.this,admin_page.class));
        finish();
        //  db.removeEventListener(childEventListener);

    }

}