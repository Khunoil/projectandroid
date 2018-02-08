package com.example.oil.finalandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataOutputStream;
import java.net.URI;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commitNow();
        mapFragment.getMapAsync(this);

        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng thailand = new LatLng(13.754, 100.501);
        googleMap.addMarker(new MarkerOptions().position(thailand).title("Marker in Thailand"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(thailand));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                DatabaseReference root   = FirebaseDatabase.getInstance().getReference();

                root.child("list").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });
    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(MapActivity.this, LoginActivity.class));
                finish();
            }
        }
    };
}
