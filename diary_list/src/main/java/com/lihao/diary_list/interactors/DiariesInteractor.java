package com.lihao.diary_list.interactors;

import com.lihao.diary_list.DiariesContract;
import com.lihao.diary_list.usecases.GetAllDiariesUseCase;

public class DiariesInteractor implements DiariesContract.Interactor {

    private GetAllDiariesUseCase getAllDiariesUseCase;

    @Override
    public GetAllDiariesUseCase getAll() {
        if (getAllDiariesUseCase == null) {
            synchronized (this) {
                if (getAllDiariesUseCase == null) {
                    getAllDiariesUseCase = new GetAllDiariesUseCase();
                }
            }
        }
        return getAllDiariesUseCase;
    }
}
