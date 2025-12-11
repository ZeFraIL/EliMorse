package elia.shapira.elimorse;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class GuideHow extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_how);

        initElements();

        TextView tvMenu = findViewById(R.id.tvMenuGH);
        tvMenu.setOnClickListener(v -> showPopupMenu(tvMenu));

        Button bBack = findViewById(R.id.bBack);
        bBack.setOnClickListener(view -> finish());

        String videoID = getString(R.string.youtube_video_id);
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String html = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoID +
                "\" frameborder=\"0\" allowfullscreen></iframe>";

        webView.loadData(html, "text/html", "utf-8");
    }

    private void initElements() {
        Intent takeIt = getIntent();
        user = (User) takeIt.getSerializableExtra("user");
    }
}
