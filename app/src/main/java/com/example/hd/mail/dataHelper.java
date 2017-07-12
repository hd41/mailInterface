package com.example.hd.mail;

/**
 * Created by hd on 6/19/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dataHelper extends SQLiteOpenHelper {

    private static final String db_name="mail1";
    private static final String table_name="sent1";
    private static final String table_name1="register";
    private static final int ver=1;

    public dataHelper(Context context) {
        super(context, db_name, null, ver);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
        arg0.execSQL("create table if not exists "+table_name+" (rowId INTEGER PRIMARY KEY AUTOINCREMENT," +
                " toName text, fromName text, subject text, body text ,dateTime text,activeState int DEFAULT 1)");
        arg0.execSQL("create table if not exists "+table_name1+" (first text, last text, mail text, pwd text)");
    }

    public Cursor getData(String s){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+table_name+" where fromName ='"+s+"' and activeState=1", null );
        return res;
    }

    public Cursor getData1(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+table_name1+"", null );
        return res;
    }

    public Cursor find(String to1){
        SQLiteDatabase db=this.getReadableDatabase();
        boolean ans=false;
        Cursor res =  db.rawQuery("select * from " + table_name + " where toName='"+to1+"'", null);
        res.moveToFirst();
        if(res.isAfterLast()==false){
            ans=true;
        }
        return res;
    }

    public Boolean find1(String mail, String pwd){
        SQLiteDatabase db=this.getReadableDatabase();
        boolean ans=false;
        Cursor res =  db.rawQuery("select * from " + table_name1 + " where mail='"+mail+"' and pwd='"+pwd+"'", null);
        res.moveToFirst();
        if(res.isAfterLast()==false){
            ans=true;
        }
        return ans;
    }

    public void insert(String to,String frm,String sub, String body,String date){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into "+table_name+"(toName,fromName,subject,body,dateTime,activeState)"+" values('"+to+"','"+frm+"','"+sub+"','"+body+"','"+date+"',1)");
    }

    public void insert1(String fname,String lname, String mail,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into "+table_name1+"(first,last,mail,pwd)"+" values('"+fname+"','"+lname+"','"+mail+"','"+pwd+"')");
    }

    public void delete_all(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+table_name+"");
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
        return numRows;
    }

    public void delete2(int s){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Update "+table_name+" set activeState=0 where rowId="+s);
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        arg0.execSQL("drop table if exists "+table_name+"");
        arg0.execSQL("drop table if exists "+table_name1+"");
        onCreate(arg0);
    }
}