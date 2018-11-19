package com.example.nikhil.authapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private TextView textViewSignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressDialog = new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        buttonSignIn=(Button) findViewById(R.id.buttonSignIn);
        textViewSignup =(TextView)findViewById(R.id.textViewSignUp);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

        private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                //email is empty
                Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();
                //stop func from executing further
                return;
            }

            if(TextUtils.isEmpty(password)){
                //password is empty
                Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Signing In...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();

                       if(task.isSuccessful()){
                           //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                       }


                        }
                    });
        }
    @Override
    public void onClick(View view) {

        if (view == buttonSignIn) {
            userLogin();

        }

        if (view == textViewSignup) {

            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

    }
}
