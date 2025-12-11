package elia.shapira.elimorse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * A helper class to manage database creation and version management.
 * This class defines the schema for the User and Exercise tables.
 */
public class HelperDB extends SQLiteOpenHelper {

    /** The name of the database file. */
    public static final String DATABASE_NAME = "Info.db";
    /** The version of the database. Must be incremented when the schema is changed. */
    public static final int DATABASE_VERSION = 2;

    // User Table
    /** The name of the user table. */
    public static final String TABLE_USER = "Users";
    /** The primary key for the user table (integer). */
    public static final String COLUMN_USER_ID = "_id";
    /** The user's name (text). */
    public static final String COLUMN_USER_NAME = "User_Name";
    /** The user's password (text). */
    public static final String COLUMN_USER_PASSWORD = "User_Password";
    /** The user's email address (text). */
    public static final String COLUMN_USER_EMAIL = "User_Email";
    /** The user's phone number (text). */
    public static final String COLUMN_USER_PHONE = "User_Phone";

    // Exercise Table
    /** The name of the exercise table. */
    public static final String TABLE_EXERCISE = "Exercises";
    /** The primary key for the exercise table (integer). */
    public static final String COLUMN_EXERCISE_ID = "_id";
    /** The kind or type of the exercise (e.g., "Numbers", "Listening"). */
    public static final String COLUMN_EXERCISE_KIND = "Exercise_Kind";
    /** The number of mistakes made in the exercise (integer). */
    public static final String COLUMN_EXERCISE_MISTAKES = "Exercise_Mistakes";
    /** The date the exercise was completed (text, format: "dd/MM/yyyy"). */
    public static final String COLUMN_EXERCISE_DATE = "Exercise_Date";
    /** The foreign key linking to the user who completed the exercise. */
    public static final String COLUMN_EXERCISE_USER = "Exercise_User";

    /**
     * Constructs a new HelperDB.
     * @param context The context to use for locating paths to the database.
     */
    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PHONE + " TEXT);";
        db.execSQL(createUserTable);

        String createExerciseTable = "CREATE TABLE " + TABLE_EXERCISE + " (" +
                COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXERCISE_KIND + " TEXT, " +
                COLUMN_EXERCISE_MISTAKES + " INTEGER, " + // Corrected to INTEGER
                COLUMN_EXERCISE_DATE + " TEXT, " +
                COLUMN_EXERCISE_USER + " TEXT);";
        db.execSQL(createExerciseTable);
    }

    /**
     * Called when the database needs to be upgraded. This method will drop the existing tables
     * and create new ones. This is a simple migration strategy suitable for development.
     * For a production app, a more sophisticated data migration strategy would be required.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }
}
