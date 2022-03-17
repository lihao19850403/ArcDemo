package com.lihao.arcdemo.usecases;

import android.text.TextUtils;

import com.lihao.arcdemo.models.DataCallback;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;

public class GetDiaryUseCase extends UseCase<GetDiaryUseCase.RequestValues, Diary> {

    public static class RequestValues implements UseCase.RequestValues {

        private String diaryId;

        private DiariesRepository diariesRepository;

        public RequestValues(String diaryId, DiariesRepository diariesRepository) {
            this.diaryId = diaryId;
            this.diariesRepository = diariesRepository;
        }
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (isAddDiary()) {
            return;
        }
        getRequestValues().diariesRepository.get(getRequestValues().diaryId, new DataCallback<Diary>() {
            @Override
            public void onSuccess(Diary diary) {
                getUseCaseCallback().onSuccess(diary);
            }

            @Override
            public void onError() {
                getUseCaseCallback().onError();
            }
        });
    }

    private boolean isAddDiary() {
        return TextUtils.isEmpty(getRequestValues().diaryId);
    }
}
