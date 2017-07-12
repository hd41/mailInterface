package com.example.hd.mail;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class sentList extends AppCompatActivity {

    ArrayList<sentMail> sm=new ArrayList<sentMail>();
    ListView list;
    baseAdapter adapter;
    dataHelper dh=new dataHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_list);
        list = (ListView) findViewById(R.id.listView);

        Intent in=getIntent();
        Bundle b2=in.getBundleExtra("smails");
        String frm=b2.getString("frm");
        filldp(frm);

        adapter = new baseAdapter(sentList.this, sm);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sentMail mail=sm.get(position);
                Intent in = new Intent(sentList.this,mailSentActivity.class);
                Bundle b1=new Bundle();
                b1.putString("frm",mail.getFrm());
                b1.putInt("rId",mail.getRowId());
                b1.putString("rec",mail.getToName());
                b1.putString("sub",mail.getSubject());
                b1.putString("body",mail.getBody());
                b1.putString("time",mail.getDateTime());
                in.putExtra("bun",b1);

                startActivity(in);
                finish();
            }
        });

    }

    public void filldp(String a){
        Cursor c=dh.getData(a);
        c.moveToFirst();
        int i=0;
        while(c.isAfterLast()==false){
            int rId=c.getInt(c.getColumnIndex("rowId"));
            String toName=c.getString(c.getColumnIndex("toName"));
            String subj=c.getString(c.getColumnIndex("subject"));
            String body=c.getString(c.getColumnIndex("body"));
            String date=c.getString(c.getColumnIndex("dateTime"));
            String from=c.getString(c.getColumnIndex("fromName"));
            int active=c.getInt(c.getColumnIndex("activeState"));
            Log.d("testing",toName+" : "+subj+" : "+body+" : "+date+" : "+active);
            sentMail sm1=new sentMail(rId,toName,subj,body,date,active,from);
            sm.add(sm1);
            c.moveToNext();
        }

    }
}
