package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.hku.cs.curvewrecker.entities.MySystem;


public class Friends extends Activity{

    private MySystem mySystem;
    private TextView username, userno, fri_mark;
    public String position = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        position = tempI.getStringExtra("Position");

        username = (TextView)findViewById(R.id.username);
        userno = (TextView)findViewById(R.id.userno);
        fri_mark = (TextView)findViewById(R.id.fri_mark);

        username.setText(mySystem.getMyUser().getMyFriendsList().get(Integer.parseInt(position)).getName());
        userno.setText(String.valueOf(mySystem.getMyUser().getMyFriendsList().get(Integer.parseInt(position)).getUid()));
        fri_mark.setText(String.valueOf(mySystem.getMyUser().getMyFriendsList().get(Integer.parseInt(position)).getMark()) + " marks");

        //Toast.makeText(Friends.this, position, Toast.LENGTH_SHORT).show();

    }
}
