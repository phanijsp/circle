package com.example.circle.postlogin.SendMessageToMultipleGroups;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.circle.R;

import java.util.ArrayList;


public class GroupsAdapter_CheckBox extends ArrayAdapter<Data_Model> {

    Context mContext;
    private ArrayList<Data_Model> groupslist;

    public GroupsAdapter_CheckBox(Context context, ArrayList<Data_Model> list) {
        super(context,R.layout.grouplist_checkbox,list);
        mContext = context;
        groupslist = list;
    }

    @Override
    public int getCount() {
        return groupslist.size();
    }


    private static class ViewHolder {
        TextView groupname;
        TextView groupyear;
        CheckBox checkBox;
    }
    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplist_checkbox, parent, false);
            viewHolder.groupname=(TextView)convertView.findViewById(R.id.groupname);
            viewHolder.groupyear=(TextView)convertView.findViewById(R.id.groupyear);
            viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.checkBox);
            result=convertView;
            convertView.setTag(viewHolder);

        }else{
            viewHolder =(ViewHolder)convertView.getTag();
            result=convertView;
        }

        Data_Model data_model =getItem(position);
        viewHolder.groupname.setText(data_model.groupname);
        viewHolder.groupyear.setText(data_model.groupyear);
        viewHolder.checkBox.setChecked(data_model.chechk_val);

        return result;
    }

    @Override
    public Data_Model getItem(int position) {
        return groupslist.get(position);
    }
}
