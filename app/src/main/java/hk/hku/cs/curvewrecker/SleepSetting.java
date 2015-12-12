package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vannahz on 2015/12/6.
 */
public class SleepSetting extends AppCompatActivity {


    private TextView toolbar_title;
    private Toolbar toolbar;
    private Button sleepnow_btn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_setting);

        //设置Toolbar
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Time Setting");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //start button
        sleepnow_btn = (Button)findViewById(R.id.sleepnow_btn);
        sleepnow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSleeping();
            }
        });
    }

    public void goToSleeping() {

        setContentView(R.layout.sleeping);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Sleeping  ");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Button wakeup_btn = (Button)findViewById(R.id.wakeup_btn);
        wakeup_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                intent = new Intent(SleepSetting.this, SleepResult.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            intent = new Intent(SleepSetting.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }


}
