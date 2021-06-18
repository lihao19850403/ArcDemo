package com.lihao.arcdemo.presenter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lihao.arcdemo.R;
import com.lihao.arcdemo.models.Diary;

public class DiaryHolder extends RecyclerViewHolder<Diary> {

    private View.OnLongClickListener mOnLongClickListener;

    public DiaryHolder(ViewGroup parent) {
        super(parent, R.layout.list_diary_item);
    }

    @Override
    public void onBindView(Diary diary) {
        super.onBindView(diary);
        TextView title = itemView.findViewById(R.id.title);
        title.setText(diary.getTitle());
        TextView desc = itemView.findViewById(R.id.desc);
        desc.setText(diary.getDescription());
        itemView.setOnLongClickListener(v -> mOnLongClickListener != null && mOnLongClickListener.onLongClick(v));
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }
}
