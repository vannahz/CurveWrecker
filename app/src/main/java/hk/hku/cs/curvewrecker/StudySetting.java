package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vannahz on 2015/12/3.
 */
public class StudySetting extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private Button start_btn;
    private LinearLayout studySetting;
    private TextView study_hour, study_minute;
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

        study_hour = (TextView)findViewById(R.id.study_hour);
        study_minute = (TextView)findViewById(R.id.study_minute);

        //change time
        studySetting = (LinearLayout)findViewById(R.id.studySetting);
        studySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeTimeDialog mChangeBirthDialog = new ChangeTimeDialog(StudySetting.this);
                mChangeBirthDialog.setDate(1, 0);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setTimeListener(new ChangeTimeDialog.OnTimeListener() {
                    @Override
                    public void onClick(String hour, String minute) {
                        Toast.makeText(StudySetting.this,hour + ":" + minute,Toast.LENGTH_LONG).show();
                        study_hour.setText("0" + hour);

                        if(Integer.parseInt(minute) < 10)
                            minute = "0" + minute;
                        study_minute.setText(minute);
                    }
                });
            }
        });


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

        setContentView(R.layout.studying);

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
