package com.example.minor_p.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.minor_p.R;
import com.example.minor_p.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        onStart();


        binding.btSkip.setOnClickListener(view -> {
                startActivity(new Intent(Activity_Login.this,MainActivity.class));
        });
        binding.phoneLogin.setOnClickListener(view -> {
            Intent intent=new Intent(Activity_Login.this, Activity_otpValidation.class);
            startActivity(intent);
        });

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_id=binding.username.getText().toString();
                String password=binding.password.getText().toString();

                if (email_id.isEmpty() && password.isEmpty()){
                    binding.username.setError("User name is empty");
                    binding.password.setError("Password is empty");
                }else {
                    signIn(email_id,password);
                }
            }
        });
    }
    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent=new Intent(Activity_Login.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Activity_Login.this, "Email or Password mismatched",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }


    private void updateUI(FirebaseUser user) {
        mAuth.getCurrentUser();
    }

    @Override
  public void onBackPressed() {
        finish();
    }
  @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser!=null){
          // String userId= currentUser.getUid();
          //  startActivity(new Intent(Activity_Login.this,MainActivity.class));
            Intent intent=new Intent(Activity_Login.this,MainActivity.class);
            //intent.putExtra("userId",userId);
            startActivity(intent);

            // mAuth.updateCurrentUser(currentUser);
        }
        // Check if user is signed in (non-null) and update UI accordingly.
    }

}