package com.lihao.arcdemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lihao.arcdemo.R;
import com.lihao.arcdemo.presenter.DiaryEditContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiaryEditFragment extends Fragment implements DiaryEditContract.View {

    public static final String DIARY_ID = "DIARY_ID";

    private DiaryEditContract.Presenter mPresenter;

    private TextView mTitle;
    private TextView mDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diary_edit, container, false);
        mTitle = root.findViewById(R.id.edit_title);
        mDescription = root.findViewById(R.id.edit_description);
        ImageView confirmBtn = getActivity().findViewById(R.id.confirm_work);
        confirmBtn.setOnClickListener(v -> done());
        return root;
    }

    @Override
    public void setPresenter(DiaryEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDiariesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    /**
     * 完成日记的修改。
     */
    public void done() {
        mPresenter.saveDiary(mTitle.getText().toString(), mDescription.getText().toString());
    }
}
