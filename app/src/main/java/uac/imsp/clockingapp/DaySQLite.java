package uac.imsp.clockingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaySQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_JOUR = "jour";
    public static final String COL_DATE_JOUR= "date_jour";

    private static final String CREATE_JOUR = "CREATE TABLE " + TABLE_JOUR + " (" +
            COL_DATE_JOUR + " TEXT  NOT NULL PRIMARY KEY)" ;


    public DaySQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_JOUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOUR);
        onCreate(db);

    }
}

