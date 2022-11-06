package com.user.technician.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.technician.modal.TechnicianModal;

import java.util.HashMap;

public class TechnicianDatabase {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public  TechnicianDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Technician");
        mAuth=FirebaseAuth.getInstance();
    }

    public Task<Void> addTechnician(TechnicianModal technicianModal){

        return databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(technicianModal);
    }

    public Task<AuthResult> technicianAuth(TechnicianModal technicianModal){

        return mAuth.createUserWithEmailAndPassword(technicianModal.getEmail(), technicianModal.getPassword());

    }

    public Task<AuthResult> technicianSignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public  Task<Void> technicianUpdate(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> technicianDelete(String key){
        return databaseReference.child(key).removeValue();
    }

}
