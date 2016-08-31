package fanzone.datacollection.activity;

/**
 * Created by satya on 29/8/16.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fanzone.datacollection.R;
import fanzone.datacollection.models.Posts;
import fanzone.datacollection.models.User;
import fanzone.datacollection.utils.fantainfirebasedb;


public class SaveProfileActivity extends BaseActivity {

    private static final String TAG = "SaveProfileActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText surver_name, name, age, from_loc, phone, email;
    private Spinner location,match,gender,fev_team,profession,education;

    private Button save, list;
    private String sp_location,sp_match,sp_gender,sp_fevTeam,sp_profession,sp_education,sys_time;
    private Calendar cal;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_profile);

        // [START initialize_database_ref]
        mDatabase = fantainfirebasedb.getDatabase().getReference();
        // [END initialize_database_ref]

        surver_name = (EditText) findViewById(R.id.surver_name);
        location = (Spinner) findViewById(R.id.location);
        match = (Spinner) findViewById(R.id.match);
        name = (EditText) findViewById(R.id.name);
        gender = (Spinner) findViewById(R.id.gender);
        age = (EditText) findViewById(R.id.age);
        from_loc = (EditText) findViewById(R.id.from_loc);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        fev_team = (Spinner) findViewById(R.id.fev_team);
        profession = (Spinner) findViewById(R.id.profession);
        education = (Spinner) findViewById(R.id.education);

        // ADAPTER FOR SPINNER

        //location
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.location, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        locadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        location.setAdapter(locadapter);

        //match


        ArrayAdapter<CharSequence> matchadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.match, android.R.layout.simple_spinner_item);
        matchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        match.setAdapter(matchadapter);

        //gender

        ArrayAdapter<CharSequence> genadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.gender, android.R.layout.simple_spinner_item);
        genadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genadapter);

        //favourite team


        ArrayAdapter<CharSequence> favadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.fav_team, android.R.layout.simple_spinner_item);

        favadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fev_team.setAdapter(favadapter);



        // proffesion

        ArrayAdapter<CharSequence> profadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.profession, android.R.layout.simple_spinner_item);
        profadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profession.setAdapter(profadapter);

        // education
        ArrayAdapter<CharSequence> eduadapter = ArrayAdapter.createFromResource(SaveProfileActivity.this,
                R.array.education, android.R.layout.simple_spinner_item);
        eduadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(eduadapter);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sys_time = sdf.format(new Date());


        //  mTitleField = (EditText) findViewById(R.id.field_title);
        //   mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {

      //  final String msurveyor,mlocation,mmatch,mname,mgender,mage,mfromloc,mphone,memail,mfevteam,mprofession,meducation,msys_time;

        final String msurveyor = surver_name.getText().toString();
        final String mlocation = location.getSelectedItem().toString();
        final String mmatch = match.getSelectedItem().toString();
        final String mname = name.getText().toString();
        final String mgender= gender.getSelectedItem().toString();
        final String mage = age.getText().toString();
        final String mfromloc = from_loc.getText().toString();
        final String mphone = phone.getText().toString();
        final String memail = email.getText().toString();
        final String mfevteam = fev_team.getSelectedItem().toString();
        final String mprofession = profession.getSelectedItem().toString();
        final String meducation = education.getSelectedItem().toString();
        final String msys_time = sys_time;

        if (TextUtils.isEmpty(msurveyor)) {
            surver_name.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(mname)) {
            name.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(mage)) {
            age.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(mfromloc)) {
            from_loc.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(mphone)) {
            phone.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(memail)) {
            email.setError(REQUIRED);
            return;
        }


        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(SaveProfileActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(msurveyor,mlocation,mmatch,mname,mgender,mage,mfromloc,mphone,memail,mfevteam,mprofession,meducation,msys_time);

                          // System.out.println(mmatch+"<----------------- Shaow My data post  ------------->"+msurveyor);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        // mTitleField.setEnabled(enabled);
        //mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost( String surveyor, String location,String match,String name,String gender,
                              String age,String fromloc,String phone,String email,String fevteam,String profession,String education,String sys_time) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("FAN").push().getKey();
        Posts posts = new Posts( surveyor,location,match,name,gender,age,fromloc,phone,email,fevteam,profession,education,sys_time);
        Map<String, Object> postValues = posts.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Fans/" + key, postValues);
       // childUpdates.put("/Fans-Post/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
