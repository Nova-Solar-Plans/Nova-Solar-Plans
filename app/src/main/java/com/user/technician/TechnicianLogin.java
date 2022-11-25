package com.user.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.user.main.R;
import com.user.main.UserNavigation;
import com.user.technician.database.TechnicianDatabase;
import com.user.technician.modal.TechnicianModal;

public class TechnicianLogin extends AppCompatActivity {

    private Button LoginBtn;
    private LinearLayout RegisterBtn;
    private EditText Email,Password;
    private ImageView BackBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_login);

        mAuth = FirebaseAuth.getInstance();
        TechnicianDatabase technicianDatabase = new TechnicianDatabase();


        LoginBtn = findViewById(R.id.login_btn);
        RegisterBtn = findViewById(R.id.register_layout);
        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_password);
        BackBtn = findViewById(R.id.nav_back);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechnicianLogin.this, UserNavigation.class);
                startActivity(intent);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = Email.getText().toString().trim();
                String pw = Password.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    Email.setError("Please enter email");
                    Email.requestFocus();
                }
                if(Password.length() < 6 ){
                    Password.setError("Password should contain at least 6 characters");
                    Password.requestFocus();
                }

                if(Patterns.EMAIL_ADDRESS.matcher(mail).matches() && pw.length() >=6){

                    technicianDatabase.technicianSignIn(mail,pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        int duration = Toast.LENGTH_SHORT;
                                        CharSequence msg = "Success";
                                        Context context = getApplicationContext();

                                        Toast toast = Toast.makeText(context, msg, duration);
                                        toast.show();
                                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);
                                        startActivity(new Intent(TechnicianLogin.this,DashBoard.class).putExtra("email",mail));
                                    }else{
                                        int duration = Toast.LENGTH_SHORT;
                                        CharSequence msg = "Fail to Login";
                                        Context context = getApplicationContext();

                                        Toast toast = Toast.makeText(context, msg, duration);
                                        toast.show();
                                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);
                                    }

                                }
                            });
                }
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechnicianLogin.this,TechnicianRegister.class);
                startActivity(intent);
            }
        });
    }
}