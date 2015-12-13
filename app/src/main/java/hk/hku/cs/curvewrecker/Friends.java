package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.hku.cs.curvewrecker.entities.MyFriend;
import hk.hku.cs.curvewrecker.entities.MySystem;


public class Friends extends Activity{

    private MySystem mySystem;
    private TextView username, userno, fri_mark;
    private MyFriend myFriend;
    public Button add_friends;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        myFriend = (MyFriend)tempI.getSerializableExtra("MyFriend");

        username = (TextView)findViewById(R.id.username);
        userno = (TextView)findViewById(R.id.userno);
        fri_mark = (TextView)findViewById(R.id.fri_mark);

        username.setText(myFriend.getName());
        userno.setText(String.format("%04d", myFriend.getUid()));
        fri_mark.setText(myFriend.getMark() + " marks");

        //Toast.makeText(Friends.this, position, Toast.LENGTH_SHORT).show();
/*
        add_friends.findViewById(R.id.add_friends);
        add_friends.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Friends.this, Followers.class);
                startActivity(intent);
                finish();

            }
        });*/

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(Friends.this, Followers.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
