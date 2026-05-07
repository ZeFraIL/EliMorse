package elia.shapira.elimorse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class History extends BaseActivity {
    //TODO make the list view to show only date and onclick an alert dialog with the "Kind","Mistakes"
    ArrayList<Exercises> alAll;
    ArrayList<String> alInfo;
    Context context;
    HelperDB helperDB;
    SQLiteDatabase db;
    TextView tv, tvMenu;
    ListView lvInfo;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initElements();

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(tvMenu);
            }
        });
    }

    private void BuildInfo() {
        String stUser,stDate,stMistake,stKind;
        db = helperDB.getReadableDatabase();
        Cursor cursor = db.query(helperDB.TABLE_EXERCISE,null,null,
                null,null,null,null);
        if(cursor.getCount()==0) {
            db.close();
            String st = "No exercises";
            Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
            sayWhat(st);
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            stUser = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_EXERCISE_USER));
            if(stUser.equals(user.getUserName())){
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                stKind = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_EXERCISE_KIND));
                stMistake = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_EXERCISE_MISTAKES));
                stDate = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_EXERCISE_DATE));
                Exercises exercises=new Exercises(stKind,stMistake,stDate,stUser);
                alAll.add(exercises);
                alInfo.add(exercises.getExDate()+"  -  "+exercises.getExKind()+"  -  finished with "+exercises.getExMistake()+" Mistakes ");
            }
            cursor.moveToNext();
        }
        db.close();
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,alInfo);
        lvInfo.setAdapter(adapter);
    }

    private void initElements() {
        context=this;
        Intent TakeIt = getIntent();
        user = (User) TakeIt.getSerializableExtra("user");
        helperDB = new HelperDB(context);
        alAll = new ArrayList<>();
        alInfo = new ArrayList<>();
        lvInfo = (ListView) findViewById(R.id.lvInfo);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(user.getUserName()+"'s Exercise History");
        tvMenu=findViewById(R.id.tvMenuH);
        BuildInfo();
    }
}