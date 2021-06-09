package com.example.ontap1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText inputEmail,inputPassword,inputPassword1,name;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private TextView btnSignIn;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase database ;
    private static  final  String USER = "Users";
    private Users users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth =FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.btnDangNhap3);
        btnSignUp = findViewById(R.id.btnDangKiRe);
        inputEmail =  findViewById(R.id.emailRe);
        inputPassword =  findViewById(R.id.passwordRe);
        inputPassword1 =  findViewById(R.id.passwordRe1);
        name = findViewById(R.id.nameRe);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Sign_in.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = inputEmail.getText().toString().trim();
               String password = inputPassword.getText().toString().trim();
               String password2 = inputPassword1.getText().toString().trim();
               String namene = name.getText().toString().trim();

               if(TextUtils.isEmpty(namene)){
                   Toast.makeText(getApplicationContext(),"Invalid name confirm",Toast.LENGTH_SHORT).show();
                   return;
               }
                if(password.equals(password2)==false){
                    Toast.makeText(getApplicationContext(),"Invalid password confirm",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Invalid email confirm",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter  password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password  too shor",Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(email,password);

            }


        });
    }
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Register.this,  new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Register.this,"Authentication failed" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            UpdateUI(null);
                        }else {
                            Toast.makeText(Register.this,"createUserWithEmail : onComplete" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            UpdateUI(user);
                            finish();

                        }

                    }


                });

    }
    private void UpdateUI(FirebaseUser currentUser ) {
        mDatabaseReference.child(auth.getCurrentUser().getUid()).setValue(users);
        startActivity(new Intent(Register.this, FaceSceen.class));
    }
}
