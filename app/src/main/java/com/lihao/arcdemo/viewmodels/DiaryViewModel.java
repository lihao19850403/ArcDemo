package com.lihao.arcdemo.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import com.lihao.arcdemo.models.DataCallback;
import com.lihao.arcdemo.models.DataSource;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;
import com.lihao.arcdemo.views.DiaryEditFragment;

import androidx.lifecycle.AndroidViewModel;

public class DiaryViewModel extends AndroidViewModel {

    /** 数据源。 */
    private final DataSource<Diary> mDiariesRepository;

    /** 视图。 */
    private DiaryEditFragment mView;

    /** 日记ID。 */
    private String mDiaryId;

    public DiaryViewModel(Application context) {
        super(context);
        mDiariesRepository = DiariesRepository.getInstance();
    }

    public void start() {
        requestDiary();
    }

    public void saveDiary(String title, String description) {
        if (isAddDiary()) {
            createDiary(title, description);
        } else {
            updateDiary(title, description);
        }
    }

    public void requestDiary() {
        if (isAddDiary()) {
            return;
        }
        mDiariesRepository.get(mDiaryId, new DataCallback<Diary>() {
            @Override
            public void onSuccess(Diary diary) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setTitle(diary.getTitle());
                mView.setDescription(diary.getDescription());
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

    public void setView(DiaryEditFragment view) {
        mView = view;
    }

    public void setDiaryId(String diaryId) {
        mDiaryId = diaryId;
    }

    private boolean isAddDiary() {
        return TextUtils.isEmpty(mDiaryId);
    }

    private void createDiary(String title, String description) {
        Diary newDiary = new Diary(title, description);
        mDiariesRepository.update(newDiary);
        mView.showDiariesList();
    }

    private void updateDiary(String title, String description) {
        Diary diary = new Diary(mDiaryId, title, description);
        mDiariesRepository.update(diary);
        mView.showDiariesList();
    }
}
