package com.example.soumyaagarwal.libraryontipsadmin.Student;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.soumyaagarwal.libraryontipsadmin.R;


public class QRCodeScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView qrCodeReaderView;
    private TextView textView;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout.LayoutParams layoutParams_frame;
    private AlertDialog viewSelectedImages;
    private String sturollno;
    public static String QRCode = "ISBN_No";
    ImageView imageView;
    public static String activity = "activity";
    int height, width;
    private String activityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        getSupportActionBar().setTitle("QR Code Scanner");
        sturollno = getIntent().getStringExtra("sturollno");
        activityString = getIntent().getStringExtra(activity);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        imageView = (ImageView) findViewById(R.id.frame_);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);

        hello();

    }

    private void hello() {
        layoutParams = new RelativeLayout.LayoutParams(width, height * 75 / 100);
        layoutParams.topMargin = 0;
        layoutParams.leftMargin = 0;
        layoutParams_frame = new RelativeLayout.LayoutParams(width, height * 55 / 100);
        layoutParams_frame.topMargin = height*10/100;
        layoutParams_frame.leftMargin = 0;

        qrCodeReaderView.setLayoutParams(layoutParams);
        imageView.setLayoutParams(layoutParams_frame);
        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(false);

        // Use this function to set front camera preview
        //qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();

        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrCodeReaderView.forceAutoFocus();
            }
        });
        textView = (TextView) findViewById(R.id.text);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
        if(text.contains("//")||text.contains(":")||text.contains("."))
        {
            Toast.makeText(this,"Not a valid code",Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        else {
            if(activityString.equals(BookIssue.IssueActivity)) {
                Intent intent = new Intent(this, BookIssue.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(QRCode, text);
                intent.putExtra("sturollno", sturollno);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(this, BookReissue.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(QRCode, text);
                intent.putExtra("sturollno", sturollno);
                startActivity(intent);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.navigation, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.:
            }
            return true;
        }
    */
    @Override
    public void onBackPressed() {
        finish();

    }
}