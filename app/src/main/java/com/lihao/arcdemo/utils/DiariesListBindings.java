package com.lihao.arcdemo.utils;

import com.lihao.arcdemo.models.Diary;
import com.lihao.arcdemo.viewmodels.DiariesAdapter;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class DiariesListBindings {

    @BindingAdapter("data")
    public static void setData(RecyclerView recyclerView, List<Diary> data) {
        DiariesAdapter adapter = (DiariesAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.update(data);
    }
}
