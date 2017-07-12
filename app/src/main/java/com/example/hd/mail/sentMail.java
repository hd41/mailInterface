package com.example.hd.mail;

/**
 * Created by hd on 6/23/17.
 */

public class sentMail {
    //rowId int, toName text, subject text, body text ,dateTime text,activeState int
    private int rowId;
    private String frm;
    private String toName;
    private String subject;
    private String body;
    private String dateTime;
    private int active;

    public sentMail(int rowId,String toName,String subject,String body,String dateTime,int active,String from){
        this.rowId=rowId;
        this.toName=toName;
        this.subject=subject;
        this.body=body;
        this.dateTime=dateTime;
        this.active=active;
        this.frm=from;
    }

    public int getRowId(){
        return rowId;
    }
    public String getToName(){
        return toName;
    }
    public String getSubject(){
        return subject;
    }
    public String getBody(){
        return body;
    }
    public String getDateTime(){
        return dateTime;
    }
    public int getActive(){
        return active;
    }
    public  String getFrm(){
        return frm;
    }
}
