package com.ironfactory.allinoneenglish.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.activities.StudyClassActivity;
import com.ironfactory.allinoneenglish.controllers.activities.WebActivity;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class StudyAdapter extends RecyclerView.Adapter {

    private static final String TAG = "StudyAdapter";

    private Context context;

    public StudyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study, parent, false);
            return new StudyViewHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_link, parent, false);
            return new StudyLinkViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_ad, parent, false);
            return new StudyAdViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 4)
            return 0;
        else if (position == 4)
            return 1;
        else
            return 2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, final int position) {
        if (mHolder instanceof StudyViewHolder) {
            StudyViewHolder holder = (StudyViewHolder) mHolder;
            if (position % 2 == 0) {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.main));
            } else {
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.banner2_background));
            }
            final int POSITION = position + 1;
            holder.titleText.setText("올인원 기초 " + POSITION + " (40강)");
            holder.dateText.setText("DAY " + (position * 10 + 1) + " ~ DAY " + POSITION * 10 + "");
            holder.updateText.setText("업데이트 날짜 2016.06.01");
            holder.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudyClassActivity.class);
                    intent.putExtra(Global.POSITION, position);
                    context.startActivity(intent);
                }
            });
        } else if (mHolder instanceof StudyLinkViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class StudyViewHolder extends RecyclerView.ViewHolder {

        final TextView titleText;
        final TextView dateText;
        final TextView updateText;
        final Button playBtn;
        final CardView cardView;

        public StudyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.item_study_card);
            titleText = (TextView) itemView.findViewById(R.id.item_study_title);
            dateText = (TextView) itemView.findViewById(R.id.item_study_date);
            updateText = (TextView) itemView.findViewById(R.id.item_study_update);
            playBtn = (Button) itemView.findViewById(R.id.item_study_play);

        }
    }

    public class StudyLinkViewHolder extends RecyclerView.ViewHolder {

        final Button qnaBtn;
        final Button questionBtn;
        final Button afterBtn;

        public StudyLinkViewHolder(View itemView) {
            super(itemView);

            qnaBtn = (Button) itemView.findViewById(R.id.fragment_study_link_qna);
            questionBtn = (Button) itemView.findViewById(R.id.fragment_study_link_question);
            afterBtn = (Button) itemView.findViewById(R.id.fragment_study_link_after);

            qnaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (!wifi.isConnected()) {
                        Toast toast =  Toast.makeText(context, "(인터넷) 와이파이를 연결해주세요", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 100, 100);
                        toast.show();
                    } else {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(Global.TYPE, Global.QNA);
                        context.startActivity(intent);
                    }
                }
            });

            questionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (!wifi.isConnected()) {
                        Toast toast =  Toast.makeText(context, "(인터넷) 와이파이를 연결해주세요", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 100, 100);
                        toast.show();
                    } else {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(Global.TYPE, Global.QUESTION);
                        context.startActivity(intent);
                    }
                }
            });

            afterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (!wifi.isConnected()) {
                        Toast toast =  Toast.makeText(context, "(인터넷) 와이파이를 연결해주세요", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 100, 100);
                        toast.show();
                    } else {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(Global.TYPE, Global.AFTER);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public class StudyAdViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final TextView text1;

        public StudyAdViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_study_ad_title);
            text1 = (TextView) itemView.findViewById(R.id.item_study_ad_1);

            title.setText("올인원 Q&A");
            text1.setText("☞ Wifi 미연결 상태에서는 수강후기, 학습질문, Q&A 서비스를 사용하실 수 없습니다.");
        }
    }
}
