package com.lihao.arcdemo.presenter;

import android.app.Activity;

import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 日记类Presenter。
 */
public class DiariesPresenter implements DiariesContract.Presenter {

    private CompositeDisposable mCompositeDisposable;

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
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        initAdapter();
        loadDiaries();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadDiaries() {
        mCompositeDisposable.clear();
        Disposable disposable = mDiariesRepository.getAll()
                .flatMap(Flowable::fromIterable)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDiaries, error -> mView.showError());
        mCompositeDisposable.add(disposable);
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
