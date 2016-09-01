package fanzone.datacollection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import fanzone.datacollection.models.ProfileData;

/**
 * Created by satya on 30/8/16.
 */
public class ProfileDetail extends AppCompatActivity{

    private FloatingActionButton fab_new_post;

    private ListView profileList;
    private Handler handler= new Handler();
    private LayoutInflater inflater= null;
    Activity ac;
    private ProgressDialog mProgressDialog;
    private Dialog dialog;
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

        setContentView(R.layout.activity_profile_detail);
        Firebase.setAndroidContext(getApplicationContext());


        ac = this;


        profileList =(ListView)findViewById(R.id.profileList);

        fab_new_post = (FloatingActionButton) findViewById(R.id.fab_new_post);

        fab_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),Profile.class);
                startActivity(intent);
            }
        });

        call_firebase(); // fetch data from fire base

        profileList.setAdapter(new ImageAdapter(ac, name, email));

       /* handler.post( new Runnable() {

            @Override
            public void run() {

                new Download().execute();
            }
        });
*/
    }

/*
    private class Download extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progress dialog
            mProgressDialog = new ProgressDialog(ProfileDetail.this);
            // Set progress dialog title
            mProgressDialog.setTitle("Please Wait Data Loading");
            // Set progress dialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progress dialog
            mProgressDialog.show();
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

               call_firebase();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            profileList.setAdapter(new ImageAdapter(ac, name, email));


            //System.out.println("Do it------work----->>");
            mProgressDialog.dismiss();


        }


    }*/


    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter(Context context) {
        }
        private Context mContext;
        public ImageAdapter(Context context, ArrayList<String> name,
                            ArrayList<String>email ) {
            // TODO Auto-generated constructor stub

            context = mContext;
        }
        public int getCount() {

            return surveyor.size();


        }
        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }
        class ViewHolder
        {
            TextView mname,memail;
            ImageView pics,display,delete;
            int postion;
            View view;
            ViewGroup viewgroup;


        }
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder vh;
           // inflater= LayoutInflater.from(ac);
            vh = new ViewHolder();
            vh.postion=position;
            vh.view=convertView;
            vh.viewgroup=parent;
            if (convertView == null) {
            // This a new view we inflate the new layout
                LayoutInflater inflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.profile_item_list, null);
            }
          //  convertView=inflater.inflate(R.layout.profile_item_list, null);

            vh.pics = (ImageView)convertView.findViewById(R.id.comment_photo);

            vh.mname = (TextView)convertView.findViewById(R.id.comment_author);

            vh.memail = (TextView)convertView.findViewById(R.id.comment_body);

            vh.mname.setText(name.get(position));
            vh.memail.setText(email.get(position));
            System.out.println("Display name--2222---->"+name.get(position));
            vh.display = (ImageView) convertView.findViewById(R.id.iv_display);

         //   vh.delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            vh.display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog = new Dialog(ProfileDetail.this,android.R.style.Theme_Translucent);
                    dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                    dialog.setContentView(R.layout.profile_detail);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();

                    wlp.gravity = Gravity.CENTER;
                    wlp.flags &= ~WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW;
                    window.setAttributes(wlp);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    TextView tv_surveyorName = (TextView) dialog.findViewById(R.id.tv_surver_name);
                    TextView tv_location = (TextView) dialog.findViewById(R.id.tv_location);
                    TextView tv_match = (TextView) dialog.findViewById(R.id.tv_match);
                    TextView tv_date = (TextView) dialog.findViewById(R.id.tv_surver_date);
                    TextView tv_name = (TextView) dialog.findViewById(R.id.tv_name);
                    TextView tv_gender = (TextView) dialog.findViewById(R.id.tv_gender);
                    TextView tv_age = (TextView) dialog.findViewById(R.id.tv_age);
                    TextView tv_from_loc = (TextView) dialog.findViewById(R.id.tv_from_loc);
                    TextView tv_phone = (TextView) dialog.findViewById(R.id.tv_phone);
                    TextView tv_email = (TextView) dialog.findViewById(R.id.tv_email);
                    TextView tv_fav_team = (TextView) dialog.findViewById(R.id.tv_fev_team);
                    TextView tv_profession = (TextView) dialog.findViewById(R.id.tv_profession);
                    TextView tv_education = (TextView) dialog.findViewById(R.id.tv_education);

                    tv_surveyorName.setText(surveyor.get(position));
                    tv_location.setText(location.get(position));
                    tv_match.setText(match.get(position));
                    tv_date.setText(sys_time.get(position));
                    tv_name.setText(name.get(position));
                    tv_gender.setText(gender.get(position));
                    tv_age.setText(age.get(position));

                    tv_from_loc.setText(fromloc.get(position));
                    tv_phone.setText(phone.get(position));
                    tv_email.setText(email.get(position));
                    tv_fav_team.setText(favteam.get(position));
                    tv_profession.setText(profession.get(position));
                    tv_education.setText(education.get(position));

                    FloatingActionButton list  =(FloatingActionButton) dialog.findViewById(R.id.btn_list);

                    list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.cancel();
                        }
                    });


                    dialog.show();

                }
            });
          //  https://github.com/sjayasowmya/DataCollectionApp
            /*vh.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Firebase ref = new Firebase(Config.FIREBASE_URL);
                    Query queryRef = ref.orderByChild("phone").equalTo(phone.get(position));

                    queryRef.removeEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


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

                }
            });
*/

//
          //  System.out.println("Display name------>"+name.get(position));
            return convertView;
        }

    }


    public  void call_firebase(){


        Firebase ref = new Firebase(Config.FIREBASE_URL);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProfileData profileData = postSnapshot.getValue(ProfileData.class);

                    surveyor.add(profileData.getSurveyor());
                    location.add(profileData.getLocation());
                    match.add(profileData.getMatch());
                    name.add(profileData.getName());
                    gender.add(profileData.getGender());
                    age.add(profileData.getAge());
                    fromloc.add(profileData.getFromloc());
                    phone.add(profileData.getPhone());
                    email.add(profileData.getEmail());
                    favteam.add(profileData.getFavteam());
                    profession.add(profileData.getProfession());
                    education.add(profileData.getEducation());
                    sys_time.add(profileData.getSys_time());

                }
                System.out.println(email+"<--------- Display all data--------->"+surveyor);
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
