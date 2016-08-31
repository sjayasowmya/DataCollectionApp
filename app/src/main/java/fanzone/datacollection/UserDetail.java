package fanzone.datacollection;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.authentication.Constants;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fanzone.datacollection.Model.ProfileData;
import fanzone.datacollection.models.User;

public class UserDetail extends Activity implements Listener {
    RecyclerView recyclerView;
    DataBaseHelper dbHelper;
    ListAdapter adapter;
    ProfileData adapters;
    Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_user_detail);
      //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);


       Firebase ref = new Firebase(Config.FIREBASE_URL);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProfileData profileData = postSnapshot.getValue(ProfileData.class);

                    System.out.println("<-----Display Childs-------"+postSnapshot);
                    //Adding it to a string
                    String string = "Address: "+profileData.getLocation();
                    System.out.println("Hellllloooo------"+string);

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        dbHelper = DataBaseHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_contactlist);
       adapter = new ListAdapter(this, dbHelper.getAllUser());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(getApplicationContext(),Profile.class);
                startActivity(add);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void nameToChnge(String mphone) {
        dbHelper.deleteRow(mphone);

        adapter = new ListAdapter(this, dbHelper.getAllUser());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}


