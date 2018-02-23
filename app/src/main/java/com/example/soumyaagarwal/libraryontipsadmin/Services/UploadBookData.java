package com.example.soumyaagarwal.libraryontipsadmin.Services;
import android.app.IntentService;
import android.content.Intent;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UploadBookData extends IntentService {
    public UploadBookData() {
        super("UploadBookData");
    }

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
                        System.out.println(nextLine[0]);
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