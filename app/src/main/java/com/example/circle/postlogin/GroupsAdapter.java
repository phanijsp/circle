package com.example.circle.postlogin;

import android.content.Context;
import android.graphics.Typeface;
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
import java.util.List;

public class GroupsAdapter extends ArrayAdapter<Groups> {

  private Context mContext;
  private List<Groups> groupslist = new ArrayList<>();

  public GroupsAdapter(@NonNull Context context, @LayoutRes ArrayList<Groups> list) {
    super(context, 0, list);
    mContext = context;
    groupslist = list;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View listItem = convertView;

    if (listItem == null)
      listItem = LayoutInflater.from(mContext).inflate(R.layout.grouplist, parent, false);
    Groups currentGroup = groupslist.get(position);

    TextView groupname = (TextView) listItem.findViewById(R.id.groupname);
    TextView groupyear = (TextView) listItem.findViewById(R.id.groupyear);
    groupname.setText(currentGroup.getname());
    groupyear.setText(currentGroup.getYear());

    //  TextView release = (TextView) listItem.findViewById(R.id.value);

    //   name.setTypeface(typeface);
    // release.setTypeface(typeface);
    // release.setText(currentMOvie.getvalue());

    return listItem;
  }
}
