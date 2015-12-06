package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vannahz on 2015/12/3.
 */
public class StudySetting extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private Button start_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_setting);

        //设置Toolbar
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Time Setting");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //start button
        start_btn = (Button)findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStudying();
            }
        });
    }

    //go to time content
    public void goToStudying() {

        setContentView(R.layout.study_content);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Studying");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Button giveup_btn = (Button)findViewById(R.id.giveup_btn);
        giveup_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(StudySetting.this, StudyResult.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
