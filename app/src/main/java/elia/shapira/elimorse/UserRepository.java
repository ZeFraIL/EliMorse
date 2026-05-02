package elia.shapira.elimorse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {
    private final HelperDB helperDB;

    public UserRepository(Context context) {
        this.helperDB = new HelperDB(context);
    }

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

    private User mapCursorToUser(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_NAME));
        String password = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PASSWORD));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PHONE));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_EMAIL));
        return new User(id, name, password, phone, email);
    }
}
