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

public class MainActivity extends AppCompatActivity {
    //声明相关变量
    private TextView toolbar_title;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView menulist;
    private String[] lvs = {"Daily Record", "Star Calender", "Friends", "Ranks", "Setting"};
    private Button study_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();

        //设置Toolbar
        toolbar_title.setText("Attack On Curve Wrecker");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        study_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudySetting.class);
                startActivity(intent);
            }
        });

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

      /*  menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this,Friends.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this,Ranking.class);
                        startActivity(intent);
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }
        });*/
    }

    private void findViews() {

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main);
        menulist = (ListView) findViewById(R.id.left_menu);
        study_btn = (Button)findViewById(R.id.study_btn);
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
