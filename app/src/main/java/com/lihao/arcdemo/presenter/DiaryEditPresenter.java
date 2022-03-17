package com.lihao.arcdemo.presenter;

import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;
import com.lihao.arcdemo.usecases.GetDiaryUseCase;
import com.lihao.arcdemo.usecases.UpdateDiaryUseCase;
import com.lihao.arcdemo.usecases.UseCase;

import androidx.annotation.NonNull;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    /** 视图。 */
    private final DiaryEditContract.View mView;

    /** 日记ID。 */
    private String mDiaryId;

    private UpdateDiaryUseCase mUpdateDiaryUseCase = new UpdateDiaryUseCase();

    private GetDiaryUseCase mGetDiaryUseCase = new GetDiaryUseCase();

    public DiaryEditPresenter(@NonNull String diaryId, @NonNull DiaryEditContract.View view) {
        mView = view;
        mDiaryId = diaryId;
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
        mUpdateDiaryUseCase.setRequestValues(new UpdateDiaryUseCase.RequestValues(DiariesRepository.getInstance(), mDiaryId, title, description))
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
        mGetDiaryUseCase.setRequestValues(new GetDiaryUseCase.RequestValues(mDiaryId, DiariesRepository.getInstance()))
                .setUseCaseCallback(new UseCase.UseCaseCallback<Diary>() {
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
