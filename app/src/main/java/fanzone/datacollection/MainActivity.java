package fanzone.datacollection;


import android.app.Activity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
    EditText et_name, et_age, et_place, et_userid, et_number;
    Spinner SpinnerTeam;
    Button btn_next;
    DbHelper dbHelper;
    String spTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);


        dbHelper = DbHelper.getInstance(getApplicationContext());

        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_place = (EditText) findViewById(R.id.et_place);
        et_userid = (EditText) findViewById(R.id.et_userid);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_next = (Button) findViewById(R.id.btn_next);
        // addListenerOnSpinnerItemSelection();
        SpinnerTeam = (Spinner) findViewById(R.id.spinner1);
        spTeam = SpinnerTeam.getSelectedItem().toString();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

                if (!et_name.getText().toString().isEmpty()) {
                    userData.name = et_name.getText().toString();
                } else {
                    userData.name = "";
                }
                if (!et_age.getText().toString().isEmpty()) {
                    userData.college = et_age.getText().toString();
                } else {
                    userData.college = "";
                }
                if (!et_userid.getText().toString().isEmpty()) {
                    userData.user_id = et_userid.getText().toString();
                } else {
                    userData.user_id = "";
                }
                if (!et_number.getText().toString().isEmpty()) {
                    userData.number = et_number.getText().toString();
                } else {
                    userData.number = "";
                }
                if (!et_place.getText().toString().isEmpty()) {
                    userData.place = et_place.getText().toString();
                } else {
                    userData.place = "";
                }
                if (!spTeam.isEmpty()) {
                    userData.team = spTeam;
                } else {
                    userData.team= "";
                }

                dbHelper.insertUserDetail(userData);

                Intent intent = new Intent(MainActivity.this, UserDetail.class);
                startActivity(intent);

            }
        });

    }

}

