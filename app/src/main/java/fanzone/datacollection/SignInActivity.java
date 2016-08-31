package fanzone.datacollection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


import fanzone.datacollection.models.User;
import fanzone.datacollection.utils.fantainfirebasedb;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
  //  private Button mSignUpButton;


    private SharedPreferences sharedpreferences;

    String signupemail,signinemail;
    String signuppassword,signinpassword;

    // Array use for data collection
    private ArrayList<String> surveyor = new ArrayList<String>();
    private ArrayList<String> location = new ArrayList<String>();
    private ArrayList<String> match = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> gender = new ArrayList<String>();
    private ArrayList<String> age = new ArrayList<String>();
    private ArrayList<String> fromloc = new ArrayList<String>();
    private ArrayList<String> phone = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> favteam = new ArrayList<String>();
    private ArrayList<String> profession = new ArrayList<String>();
    private ArrayList<String> education = new ArrayList<String>();
    private ArrayList<String> sys_time = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        Firebase.setAndroidContext(this);
        mDatabase = fantainfirebasedb.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
      // mSignUpButton = (Button) findViewById(R.id.button_sign_up);

        // Click listeners
        mSignInButton.setOnClickListener(this);
      //  mSignUpButton.setOnClickListener(this);



    }


    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            System.out.println("HHHHHHHHHH");
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        signinemail = mEmailField.getText().toString();
        signinpassword = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(signinemail, signinpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());

                            sharedpreferences = getSharedPreferences("User", MODE_WORLD_READABLE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("surveyorName",signinemail);

                            editor.commit();
                            System.out.println("Display user name --------->"+signinemail);

                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        signupemail = mEmailField.getText().toString();
        signuppassword = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(signupemail, signuppassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());

                        } else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        //startActivity(new Intent(SignInActivity.this, ProfileDetail.class));

        Intent sign = new Intent(SignInActivity.this,ProfileDetail.class);
        sign.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sign.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sign);
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        }
       /* else if (i == R.id.button_sign_up) {
            signUp();
        }*/
    }
}
