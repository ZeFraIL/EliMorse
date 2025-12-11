package elia.shapira.elimorse;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogAndReg extends AppCompatActivity {

    LoginFragment fLogin;
    RegistryFragment fRegister;
    RadioButton rbLogin;
    RadioButton rbRegister;
    Button bConfirm;
    TextView tvMenu;
    Context context;
    FrameLayout FL;
    HelperDB helperDB;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_and_reg);

        initElements();

        rbLogin.setOnClickListener(view -> {
            if (rbLogin.isChecked())  {
                fLogin=new LoginFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FL, fLogin);
                ft.commit();
            }
        });

        rbRegister.setOnClickListener(view -> {
            if (rbRegister.isChecked())  {
                fRegister=new RegistryFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FL, fRegister);
                ft.commit();
            }
        });

        bConfirm.setOnClickListener(view -> {
            if(rbRegister.isChecked()){
                String stName=fRegister.getName();
                if (stName.isEmpty())  {
                    Toast.makeText(context, R.string.name_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                String stPassword=fRegister.getPassword();
                if (stPassword.isEmpty())  {
                    Toast.makeText(context, R.string.password_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                String stMail=fRegister.getMail();
                if (stMail.isEmpty())  {
                    Toast.makeText(context, R.string.email_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                String stPhone=fRegister.getPhone();
                if (stPhone.isEmpty())  {
                    Toast.makeText(context, R.string.phone_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }

                executorService.execute(() -> {
                    User existingUser = isUserFound(stName, stPassword);
                    mainThreadHandler.post(() -> {
                        if (existingUser == null) {
                            executorService.execute(() -> {
                                SQLiteDatabase db = helperDB.getWritableDatabase();
                                ContentValues contentValues=new ContentValues();
                                contentValues.put(HelperDB.COLUMN_USER_NAME, stName);
                                contentValues.put(HelperDB.COLUMN_USER_PASSWORD, stPassword);
                                contentValues.put(HelperDB.COLUMN_USER_EMAIL, stMail);
                                contentValues.put(HelperDB.COLUMN_USER_PHONE, stPhone);
                                db.insert(HelperDB.TABLE_USER, null, contentValues);
                                db.close();
                                User user = new User(stName,stPassword,stPhone,stMail);
                                mainThreadHandler.post(() -> {
                                    Intent go=new Intent(context, DashBoard.class);
                                    go.putExtra("user", user);
                                    startActivity(go);
                                });
                            });
                        } else {
                            Toast.makeText(context, R.string.user_exists_error, Toast.LENGTH_LONG).show();
                        }
                    });
                });
            }
            if (rbLogin.isChecked()) {
                String stName = fLogin.getName();
                if (stName.isEmpty()) {
                    Toast.makeText(context, R.string.name_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                String stPassword = fLogin.getPassword();
                if (stPassword.isEmpty()) {
                    Toast.makeText(context, R.string.password_field_empty, Toast.LENGTH_LONG).show();
                    return;
                }

                executorService.execute(() -> {
                    User user = isUserFound(stName, stPassword);
                    mainThreadHandler.post(() -> {
                        if (user != null)  {
                            Intent go=new Intent(context, DashBoard.class);
                            go.putExtra("user",user);
                            startActivity(go);
                        }
                        else {
                            MyToast.showToast(context,getString(R.string.user_not_found));
                        }
                    });
                });
            }
        });
    }

    private User isUserFound(String stName, String stPassword) {
        SQLiteDatabase db = helperDB.getReadableDatabase();
        String selection = HelperDB.COLUMN_USER_NAME + " = ? AND " + HelperDB.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { stName, stPassword };
        Cursor cursor = db.query(HelperDB.TABLE_USER, null, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_NAME));
            String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PASSWORD));
            String userPhone = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_PHONE));
            String userMail = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_EMAIL));
            user = new User(userName, userPassword, userPhone, userMail);
        }

        cursor.close();
        db.close();
        return user;
    }

    private void initElements() {
        context=LogAndReg.this;
        tvMenu=findViewById(R.id.tvMenuLAR);
        rbLogin= findViewById(R.id.rbLogin);
        rbRegister= findViewById(R.id.rbRegister);
        bConfirm= findViewById(R.id.bConfirm);
        FL=findViewById(R.id.FL);
        helperDB=new HelperDB(context);
        fLogin=new LoginFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FL, fLogin);
        ft.commit();
    }
}