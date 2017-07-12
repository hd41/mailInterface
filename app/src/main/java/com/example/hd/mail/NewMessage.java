package com.example.hd.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import static android.R.attr.path;

public class NewMessage extends AppCompatActivity {

    dataHelper dh=new dataHelper(this);
    EditText sub,frm,rec,body;
    Button res,send,chooseFile;
    TextView fileName;
    String filePath,fileName1,from1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        frm=(EditText)this.findViewById(R.id.from);
        rec=(EditText)this.findViewById(R.id.to);
        sub=(EditText)this.findViewById(R.id.sub);
        body=(EditText)this.findViewById(R.id.body);
        fileName=(TextView)this.findViewById(R.id.chooseText);

        Intent intent=getIntent();

        Bundle b1=intent.getBundleExtra("bun");
        from1=b1.getString("id");
        frm.setText(""+from1);

        res=(Button)this.findViewById(R.id.res);
        send=(Button)this.findViewById(R.id.send);
        chooseFile=(Button)this.findViewById(R.id.chooseButton);


        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //view.setVisibility(view.GONE);
            }
        });

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Action perform
                final String rec1=rec.getText().toString();
                final String sub1=sub.getText().toString();
                final String body1=body.getText().toString();
                String path;
                Log.d("testing","yo chill");
                if(filePath!=null){
                    File fileLocation=new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),filePath);
                    Log.d("testing","file Location: "+fileLocation);
                    Uri path1= Uri.fromFile(fileLocation);
                    
                    SendMail sm=new SendMail(getApplicationContext(),rec1,sub1,body1,fileName1,path1.toString());
                    sm.execute();
                }
                else{
                    path="hello";

                    SendMail sm=new SendMail(getApplicationContext(),rec1,sub1,body1,fileName1,path);
                    sm.execute();
                }
            }
        });
    }

    public void performFileSearch(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    fileName1=getFileName(uri);
                    filePath=data.getData().getPath();
                    fileName.setText(""+fileName1);
                }
                break;
        }
    }

    public String getFileName(Uri uri){
        String result=null;
        if(result==null){
            result=uri.getPath();

            int cut=result.lastIndexOf(':');
            if(cut!=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }

    public class SendMail extends AsyncTask<Void,Void,Void> {

        //Declaring Variables
        private Context context;
        private Session session;

        //Information to send email
        private String email,subject,body;
        private String path,name;
        //Progressdialog to show while sending email
        private ProgressDialog progressDialog;

        //Class Constructor
        public SendMail(Context context, String email, String subject, String message,String name,String path){
            //Initializing variables
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.body = message;
            this.path=path;
            this.name=name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing progress dialog while sending email
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewMessage.this);
            progressDialog.setMessage("Sending ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            dh.insert(email,from1,subject,body,formattedDate);
            Log.d("testing",email+" ******** "+subject+" ******** "+body+" *********  "+formattedDate);
            progressDialog.dismiss();
            //Showing a success message
            Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            Properties props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Creating a new session
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("your mailId", "your pwd");
                        }
                    });

            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress("your mailID"));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));

                // Set Subject: header field
                message.setSubject(subject);

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                messageBodyPart.setText(body);
                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                if(name!=null){
                    // Part two is attachment
                    messageBodyPart = new MimeBodyPart();
                    //File f=new File("SD Card/Others/mayank.pdf");
                    String secStore=System.getenv("SECONDARY_STORAGE");
                    File f=new File(secStore+File.separator+name);
                    Log.d("testing",f.getAbsolutePath());
                    DataSource source = new FileDataSource(f);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(name);
                    multipart.addBodyPart(messageBodyPart);
                }
                // Send the complete message parts
                message.setContent(multipart);
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sent_mails) {
            Intent intent=new Intent(NewMessage.this,sentList.class);
            Bundle b1=new Bundle();
            b1.putString("frm",from1);
            intent.putExtra("smails",b1);
            startActivity(intent);
            return true;
        }
        if(id== R.id.exit){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
