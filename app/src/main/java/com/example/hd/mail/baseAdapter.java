package com.example.hd.mail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hd on 6/23/17.
 */

public class baseAdapter extends BaseAdapter {

    private Activity activity;
    private static ArrayList matter;
    private static LayoutInflater inflater = null;

    public baseAdapter(Activity a,ArrayList b){
        activity=a;
        this.matter=b;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return matter.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.layout, null);

        TextView sub = (TextView) vi.findViewById(R.id.row);
        sentMail sm2=(sentMail)matter.get(position);
        String to = sm2.getSubject().toString();
        sub.setText(to);


        TextView body1 = (TextView) vi.findViewById(R.id.row2);
        String body=sm2.getSubject().toString();
        body1.setText(body);

        return vi;

    }
}
