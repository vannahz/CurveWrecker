package hk.hku.cs.curvewrecker;


import android.content.Intent;
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

import hk.hku.cs.curvewrecker.Calendar;
import hk.hku.cs.curvewrecker.Followers;
import hk.hku.cs.curvewrecker.R;
import hk.hku.cs.curvewrecker.Ranking;
import hk.hku.cs.curvewrecker.RoundImageView;
import hk.hku.cs.curvewrecker.Setting;
import hk.hku.cs.curvewrecker.SleepSetting;
import hk.hku.cs.curvewrecker.StudySetting;
import hk.hku.cs.curvewrecker.entities.MyRank;
import hk.hku.cs.curvewrecker.entities.MySubRank;
import hk.hku.cs.curvewrecker.entities.MySystem;

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
    private static String url2="http://i.cs.hku.hk/~jzyan/servertest/ranking.php";
    public URL http_url;
    public String data;
    public Handler handler;
    public String result = "";
    public String id1, id2, id3, id4, id5;
    public String name1, name2, name3, name4, name5;
    public String mark1, mark2, mark3, mark4, mark5;
    public String[] a;

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
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, StudySetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.sleep_btn:
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, SleepSetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.calendar_btn:
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Calendar.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        break;
                    case R.id.rank_btn:
                        mySystem.getMyUser().setMyRank(new MyRank());
                        getRank();
                        while(mySystem.getMyUser().getMyRank().getMySubRankList().size() < 5 ){
                             //Log.d("!!!", "!!!!!!");
                        }
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Ranking.class);
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
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Calendar.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Followers.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        mySystem.getMyUser().setMyRank(new MyRank());
                        getRank();
                        while(mySystem.getMyUser().getMyRank().getMySubRankList().size() < 5 ){
                            //Log.d("!!!", "!!!!!!");
                        }
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Ranking.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(hk.hku.cs.curvewrecker.MainActivity.this, Setting.class);
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

    public void getRank()
    {
        String params = "uid=" + mySystem.getMyUser().getUid() + "";
        getMethod(params);
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
                        //Toast.makeText(hk.hku.cs.curvewrecker.MainActivity.this, msg.getData().getString("msg"),
                                //Toast.LENGTH_SHORT).show();
                        break;
                    //Register Failure!
                    case 2:
                        //Toast.makeText(hk.hku.cs.curvewrecker.MainActivity.this, msg.getData().getString("msg"),
                                //Toast.LENGTH_SHORT).show();
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

    public void getMethod(final String params)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http_url = new URL(url2);
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
                            analyseTest(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void analyseTest (String data)
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
                result = msg;
                result = result.substring(0, result.length() - 1);
                Log.d("1!!!!",  result.substring(0,5));
                getJason(result);
                mySystem.getMyUser().setMyRank(new MyRank());
                mySystem.getMyUser().getMyRank().addMyRankList(new MySubRank(Integer.parseInt(id1), name1, Integer.parseInt(mark1), 0));
                mySystem.getMyUser().getMyRank().addMyRankList(new MySubRank(Integer.parseInt(id2), name2, Integer.parseInt(mark2), 1));
                mySystem.getMyUser().getMyRank().addMyRankList(new MySubRank(Integer.parseInt(id3), name3, Integer.parseInt(mark3), 2));
                mySystem.getMyUser().getMyRank().addMyRankList(new MySubRank(Integer.parseInt(id4), name4, Integer.parseInt(mark4), 3));
                mySystem.getMyUser().getMyRank().addMyRankList(new MySubRank(Integer.parseInt(id5), name5, Integer.parseInt(mark5), 4));
                mySystem.saveFile();
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

    public void getJason(String result)
    {
        a = result.split("\\.");
        id1 = a[0];
        name1 = a[1];
        mark1 = a[2];
        id2 = a[3];
        name2 = a[4];
        mark2 = a[5];
        id3 = a[6];
        name3 = a[7];
        mark3 = a[8];
        id4 = a[9];
        name4 = a[10];
        mark4 = a[11];
        id5 = a[12];
        name5 = a[13];
        mark5 = a[14];
        //Log.d("Followers:", String.format("%s,%s,%s", a[0], a[1], a[2]));
    }
}
