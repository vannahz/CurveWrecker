package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hk.hku.cs.curvewrecker.entities.MyMission;
import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.entities.MyTime;

/**
 * Created by vannahz on 2015/12/6.
 */
public class SleepSetting extends AppCompatActivity {


    private TextView toolbar_title;
    private Toolbar toolbar;
    private Button sleepnow_btn;
    private LinearLayout sleepSetting;
    private TextView sleep_hour, sleep_minute;
    private Intent intent;
    private MySystem mySystem;
    MyTime preTime;
    MyTime crtTime;
    int myHour;
    int myMin;
    MyCount myCount;
    private TextView myRemainTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_setting);

        //get mysystem
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        preTime = new MyTime();
        crtTime = new MyTime();

        //initial string
        //myHour = 1;
        myHour = mySystem.getMyUser().getSleepTime().getHour();
        myMin = mySystem.getMyUser().getSleepTime().getMinute();

        //设置Toolbar
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Time Setting");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        sleep_hour = (TextView)findViewById(R.id.sleep_hour);
        sleep_hour.setText(String.format("%d", myHour));
        sleep_minute = (TextView)findViewById(R.id.sleep_minute);
        sleep_minute.setText(String.format("%02d", myMin));

//change time
        sleepSetting = (LinearLayout)findViewById(R.id.sleepSetting);
        sleepSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSleepTimeDialog mChangeBirthDialog = new ChangeSleepTimeDialog(SleepSetting.this);
                mChangeBirthDialog.setDate(myHour, myMin);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setTimeListener(new ChangeSleepTimeDialog.OnTimeListener() {
                    @Override
                    public void onClick(String hour, String minute) {
                        myHour = Integer.parseInt(hour);
                        myMin = Integer.parseInt(minute);
                        Toast.makeText(SleepSetting.this, hour + ":" + minute, Toast.LENGTH_LONG).show();
                        sleep_hour.setText("0" + hour);

                        if (Integer.parseInt(minute) < 10)
                            minute = "0" + minute;
                        sleep_minute.setText(minute);


                    }
                });
            }
        });
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
        mySystem.getMyUser().setCrtMission(new MyMission(0, new MyTime(myMin, myHour)));

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Sleeping  ");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        myRemainTime = (TextView) findViewById(R.id.sleep_time);
        String tempS = "";
        tempS = String.format("%d:%02d:%02d",mySystem.getMyUser().getCrtMission().getRemainTime().getHour(),
                mySystem.getMyUser().getCrtMission().getRemainTime().getMinute(),
                mySystem.getMyUser().getCrtMission().getRemainTime().getSecond());
        myRemainTime.setText(tempS);
        myCount = new MyCount(mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds());

        Button wakeup_btn = (Button)findViewById(R.id.wakeup_btn);
        wakeup_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                myCount.cancel();
                mySystem.getMyUser().getCrtMission().setStatus(true);
                intent = new Intent(SleepSetting.this, SleepResult.class);
                mySystem.getMyUser().getCrtMission().getEndTime().getCurrentTime();
                mySystem.addMissionToTarget(mySystem.getMyUser().getCrtMission());
                mySystem.saveFile();
                intent.putExtra("MySystem", mySystem);
                startActivity(intent);
                finish();
            }
        });

        myCount.start();
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

    class MyCount extends CountDownTimer {
        public MyCount(int remainT) {
            super((remainT+1) * 1000, 1000);
        }
        @Override
        public void onFinish() {
            //if (mySystem.getMyUser().getCrtMission().isDone()) {
            mySystem.getMyUser().getCrtMission().getRemainTime().resetTimeBySec(0);
            mySystem.getMyUser().getCrtMission().setStatus(true);
            // Log.d("StudySetting: secs-", String.format("%d", mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds()));
            intent = new Intent(SleepSetting.this, SleepResult.class);
            mySystem.getMyUser().getCrtMission().getEndTime().getCurrentTime();
            mySystem.addMissionToTarget(mySystem.getMyUser().getCrtMission());
            mySystem.saveFile();
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
            finish();
            //}
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // Log.d("####StudySeting:secs-", String.format("%d", millisUntilFinished));

            //mySystem.getMyUser().getCrtMission().decreaseRemainTime();
            mySystem.getMyUser().getCrtMission().getRemainTime().resetTimeBySec((int) (millisUntilFinished /1000)-1);
            //toolbar_title.setText("Studying" + mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds());
            String tempS = "";
            tempS = String.format("%d:%02d:%02d",mySystem.getMyUser().getCrtMission().getRemainTime().getHour(),
                    mySystem.getMyUser().getCrtMission().getRemainTime().getMinute(),
                    mySystem.getMyUser().getCrtMission().getRemainTime().getSecond());
            myRemainTime.setText(tempS);
            //  Log.d("####StudySeting:secs-", String.format("%d", mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds()));
        }
    }


}
