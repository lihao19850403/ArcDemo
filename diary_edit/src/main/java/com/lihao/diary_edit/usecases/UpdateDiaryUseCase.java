package com.lihao.diary_edit.usecases;

import android.text.TextUtils;

import com.lihao.en_common.data.DiariesRepository;
import com.lihao.en_common.model.Diary;
import com.lihao.en_common.usecase.UseCase;

public class UpdateDiaryUseCase extends UseCase<UpdateDiaryUseCase.RequestValues, Void> {

    public static class RequestValues implements UseCase.RequestValues {

        private DiariesRepository diariesRepository;

        private String diaryId;

        private String title;

        private String description;

        public RequestValues(DiariesRepository diariesRepository, String diaryId, String title, String description) {
            this.diariesRepository = diariesRepository;
            this.diaryId = diaryId;
            this.title = title;
            this.description = description;
        }
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (isAddDiary()) {
            createDiary(getRequestValues().title, getRequestValues().description);
        } else {
            updateDiary(getRequestValues().title, getRequestValues().description);
        }
    }

    private boolean isAddDiary() {
        return TextUtils.isEmpty(getRequestValues().diaryId);
    }

    private void createDiary(String title, String description) {
        Diary newDiary = new Diary(title, description);
        getRequestValues().diariesRepository.update(newDiary);
        getUseCaseCallback().onSuccess(null);
    }

    private void updateDiary(String title, String description) {
        Diary diary = new Diary(getRequestValues().diaryId, title, description);
        getRequestValues().diariesRepository.update(diary);
        getUseCaseCallback().onSuccess(null);
    }
}
