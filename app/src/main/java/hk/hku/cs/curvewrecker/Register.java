package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.ChangeSleepTimeDialog;


public class Register extends AppCompatActivity {

    RoundImageView btn_submit;
    MySystem mySystem;
    Button next_btn_name;
    Button next_btn_portrait;
    TextView sleeptime_set;
    Button next_btn_sleeptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Log.d("Register:", "Register test!!!!!");
        if(mySystem.loadFile()){
            Log.d("loadFile()","find file!!!!!");
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent tempIntent = getIntent();
        Serializable tempExtra = tempIntent.getSerializableExtra("MySystem");
        mySystem = (MySystem) tempExtra;

        btn_submit = (RoundImageView)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               setUsername();
            }
        });

    }

    public void setUsername(){
        setContentView(R.layout.activity_register_name);

        next_btn_name = (Button)findViewById(R.id.next_btn_name);
        next_btn_name.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                setPortrait();
            }
        });
    }

    public void setPortrait(){

        setContentView(R.layout.activity_register_portrait);

        next_btn_portrait = (Button)findViewById(R.id.next_btn_portrait);
        next_btn_portrait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                setSleepTime();
            }
        });

    }

    public void setSleepTime(){

        setContentView(R.layout.activity_register_sleeptime);

        sleeptime_set = (TextView)findViewById(R.id.sleeptime_set);
        sleeptime_set.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ChangeSleepTimeDialog m = new ChangeSleepTimeDialog(Register.this);
                 m.setDate(8, 0);
                 m.show();
                 m.setTimeListener(new ChangeSleepTimeDialog.OnTimeListener() {
                     @Override
                     public void onClick(String hour, String minute) {
                         sleeptime_set.setText(hour + " hour " + minute + " min");
                     }
                 });
             }
        });


        next_btn_sleeptime = (Button)findViewById(R.id.next_btn_sleeptime);
        next_btn_sleeptime.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
