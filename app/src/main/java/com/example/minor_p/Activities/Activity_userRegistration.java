package com.example.minor_p.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.minor_p.Model_Activities.UserData_model;
import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityUserRegistationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Activity_userRegistration extends AppCompatActivity {
    ActivityUserRegistationBinding binding;
    private String mobileNo;
    private DatabaseReference mDatabase;
    private String firstName,lastName,userId,email,location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_user_registation);
        mobileNo=getIntent().getStringExtra("Mobile_No");
        userId=getIntent().getStringExtra("userId");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //db = FirebaseFirestore.getInstance();

        binding.btRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName=binding.etFirstName.getText().toString();
                lastName=binding.etLastName.getText().toString();
                email=binding.etEmail.getText().toString();
                location=binding.etLocation.getText().toString();
                if(firstName.isEmpty()){
                    binding.etFirstName.setError("Filed is empty");
                }else if(lastName.isEmpty()){
                    binding.etLastName.setError("Filed is empty");
                }else if(location.isEmpty()){
                    binding.etLocation.setError("Field is empty");
                }else{
                    registerUser(firstName,lastName,email,mobileNo,location);
                }
            }
        });
    }
    private void registerUser(String user_firstName,String user_lastName,String userEmail, String userMobile_no, String userLocation){
        UserData_model data_model=new UserData_model(user_firstName,user_lastName,userEmail,userMobile_no,userLocation);
        mDatabase.child("USERS").child(userId).setValue(data_model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Intent intent=new Intent(Activity_userRegistration.this,MainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(Activity_userRegistration.this,"Something went wrong",Toast.LENGTH_SHORT).show();
             }
         });
    }

}
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference user_data1 = database.getReference("user_data");
//        user_data1.child("USERS").child("MobileNo").get();
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference user_data = rootRef.child("USERS").push();
//        Map<String, Object> updates = new HashMap<>();
//        updates.put("Name",binding.etName.getText().toString());
//        if (binding.etEmail.getText().toString().isEmpty()){
//            updates.put("Email"," ");
//        }else{
//            updates.put("Email",binding.etEmail.getText().toString());
//            updates.put("MobileNo",mobileNo);
//            updates.put("Location",binding.etLocation.getText().toString());
//
//         user_data.setValue(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
//             @Override
//             public void onSuccess(Void unused) {
//
//                 startActivity(new Intent(Activity_userRegistration.this,MainActivity.class));
//             }
//         }).addOnFailureListener(new OnFailureListener() {
//             @Override
//             public void onFailure(@NonNull Exception e) {
//                 Toast.makeText(Activity_userRegistration.this,"Something went wrong",Toast.LENGTH_SHORT).show();
//             }
//         });
//        }