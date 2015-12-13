package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.hku.cs.curvewrecker.entities.MySubRank;
import hk.hku.cs.curvewrecker.entities.MySystem;


public class Ranking extends Activity {

    ListView lv_ranking;
    MySystem mySystem;
    RoundImageView avatar;
    TextView myName;
    int[] id = {R.drawable.image1, R.drawable.image4, R.drawable.image5, R.drawable.image3, R.drawable.image8, R.drawable.image7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        //initial system;
        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");

        lv_ranking = (ListView)findViewById(R.id.lv_ranking);

        myName = (TextView) findViewById(R.id.username);
        avatar = (RoundImageView) findViewById(R.id.portrait);
        myName.setText(mySystem.getMyUser().getName());
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
            map.put("img", id[rankV]);
            map.put("username", tempR.getName());
            map.put("time", tempR.getMark()+" marks");
            list.add(map);
            rankV++;
        }

        return list;
    }

    private int getImageId(int index){

        return 0;
    }


}
