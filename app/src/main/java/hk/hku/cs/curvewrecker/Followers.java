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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Followers extends AppCompatActivity implements View.OnClickListener{

    ListView lv_follower;
    Button btn_followers_followed;
    Button btn_followers_fans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        lv_follower = (ListView)findViewById(R.id.lv_ranking);

        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),
                R.layout.item_ranking,
                new String[] { "value"},
                new int[] { R.id.txt_item_followers_name });

        lv_follower.setAdapter(adapter);

        btn_followers_fans = (Button) findViewById(R.id.btn_followers_fans);
        btn_followers_followed = (Button) findViewById(R.id.btn_followers_followed);

        lv_follower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(Followers.this,Friends.class);
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

    @Override
    public void onClick(View v) {

    }
}
