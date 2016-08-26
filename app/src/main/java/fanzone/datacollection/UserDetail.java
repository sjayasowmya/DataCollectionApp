package fanzone.datacollection;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

public class UserDetail extends Activity implements Listener {
    RecyclerView recyclerView;
    DbHelper dbHelper;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_user_detail);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);


        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_contactlist);
        adapter = new ListAdapter(this, dbHelper.getAllUser());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void nameToChnge(String name) {
        dbHelper.deleteRow(name);

        adapter = new ListAdapter(this, dbHelper.getAllUser());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


