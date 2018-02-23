package com.example.soumyaagarwal.libraryontipsadmin.ViewBook;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.soumyaagarwal.libraryontipsadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ViewBookAdapter extends RecyclerView.Adapter<ViewBookAdapter.MyViewHolder> implements View.OnClickListener {

    private List<viewbd> bookList;
    private List<viewbd> filtered;
    Context context;

    public ViewBookAdapter(List<viewbd> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView book_name,book_author;
        public ImageView book_image;

        public MyViewHolder(View view) {
            super(view);
            book_name = (TextView) view.findViewById(R.id.book_name);
            book_author = (TextView) view.findViewById(R.id.book_author);
            book_image = (ImageView) view.findViewById(R.id.book_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewbook_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final viewbd book = bookList.get(position);
        holder.book_name.setText(book.getBook_name());
        holder.book_author.setText(book.getBook_author());
        /*Glide.with(context.getApplicationContext()).load(R.drawable.samplebook)
                .thumbnail(0.5f)
                .apply(new RequestOptions().fitCenter().placeholder(R.drawable.samplebook).diskCacheStrategy(DiskCacheStrategy.ALL).override(150, 150))
                .into(holder.book_image);*/

        Glide.with(context.getApplicationContext()).load(R.drawable.samplebook)
                .thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.samplebook).diskCacheStrategy(DiskCacheStrategy.ALL).override(150, 150)
                .into(holder.book_image);

        StorageReference image_storage = FirebaseStorage.getInstance().getReference().child("image");

        image_storage.child(book.getBook_ISBN()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String download_url = uri.toString();
                /*Glide.with(context.getApplicationContext()).load(download_url)
                        .thumbnail(0.5f)
                        .apply(new RequestOptions().fitCenter().placeholder(R.drawable.ic_name).diskCacheStrategy(DiskCacheStrategy.ALL).override(150, 150))
                        .into(holder.book_image);*/

                Glide.with(context.getApplicationContext()).load(download_url)
                        .thumbnail(0.5f)
                        .crossFade().placeholder(R.drawable.ic_name).diskCacheStrategy(DiskCacheStrategy.ALL).override(150, 150)
                        .into(holder.book_image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}