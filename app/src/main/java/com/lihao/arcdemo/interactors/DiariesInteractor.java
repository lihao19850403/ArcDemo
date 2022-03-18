package com.lihao.arcdemo.interactors;

import com.lihao.arcdemo.presenter.DiariesContract;
import com.lihao.arcdemo.usecases.GetAllDiariesUseCase;

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
