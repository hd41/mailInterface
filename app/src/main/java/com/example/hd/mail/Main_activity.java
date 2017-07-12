package com.example.hd.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main_activity extends AppCompatActivity {

    dataHelper db=new dataHelper(this);
    EditText emID,pwd;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emID=(EditText)this.findViewById(R.id.mailId);
        pwd=(EditText)this.findViewById(R.id.pwd);
        tv=(TextView)this.findViewById(R.id.textView);
        db.insert1("Himanshu","Dhanwant","jarvis1247@gmail.com","hello");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                String emID1=emID.getText().toString();
//                String pwd1=pwd.getText().toString();

                String emID1="jarvis1247@gmail.com";
                String pwd1="hello";

                if(db.find1(emID1,pwd1)==true) {
                    Intent in = new Intent(Main_activity.this, NewMessage.class);
                    Bundle b1 = new Bundle();
                    b1.putString("id", emID.getText().toString());
                    b1.putString("pass", pwd.getText().toString());
                    in.putExtra("bun", b1);
                    startActivity(in);

                    Snackbar.make(view, "Successfully Logged In", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{
                    Snackbar.make(view, "Wrong ID and Password!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(Main_activity.this,SignUp.class);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
