package com.example.circle.postlogin;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.circle.R;

import java.util.ArrayList;
import java.util.List;

    public class messagesAdapter extends ArrayAdapter<messages> {

        private Context mContext;
        private List<messages> messageslist = new ArrayList<>();

        public messagesAdapter(@NonNull Context context, @LayoutRes ArrayList<messages> list) {
            super(context, 0, list);
            mContext = context;
            messageslist = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;

            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.messagelist,parent, false);
            messages currentmessage = messageslist.get(position);

            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time =(TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout= (LinearLayout)listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout)listItem.findViewById(R.id.rootview);
           // TextView type = (TextView) listItem.findViewById(R.id.textView3);

            if(currentmessage.getSender().equals(messages.getUsername())){
                //linearLayout.setGravity(Gravity.RIGHT);
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout,100,8,8,8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);

            }
            if(!currentmessage.getSender().equals(messages.getUsername())){
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout,8,8,100,8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);

            }
           messagevalue.setText(currentmessage.getMessagevalue());
            time.setText(currentmessage.getTime());
          //  type.setText(currentmessage.getMessagetype());


            return listItem;
        }
        private void setMargins (View view, int left, int top, int right, int bottom) {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                p.setMargins(left, top, right, bottom);
                view.requestLayout();
            }
        }
    }



