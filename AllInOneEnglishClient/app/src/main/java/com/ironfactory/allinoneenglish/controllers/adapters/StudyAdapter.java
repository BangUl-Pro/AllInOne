package com.ironfactory.allinoneenglish.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.activities.StudyClassActivity;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.StudyViewHolder> {

    private static final String TAG = "StudyAdapter";

    private Context context;

    public StudyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public StudyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study, parent, false);
        return new StudyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudyViewHolder holder, final int position) {
//        holder.titleImage.setImageResource();
        if (position % 2 == 0) {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.main));
        } else {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.banner2_background));
        }
        final int POSITION = position + 1;
        holder.titleText.setText("올인원 기초 " + POSITION + " (40강)");
        holder.dateText.setText("DAY " + POSITION + position * 10 + " ~ DAY " + POSITION * 10 + "");
        holder.updateText.setText("업데이트 날짜 2016.01.01");
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudyClassActivity.class);
                intent.putExtra(Global.POSITION, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class StudyViewHolder extends RecyclerView.ViewHolder {

        final ImageView titleImage;
        final TextView titleText;
        final TextView dateText;
        final TextView updateText;
        final Button playBtn;
        final CardView cardView;

        public StudyViewHolder(View itemView) {
            super(itemView);
            
            cardView = (CardView) itemView.findViewById(R.id.item_study_card);
            titleImage = (ImageView) itemView.findViewById(R.id.item_study_title_image);
            titleText = (TextView) itemView.findViewById(R.id.item_study_title);
            dateText = (TextView) itemView.findViewById(R.id.item_study_date);
            updateText = (TextView) itemView.findViewById(R.id.item_study_update);
            playBtn = (Button) itemView.findViewById(R.id.item_study_play);
            
        }
    }
}
