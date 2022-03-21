package com.lihao.diary_list.usecases;

import com.lihao.en_common.data.DiariesRepository;
import com.lihao.en_common.model.Diary;
import com.lihao.en_common.source.DataCallback;
import com.lihao.en_common.usecase.UseCase;

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
