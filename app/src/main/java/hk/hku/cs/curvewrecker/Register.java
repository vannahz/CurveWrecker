package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import hk.hku.cs.curvewrecker.Image3D.Image3DUtil;
import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.ChangeSleepTimeDialog;
import hk.hku.cs.curvewrecker.entities.MyTime;
import hk.hku.cs.curvewrecker.entities.MyUser;


public class Register extends AppCompatActivity {

    RoundImageView btn_submit;
    Button next_btn_name;
    Button next_btn_portrait;
    TextView sleeptime_set;
    Button next_btn_sleeptime;
    EditText edit_name;

    private static String url="http://i.cs.hku.hk/~jzyan/servertest/register.php";
    public URL http_url;
    public String data;
    public Handler handler;
    public String userId = "";
    public String id = "";
    public int mark = 0;
    Image3DUtil image3DUtil = new Image3DUtil();
    public int image = 0;
    MySystem mySystem;
    int myHour;
    int myMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mySystem = new MySystem(this.getFilesDir());

        if(mySystem.loadFile()){
            Log.d("loadFile()","find file!!!!!");
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
            finish();
        }

        //initial
        mySystem.initialFakeData();
        myHour = 8;
        myMin = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    //    Intent tempIntent = getIntent();
    //    Serializable tempExtra = tempIntent.getSerializableExtra("MySystem");
   //     mySystem = (MySystem) tempExtra;

        btn_submit = (RoundImageView)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               setUsername();
            }
        });

    }

    public void setUsername(){
        setContentView(R.layout.activity_register_name);
        handlerTest();

        edit_name = (EditText)findViewById(R.id.edit_name);
        next_btn_name = (Button)findViewById(R.id.next_btn_name);
        next_btn_name.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mySystem.getMyUser().setName(edit_name.getText().toString());
                register();
                setPortrait();
            }
        });
    }

    public void setPortrait(){

        setContentView(R.layout.activity_register_portrait);

        next_btn_portrait = (Button)findViewById(R.id.next_btn_portrait);
        next_btn_portrait.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                image = image3DUtil.getmCurrentImage();
                System.out.println(image);
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
                 m.setDate(myHour, myMin);
                 m.show();
                 m.setTimeListener(new ChangeSleepTimeDialog.OnTimeListener() {
                     @Override
                     public void onClick(String hour, String minute) {
                         myHour = Integer.parseInt(hour);
                         myMin = Integer.parseInt(minute);

                         sleeptime_set.setText(hour + " hour " + minute + " min");
                     }
                 });
             }
        });


        next_btn_sleeptime = (Button)findViewById(R.id.next_btn_sleeptime);
        next_btn_sleeptime.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Register: sleep-", String.format("%d:%02d", myHour,myMin));
                mySystem.getMyUser().setSleepTime(new MyTime(myMin,myHour));
                if( userId != "") {
                    mySystem.getMyUser().setUid(Integer.parseInt(userId));
                }
                else{
                    mySystem.getMyUser().setUid(0);
                }
                mySystem.saveFile();
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void register()
    {
        String params = "uid=" + userId + "&uname=" + edit_name.getText().toString() + "&mark=" + mark + "";
        postMethod(params);
    }

    public void handlerTest()
    {
        handler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch(msg.what)
                {
                    //Register successfully!
                    case 1:
                        Toast.makeText(Register.this, msg.getData().getString("msg"),
                                Toast.LENGTH_SHORT).show();
                        break;
                    //Register Failure!
                    case 2:
                        Toast.makeText(Register.this, msg.getData().getString("msg"),
                                Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
    }

    public void postMethod(final String params)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http_url = new URL(url);
                    if (http_url != null) {
                        HttpURLConnection conn = (HttpURLConnection) http_url.openConnection();
                        conn.setConnectTimeout(5 * 1000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        //String params = "uid=" + uid.getText().toString() + "&uname=" + uname.getText().toString()
                        //+ "&gender=" + gender.getText().toString() + "&mark=" + mark + "";
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("Content-Length", String.valueOf(params.getBytes().length));
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                        bw.write(params);
                        bw.close();
                        if (conn.getResponseCode() == 200) {
                            InputStream is = conn.getInputStream();
                            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                            data = buf.readLine();
                            buf.close();
                            is.close();
                            analyse(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void analyse (String data)
    {
        System.out.println(data);
        try {
            JSONObject json_data = new JSONObject(data);
            Boolean state = json_data.getBoolean("success");
            String msg = json_data.getString("msg");
            userId = msg;
            System.out.println(userId);
            //Register successfully!
            if(state)
            {
                Message message = new Message();
                message.what=1;
                Bundle temp = new Bundle();
                temp.putString("msg", msg);
                message.setData(temp);
                handler.sendMessage(message);
            }
            //Register not successfully!
            else
            {
                Message message = new Message();
                message.what=2;
                Bundle temp = new Bundle();
                temp.putString("msg",msg);
                message.setData(temp);
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
