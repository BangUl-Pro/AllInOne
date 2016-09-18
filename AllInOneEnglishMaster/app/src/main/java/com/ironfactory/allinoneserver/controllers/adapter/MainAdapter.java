package com.ironfactory.allinoneserver.controllers.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.ironfactory.allinoneserver.R;
import com.ironfactory.allinoneserver.entities.UserEntity;
import com.ironfactory.allinoneserver.networks.RequestListener;
import com.ironfactory.allinoneserver.networks.SocketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IronFactory on 2016. 4. 25..
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private static final String TAG = "MainAdapter";

    private List<UserEntity> userEntities;

    public MainAdapter() {
        userEntities = new ArrayList<>();
    }

    public MainAdapter(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {
        holder.idView.setText(userEntities.get(position).getId());
        if (userEntities.get(position).getAccessable() == UserEntity.UNACCESSABLE) {
            holder.accessableSwitch.setChecked(false);
        } else {
            holder.accessableSwitch.setChecked(true);
        }

        holder.accessableSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int access;
                if (holder.accessableSwitch.isChecked())
                    access = UserEntity.ACCESSABLE;
                else
                    access = UserEntity.UNACCESSABLE;
                Log.d(TAG, "바뀜?");
                SocketManager.setAccessable(userEntities.get(position).getId(), access, new RequestListener.OnSetAccessable() {
                    @Override
                    public void onSuccess() {
                        userEntities.get(position).setAccessable(access);
                    }

                    @Override
                    public void onException(int code) {
                        Log.d(TAG, "사용권한 설정 실패");
                    }
                });
            }
        });
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        final TextView idView;
        final Switch accessableSwitch;

        public MainViewHolder(View itemView) {
            super(itemView);

            idView = (TextView) itemView.findViewById(R.id.item_user_id);
            accessableSwitch = (Switch) itemView.findViewById(R.id.item_user_accessable);
        }
    }
}
