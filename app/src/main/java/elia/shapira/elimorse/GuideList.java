package elia.shapira.elimorse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GuideList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuGL);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));
    }

    private void initElements() {
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");

        ListView lv = findViewById(R.id.lv);
        ArrayList<String> info = new ArrayList<>();
        
        try (InputStream is = getResources().openRawResource(R.raw.all_morse);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line1, line2;
            while ((line1 = br.readLine()) != null && (line2 = br.readLine()) != null) {
                MorseCode morseCode = new MorseCode(line1, line2);
                info.add(morseCode.toString());
            }
        } catch (IOException e) {
            Log.e("GuideList", "Error reading morse code file", e);
            Toast.makeText(this, R.string.error_reading_file, Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, info);
        lv.setAdapter(adapter);
    }
}
