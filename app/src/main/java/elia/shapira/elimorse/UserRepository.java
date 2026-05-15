package elia.shapira.elimorse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A repository class that handles database operations for the User entity.
 * It provides methods for finding a user by credentials and registering new users.
 */
public class UserRepository {
    /** The database helper instance for accessing the SQLite database. */
    private final HelperDB helperDB;

    /**
     * Constructs a new UserRepository.
     * @param context The application context.
     */
    public UserRepository(Context context) {
        this.helperDB = new HelperDB(context);
    }

    /**
     * Searches for a user in the database with the given username and password.
     *
     * @param name     The username to search for.
     * @param password The password to search for.
     * @return A {@link User} object if found, otherwise null.
     */
    public User findUser(String name, String password) {
        SQLiteDatabase db = helperDB.getReadableDatabase();
        String selection = HelperDB.COLUMN_USER_NAME + " = ? AND " + HelperDB.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { name, password };
        Cursor cursor = db.query(HelperDB.TABLE_USER, null, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = mapCursorToUser(cursor);
            cursor.close();
        }
        db.close();
        return user;
    }

    /**
     * Registers a new user in the database.
     *
     * @param user The user object containing registration details.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long registerUser(User user) {
        SQLiteDatabase db = helperDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HelperDB.COLUMN_USER_NAME, user.getUserName());
        values.put(HelperDB.COLUMN_USER_PASSWORD, user.getUserPassword());
        values.put(HelperDB.COLUMN_USER_EMAIL, user.getUserMail());
        values.put(HelperDB.COLUMN_USER_PHONE, user.getUserPhone());
        
        long id = db.insert(HelperDB.TABLE_USER, null, values);
        db.close();
        return id;
    }

    /**
     * Maps a database cursor to a User object.
     *
     * @param cursor The cursor pointing to a user record.
     * @return A {@link User} object populated with data from the cursor.
     * @throws IllegalArgumentException if the cursor doesn't contain required columns.
     */
    private User mapCursorToUser(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_NAME));
        String password = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PASSWORD));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PHONE));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_EMAIL));
        return new User(id, name, password, phone, email);
    }
}
