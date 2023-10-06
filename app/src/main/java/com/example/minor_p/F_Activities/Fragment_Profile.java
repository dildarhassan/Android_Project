package com.example.minor_p.F_Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.minor_p.Activities.Activity_Login;
import com.example.minor_p.Activities.Dialogue_otpVerification;
import com.example.minor_p.Activities.MainActivity;
import com.example.minor_p.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Fragment_Profile extends Fragment {
    FragmentProfileBinding binding;
    private DatabaseReference mDatabase;
    private String userId;
   // SharedPreferences sharedPreferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


          userId=((MainActivity) requireActivity()).returnUserid();
//        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("MY_FILE",Context.MODE_PRIVATE);
//        userId=sharedPreferences.getString("user_Id","NA");

        mDatabase = FirebaseDatabase.getInstance().getReference("USERS");
        mDatabase.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                 //   Log.e("firebase", "Error getting data", task.getException());
                    //  binding.tvFirstName.setText(FirebaseDatabase.getInstance().getReference("USERS").orderByChild("name").toString());
                     //   binding.tvEmail.setText(FirebaseDatabase.getInstance().getReference("USERS").orderByChild("email").toString());toString

                    binding.simpleProgressBar.setVisibility(View.GONE);
                    binding.simpleProgressBar1.setVisibility(View.GONE);
                    binding.simpleProgressBar2.setVisibility(View.GONE);
                    binding.simpleProgressBar3.setVisibility(View.GONE);
                    binding.simpleProgressBar11.setVisibility(View.GONE);

                        DataSnapshot dataSnapshot=task.getResult();
                        String profileName=String.valueOf(dataSnapshot.child("firstName").getValue());
                        String firstName=String.valueOf(dataSnapshot.child("firstName").getValue());
                        String lastname=String.valueOf(dataSnapshot.child("lastName").getValue());
                        String email=String.valueOf(dataSnapshot.child("email").getValue());
                        String location=String.valueOf(dataSnapshot.child("location").getValue());

                        binding.tvProfileName.setText("Hello"+'\n'+profileName);
                        binding.tvFirstName.setText(firstName);
                        binding.tvLastName.setText(lastname);
                        binding.tvEmail.setText(email);
                        binding.tvLocation.setText(location);

                    }else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }

                }
        });
        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialoge=new AlertDialog.Builder(getContext())
                        .setTitle("Do You Want To Log Out")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(getActivity(), Activity_Login.class));
                                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("MY_FILE",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor myFile=sharedPreferences.edit();
                                        myFile.clear().apply();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setCancelable(true);
                alertDialoge.show();

            }
        });

    }

}