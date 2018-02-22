package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.soumyaagarwal.libraryontipsadmin.LibraryMap.MapL;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.Book;
import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class BD extends AppCompatActivity {

    private Book book;
    TextView totalRating;
    TextView book_name, author, copies, sub, branch;
    String isbn, download_url, ShelfNo;
    StorageReference image_storage;
    ImageView image;
    DatabaseReference db;
    FloatingActionButton locateShelf, pdfread;
    ValueEventListener dbEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);

        isbn = getIntent().getStringExtra("ISBN_No");

        book_name = (TextView) findViewById(R.id.title);
        branch = (TextView) findViewById(R.id.branch);
        author = (TextView) findViewById(R.id.author);
        sub = (TextView) findViewById(R.id.subject);
        image = (ImageView) findViewById(R.id.detailimage);
        copies = (TextView) findViewById(R.id.copies_avail);
        totalRating = (TextView) findViewById(R.id.totalRating);
        pdfread = (FloatingActionButton) findViewById(R.id.pdfread);
        db = FirebaseDatabase.getInstance().getReference().child("Book").child(isbn).getRef();

        locateShelf = (FloatingActionButton) findViewById(R.id.detail_shelf);
        image_storage = FirebaseStorage.getInstance().getReference().child("image");

        dbEventListener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> mapBook = (Map<String, Object>) dataSnapshot.getValue();

                book = new Book();
                book.setAuthor((String) mapBook.get("Author"));
                book.setBranch((String) mapBook.get("Branch"));
                book.setCopiesNo((String) mapBook.get("CopiesNo"));
                book.setISBN_No((String) mapBook.get("ISBN_No"));
                book.setNoOfPages((String) mapBook.get("NoOfPages"));
                book.setRatings((String) mapBook.get("Ratings"));
                book.setRatingPeopleNumber((String) mapBook.get("RatingPeopleNumber"));
                book.setTitle((String) mapBook.get("Title"));
                book.setShelfNo((String) mapBook.get("ShelfNo"));
                book.setSubject((String) mapBook.get("Subject"));
                book.setAvailableCopies((String) mapBook.get("AvailableCopies"));
                book.setPath((String) mapBook.get("Path"));
                totalRating.setText(book.getRatings() + "/5");
                copies.setText(book.getAvailableCopies());
                book_name.setText(book.getTitle());
                author.setText(" -" + book.getAuthor());
                sub.setText(book.getSubject());
                ShelfNo = book.getShelfNo();
                branch.setText(book.getBranch());

                locateShelf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BD.this, MapL.class);
                        intent.putExtra("ShelfNo", ShelfNo);
                        startActivity(intent);
                    }
                });

               pdfread.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //TODO: DOwnload PDF
                   }
               });

                image_storage.child(isbn).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        download_url = uri.toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Can not load image", Toast.LENGTH_SHORT).show();
                    }
                });
                Glide.with(BD.this).load(download_url)
                        .thumbnail(0.5f)
                        .apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_name).transform(new CircleTransform(BD.this)).diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(image);
                //TODO: Picasso.with(getBaseContext()).load(download_url).placeholder(R.drawable.placeholder).into(image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (dbEventListener != null)
            db.removeEventListener(dbEventListener);

        Intent intent = new Intent(this, viewbook.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
