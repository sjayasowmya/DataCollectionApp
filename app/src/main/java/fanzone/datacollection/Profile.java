package fanzone.datacollection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fanzone.datacollection.Model.ProfileData;

/**
 * Created by satya on 26/8/16.
 */
public class Profile extends Activity {

    private EditText surver_name, location, match, name, gender, age, from_loc, phone, email, fev_team, profession, education;
    DataBaseHelper dbHelper;
    private Button save, list;
    private String sp_location,sp_match,sp_gender,sp_fevTeam,sp_profession,sp_education,sys_time;
    UserData userData;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_profile);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        Firebase.setAndroidContext(this);

        dbHelper = DataBaseHelper.getInstance(getApplicationContext());

        // edittext

        surver_name = (EditText) findViewById(R.id.surver_name);
        location = (EditText) findViewById(R.id.location);
        match = (EditText) findViewById(R.id.match);
        name = (EditText) findViewById(R.id.name);
        gender = (EditText) findViewById(R.id.gender);
        age = (EditText) findViewById(R.id.age);
        from_loc = (EditText) findViewById(R.id.from_loc);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        fev_team = (EditText) findViewById(R.id.fev_team);
        profession = (EditText) findViewById(R.id.profession);
        education = (EditText) findViewById(R.id.education);

        location.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {



                final  String[] spinnerValues = {"MAC,Chennai","NPR College,Dindigul","ICL Ground,Tirunelveli"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Location");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_location  = spinnerValues[position];

                                location.setText(sp_location);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        match.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {



                final  String[] spinnerValues = {"Chepauk vs Thoothukudi","Tiruvallur vs Karaikudi","Coimbatore vs Kancheepuram",
                                                  "Dindigul vs Madurai"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Match");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_match  = spinnerValues[position];

                                match.setText(sp_match);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });
        gender.setText("Male");

        gender.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {



                final  String[] spinnerValues = {"Male","Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Gender");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_gender  = spinnerValues[position];

                                gender.setText(sp_gender);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        fev_team.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {

                final  String[] spinnerValues = {"Chepauk","Coimbatore","Dindigul","Karaikudi","Kancheepuram","Madurai","Tiruvallur","Thoothukudi"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Team");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_fevTeam  = spinnerValues[position];

                                fev_team.setText(sp_fevTeam);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        profession.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {

                final  String[] spinnerValues = {"Business","House wife","Service","Student","Sports"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Profession");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_profession  = spinnerValues[position];

                                profession.setText(sp_profession);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        education.setOnClickListener(new View.OnClickListener() {

            // add button listener

            @Override
            public void onClick(View view) {



                final  String[] spinnerValues = {"Under X","X","XII","Under Graduate","Post Graduate"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Select Education");
                builder.setSingleChoiceItems(spinnerValues, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {


                                sp_education  = spinnerValues[position];

                                education.setText(sp_education);

                                dialog.cancel();

                            }
                        });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        // finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

         sys_time = sdf.format(new Date());



       // System.out.println("----Sys-time------"+sys_time);
        // button

        save = (Button) findViewById(R.id.btn_save);
        list = (Button) findViewById(R.id.btn_list);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Firebase ref = new Firebase(Config.FIREBASE_URL);
               userData = new UserData();
                //Creating Person object
                final ProfileData profileData = new ProfileData();


                userData.msurveyor = surver_name.getText().toString();
                userData.mlocation = location.getText().toString();
                userData.mmatch = match.getText().toString();
                userData.mname = name.getText().toString();
                userData.mgender= gender.getText().toString();
                userData.mage = age.getText().toString();
                userData.mfromloc = from_loc.getText().toString();
                userData.mphone = phone.getText().toString();
                userData.memail = email.getText().toString();
                userData.mfevteam = fev_team.getText().toString();
                userData.mprofession = profession.getText().toString();
                userData.meducation = education.getText().toString();
                userData.msys_time = sys_time;


                      if(!surver_name.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                        && !match.getText().toString().isEmpty() && !name.getText().toString().isEmpty()
                        && !gender.getText().toString().isEmpty() && !age.getText().toString().isEmpty()
                        && !from_loc.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty() && !fev_team.getText().toString().isEmpty()
                        && !profession.getText().toString().isEmpty() && !education.getText().toString().isEmpty()) {

                          dbHelper.insertUserDetail(userData);


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
                          profileData.setFevteam(userData.mfevteam);
                          profileData.setProfession(userData.mprofession);
                          profileData.setEducation(userData.meducation);

                          //Storing values to firebase
                         ref.child("TNPL").push().setValue(profileData);



                         /* ref.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot snapshot) {
                                  System.out.println("Display all the datas..........>"+snapshot.getValue());


                              }
                              @Override
                              public void onCancelled(FirebaseError firebaseError) {
                                  System.out.println("The read failed: " + firebaseError.getMessage());
                              }
                          });*/




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
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UserDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

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


}





