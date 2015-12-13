package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import hk.hku.cs.curvewrecker.entities.MySystem;

public class Setting extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView toolbar_title;
    private TextView userName;
    private TextView userID;
    private TextView sleepTime;
    private MySystem mySystem;
    private ImageView avatar;

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
        avatar = (ImageView) findViewById(R.id.portrait_set);
        switch(mySystem.getMyUser().getImgPath()){
            case 0:
                avatar.setImageResource(R.drawable.image1);
                break;
            case 1:
                avatar.setImageResource(R.drawable.image2);
                break;
            case 2:
                avatar.setImageResource(R.drawable.image3);
                break;
            case 3:
                avatar.setImageResource(R.drawable.image4);
                break;
            case 4:
                avatar.setImageResource(R.drawable.image5);
                break;
            case 5:
                avatar.setImageResource(R.drawable.image6);
                break;
            case 6:
                avatar.setImageResource(R.drawable.image7);
                break;
            case 7:
                avatar.setImageResource(R.drawable.image8);
                break;
        }
        sleepTime = (TextView)findViewById(R.id.sleeptime_set);
        sleepTime.setText(String.format("%02dh%02dm", mySystem.getMyUser().getSleepTime().getHour(), mySystem.getMyUser().getSleepTime().getMinute()));

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Profile");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}
