package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudyResult extends AppCompatActivity {

    private Button return_btn;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_result);
        return_btn = (Button) findViewById(R.id.return_btn);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Study Fail");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudyResult.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            intent = new Intent(StudyResult.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
