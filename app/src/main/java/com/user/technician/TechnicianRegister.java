package com.user.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.user.main.R;
import com.user.technician.database.TechnicianDatabase;
import com.user.technician.modal.TechnicianModal;

public class TechnicianRegister extends AppCompatActivity {

    private ImageView BackLogin;
    private LinearLayout BackLoginLayout;
    private EditText Name,Email,Address,NIC,Password,ConfirmPassword;
    private Button RegisterButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_register);

        TechnicianDatabase technicianDatabase = new TechnicianDatabase();

        progressDialog = new ProgressDialog(this);


        BackLogin =findViewById(R.id.back_login);
        BackLoginLayout = findViewById(R.id.back_login_layout);

        Name = findViewById(R.id.register_name);
        Email = findViewById(R.id.register_email);
        Address = findViewById(R.id.register_address);
        NIC = findViewById(R.id.register_nic);
        Password = findViewById(R.id.register_password);
        ConfirmPassword = findViewById(R.id.register_confirm_password);
        RegisterButton = findViewById(R.id.technician_register_btn);


        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String nic = NIC.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cnfmPassword = ConfirmPassword.getText().toString().trim();
                boolean isSuccess = true;

                if(name.isEmpty()){
                    Name.setError("Field required");
                    Name.requestFocus();
                    isSuccess = false;
                }
                if(email.isEmpty()){
                    Email.setError("Field required");
                    Email.requestFocus();
                    isSuccess = false;
                }
                if(address.isEmpty()){
                    Address.setError("Field required");
                    Address.requestFocus();
                    isSuccess = false;
                }
                if(nic.isEmpty()){
                    NIC.setError("Field required");
                    NIC.requestFocus();
                    isSuccess = false;
                }
                if(password.isEmpty()){
                    Password.setError("Field required");
                    Password.requestFocus();
                    isSuccess = false;
                }
                if(cnfmPassword.isEmpty()){
                    ConfirmPassword.setError("Field required");
                    ConfirmPassword.requestFocus();
                    isSuccess = false;
                }
                if(!password.equals(cnfmPassword)){
                    ConfirmPassword.setError("Passwords didn't match");
                    ConfirmPassword.requestFocus();
                    isSuccess = false;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Please enter valid email");
                    Email.requestFocus();
                    isSuccess=false;
                }


                if(isSuccess){
                    TechnicianModal technicianModal = new TechnicianModal(name, email, address,nic, password, "0");

                    technicianDatabase.technicianAuth(technicianModal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                technicianDatabase.addTechnician(technicianModal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(TechnicianRegister.this, "Technician Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(TechnicianRegister.this, TechnicianLogin.class));
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(TechnicianRegister.this, "Technician Failed to Register", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(TechnicianRegister.this, "Technician Failed to Register email", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                }
            }
        });


        BackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechnicianRegister.this,TechnicianLogin.class);
                startActivity(intent);
            }
        });

        BackLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechnicianRegister.this,TechnicianLogin.class);
                startActivity(intent);
            }
        });
    }
}