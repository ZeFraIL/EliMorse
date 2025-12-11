package elia.shapira.elimorse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GuideWhat extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_what);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuGW);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));
    }

    private void initElements() {
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
    }
}
