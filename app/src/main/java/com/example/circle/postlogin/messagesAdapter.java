package com.example.circle.postlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.circle.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class messagesAdapter extends ArrayAdapter<messages> {

    private Context mContext;
    private List<messages> messageslist = new ArrayList<>();
    VideoRequestHandler videoRequestHandler;
    Picasso picassoInstance;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_AUDIO = 3;
    public static final int TYPE_DOCUMENT = 4;
    public static final int TYPE_IMAGE_DOWNLOADED = 5;
    public static final int TYPE_VIDEO_DOWNLOADED = 6;
    public static final int TYPE_AUDIO_DOWNLOADED = 7;
    public static final int TYPE_DOCUMENT_DOWNLOADED = 8;

    public messagesAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<messages> list) {
        super(context, 0, list);
        mContext = context;
        messageslist = list;
        videoRequestHandler = new VideoRequestHandler();
        picassoInstance = new Picasso.Builder(context.getApplicationContext())
                .addRequestHandler(videoRequestHandler)
                .build();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        messages currentmessage = messageslist.get(position);
        if(getItemViewType(position)==TYPE_IMAGE){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_image, parent, false);
            }

            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            ImageView download_button = (ImageView) listItem.findViewById(R.id.imageView4);
            ImageView thumb = (ImageView) listItem.findViewById(R.id.video_thumbnail_layout);
            LottieAnimationView lottieAnimationView = (LottieAnimationView)listItem.findViewById(R.id.loading_animation);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            if(currentmessage.getMessagestatus().equals("downloading")){
                thumb.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                download_button.setVisibility(View.GONE);
            }
            if(currentmessage.getMessagestatus().equals("not_downloaded")){
                lottieAnimationView.setVisibility(View.GONE);
                download_button.setVisibility(View.VISIBLE);
                thumb.setVisibility(View.VISIBLE);
            }
            messagevalue.setText(currentmessage.getMessagevalue());
            time.setText(currentmessage.getTime());
            return listItem;
        }else if(getItemViewType(position)==TYPE_IMAGE_DOWNLOADED){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_image_downloaded, parent, false);
            }
            ImageView imageView = (ImageView) listItem.findViewById(R.id.video_thumbnail_layout);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
//            imageView.setImageBitmap(BitmapFactory.decodeFile(currentmessage.messagevalue));
            Picasso.get().load(new File(currentmessage.getMessagevalue())).into(imageView);
            time.setText(currentmessage.getTime());
            return listItem;
        }
        else if(getItemViewType(position)==TYPE_VIDEO){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_video, parent, false);
            }

            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            ImageView download_button = (ImageView) listItem.findViewById(R.id.imageView4);
            ImageView thumb = (ImageView) listItem.findViewById(R.id.video_thumbnail_layout);
            LottieAnimationView lottieAnimationView = (LottieAnimationView)listItem.findViewById(R.id.loading_animation);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            if(currentmessage.getMessagestatus().equals("downloading")){
                thumb.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                download_button.setVisibility(View.GONE);
            }
            if(currentmessage.getMessagestatus().equals("not_downloaded")){
                lottieAnimationView.setVisibility(View.GONE);
                download_button.setVisibility(View.VISIBLE);
                thumb.setVisibility(View.VISIBLE);
            }
            messagevalue.setText(currentmessage.getMessagevalue());
            time.setText(currentmessage.getTime());
            return listItem;
        }else if(getItemViewType(position)==TYPE_VIDEO_DOWNLOADED){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_video_downloaded, parent, false);
            }
            ImageView imageView = (ImageView) listItem.findViewById(R.id.video_thumbnail_layout);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
//            imageView.setImageBitmap(BitmapFactory.decodeFile(currentmessage.messagevalue));
//            Picasso.get().load(new File(currentmessage.getMessagevalue())).into(imageView);

            picassoInstance.load(VideoRequestHandler.SCHEME_VIDEO+":"+currentmessage.getMessagevalue()).into(imageView);

