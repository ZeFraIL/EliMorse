package elia.shapira.elimorse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Guide extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuGuide);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));

        Button bWhat = findViewById(R.id.bWhat);
        Button bHow = findViewById(R.id.bHow);
        Button bList = findViewById(R.id.bList);

        bWhat.setOnClickListener(v -> startActivity(new Intent(this, GuideWhat.class)));
        bHow.setOnClickListener(v -> startActivity(new Intent(this, GuideHow.class)));
        bList.setOnClickListener(v -> startActivity(new Intent(this, GuideList.class)));
    }

    private void initElements() {
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
    }
}
