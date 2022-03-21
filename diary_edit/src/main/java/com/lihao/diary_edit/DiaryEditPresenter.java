package com.lihao.diary_edit;

import com.lihao.diary_edit.interactors.DiariesEditInteractor;
import com.lihao.diary_edit.usecases.GetDiaryUseCase;
import com.lihao.diary_edit.usecases.UpdateDiaryUseCase;
import com.lihao.en_common.data.DiariesRepository;
import com.lihao.en_common.model.Diary;
import com.lihao.en_common.usecase.UseCase;

import androidx.annotation.NonNull;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    /** 视图。 */
    private final DiaryEditContract.View mView;

    /** 日记ID。 */
    private String mDiaryId;

    private DiariesEditInteractor mInteractor;

    public DiaryEditPresenter(@NonNull String diaryId, @NonNull DiaryEditContract.View view, DiariesEditInteractor interactor) {
        mView = view;
        mDiaryId = diaryId;
        mInteractor = interactor;
    }

    @Override
    public void start() {
        requestDiary();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void saveDiary(String title, String description) {
        mInteractor.updateDiary().setRequestValues(new UpdateDiaryUseCase.RequestValues(DiariesRepository.getInstance(), mDiaryId, title, description))
                .setUseCaseCallback(new UseCase.UseCaseCallback<Void>() {

                    @Override
                    public void onSuccess(Void response) {
                        mView.showDiariesList();
                    }

                    @Override
                    public void onError() {

                    }
                })
                .run();
    }

    @Override
    public void requestDiary() {
        mInteractor.getDiary().setRequestValues(new GetDiaryUseCase.RequestValues(mDiaryId, DiariesRepository.getInstance()))
                .setUseCaseCallback(new UseCase.UseCaseCallback<Diary>(){

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
                })
                .run();
    }
}