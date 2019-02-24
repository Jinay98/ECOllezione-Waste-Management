package com.example.android.wastemanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class PostAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView name, message;
    ImageView userImg, postImg;
    FirebaseAuth auth;
    Context context;

    public PostAdapterViewHolder(View itemView) {
        super(itemView);
        auth = FirebaseAuth.getInstance();
        message = itemView.findViewById(R.id.comment);
        name = itemView.findViewById(R.id.userName);
        userImg = itemView.findViewById(R.id.profileImg);
        postImg = itemView.findViewById(R.id.postImg);
    }

    public void setContext(Context context){
        this.context = context;
    }
    public void setName(String username){
        name.setText(username);
    }
    public void setMessage(String postMessage){
        message.setText(postMessage);
    }
    public void setUserImage(String image){
        Glide.with(context).load(image).into(userImg);
    }
    public void setPostImg(String postimage){
        Glide.with(context).load(postimage).into(postImg);
    }
}
