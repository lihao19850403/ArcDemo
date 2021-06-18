package com.lihao.arcdemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lihao.arcdemo.DiaryEditActivity;
import com.lihao.arcdemo.R;
import com.lihao.arcdemo.presenter.DiariesAdapter;
import com.lihao.arcdemo.presenter.DiariesContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiariesFragment extends Fragment implements DiariesContract.View {

    private DiariesContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    @Override
    public void setPresenter(DiariesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diaries, container, false);
        mRecyclerView = root.findViewById(R.id.diaries_list);
        initDiariesList();
        ImageView addDiaries = getActivity().findViewById(R.id.add_diaries);
        addDiaries.setOnClickListener(v -> mPresenter.addDiary());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void gotoWriteDiary() {
        Intent intent = new Intent(getContext(), DiaryEditActivity.class);
        getActivity().startActivityForResult(intent, 100);
    }

    @Override
    public void gotoUpdateDiary(final String diaryId) {
        Intent intent = new Intent(getContext(), DiaryEditActivity.class);
        intent.putExtra(DiaryEditFragment.DIARY_ID, diaryId);
        getActivity().startActivityForResult(intent, 100);
    }

    @Override
    public void showSuccess() {
        showMessage(getString(R.string.success));
    }

    @Override
    public void showError() {
        showMessage(getString(R.string.error));
    }

    @Override
    public boolean isActive() {
        return isAdded();
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
