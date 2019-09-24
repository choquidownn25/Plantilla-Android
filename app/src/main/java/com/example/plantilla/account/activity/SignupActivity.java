package com.example.plantilla.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.ui.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    //region Atributos
    private static final String TAG = "SignupActivity";
    private EditText email;
    private EditText password;
    private Button signUpButton;
    private Button signInButton;
    private ProgressBar progressBar;
    private Button resetButton;
    private FirebaseAuth firebaseAuth;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email  = (EditText) findViewById(R.id.email);
        password  = (EditText) findViewById(R.id.password);
        signUpButton  = (Button) findViewById(R.id.sign_up_button);
        signInButton  = (Button) findViewById(R.id.sign_in_button);
        resetButton  = (Button) findViewById(R.id.reset_button);
        progressBar  = (ProgressBar) findViewById(R.id.progress_bar);


        //click R.layout.activity_signup press alt + enter to generate

        //firebase authentication instance
        firebaseAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.startActivity(new Intent(SignupActivity.this, ResetActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.registerUser();
            }
        });

    }

    private void registerUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(userEmail)) {
            showToast("Enter email address!");
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            showToast("Enter Password!");
            return;
        }

        if(userPassword.length() < 6){
            showToast(SignupActivity.this.getString(R.string.minimum_password));
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Registrar usuario
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "New user registration: " + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            SignupActivity.this.showToast(SignupActivity.this.getString(R.string.auth_failed_signup) + task.getException());
                            Toast.makeText(getApplicationContext(), SignupActivity.this.getString(R.string.auth_failed_signup) +  task.getException(),Toast.LENGTH_LONG).show();
                        } else {
                            SignupActivity.this.startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), SignupActivity.this.getString(R.string.auth_failed_success), Toast.LENGTH_LONG).show();
                            SignupActivity.this.finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}
