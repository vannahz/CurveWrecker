package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RoundImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();

        //initial mySystem
        mySystem = new MySystem();
        //do a fake data initial
        mySystem.initialFakeData();


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
        float tempStudyTime = mySystem.getMyUser().getStudyTarget().getActualTime().getTotalHour();
        preStudyTime.setText(String.format("%.1f", tempStudyTime));


        View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                switch (v.getId()) {
                    case R.id.study_btn:
                        intent = new Intent(MainActivity.this, StudySetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
                        break;
                    case R.id.sleep_btn:
                        intent = new Intent(MainActivity.this, SleepSetting.class);
                        //passing data by this method
                        intent.putExtra("MySystem",mySystem);
                        startActivity(intent);
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
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, Ranking.class);
                        intent.putExtra("MySystem", mySystem);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, Setting.class);
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
        rank.setText(String.format("%d", mySystem.getMyUser().getTitle()));
        Log.d("######MainActivity:exp:", String.format("%d", mySystem.getMyUser().getMyAttributes().getExp()));
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
}
