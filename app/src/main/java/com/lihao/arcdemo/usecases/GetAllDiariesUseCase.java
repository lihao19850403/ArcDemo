package com.lihao.arcdemo.usecases;

import com.lihao.arcdemo.models.DataCallback;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;

import java.util.List;

public class GetAllDiariesUseCase extends UseCase<DiariesRepository, List<Diary>> {

    @Override
    protected void executeUseCase(DiariesRepository requestValues) {
        requestValues.getAll(new DataCallback<List<Diary>>() {
            @Override
            public void onSuccess(List<Diary> diaries) {
                getUseCaseCallback().onSuccess(diaries);
            }

            @Override
            public void onError() {
                getUseCaseCallback().onError();
            }
        });
    }
}
