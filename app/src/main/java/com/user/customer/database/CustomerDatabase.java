package com.user.customer.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.customer.modal.CustomerModal;
import com.user.technician.modal.TechnicianModal;

import java.util.HashMap;

public class CustomerDatabase {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  CustomerDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Customer");
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addCustomer(CustomerModal customerModal){

        return databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(customerModal);
    }

    public Task<AuthResult> customerAuth(CustomerModal customerModal){

        return mAuth.createUserWithEmailAndPassword(customerModal.getEmail(), customerModal.getPassword());

    }

    public Task<AuthResult> customerSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> customerUpdate(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> customerDelete(String key){
        return databaseReference.child(key).removeValue();
    }

}
