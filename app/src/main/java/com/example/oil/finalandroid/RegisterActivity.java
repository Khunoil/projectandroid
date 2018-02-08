package com.example.oil.finalandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Message;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btSignup;
    private EditText firstname;
    private EditText lastname;
    private EditText codecard;
    private EditText password;
    private EditText email;
    private EditText phone;
    private EditText license;
    private Spinner carbrand;
    private Spinner carcolor;
    private Spinner country;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btSignup = findViewById(R.id.signup);
        codecard = findViewById(R.id.codecard);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        carbrand = (Spinner) findViewById(R.id.carbrand);
        carcolor = (Spinner) findViewById(R.id.carcolor);
        license = findViewById(R.id.licenseplate);
        country = (Spinner) findViewById(R.id.country);


        carbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RegisterActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        carcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RegisterActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RegisterActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (codecard.length() == 13) {
                } else {
                    codecard.setError(" Your identity card 13 characters ");
                }

                if (firstname.length() >= 6 && firstname.length() <= 24) {
                } else {
                    firstname.setError("Enter minimum 8 characters");
                }

                if (lastname.length() >= 6 && lastname.length() <= 24) {
                } else {
                    lastname.setError("Enter minimum 8 characters");
                }

                if (password.length() > 6) {
                } else {
                    password.setError("Enter minimum 6 characters");
                }

                if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                } else {
                    email.setError("Enter email address!");
                }

                if (phone.length() >= 9 && phone.length() <= 10) {
                } else {
                    phone.setError("Your phone");
                }

                if (TextUtils.isEmpty(license.getText().toString())) {
                    license.setError("Your car license ");
                } else {

                }

                if (codecard.length() == 13
                        && firstname.length() >= 6 && firstname.length() <= 24
                        && lastname.length() >= 6 && lastname.length() <= 24
                        && password.length() > 6
                        && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()
                        && phone.length() >= 9 && phone.length() <= 10
                        && !TextUtils.isEmpty(license.getText().toString())) {

                    AlertDialog.Builder checklist = new AlertDialog.Builder(RegisterActivity.this);
                    checklist.setTitle("Confirm Register");
                    checklist.setMessage("Are your sure register,You want to exit");
                    checklist.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            RegisModel model = new RegisModel(Integer.parseInt(codecard.getText().toString()), firstname.getText().toString(), lastname.getText().toString()
                                    , password.getText().toString(), email.getText().toString(), phone.getText().toString(), license.getText().toString()
                                    , carbrand.getSelectedItem().toString(), carcolor.getSelectedItem().toString(), country.getSelectedItem().toString());

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                            myRef.child("users");
                            myRef.push().setValue(model);

                            dialogInterface.dismiss();
                            register();
                            finish();

                        }
                    });
                    checklist.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    checklist.show();
                }

            }
        });

    }

    void register() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}