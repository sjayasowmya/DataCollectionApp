package fanzone.datacollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satya on 26/8/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    // Database Info
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERdETAILS = "userdetails";


    //userdetail Table Columns
    private static final String _ID = "_id";
    private static final String SURVEYOR = "msurveyor";
    private static final String LOCATION = "mlocation";
    private static final String MATCH = "mmatch";
    private static final String NAME = "mname";
    private static final String GENDER = "mgender";
    private static final String AGE="mage";
    private static final String FROMLOCATION = "mfromloc";
    private static final String PHONE = "mphone";
    private static final String EMAIL = "memail";
    private static final String FEVTEAM = "mfevteam";
    private static final String PROFESSION = "mprofession";
    private static final String EDUCATION="meducation";
    private static final String TIME = "msystime";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDETAILS_TABLE = "CREATE TABLE " + TABLE_USERdETAILS +
                "(" +
                _ID + " INTEGER PRIMARY KEY ," +
                SURVEYOR + " TEXT," +
                LOCATION + " TEXT," +
                MATCH + " TEXT," +
                NAME + " TEXT," +
                GENDER+" TEXT,"+
                AGE + " TEXT," +
                FROMLOCATION + " TEXT," +
                PHONE + " TEXT," +
                EMAIL + " TEXT," +
                FEVTEAM + " TEXT," +
                PROFESSION+" TEXT,"+
                EDUCATION+" TEXT ,"+
                TIME+" TEXT "+
                ")";
        db.execSQL(CREATE_USERDETAILS_TABLE);

    }

    /*
     Called when the database needs to be upgraded.
     This method will only be called if a database already exists on disk with the same DATABASE_NAME,
     but the DATABASE_VERSION is different than the version of the database that exists on disk.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERdETAILS);

            onCreate(db);
        }
    }

    private static DataBaseHelper mDbHelper;


    public static synchronized DataBaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DataBaseHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

      /*
   Insert a  user detail into database
   */

    public void insertUserDetail(UserData userData) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(SURVEYOR, userData.msurveyor);
            values.put(LOCATION, userData.mlocation);
            values.put(MATCH, userData.mmatch);
            values.put(NAME, userData.mname);
            values.put(GENDER, userData.mgender);
            values.put(AGE,userData.mage);
            values.put(FROMLOCATION,userData.mfromloc);
            values.put(PHONE,userData.mphone);
            values.put(EMAIL,userData.memail);
            values.put(FEVTEAM,userData.mfevteam);
            values.put(PROFESSION,userData.mprofession);
            values.put(EDUCATION,userData.meducation);
            values.put(TIME,userData.msys_time);

            db.insertOrThrow(TABLE_USERdETAILS, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }

    }

     /*
   fetch all data from UserTable
    */

    public List<UserData> getAllUser() {

        List<UserData> usersdetail = new ArrayList<>();

        String USER_DETAILS_SELECT_QUERY = "SELECT * FROM " + TABLE_USERdETAILS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAILS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserData userData = new UserData();
                    userData.msurveyor = cursor.getString(cursor.getColumnIndex(SURVEYOR));
                    userData.mlocation = cursor.getString(cursor.getColumnIndex(LOCATION));
                    userData.mmatch = cursor.getString(cursor.getColumnIndex(MATCH));
                    userData.mname = cursor.getString(cursor.getColumnIndex(NAME));
                    userData.mgender = cursor.getString(cursor.getColumnIndex(GENDER));
                    userData.mage=cursor.getString(cursor.getColumnIndex(AGE));
                    userData.mfromloc = cursor.getString(cursor.getColumnIndex(FROMLOCATION));
                    userData.mphone = cursor.getString(cursor.getColumnIndex(PHONE));
                    userData.memail = cursor.getString(cursor.getColumnIndex(EMAIL));
                    userData.mfevteam = cursor.getString(cursor.getColumnIndex(FEVTEAM));
                    userData.mprofession = cursor.getString(cursor.getColumnIndex(PROFESSION));
                    userData.meducation=cursor.getString(cursor.getColumnIndex(EDUCATION));
                    userData.msys_time=cursor.getString(cursor.getColumnIndex(TIME));

                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return usersdetail;

    }


    /*
   Delete single row from UserTable
     */
    void deleteRow(String mphone) {
        SQLiteDatabase db = getWritableDatabase();


        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERdETAILS + " where mphone ='" + mphone + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }

    }



}