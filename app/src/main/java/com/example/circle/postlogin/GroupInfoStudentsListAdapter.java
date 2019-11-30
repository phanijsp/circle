package com.example.circle.postlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.circle.R;

import java.util.ArrayList;

public class GroupInfoStudentsListAdapter  extends ArrayAdapter {

    private Context context;
    private ArrayList<student> list;
    public GroupInfoStudentsListAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<student> list){
        super(context,0,list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.student, parent, false);
        student currentstudent = list.get(position);
        TextView studenttitle = (TextView) listItem.findViewById(R.id.studentname);
        studenttitle.setText(currentstudent.getStudentname());
        return listItem;
    }
}