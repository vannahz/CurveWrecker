package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.hku.cs.curvewrecker.entities.MySubRank;
import hk.hku.cs.curvewrecker.entities.MySystem;


public class Ranking extends Activity {

    ListView lv_ranking;
    MySystem mySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        //initial system;
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");

        lv_ranking = (ListView)findViewById(R.id.lv_ranking);



        //update rank before here
        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),
                R.layout.item_ranking,
                new String[] { "rank", "img", "username", "time"},
                new int[] { R.id.txt_item_ranking_number, R.id.portrait, R.id.txt_item_ranking_user_name, R.id.txt_item_ranking_time});

        lv_ranking.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;// = new HashMap<String, Object>();

        int rankV = 1;
        for(MySubRank tempR: mySystem.getMyUser().getMyRank().getMySubRankList()){
            map = new HashMap<String, Object>();
            map.put("rank", rankV+"");
            map.put("img", R.drawable.portrait);
            map.put("username", tempR.getName());
            map.put("time", tempR.getMark()+"");
            list.add(map);
            rankV++;
        }

        return list;
    }

    private int getImageId(int index){

        return 0;
    }


}
