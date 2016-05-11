package com.ironfactory.allinoneenglish.controllers.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.activities.StudyClassActivity;
import com.ironfactory.allinoneenglish.utils.VLCUtils;
import com.ironfactory.allinoneenglish.utils.VideoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class StudyClassAdapter extends RecyclerView.Adapter<StudyClassAdapter.StudyViewHolder> {

    private static final String TAG = "StudyClassAdapter";

    private Context context;
    private int position;
    private int mPosition = 0;
    private OnPlayVideo sender;

    public StudyClassAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        if (position != 0)
            mPosition = position * 41 - position + 1;
        sender = (OnPlayVideo) context;
    }

    @Override
    public StudyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_class, parent, false);
        return new StudyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.background_mine_soft));
            holder.numText.setTextColor(context.getResources().getColor(R.color.background_mine_soft));
        } else {
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.background_mine_hard));
            holder.numText.setTextColor(context.getResources().getColor(R.color.background_mine_hard));
        }

        final int coursePosition = position + mPosition;
        StringBuilder sb = new StringBuilder();
        sb.append(position + 1);
        if (coursePosition < 10)
            sb.insert(0, 0);
        holder.numText.setText(sb.toString());
        holder.titleText.setText(Global.files.get(coursePosition).getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Log.d(TAG, "time = " + Global.courses.get(coursePosition).getLastStudyDate().getTime());
        if (Global.courses.get(coursePosition).getLastStudyDate().getTime() == 0)
            holder.lastStudyDayText.setVisibility(View.INVISIBLE);
        else {
            holder.lastStudyDayText.setVisibility(View.VISIBLE);
            holder.lastStudyDayText.setText("최종학습일 " + format.format(Global.courses.get(coursePosition).getLastStudyDate()).toString());
        }
        holder.playText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VideoUtils videoUtils = new VideoUtils();
                sender.onPlay();
                final Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        videoUtils.setPosition(coursePosition);
                        videoUtils.decryptVideo();
                        final VLCUtils vlcUtils = new VLCUtils(context);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                sender.onStopPlay();
                                vlcUtils.playVideo(videoUtils.getVideoDecryptPath());

                                StudyClassActivity.filePath = videoUtils.getVideoDecryptPath();
                            }
                        });
                        Global.courses.get(position).setLastStudyDate(new Date(System.currentTimeMillis()));
                        Global.dbManager.updateCourse(Global.courses.get(position));
                    }
                }).start();
            }
        });

        if (Global.courses.get(coursePosition).isBookmark()) {
            holder.bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark);
        } else {
            holder.bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark_blank);
        }

        holder.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB 수정 해야함
                if (Global.courses.get(coursePosition).isBookmark()) {
                    holder.bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark_blank);
                    Global.courses.get(coursePosition).setBookmark(false);
                } else {
                    holder.bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark);
                    Global.courses.get(coursePosition).setBookmark(true);
                }
                Global.dbManager.updateCourse(Global.courses.get(coursePosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (position == 0)
            return 41;
        return 40;
    }

    public class StudyViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout container;
        final TextView numText;
        final TextView titleText;
        final TextView lastStudyDayText;
        final TextView playText;
        final ImageButton bookmarkBtn;

        public StudyViewHolder(View itemView) {
            super(itemView);

            container = (LinearLayout) itemView.findViewById(R.id.item_study_class_container);
            numText = (TextView) itemView.findViewById(R.id.item_study_class_num);
            titleText = (TextView) itemView.findViewById(R.id.item_study_class_title);
            lastStudyDayText = (TextView) itemView.findViewById(R.id.item_study_class_last_study_day);
            playText = (TextView) itemView.findViewById(R.id.item_study_class_play);
            bookmarkBtn = (ImageButton) itemView.findViewById(R.id.item_study_class_bookmark);
        }
    }

    public interface OnPlayVideo {
        void onPlay();
        void onStopPlay();
    }
}