//            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(currentmessage.getMessagevalue(), MediaStore.Video.Thumbnails.MICRO_KIND);
//            imageView.setImageBitmap(bitmap);
            time.setText(currentmessage.getTime());
            return listItem;
        }
        else if(getItemViewType(position)==TYPE_AUDIO){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_audio, parent, false);
            }
            ImageView thumb = (ImageView) listItem.findViewById(R.id.video_thumbnail_layout);
            ImageView download_button = (ImageView) listItem.findViewById(R.id.imageView4);
            LottieAnimationView lottieAnimationView = (LottieAnimationView)listItem.findViewById(R.id.loading_animation);
            TextView value = (TextView)listItem.findViewById(R.id.value);
            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            if(currentmessage.getMessagestatus().equals("downloading")){
                thumb.setVisibility(View.INVISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                download_button.setVisibility(View.GONE);
            }
            if(currentmessage.getMessagestatus().equals("not_downloaded")){
                lottieAnimationView.setVisibility(View.GONE);
                download_button.setVisibility(View.VISIBLE);
                thumb.setVisibility(View.VISIBLE);
            }
            messagevalue.setText(getFileName(currentmessage.getMessagevalue()));
            value.setText(getFileName(currentmessage.getMessagevalue()));
            time.setText(currentmessage.getTime());
            return listItem;
        }
        else if(getItemViewType(position)==TYPE_AUDIO_DOWNLOADED){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_audio_downloaded, parent, false);
            }
            TextView value = (TextView)listItem.findViewById(R.id.value);
            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            messagevalue.setText(getFileName(currentmessage.getMessagevalue()));
            value.setText(getFileName(currentmessage.getMessagevalue()));
            time.setText(currentmessage.getTime());
            return listItem;
        }
        else if(getItemViewType(position)==TYPE_DOCUMENT){
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.view_type_document, parent, false);
            }

            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            messagevalue.setText(currentmessage.getMessagevalue());
            time.setText(currentmessage.getTime());
            return listItem;
        }
        else{
            if (listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.messagelist, parent, false);
            }
            TextView messagevalue = (TextView) listItem.findViewById(R.id.messagevalue);
            TextView sender = (TextView) listItem.findViewById(R.id.sender);
            TextView time = (TextView) listItem.findViewById(R.id.timeview);
            LinearLayout linearLayout = (LinearLayout) listItem.findViewById(R.id.ll);
            LinearLayout lLayout = (LinearLayout) listItem.findViewById(R.id.rootview);
            if (currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.RIGHT);
                setMargins(linearLayout, 100, 8, 8, 8);
                linearLayout.setBackgroundResource(R.drawable.chatrightbg);
                sender.setVisibility(View.GONE);
            }
            if (!currentmessage.getSender().equals(messages.getUsername())) {
                lLayout.setGravity(Gravity.LEFT);
                setMargins(linearLayout, 8, 8, 100, 8);
                sender.setVisibility(View.VISIBLE);
                sender.setText(currentmessage.getSender());
                linearLayout.setBackgroundResource(R.drawable.chatleftbg);
            }
            messagevalue.setText(currentmessage.getMessagevalue());
            time.setText(currentmessage.getTime());
            return listItem;
        }

    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public int getViewTypeCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        messages message = messageslist.get(position);
        if (message.messagetype.equals("image")) {
            return TYPE_IMAGE;
        } else if (message.messagetype.equals("video")) {
            return TYPE_VIDEO;
        } else if (message.messagetype.equals("audio")) {
            return TYPE_AUDIO;
        } else if (message.messagetype.equals("document")) {
            return TYPE_DOCUMENT;
        }else if(message.messagetype.equals("image_downloaded")){
            return TYPE_IMAGE_DOWNLOADED;
        }else if(message.messagetype.equals("video_downloaded")){
            return TYPE_VIDEO_DOWNLOADED;
        }else if(message.messagetype.equals("audio_downloaded")){
            return  TYPE_AUDIO_DOWNLOADED;
        }else if(message.messagetype.equals("document_downloaded")){
            return TYPE_DOCUMENT_DOWNLOADED;
        }
        else{
            return TYPE_TEXT;
        }
    }

    public String getFileName(String url){
        char r[] = url.toCharArray();
        String filename="";
        for(int i = 0 ; i < r.length ; i ++){
            if(r[i]=='/'){
                filename = "";
            }else{
                filename = filename+r[i];
            }
        }
        return filename;
    }
}



