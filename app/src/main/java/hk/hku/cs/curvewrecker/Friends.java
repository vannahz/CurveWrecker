package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class Friends extends Activity implements View.OnClickListener {


    ListView list_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

    }
    @Override
    public void onClick(View v) {

    }
}
