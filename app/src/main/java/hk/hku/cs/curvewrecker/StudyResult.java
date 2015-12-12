package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.entities.MyTime;

public class StudyResult extends AppCompatActivity {

    private Button return_btn;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private MySystem mySystem;
    private TextView todayTime;
    private TextView currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_result);
        return_btn = (Button) findViewById(R.id.return_btn);

        //get mysystem
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");


        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        if(mySystem.getMyUser().getCrtMission().isDone()){
            toolbar_title.setText("Study Success");
        }
        else {
            toolbar_title.setText("Study Fail");
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        todayTime = (TextView) findViewById(R.id.today_hour);
        todayTime.setText(mySystem.getMyUser().getStudyTarget().getActualTime().getHour() + "h"
                + mySystem.getMyUser().getStudyTarget().getActualTime().getMinute() + "min");
        currentTime = (TextView) findViewById(R.id.current_hour);
        MyTime tempT = new MyTime();
        Log.d("StudyResult:", String.format("%d, %d", mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds(), mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds()));
        tempT.resetTimeBySec(mySystem.getMyUser().getCrtMission().getTargetTime().getTotalSeconds()
                - mySystem.getMyUser().getCrtMission().getRemainTime().getTotalSeconds());
        currentTime.setText(tempT.getHour() + "h"
                + tempT.getMinute() + "min");
    }

}
