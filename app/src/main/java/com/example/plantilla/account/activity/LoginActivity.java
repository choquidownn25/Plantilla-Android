package com.example.plantilla.account.activity;

import android.content.Intent;
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
import com.example.plantilla.ui.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //region Atributos
    private EditText email;
    private EditText password;
    private Button loginButton;
    private Button resetButton;
    private Button btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth; //Clase Firebase
    //endregion

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email  = (EditText) findViewById(R.id.email);
        password  = (EditText) findViewById(R.id.password);
        loginButton  = (Button) findViewById(R.id.login_button);
        resetButton  = (Button) findViewById(R.id.reset_button);
        btnSignup  = (Button) findViewById(R.id.btn_signup);
        progressBar  = (ProgressBar) findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();


        // proceso de inicio de sesión automático
        // pasar a la actividad principal si el usuario ya inició sesión

        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            startActivity(new Intent(LoginActivity.this, PagerActivity.class));
            finish();
        }
        //Retorno de instancia de esta clase
        firebaseAuth = FirebaseAuth.getInstance();
        //Registrar usuario
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        //Recuparar clave
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });
        //Acceder
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                final String userpassword = password.getText().toString();

                if (TextUtils.isEmpty(useremail)) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userpassword)) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //login user
                firebaseAuth.signInWithEmailAndPassword(useremail,userpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {

                                    if (userpassword.length() < 6) {
                                        password.setError(LoginActivity.this.getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, PagerActivity.class));
                                    LoginActivity.this.finish();
                                }
                            }
                        });

            }
        });
    }

}
