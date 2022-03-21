package com.lihao.diary_edit.interactors;

import com.lihao.diary_edit.DiaryEditContract;
import com.lihao.diary_edit.usecases.GetDiaryUseCase;
import com.lihao.diary_edit.usecases.UpdateDiaryUseCase;

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
