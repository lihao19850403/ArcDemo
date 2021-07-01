package com.lihao.arcdemo.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.lihao.arcdemo.models.Diary;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiariesAdapter extends RecyclerView.Adapter<DiaryHolder> {

    private List<Diary> mDiaries;

    private OnLongClickListener<Diary> mOnLongClickListener;

    /**
     * 长按事件接口。
     * @param <T> 监听接收的数据类型。
     */
    public interface OnLongClickListener<T> {

        boolean onLongClick(View v, T data);
    }

    public DiariesAdapter(List<Diary> diaries) {
        mDiaries = diaries;
    }

    @NonNull
    @Override
    public DiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryHolder holder, int position) {
        final Diary diary = mDiaries.get(position);
        holder.onBindView(diary);
        holder.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return mOnLongClickListener != null && mOnLongClickListener.onLongClick(v, diary);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaries.size();
    }

    public void update(List<Diary> diaries) {
        mDiaries = diaries;
        notifyDataSetChanged();
    }

    public void setOnLongClickListener(OnLongClickListener<Diary> onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }
}
