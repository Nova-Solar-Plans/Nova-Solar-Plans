package com.user.customer;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.user.customer.database.CustomerDatabase;
import com.user.customer.modal.CustomerModal;
import com.user.main.R;
import com.user.technician.TechnicianLogin;
import com.user.technician.TechnicianRegister;

public class CustomerRegister extends AppCompatActivity {

    private ImageView BackArrow;
    private LinearLayout BackLoginLayout;
    private Button RegisterBtn;
    private EditText Name,Email,Address,Password,CnfmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        progressDialog = new ProgressDialog(this);

        CustomerDatabase customerDatabase = new CustomerDatabase();


        BackArrow = findViewById(R.id.customer_back_login);
        BackLoginLayout = findViewById(R.id.customer_back_login_layout);
        RegisterBtn = findViewById(R.id.customer_register_btn);
        Name = findViewById(R.id.customer_register_name);
        Email = findViewById(R.id.customer_register_email);
        Address = findViewById(R.id.customer_register_address);
        Password = findViewById(R.id.customer_register_password);
        CnfmPassword = findViewById(R.id.customer_register_confirm_password);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cnfmPassword = CnfmPassword.getText().toString().trim();
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
                if(password.isEmpty()){
                    Password.setError("Field required");
                    Password.requestFocus();
                    isSuccess = false;
                }
                if(cnfmPassword.isEmpty()){
                    CnfmPassword.setError("Field required");
                    CnfmPassword.requestFocus();
                    isSuccess = false;
                }
                if(!password.equals(cnfmPassword)){
                    CnfmPassword.setError("Passwords didn't match");
                    CnfmPassword.requestFocus();
                    isSuccess = false;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Please enter valid email");
                    Email.requestFocus();
                    isSuccess=false;
                }

                if(isSuccess){

                    CustomerModal customerModal = new CustomerModal(name,email,address,password);

                    customerDatabase.customerAuth(customerModal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                customerDatabase.addCustomer(customerModal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(CustomerRegister.this, "Customer Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CustomerRegister.this, CustomerLogin.class));
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(CustomerRegister.this, "Customer Failed to Register", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(CustomerRegister.this, "Customer Failed to Register email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    progressDialog.dismiss();
                }



            }
        });
    }
}