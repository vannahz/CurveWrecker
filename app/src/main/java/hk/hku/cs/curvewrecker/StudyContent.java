package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StudyContent extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView zouni;
    private Button giveup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_content);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        zouni = (ImageView) findViewById(R.id.zouni);
        giveup_btn = (Button)findViewById(R.id.giveup_btn);

        toolbar.setTitle("Studying");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        giveup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyContent.this, StudyResult.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
