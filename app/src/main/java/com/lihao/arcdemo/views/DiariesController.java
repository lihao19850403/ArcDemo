package com.lihao.arcdemo.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.lihao.arcdemo.R;
import com.lihao.arcdemo.controllers.BaseController;
import com.lihao.arcdemo.presenter.DiariesAdapter;
import com.lihao.arcdemo.presenter.DiariesContract;
import com.lihao.arcdemo.presenter.DiaryEditPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiariesController extends BaseController implements DiariesContract.View {

    private DiariesContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    @Override
    public void setPresenter(DiariesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View root = inflater.inflate(R.layout.controller_diaries, container, false);
        mRecyclerView = root.findViewById(R.id.diaries_list);
        initDiariesList();
        ImageView addDiaries = getActivity().findViewById(R.id.control_diaries);
        addDiaries.setOnClickListener(v -> mPresenter.addDiary());
        setActive(true);
        ((TextView) getActivity().findViewById(R.id.toolbar_title)).setText(R.string.app_name);
        ((ImageView) getActivity().findViewById(R.id.control_diaries)).setImageResource(android.R.drawable.ic_input_add);
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void gotoWriteDiary() {
        DiaryEditController diaryEditController = new DiaryEditController("");
        diaryEditController.setTargetController(this);
        diaryEditController.setPresenter(new DiaryEditPresenter("", diaryEditController));
        getRouter().pushController(RouterTransaction.with(diaryEditController));
    }

    @Override
    public void gotoUpdateDiary(final String diaryId) {
        DiaryEditController diaryEditController = new DiaryEditController(diaryId);
        diaryEditController.setTargetController(this);
        diaryEditController.setPresenter(new DiaryEditPresenter(diaryId, diaryEditController));
        getRouter().pushController(RouterTransaction.with(diaryEditController));
    }

    @Override
    public void showSuccess() {
        showMessage(getResources().getString(R.string.success));
    }

    @Override
    public void showError() {
        showMessage(getResources().getString(R.string.error));
    }

    @Override
    public void setListAdapter(DiariesAdapter diariesAdapter) {
        mRecyclerView.setAdapter(diariesAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onResult(requestCode, resultCode);
    }

    private void initDiariesList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
