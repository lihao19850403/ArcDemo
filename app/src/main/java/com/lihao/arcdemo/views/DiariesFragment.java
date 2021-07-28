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
import com.lihao.arcdemo.databinding.FragmentDiariesBinding;
import com.lihao.arcdemo.viewmodels.DiariesAdapter;
import com.lihao.arcdemo.viewmodels.DiariesViewModel;
import com.lihao.arcdemo.livedatas.ToastInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DiariesFragment extends Fragment implements BaseView<DiariesViewModel> {

    private DiariesViewModel mViewModel;

    private FragmentDiariesBinding mDiariesBinding;

    @Override
    public void setViewModel(DiariesViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initToast();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDiariesBinding = FragmentDiariesBinding.inflate(inflater, container, false);
        mDiariesBinding.setViewModel(mViewModel);
        mDiariesBinding.setLayoutManager(new LinearLayoutManager(getContext()));
        initDiariesList();

        ImageView addDiaries = getActivity().findViewById(R.id.add_diaries);
        addDiaries.setOnClickListener(v -> mViewModel.addDiary());
        return mDiariesBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    public void gotoWriteDiary() {
        Intent intent = new Intent(getContext(), DiaryEditActivity.class);
        getActivity().startActivityForResult(intent, 100);
    }

    public void gotoUpdateDiary(final String diaryId) {
        Intent intent = new Intent(getContext(), DiaryEditActivity.class);
        intent.putExtra(DiaryEditFragment.DIARY_ID, diaryId);
        getActivity().startActivityForResult(intent, 100);
    }

    public void showSuccess() {
        showMessage(getString(R.string.success));
    }

    public void setListAdapter(DiariesAdapter diariesAdapter) {
        mDiariesBinding.diariesList.setAdapter(diariesAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onResult(requestCode, resultCode);
    }

    private void initDiariesList() {
        mDiariesBinding.diariesList.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mDiariesBinding.diariesList.setItemAnimator(new DefaultItemAnimator());
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        mViewModel.listAdapter.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                setListAdapter(mViewModel.listAdapter.get());
            }
        });
    }

    private void initToast() {
        mViewModel.getToastInfo().observe(this, (ToastInfo.ToastObserver) toastInfo -> showMessage(toastInfo));
    }
}
