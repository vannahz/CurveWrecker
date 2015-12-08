package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Friends extends Activity implements View.OnClickListener {


    ListView lv_friends_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        lv_friends_information = (ListView)findViewById(R.id.lv_friends_information);

        SimpleAdapter adapter = new SimpleAdapter(this,
                getData(),
                R.layout.item_friends_information,
                new String[] { "attribute", "value"},
                new int[] { R.id.txt_item_friends_attribute, R.id.txt_item_friends_information});

        lv_friends_information.setAdapter(adapter);

    }
    @Override
    public void onClick(View v) {
        //Implement follow code here

    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attribute", "Alias");
        map.put("value", "Angel");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("attribute", "User ID");
        map.put("value", "123456");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("attribute", "Message");
        map.put("value", "Angel");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("attribute", "School");
        map.put("value", "The University Of Hong Kong");
        list.add(map);

        return list;
    }
}
