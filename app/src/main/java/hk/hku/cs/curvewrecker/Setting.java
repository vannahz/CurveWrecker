package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import hk.hku.cs.curvewrecker.entities.MySystem;

public class Setting extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView toolbar_title;
    private TextView userName;
    private TextView userID;
    private TextView sleepTime;
    private MySystem mySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");

        userName =  (TextView)findViewById(R.id.username_set);
        userName.setText(mySystem.getMyUser().getName());
        userID = (TextView)findViewById(R.id.userno_set);
        userID.setText(String.format("%04d", mySystem.getMyUser().getUid()));
        sleepTime = (TextView)findViewById(R.id.sleeptime_set);
        sleepTime.setText(String.format("%02dh%02dm", mySystem.getMyUser().getSleepTime().getHour(), mySystem.getMyUser().getSleepTime().getMinute()));

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Profile");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}
