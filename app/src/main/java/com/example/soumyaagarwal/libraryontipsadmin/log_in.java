package com.example.soumyaagarwal.libraryontipsadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class log_in extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private Button btnLogin;
    private ScrollView parentlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(log_in.this, admin_page.class));
            finish();
        }

        setContentView(R.layout.activity_log_in);

        inputEmail = (EditText) findViewById(R.id.emaillogin);
        inputPassword = (EditText) findViewById(R.id.passwordlogin);
        btnLogin = (Button) findViewById(R.id.btn_loginlogin);
        parentlayout = (ScrollView) findViewById(R.id.scrollView);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    inputEmail.setText("");
                    inputPassword.setText("");
                    Snackbar snackbar = Snackbar
                            .make(parentlayout, "Either Email or Password is wrong", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {

                    progressDialog = new ProgressDialog(log_in.this, R.style.MyAlertDialogStyle);
                    progressDialog.setMax(100);
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(log_in.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressDialog.dismiss();
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(log_in.this, "Authentication Failed", Toast.LENGTH_LONG).show();

                                    } else {
                                        startActivity(new Intent(log_in.this, admin_page.class));
                                    }
                                }
                            });

                }
            }
        });

    }
}