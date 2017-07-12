package com.example.hd.mail;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    dataHelper db=new dataHelper(this);
    EditText et1,et2,et3,et4,et5;
    Button back,create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et1=(EditText)this.findViewById(R.id.fName);
        et2=(EditText)this.findViewById(R.id.lName);
        et3=(EditText)this.findViewById(R.id.mailId);
        et4=(EditText)this.findViewById(R.id.pwd);
        et5=(EditText)this.findViewById(R.id.rpwd);

        back=(Button)this.findViewById(R.id.back);
        create=(Button)this.findViewById(R.id.create);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et4.getText().toString()).equals(et5.getText().toString())) {
                    db.insert1(et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString());
                    Snackbar.make(view, "Successfully Created Account", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(view, "Password doesn't match!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }
}
