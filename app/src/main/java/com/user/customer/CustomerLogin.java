package com.user.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.user.main.R;
import com.user.manager.ManagerDashboard;
import com.user.manager.ManagerLogin;

public class CustomerLogin extends AppCompatActivity {

    private EditText Email,Password;
    private Button LoginBtn;
    private LinearLayout RegisterLayout;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        Email = findViewById(R.id.customer_email);
        Password = findViewById(R.id.customer_password);
        LoginBtn = findViewById(R.id.customer_login_btn);
        RegisterLayout = findViewById(R.id.customer_register_layout);

        RegisterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerLogin.this,CustomerRegister.class);
                startActivity(intent);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                boolean isSuccess = true;

                if(email.isEmpty()){
                    Email.setError("Field Required!");
                    Email.requestFocus();
                    isSuccess = false;
                }
                if(password.isEmpty()){
                    Password.setError("Field Required!");
                    Password.requestFocus();
                    isSuccess = false;
                }

                if(isSuccess){

                    mAuth=FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                int duration = Toast.LENGTH_SHORT;
                                CharSequence msg = "Success";
                                Context context = getApplicationContext();

                                Toast toast = Toast.makeText(context, msg, duration);
                                toast.show();
                                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 10, 0);
                                Intent intent = new Intent(CustomerLogin.this, CustomerDashboard.class);
                                startActivity(intent);
                            }else {
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
    }
}