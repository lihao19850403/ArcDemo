package com.lihao.arcdemo.interactors;

import com.lihao.arcdemo.presenter.DiaryEditContract;
import com.lihao.arcdemo.usecases.GetDiaryUseCase;
import com.lihao.arcdemo.usecases.UpdateDiaryUseCase;

public class DiariesEditInteractor implements DiaryEditContract.Interactor {

    private UpdateDiaryUseCase updateDiaryUseCase;

    private GetDiaryUseCase getDiaryUseCase;

    @Override
    public UpdateDiaryUseCase updateDiary() {
        if (updateDiaryUseCase == null) {
            synchronized (this) {
                if (updateDiaryUseCase == null) {
                    updateDiaryUseCase = new UpdateDiaryUseCase();
                }
            }
        }
        return updateDiaryUseCase;
    }

    @Override
    public GetDiaryUseCase getDiary() {
        if (getDiaryUseCase == null) {
            synchronized (this) {
                if (getDiaryUseCase == null) {
                    getDiaryUseCase = new GetDiaryUseCase();
                }
            }
        }
        return getDiaryUseCase;
    }
}
