package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.EditText;
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

import hk.hku.cs.curvewrecker.entities.MyFriend;
import hk.hku.cs.curvewrecker.entities.MySystem;

public class Followers extends AppCompatActivity{

    ListView lv_follower;
    private TextView toolbar_title;
    private Toolbar toolbar;
    SimpleAdapter adapter;
    private Button search_btn;
    private EditText search_friends;
    private MySystem mySystem;

    private static String url="http://i.cs.hku.hk/~jzyan/servertest/addFriends.php";
    public URL http_url;
    public String data;
    public Handler handler;
    public String result = "";
    public String[] a;
    public String id = "";
    public String name = "";
    public String mark = "";

    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Friends");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        search_btn = (Button)findViewById(R.id.search_btn);
        search_friends = (EditText)findViewById(R.id.search_friends);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent tempI = getIntent();
        mySystem = (MySystem)tempI.getSerializableExtra("MySystem");
        handlerTest();

        lv_follower = (ListView)findViewById(R.id.lv_followers);

        getData();
        adapter = new SimpleAdapter(this,
                list,
                R.layout.item_followers,
                new String[] {"value"},
                new int[] { R.id.txt_item_followers_name });

        search_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                addFriends();
                getData();
                adapter.notifyDataSetChanged();
            }
        });

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

    private void getData() {
       // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;
        for(MyFriend tempF: mySystem.getMyUser().getMyFriendsList())
        {
            map = new HashMap<String, Object>();
            map.put("value", tempF.getName());
            list.add(map);
        }

      //  return list;
    }

    public void addFriends()
    {
        String params = "uid=" + mySystem.getMyUser().getUid() + "&fid=" + search_friends.getText().toString() + "";
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
                        Toast.makeText(Followers.this, msg.getData().getString("msg"),
                                Toast.LENGTH_SHORT).show();
                        break;
                    //Register Failure!
                    case 2:
                        Toast.makeText(Followers.this, msg.getData().getString("msg"),
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
            //Register successfully!
            if(state)
            {
                result = msg;
                //System.out.println(result);
                getJason(result);
                mySystem.getMyUser().addMyFriend(new MyFriend(Integer.parseInt(id), name, Integer.parseInt(mark)));
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

    public void getJason(String result)
    {
        a = result.split("\\.");
        id = a[0];
        name = a[1];
        mark = a[2];
        Log.d("Followers:", String.format("%s,%s,%s", a[0],a[1],a[2]));

    }
}
