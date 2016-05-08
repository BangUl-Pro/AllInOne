package com.ironfactory.allinoneenglish.controllers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private static final String TAG = "MainAdapter";

    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.textView.setText(Global.files.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.files.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.item_main_text);
        }
    }
}
