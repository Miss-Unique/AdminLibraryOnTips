package com.example.soumyaagarwal.libraryontipsadmin.Services;
import android.app.IntentService;
import android.content.Intent;

import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.Book;
import com.example.soumyaagarwal.libraryontipsadmin.ModelClass.CopyBook;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UploadBookData extends IntentService {
    public UploadBookData() {
        super("UploadBookData");
    }
    public DatabaseReference DBREF;
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String CSVFilePath = intent.getStringExtra("CSVFileName");
            if(CSVFilePath!=null)
            {
                try {
                    CSVReader reader = new CSVReader(new FileReader(CSVFilePath));
                    String nextLine[];
                    while ((nextLine = reader.readNext()) != null) {
                        // nextLine[] is an array of values from the line
                        Book book = new Book(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4],nextLine[5],"0","0",nextLine[6],nextLine[6],nextLine[7]);
                        DBREF=FirebaseDatabase.getInstance().getReference();
                        DBREF.child("Book").child(nextLine[0]).setValue(book);
                        for (int i = 0,j=Integer.parseInt(nextLine[6]);i<j;i++)
                        {
                            CopyBook copyBook = new CopyBook(nextLine[i+10],nextLine[8],nextLine[0],nextLine[9],"null","null","null");
                            DBREF.child("CopyBook").child(nextLine[i+9]).setValue(copyBook);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
