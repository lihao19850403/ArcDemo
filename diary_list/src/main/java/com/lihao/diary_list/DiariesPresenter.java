package com.lihao.diary_list;

import android.app.Activity;


import com.lihao.diary_list.list.DiariesAdapter;
import com.lihao.en_common.data.DiariesRepository;
import com.lihao.en_common.model.Diary;
import com.lihao.en_common.source.DataCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 日记类Presenter。
 */
public class DiariesPresenter implements DiariesContract.Presenter {

    /** 日记列表视图。 */
    private final DiariesContract.View mView;

    /** 日记信息。 */
    private final DiariesRepository mDiariesRepository;

    /** 列表适配器。 */
    private DiariesAdapter mListAdapter;

    /** 要操作的日记信息。 */
    private Diary mDiary;

    public DiariesPresenter(@NonNull DiariesContract.View iView) {
        mDiariesRepository = DiariesRepository.getInstance();
        mView = iView;
    }

    @Override
    public void start() {
        initAdapter();
        loadDiaries();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadDiaries() {
        mDiariesRepository.getAll(new DataCallback<List<Diary>>() {
            @Override
            public void onSuccess(List<Diary> diaries) {
                if (!mView.isActive()) {
                    return;
                }
                updateDiaries(diaries);
            }

            @Override
            public void onError() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showError();
            }
        });
    }

    @Override
    public void addDiary() {
        mView.gotoWriteDiary();
    }

    @Override
    public void updateDiary(@NonNull Diary diary) {
        mDiary = diary;
        mView.gotoUpdateDiary(diary.getId());
    }

    @Override
    public void onInputDialogClick(String desc) {
        mDiary.setDescription(desc);
        mDiariesRepository.update(mDiary);
        loadDiaries();
    }

    @Override
    public void onResult(int requestCode, int resultCode) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        mView.showSuccess();
    }

    private void initAdapter() {
        mListAdapter = new DiariesAdapter(new ArrayList<Diary>());
        mListAdapter.setOnLongClickListener((v, data) -> {
            updateDiary(data); // 更新日记。
            return false;
        });
        mView.setListAdapter(mListAdapter);
    }

    private void updateDiaries(List<Diary> diaries) {
//        for (Diary diary : diaries) {
//            diary.registerObserver(this);
//        }
        mListAdapter.update(diaries);
    }
}
