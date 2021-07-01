package com.lihao.arcdemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lihao.arcdemo.R;
import com.lihao.arcdemo.databinding.FragmentDiaryEditBinding;
import com.lihao.arcdemo.viewmodels.DiaryViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiaryEditFragment extends Fragment implements BaseView<DiaryViewModel> {

    public static final String DIARY_ID = "DIARY_ID";

    private DiaryViewModel mViewModel;

    private FragmentDiaryEditBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDiaryEditBinding.inflate(inflater, container, false);

        ImageView confirmBtn = getActivity().findViewById(R.id.confirm_work);
        confirmBtn.setOnClickListener(v -> done());
        return mBinding.getRoot();
    }

    @Override
    public void setViewModel(DiaryViewModel viewmodel) {
        mViewModel = viewmodel;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.start();
    }

    public boolean isActive() {
        return isAdded();
    }

    public void setTitle(String title) {
        mBinding.editTitle.setText(title);
    }

    public void setDescription(String description) {
        mBinding.editDescription.setText(description);
    }

    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    public void showDiariesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    /**
     * 完成日记的修改。
     */
    public void done() {
        mViewModel.saveDiary(mBinding.editTitle.getText().toString(), mBinding.editDescription.getText().toString());
    }
}
