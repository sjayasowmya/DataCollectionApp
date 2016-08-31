package fanzone.datacollection.utils;

import com.google.firebase.database.FirebaseDatabase;

public class fantainfirebasedb {

    private static FirebaseDatabase mFirebaseDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mFirebaseDatabase == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.setPersistenceEnabled(true);
        }
        return mFirebaseDatabase;
    }
}
