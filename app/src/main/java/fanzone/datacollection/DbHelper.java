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
 * Created by jayasowmya on 25/8/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    // Database Info
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERdETAIL = "userdetail";


    //userdetail Table Columns
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String COLLEGE = "college";
    private static final String PLACE = "place";
    private static final String USER_ID = "userId";
    private static final String NUMBER = "number";
    private static final String TEAMNAME="team";



    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDETAIL_TABLE = "CREATE TABLE " + TABLE_USERdETAIL +
                "(" +
                _ID + " INTEGER PRIMARY KEY ," +
                USER_ID + " TEXT," +
                NAME + " TEXT," +
                COLLEGE + " TEXT," +
                PLACE + " TEXT," +
                NUMBER + " TEXT," +
                TEAMNAME+" TEXT"+
                ")";
        db.execSQL(CREATE_USERDETAIL_TABLE);

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
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERdETAIL);

            onCreate(db);
        }
    }

    private static DbHelper mDbHelper;


    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DbHelper(Context context) {
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
            values.put(NAME, userData.name);
            values.put(COLLEGE, userData.college);
            values.put(PLACE, userData.place);
            values.put(USER_ID, userData.user_id);
            values.put(NUMBER, userData.number);
            values.put(TEAMNAME,userData.team);

            db.insertOrThrow(TABLE_USERdETAIL, null, values);
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

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USERdETAIL;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserData userData = new UserData();
                    userData.name = cursor.getString(cursor.getColumnIndex(NAME));
                    userData.college = cursor.getString(cursor.getColumnIndex(COLLEGE));
                    userData.place = cursor.getString(cursor.getColumnIndex(PLACE));
                    userData.user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                    userData.number = cursor.getString(cursor.getColumnIndex(NUMBER));
                    userData.team=cursor.getString(cursor.getColumnIndex(TEAMNAME));

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
    void deleteRow(String number) {
        SQLiteDatabase db = getWritableDatabase();


        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERdETAIL + " where number ='" + number + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }

    }



    }