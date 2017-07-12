package com.example.hd.mail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mailSentActivity extends AppCompatActivity {

    dataHelper dh=new dataHelper(this);
    TextView tv1,tv2,tv3,tv4;
    Button del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv1=(TextView)this.findViewById(R.id.rec);
        tv2=(TextView)this.findViewById(R.id.sub);
        tv3=(TextView)this.findViewById(R.id.body);
        tv4=(TextView)this.findViewById(R.id.time);
        del=(Button)this.findViewById(R.id.del);

        Intent intent=getIntent();

        Bundle b1=intent.getBundleExtra("bun");

        tv1.setText(b1.getString("rec"));
        tv2.setText(b1.getString("sub"));
        tv3.setText(b1.getString("body"));
        tv4.setText(b1.getString("time"));
        final int rId1=b1.getInt("rId");
        final String from1=b1.getString("frm");

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.delete2(rId1);
                Intent in=new Intent(mailSentActivity.this,sentList.class);
                Bundle b1=new Bundle();
                b1.putString("frm",from1);
                in.putExtra("smails",b1);
                startActivity(in);
                finish();

            }
        });
    }
}
