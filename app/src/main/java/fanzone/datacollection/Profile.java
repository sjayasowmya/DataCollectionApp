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

/**
 * Created by satya on 26/8/16.
 */
public class Profile extends Activity {

    private EditText surver_name, location, match, name, gender, age, from_loc, phone, email, fev_team, profession, education;
    DataBaseHelper dbHelper;
    Button save, list;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_profile);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

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

        // button

        save = (Button) findViewById(R.id.btn_save);
        list = (Button) findViewById(R.id.btn_list);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userData = new UserData();

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

                openDialog();


            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UserDetail.class);
                startActivity(intent);
            }
        });

    }
  public void openDialog(){

      LayoutInflater inflater = LayoutInflater.from(Profile.this);
      AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
      builder.setTitle("Save Your Record !!!");
      builder.setMessage("Do you want to save another Record ?");
      // builder.setView(subView);
      AlertDialog alertDialog = builder.create();

      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

              if (!surver_name.getText().toString().isEmpty()&& !location.getText().toString().isEmpty()
                      && !match.getText().toString().isEmpty() && !name.getText().toString().isEmpty()
                      && !gender.getText().toString().isEmpty() && !age.getText().toString().isEmpty()
                      && !from_loc.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                      && !email.getText().toString().isEmpty() && !fev_team.getText().toString().isEmpty()
                      && !profession.getText().toString().isEmpty() && !education.getText().toString().isEmpty()) {

                  dbHelper.insertUserDetail(userData);



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

      builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              //Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
          }
      });

      builder.show();


  }


}





