package com.unbeatable.vidhyadaan.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unbeatable.vidhyadaan.R;
import com.unbeatable.vidhyadaan.firebasemodle.User;

/**
 * Created by Rakshit on 20-09-2016 at 15:13.
 */

public class UserVH extends RecyclerView.ViewHolder {

    TextView tvOtherUserName, tvStdOtherUser;
    CardView cardOtherUser;
    Context context;
    User user;

    private UserVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvOtherUserName = (TextView) itemView.findViewById(R.id.tv_otherUserName);
        tvStdOtherUser = (TextView) itemView.findViewById(R.id.tv_stdOtherUser);
        cardOtherUser = (CardView) itemView.findViewById(R.id.card_otherUser);
    }

    public static UserVH create(Context context, ViewGroup parent) {
        return new UserVH(context, LayoutInflater.from(context).inflate(R.layout.single_view_user_row, parent, false));
    }

    public static void bind(UserVH vh, User user) {
        vh.user = user;
        //vh.tvOtherUserName.setText(user.);
    }
}
