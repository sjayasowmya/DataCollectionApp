package fanzone.datacollection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fanzone.datacollection.Model.ProfileData;
import fanzone.datacollection.activity.*;

/**
 * Created by satya on 26/8/16.
 */
public class Profile extends AppCompatActivity {

    private EditText surver_name, name, age, from_loc, phone, email;
    private Spinner location,match,gender,fev_team,profession,education;

     private FloatingActionButton mSubmitButton;
    private SharedPreferences sharedpreferences;
    //DataBaseHelper dbHelper;
    private Button save, list;
    private String sp_location,sp_match,sp_gender,sp_fevTeam,sp_profession,sp_education,sys_time;
    private String surveyorName;
    UserData userData;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_profile);
      //  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        Firebase.setAndroidContext(this);

      //  dbHelper = DataBaseHelper.getInstance(getApplicationContext());

        // edittext



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


        sharedpreferences =getSharedPreferences("User", MODE_WORLD_READABLE);
        surveyorName = sharedpreferences.getString("surveyorName", "surveyorName");
        String[] separated = surveyorName.split("@");
        surveyorName = separated[0];
        //System.out.println("SurveName ---------->>>> "+surveyorName);
        if(!surveyorName.equalsIgnoreCase(""))
        {
            surver_name.setText(surveyorName); /* Edit the value here*/
        }
        // ADAPTER FOR SPINNER

        //location
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> locadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.location, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        locadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        location.setAdapter(locadapter);

        //match


        ArrayAdapter<CharSequence> matchadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.match, android.R.layout.simple_spinner_item);
        matchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        match.setAdapter(matchadapter);

        //gender

        ArrayAdapter<CharSequence> genadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.gender, android.R.layout.simple_spinner_item);
        genadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genadapter);

        //favourite team


        ArrayAdapter<CharSequence> favadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.fav_team, android.R.layout.simple_spinner_item);

        favadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fev_team.setAdapter(favadapter);



        // proffesion

        ArrayAdapter<CharSequence> profadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.profession, android.R.layout.simple_spinner_item);
        profadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profession.setAdapter(profadapter);

        // education
        ArrayAdapter<CharSequence> eduadapter = ArrayAdapter.createFromResource(Profile.this,
                R.array.education, android.R.layout.simple_spinner_item);
        eduadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(eduadapter);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sys_time = sdf.format(new Date());


        //  mTitleField = (EditText) findViewById(R.id.field_title);
        //   mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);
       // mListButton = (FloatingActionButton) findViewById(R.id.fab_list);

       // System.out.println("----Sys-time------"+sys_time);
        // button

       // save = (Button) findViewById(R.id.btn_save);
       // list = (Button) findViewById(R.id.btn_list);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Firebase ref = new Firebase(Config.FIREBASE_URL);
               userData = new UserData();
                //Creating Person object
                final ProfileData profileData = new ProfileData();

                userData.msurveyor = surver_name.getText().toString();
                userData.mlocation  = location.getSelectedItem().toString();
                userData.mmatch  = match.getSelectedItem().toString();
                userData.mname = name.getText().toString();
                userData.mgender= gender.getSelectedItem().toString();
                userData.mage = age.getText().toString();
                userData.mfromloc = from_loc.getText().toString();
                userData.mphone = phone.getText().toString();
                userData.memail = email.getText().toString();
                userData.mfavteam = fev_team.getSelectedItem().toString();
                userData.mprofession = profession.getSelectedItem().toString();
                userData.meducation = education.getSelectedItem().toString();
                userData.msys_time = sys_time;

                      if(!surver_name.getText().toString().isEmpty() && !location.getSelectedItem().toString().isEmpty()
                        && !match.getSelectedItem().toString().isEmpty() && !name.getText().toString().isEmpty()
                        && !gender.getSelectedItem().toString().isEmpty() && !age.getText().toString().isEmpty()
                        && !from_loc.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty() && !fev_team.getSelectedItem().toString().isEmpty()
                        && !profession.getSelectedItem().toString().isEmpty() && !education.getSelectedItem().toString().isEmpty()) {

                         // dbHelper.insertUserDetail(userData);


                          profileData.setSurveyor(userData.msurveyor);
                          profileData.setLocation(userData.mlocation);
                          profileData.setSys_time(userData.msys_time);
                          profileData.setMatch(userData.mmatch);
                          profileData.setName(userData.mname);
                          profileData.setGender(userData.mgender);
                          profileData.setAge(userData.mage);
                          profileData.setFromloc(userData.mfromloc);
                          profileData.setPhone(userData.mphone);
                          profileData.setEmail(userData.memail);
                          profileData.setFavteam(userData.mfavteam);
                          profileData.setProfession(userData.mprofession);
                          profileData.setEducation(userData.meducation);

                          //Storing values to firebase
                         ref.child("TNPL").push().setValue(profileData);




                          openDialog();

                }else {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_items,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = (TextView) layout.findViewById(R.id.tostprint);
                    text.setText("Please Fill All Field Before Submit !!!");
                    Toast toast = new Toast(Profile.this);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }


            }
        });
       /* mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UserDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });*/

    }

  public void openDialog(){

      LayoutInflater inflater = LayoutInflater.from(Profile.this);
      final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
      builder.setTitle("TNPL");
      builder.setMessage("Your Information Saved Successfully.");
      // builder.setView(subView);
      AlertDialog alertDialog = builder.create();

      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              startActivity(new Intent(getApplicationContext(), ProfileDetail.class));
              finish();
          }
      });

     /* builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
          }
      });
*/
      builder.show();


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





