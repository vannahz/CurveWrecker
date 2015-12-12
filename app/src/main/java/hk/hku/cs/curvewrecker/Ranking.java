package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Ranking extends Activity {

    ListView lv_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lv_ranking = (ListView)findViewById(R.id.lv_ranking);

        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),
                R.layout.item_ranking,
                new String[] { "rank", "img", "username", "time"},
                new int[] { R.id.txt_item_ranking_number, R.id.portrait, R.id.txt_item_ranking_user_name, R.id.txt_item_ranking_time});

        lv_ranking.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rank", "1");
        map.put("img", R.drawable.portrait);
        map.put("username", "Angel");
        map.put("time", "200min");

        list.add(map);

        map = new HashMap<String, Object>();
        map.put("rank", "2");
        map.put("img", R.drawable.portrait);
        map.put("username", "Tom");
        map.put("time", "190min");

        list.add(map);

        map = new HashMap<String, Object>();
        map.put("rank", "3");
        map.put("img", R.drawable.portrait);
        map.put("username", "Jane");
        map.put("time", "170min");

        list.add(map);

        map = new HashMap<String, Object>();
        map.put("rank", "4");
        map.put("img", R.drawable.portrait);
        map.put("username", "Kara");
        map.put("time", "150min");

        list.add(map);

        map = new HashMap<String, Object>();
        map.put("rank", "5");
        map.put("img", R.drawable.portrait);
        map.put("username", "Sara");
        map.put("time", "130min");

        list.add(map);

        return list;
    }


}
