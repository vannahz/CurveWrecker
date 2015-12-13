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

public class StudyResult extends AppCompatActivity {

    private Button return_btn;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Intent intent;
    private MySystem mySystem;
    private TextView todayTime;
    private TextView currentTime;
    private TextView resultS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_result);
        return_btn = (Button) findViewById(R.id.return_btn);
        resultS = (TextView) findViewById(R.id.dress_img);

        //get mysystem
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");


        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        if(mySystem.getMyUser().getCrtMission().isDone()){
            resultS.setText("Success!^.^");
            toolbar_title.setText("Study Success");
        }
        else {
            resultS.setText("Failed!T.T");
            toolbar_title.setText("Study Fail");
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudyResult.this, MainActivity.class);
                intent.putExtra("MySystem",mySystem);
                startActivity(intent);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            intent = new Intent(StudyResult.this, MainActivity.class);
            intent.putExtra("MySystem",mySystem);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
