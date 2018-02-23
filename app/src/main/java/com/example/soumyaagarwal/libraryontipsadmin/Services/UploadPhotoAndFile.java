package com.example.soumyaagarwal.libraryontipsadmin.Services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPhotoAndFile extends IntentService {
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public UploadPhotoAndFile() {
        super("UploadPhotoAndFile");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String filepathpdf = intent.getStringExtra("filepathpdf");
            String filepathimg = intent.getStringExtra("filepathimg");
            String isbn = intent.getStringExtra("isbn");
            uploadFile(filepathpdf, filepathimg, isbn);
        }
    }

    public void uploadFile(final String pathpdf, final String pathimg, final String isbn) {
        if (pathimg != null) {

            StorageReference riversRef = mStorageRef.child("image").child(isbn);
            riversRef.putFile(Uri.parse(pathimg))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "IMG Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }
        if (pathpdf != null) {
            StorageReference riversRef = mStorageRef.child("pdf").child(isbn);
            riversRef.putFile(Uri.parse(pathimg))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "PDF Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        }
                    });
        }

    }
}