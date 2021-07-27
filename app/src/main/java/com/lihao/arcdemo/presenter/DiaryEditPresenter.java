package com.lihao.arcdemo.presenter;

import android.text.TextUtils;

import com.lihao.arcdemo.models.DataSource;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    private CompositeDisposable mCompositeDisposable;

    /** 数据源。 */
    private final DataSource<Diary> mDiariesRepository;

    /** 视图。 */
    private final DiaryEditContract.View mView;

    /** 日记ID。 */
    private String mDiaryId;

    public DiaryEditPresenter(@NonNull String diaryId, @NonNull DiaryEditContract.View view) {
        mDiariesRepository = DiariesRepository.getInstance();
        mView = view;
        mDiaryId = diaryId;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        requestDiary();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void saveDiary(String title, String description) {
        if (isAddDiary()) {
            createDiary(title, description);
        } else {
            updateDiary(title, description);
        }
    }

    @Override
    public void requestDiary() {
        if (isAddDiary()) {
            return;
        }
        mCompositeDisposable.clear();
        Disposable disposable = mDiariesRepository.get(mDiaryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDiaryUI, error -> mView.showError());
        mCompositeDisposable.add(disposable);
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

    /**
     * 更新日记页面数据。注：只更新UI。
     * @param diary 包含新数据的diary实例。
     */
    private void updateDiaryUI(Diary diary) {
        mView.setTitle(diary.getTitle());
        mView.setDescription(diary.getDescription());
    }
}
