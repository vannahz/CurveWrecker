package hk.hku.cs.curvewrecker;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.hku.cs.curvewrecker.entities.MySystem;
import hk.hku.cs.curvewrecker.entities.MyTime;

public class MainActivity extends AppCompatActivity {
    //声明相关变量
    private TextView toolbar_title;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView menulist;
    private String[] lvs = {"Daily Record", "Star Calender", "Friends", "Ranks", "Setting"};
    private Button study_btn, sleep_btn, calendar_btn, rank_btn;
    private MySystem mySystem;
    private TextView currentMark;
    private TextView sleepTime;
    private TextView preStudyTime;
    private TextView userName;
    private TextView userID;
    private TextView friendNo;
    private TextView totalHour;
    private TextView rank;
    private TextView studyTime;
    private RoundImageView avatar;

    private static String url="http://i.cs.hku.hk/~jzyan/servertest/updateMark.php";
    public URL http_url;
    public String data;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();

        mySystem = new MySystem(this.getFilesDir());

            //initial mySystem
            if(!mySystem.loadFile()){
                Log.d("####MainActivity:","cant find file");
                //do a fake data initial
                //     mySystem.initialFakeData();
                mySystem.saveFile();

        }

        mySystem.updateDate();
    /*    else
        {
            Intent tempI = getIntent();
            mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        }
*/
        /*//check if there is data stored
        if(!mySystem.loadFile()){
            Log.d("loadFile()","cannot find file!!!!!");
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
        }
        Log.d("MySystem","Next to save file!!!!!");
        mySystem.saveFile();
*/
        //设置Toolbar
        toolbar_title.setText("Attack On Curve Wrecker");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //currentMark.setText(mySystem.getCurrentMark());
        currentMark.setText(String.format("%d", mySystem.getCurrentMark()));
        float tempSleepTime = mySystem.getMyUser().getSleepTarget().getActualTime().getTotalHour();
        sleepTime.setText(String.format("%.1f", tempSleepTime));
        int tempStudyTime = mySystem.getMyUser().getStudyTarget().getActualTime().getTotalMinute();
        studyTime.setText(String.format("%d min", tempStudyTime));
        float tempPreStudy = mySystem.getAverageStudyTime();
        preStudyTime.setText(String.format("%.1f", tempPreStudy));

        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                switch (v.getId()) {
                    case R.id.study_btn:
                        intent = new Intent(MainActivity.this, StudySetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.sleep_btn:
                        intent = new Intent(MainActivity.this, SleepSetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.calendar_btn:
                        intent = new Intent(MainActivity.this, Calendar.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        break;
                    case R.id.rank_btn:
                        intent = new Intent(MainActivity.this, Ranking.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        };

        study_btn.setOnClickListener(handler);
        sleep_btn.setOnClickListener(handler);
        calendar_btn.setOnClickListener(handler);
        rank_btn.setOnClickListener(handler);

        handlerTest();
        updateMark();

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
              //  mAnimationDrawable.stop();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
               // mAnimationDrawable.start();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置菜单列表
        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),
                R.layout.listitem,
                new String[] { "img", "text"},
                new int[] { R.id.list_image, R.id.list_text});



        menulist.setAdapter(adapter);

        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, Calendar.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, Followers.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, Ranking.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, Setting.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

        mySystem.checkLevelInfo();

        findMenuListView();
        userName.setText(mySystem.getMyUser().getName());
        userID.setText(String.format("%d", mySystem.getMyUser().getUid()));
        friendNo.setText(String.format("%d", mySystem.getMyUser().getMyFriendsList().size()));
        totalHour.setText(String.format("%d", mySystem.getTotalHour()));
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

        rank.setText(String.format("%d", mySystem.getMyUser().getTitle()));
     //   Log.d("######MainActivity:exp:", String.format("%d", mySystem.getMyUser().getMyAttributes().getExp()));
    }

    private void findViews() {
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main);
        menulist = (ListView) findViewById(R.id.left_menu);
        study_btn = (Button)findViewById(R.id.study_btn);
        sleep_btn = (Button) findViewById(R.id.sleep_btn);
        calendar_btn = (Button) findViewById(R.id.calendar_btn);
        rank_btn = (Button)findViewById(R.id.rank_btn);
        currentMark = (TextView) findViewById(R.id.score);
        sleepTime = (TextView) findViewById(R.id.sleep);
        preStudyTime = (TextView) findViewById(R.id.yesterday);
        studyTime = (TextView) findViewById(R.id.studyTime);

    }

    private void findMenuListView(){
        userName = (TextView) findViewById(R.id.username);
        userID = (TextView) findViewById(R.id.userno);
        friendNo = (TextView) findViewById(R.id.friend_no);
        totalHour = (TextView) findViewById(R.id.total_hour);
        rank = (TextView) findViewById(R.id.rank);
        avatar = (RoundImageView) findViewById(R.id.portrait);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.icalender);
        map.put("text", "Daily Record");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.star);
        map.put("text", "Star Calender");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.friend);
        map.put("text", "Friends");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.rank);
        map.put("text", "Ranks");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.setting);
        map.put("text", "Setting");
        list.add(map);

        return list;
    }

    public void updateMark()
    {
        String params = "uid=" + mySystem.getMyUser().getUid() + "&mark=" + mySystem.getCurrentMark() + "";
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
                        Toast.makeText(MainActivity.this, msg.getData().getString("msg"),
                                Toast.LENGTH_SHORT).show();
                        break;
                    //Register Failure!
                    case 2:
                        Toast.makeText(MainActivity.this, msg.getData().getString("msg"),
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
            //userId = msg;
            //System.out.println(userId);
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
