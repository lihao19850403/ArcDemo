package com.lihao.arcdemo.presenter;

import android.app.Activity;

import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;
import com.lihao.arcdemo.usecases.UseCase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 日记类Presenter。
 */
public class DiariesPresenter implements DiariesContract.Presenter {

    /** 日记列表视图。 */
    private final DiariesContract.View mView;

    private DiariesContract.Interactor mInteractor;

    private DiariesContract.Router mRouter;

    /** 列表适配器。 */
    private DiariesAdapter mListAdapter;

    /** 要操作的日记信息。 */
    private Diary mDiary;

    public DiariesPresenter(@NonNull DiariesContract.View iView, DiariesContract.Interactor interactor, DiariesContract.Router router) {
        mView = iView;
        mInteractor = interactor;
        mRouter = router;
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
        mInteractor.getAll().setRequestValues(DiariesRepository.getInstance())
                .setUseCaseCallback(new UseCase.UseCaseCallback<List<Diary>>(){

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
                })
                .run();
    }

    @Override
    public void addDiary() {
        mRouter.gotoWriteDiary();
    }

    @Override
    public void updateDiary(@NonNull Diary diary) {
        mDiary = diary;
        mRouter.gotoUpdateDiary(diary.getId());
    }

    @Override
    public void onInputDialogClick(String desc) {
        mDiary.setDescription(desc);
        DiariesRepository.getInstance().update(mDiary);
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
        mListAdapter.update(diaries);
    }
}
