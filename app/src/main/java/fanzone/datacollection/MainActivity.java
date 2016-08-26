package fanzone.datacollection;


import android.app.Activity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

                userData.name = et_name.getText().toString();
                userData.college = et_age.getText().toString();
                userData.user_id = et_userid.getText().toString();
                userData.number = et_number.getText().toString();
                userData.place = et_place.getText().toString();
                userData.team = spTeam;
                if (!et_name.getText().toString().isEmpty()&& !et_age.getText().toString().isEmpty()
                        && !et_userid.getText().toString().isEmpty()
                       && !et_number.getText().toString().isEmpty() && !et_place.getText().toString().isEmpty() && !spTeam.isEmpty()) {

                    dbHelper.insertUserDetail(userData);

                    Intent intent = new Intent(MainActivity.this, UserDetail.class);
                    startActivity(intent);

                }else {

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_items,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text = (TextView) layout.findViewById(R.id.tostprint);
                        text.setText("Please Fill All Field Before Submit !!!");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                }





            }
        });

    }

}

