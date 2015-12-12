package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
 * Created by vannahz on 2015/12/3.
 */
public class StudySetting extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private Button start_btn;
    private LinearLayout studySetting;
    private TextView study_hour, study_minute;
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
        setContentView(R.layout.study_setting);

        //get mysystem
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        preTime = new MyTime();
        crtTime = new MyTime();

        //initial string
        myHour = 1;
        myMin = 0;

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
                        myHour = Integer.parseInt(hour);
                        myMin = Integer.parseInt(minute);
                        Toast.makeText(StudySetting.this, hour + ":" + minute, Toast.LENGTH_LONG).show();
                        study_hour.setText("0" + hour);

                        if (Integer.parseInt(minute) < 10)
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
        mySystem.getMyUser().setCrtMission(new MyMission(1, new MyTime(myMin, myHour)));
        //Log.d("####StudySeting:secs-", String.format("%d", mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds()));

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Studying");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        myRemainTime = (TextView) findViewById(R.id.study_time);
        String tempS = "";
        tempS = String.format("%d:%02d:%02d",mySystem.getMyUser().getCrtMission().getRemainTime().getHour(),
                                mySystem.getMyUser().getCrtMission().getRemainTime().getMinute(),
                                mySystem.getMyUser().getCrtMission().getRemainTime().getSecond());
        myRemainTime.setText(tempS);
        myCount = new MyCount(mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds());

        Button giveup_btn = (Button)findViewById(R.id.giveup_btn);
        giveup_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                myCount.cancel();
                mySystem.getMyUser().getCrtMission().setStatus(true);
                intent = new Intent(StudySetting.this, StudyResult.class);
                mySystem.getMyUser().getCrtMission().getEndTime().getCurrentTime();
                mySystem.addMissionToTarget(mySystem.getMyUser().getCrtMission());
                intent.putExtra("MySystem", mySystem);
                startActivity(intent);
                finish();
            }
        });


        myCount.start();

    /*    preTime.getCurrentTime();
        crtTime.getCurrentTime();
        Thread newThread = new Thread(new Runnable(){
            int counter = 0;
    //    newHandler.post(new Runnable() {

            public void run() {

                while (!mySystem.getMyUser().getCrtMission().getStatus()) {
                    crtTime.getCurrentTime();
                    if (!preTime.equal(crtTime)) {
                        counter++;

                        Log.d("####StudySeting:secs-", String.format("%d", mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds()));
                        //  Log.d("StudySeting:counter-", String.format("%d", counter));
                        //  Log.d("StudySeting:min , sec-", String.format("%d , %d", crtTime.getMinute(), crtTime.getSecond()));
                        mySystem.getMyUser().getCrtMission().decreaseRemainTime();
                        preTime = crtTime.copy();
                     //   Handler tempHandler = new Handler();
                     //   tempHandler.postDelayed(new Runnable() {
                     //       @Override
                     //       public void run() {
                     //       }
                     //   }, 500);
                    }

                }
                if (mySystem.getMyUser().getCrtMission().isDone()) {
                    Log.d("StudySetting: secs-", String.format("%d", mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds()));
                    Intent intent = new Intent(StudySetting.this, StudyResult.class);
                    mySystem.getMyUser().getCrtMission().getEndTime().getCurrentTime();
                    mySystem.addMissionToTarget(mySystem.getMyUser().getCrtMission());
                    intent.putExtra("MySystem", mySystem);
                    startActivity(intent);
                    finish();
                }

            }
        });
        newThread.start();*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            intent = new Intent(StudySetting.this, MainActivity.class);
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
            intent = new Intent(StudySetting.this, StudyResult.class);
            mySystem.getMyUser().getCrtMission().getEndTime().getCurrentTime();
            mySystem.addMissionToTarget(mySystem.getMyUser().getCrtMission());
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
            finish();
            //}
        }
        @Override
        public void onTick(long millisUntilFinished) {
           // Log.d("####StudySeting:secs-", String.format("%d", millisUntilFinished));

            //mySystem.getMyUser().getCrtMission().decreaseRemainTime();
            mySystem.getMyUser().getCrtMission().getRemainTime().resetTimeBySec((int)(millisUntilFinished /1000)-1);
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

