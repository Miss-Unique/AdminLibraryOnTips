package com.example.soumyaagarwal.libraryontipsadmin.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadPhotoAndFile extends IntentService {
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public UploadPhotoAndFile() {
        super("UploadPhotoAndFile");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String filePath = intent.getStringExtra("filePath");
            String mykey = intent.getStringExtra("mykey");
            String otheruserkey = intent.getStringExtra("otheruserkey");
            String receiverToken = intent.getStringExtra("receiverToken");
            String bookId = intent.getStringExtra("bookId");
            String timestamp = intent.getStringExtra("timestamp");
            long id = intent.getLongExtra("id", 0L);
            db = db.child("Book").child(bookId).child("Path");
            uploadFile(filePath, mykey, otheruserkey, receiverToken, bookId, db, timestamp, id);
        }
    }

    public void uploadFile(final String path, final String mykey, final String otheruserkey, final String receiverToken, final String dbTableKey, final DatabaseReference dbChat, final String timestamp, final long id) {
        //if there is a file to upload
        //put case
//        System.out.println("uri found" + Uri.fromFile(new File(path)));
        if (Uri.fromFile(new File(path)) != null) {
            //displaying a progress dialog while upload is going on
            StorageReference riversRef = mStorageRef.child(dbTableKey).child("files");


            riversRef.putFile(Uri.fromFile(new File(path)))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            db.setValue(downloadUrl.toString());
                     //       ChatMessage cm = new ChatMessage(mykey, otheruserkey, timestamp, "doc", id + "", "0", downloadUrl.toString(), receiverToken, dbTableKey, 100, path, "");
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            dbChat.child(String.valueOf(id)).removeValue();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                   /* .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                            dbChat.child(String.valueOf(id)).child("percentUploaded").setValue(progress);
                            //displaying percentage in progress dialog
                            //                              progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });*/


        }
    }
}