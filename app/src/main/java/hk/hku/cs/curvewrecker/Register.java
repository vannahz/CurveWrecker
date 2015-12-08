package hk.hku.cs.curvewrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import hk.hku.cs.curvewrecker.entities.MySystem;


public class Register extends Activity implements View.OnClickListener{

    Button btn_birthday;
    Button btn_grade;
    Button btn_submit;
    EditText edt_birthday;
    EditText edt_grade;
    MySystem mySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent tempIntent = getIntent();
        Serializable tempExtra = tempIntent.getSerializableExtra("MySystem");
        mySystem = (MySystem) tempExtra;
        btn_birthday = (Button)findViewById(R.id.btn_birthday);
        btn_grade = (Button)findViewById(R.id.btn_grade);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        edt_birthday = (EditText)findViewById(R.id.edt_birthday);
        edt_grade = (EditText)findViewById(R.id.edt_grade);

        btn_birthday.setOnClickListener(this);
        btn_grade.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_birthday){
            /**Add pop up date selector code here**/
        }
        else if(v.getId() == R.id.btn_grade){
            /**Add pop up grade selector code here**/
        }
        else if(v.getId() == R.id.btn_submit){
            /**Add submit code here**/
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.putExtra("MySystem", mySystem);
            startActivity(intent);
        }
    }
}
