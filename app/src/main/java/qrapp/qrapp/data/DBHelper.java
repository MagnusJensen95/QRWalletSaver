package qrapp.qrapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Magnus on 23-12-2016.
 */

public class DBHelper extends SQLiteOpenHelper  {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String TABLE_NAME = "Medlemskaber";

    private static final String COLUMN_NAME_TITLE = "kortadresse";
    private static final String COLUMN_NAME_SUBTITLE  = "kortdata";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +

                    COLUMN_NAME_TITLE + " TEXT PRIMARY KEY," +
                    COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<CardData> getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {

                COLUMN_NAME_TITLE,
                COLUMN_NAME_SUBTITLE
        };

        Cursor cursor = db.query(
                TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null// The sort order
        );

        ArrayList<CardData> cards = new ArrayList<>();
        while(cursor.moveToNext()) {
            CardData card = new CardData(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE) ));

                       cards.add(card);
        }
        cursor.close();
        db.close();

        return cards;
    }

    public boolean addCardData(CardData card){
        boolean success = false;
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, db.getVersion(), db.getVersion()+1);
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, card.getMedlemskab());
        values.put(COLUMN_NAME_SUBTITLE, card.getKortData());

// Insert the new row, returning the primary key value of the new row

            long newRowId = db.insert(TABLE_NAME, null, values);
            if(newRowId != -1) {
                success = true;
            }
        db.close();
        return  success;

    }


}

