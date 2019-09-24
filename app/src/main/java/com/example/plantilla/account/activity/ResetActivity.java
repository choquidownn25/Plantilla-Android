package com.example.plantilla.account.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.plantilla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    //region Atributos
    private EditText email;
    private Button btnResetPassword;
    private Button btnBack;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email  = (EditText) findViewById(R.id.email);
        btnResetPassword  = (Button) findViewById(R.id.btn_reset_password);
        btnBack  = (Button) findViewById(R.id.btn_back);
        progressBar  = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetActivity.this.finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();

                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(ResetActivity.this, "Enter your register email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //reset password you will get a mail
                firebaseAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}
