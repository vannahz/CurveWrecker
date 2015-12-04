package hk.hku.cs.curvewrecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class StudyResult extends AppCompatActivity {

    private Button return_btn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        return_btn = (Button) findViewById(R.id.return_btn);

        setSupportActionBar(toolbar);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
