package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Followers extends AppCompatActivity{

    ListView lv_follower;
    private TextView toolbar_title;
    private Toolbar toolbar;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Friends");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        lv_follower = (ListView)findViewById(R.id.lv_followers);

        adapter = new SimpleAdapter(this,
                getData(),
                R.layout.item_followers,
                new String[] {"value"},
                new int[] { R.id.txt_item_followers_name });

        lv_follower.setAdapter(adapter);
        lv_follower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        Log.v("msg", "click");
                        intent = new Intent(Followers.this, Friends.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", "Angel");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("value", "Angel2");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("value", "Angel3");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("value", "Angel4");
        list.add(map);

        return list;
    }
}
