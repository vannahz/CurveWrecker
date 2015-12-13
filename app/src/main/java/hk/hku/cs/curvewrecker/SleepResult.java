package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.entities.MyTime;

/**
 * Created by vannahz on 2015/12/6.
 */
public class SleepResult extends AppCompatActivity {

    private Button fight_btn;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private MySystem mySystem;
    private Intent intent;
    private TextView averageTime;
    private TextView currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_result);
        fight_btn = (Button) findViewById(R.id.fight_btn);

        //get mysystem
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");


        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        if(mySystem.getMyUser().getCrtMission().isDone()){
            toolbar_title.setText("Sleep Success");
        }
        else {
            toolbar_title.setText("Sleep Fail");
        }
        Log.d("###SleepResult","checkpoint3");
        toolbar_title.setText("Morning");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(SleepResult.this, MainActivity.class);
                intent.putExtra("MySystem", mySystem);
                startActivity(intent);
                finish();
            }
        });
        Log.d("###SleepResult", "checkpoint1");
        averageTime = (TextView) findViewById(R.id.average);
        averageTime.setText(mySystem.getAverageSleep().getHour() + "h"
                + mySystem.getAverageSleep().getMinute() + "min");
        currentTime = (TextView) findViewById(R.id.today_sleep);
        MyTime tempT = new MyTime();
        Log.d("###SleepResult","checkpoint2");
        Log.d("SleepResult:", String.format("%d, %d", mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds(), mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds()));
        tempT.resetTimeBySec(mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds()
                - mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds());
        currentTime.setText(tempT.getHour() + "h"
                + tempT.getMinute() + "min");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            intent = new Intent(SleepResult.this, MainActivity.class);
            intent.putExtra("MySystem",mySystem);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
