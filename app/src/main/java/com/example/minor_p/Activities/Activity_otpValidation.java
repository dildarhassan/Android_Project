package com.example.minor_p.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityOtpValidation1Binding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Activity_otpValidation extends AppCompatActivity implements Dialogue_otpVerification.DialogInterface {
    ActivityOtpValidation1Binding binding;
    private String mobile_number;
    private String string_otp_id;
    private FirebaseAuth mAuth;
    private String userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_otp_validation1);
        mAuth=FirebaseAuth.getInstance();


        binding.btnSubmitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_number=binding.etPhonenumber.getText().toString();
                if(mobile_number.isEmpty() ){
                    binding.etPhonenumber.setError("Phone number is empty.");
                }else if (mobile_number.length()<10){
                    binding.etPhonenumber.setError("Enter a valid phone number");
                }else {
                      initiate_otp();
                }
            }
        });

    }
    private void initiate_otp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+mobile_number)                             //Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)                   // Timeout and unit
                        .setActivity(Activity_otpValidation.this)                // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            //getOwnerActivity()
                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                string_otp_id = verificationId;
                                Toast.makeText(Activity_otpValidation.this,"OTP send ",Toast.LENGTH_LONG).show();
                                Dialogue_otpVerification dialogue_otp_verification=new Dialogue_otpVerification(Activity_otpValidation.this,string_otp_id,mobile_number);
                                dialogue_otp_verification.setCancelable(false);
                                dialogue_otp_verification.setListener(Activity_otpValidation.this);
                                dialogue_otp_verification.show();
                            }
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                //signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Activity_otpValidation.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                            }
                        })
                        //nVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                            // Update UI
                        } else {
                            Toast.makeText(Activity_Otp_Validation_1.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSuccess(Task<AuthResult> task) {
        //start activity
        userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
        sharedPreferences=getSharedPreferences("MY_FILE",MODE_PRIVATE);
        SharedPreferences.Editor myFile=sharedPreferences.edit();
        myFile.putString("user_Id",userId);
        myFile.apply();
        
        boolean isNew=Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser();
        Intent intent;
        if(isNew) {
            intent = new Intent(Activity_otpValidation.this, Activity_userRegistration.class);
            intent.putExtra("Mobile_No", mobile_number);
            intent.putExtra("userId",userId);
        }else {
            intent = new Intent(Activity_otpValidation.this, MainActivity.class);// startActivity(new Intent(Activity_otpValidation.this,MainActivity.class));
        }
        startActivity(intent);
        finish();

    }
    @Override
    public void failure(Task<AuthResult> task) {
        Toast.makeText(Activity_otpValidation.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

}