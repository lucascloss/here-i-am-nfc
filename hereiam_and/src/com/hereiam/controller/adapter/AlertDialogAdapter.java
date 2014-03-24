package com.hereiam.controller.adapter;

import java.util.ArrayList;

import com.hereiam.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlertDialogAdapter extends BaseAdapter{
 
	private Context context;
    private ArrayList<String> listarray = null;
    private LayoutInflater inflater;
    
    public AlertDialogAdapter(Context context, ArrayList<String> list)
    {
    	this.context = context;
    	this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listarray = list;
    }
    @Override
    public int getCount() {     
        return listarray.size();
    }
 
    @Override
    public Object getItem(int arg0) {
        return null;
    }
 
    @Override
    public long getItemId(int arg0) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {       
    	ViewHolder holder;
        if (convertView == null ) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.alertdialog_places, null);
             
            holder.txtListResults = (TextView) convertView.findViewById(R.id.txtListResults);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
         
        String datavalue = listarray.get(position);
        holder.txtListResults.setText(datavalue);         
        return convertView;
    }
     
    private static class ViewHolder {
        TextView txtListResults;
    }
	
}