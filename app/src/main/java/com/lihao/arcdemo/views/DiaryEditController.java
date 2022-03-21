package com.lihao.arcdemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lihao.arcdemo.R;
import com.lihao.arcdemo.controllers.BaseController;
import com.lihao.arcdemo.presenter.DiaryEditContract;

import androidx.annotation.NonNull;

public class DiaryEditController extends BaseController implements DiaryEditContract.View {

    public static final String DIARY_ID = "DIARY_ID";

    private DiaryEditContract.Presenter mPresenter;

    private TextView mTitle;
    private TextView mDescription;

    private String mDiaryId;

    public DiaryEditController(String diaryId) {
        Bundle bundle = new Bundle();
        bundle.putString(DIARY_ID, diaryId);
        mDiaryId = diaryId;
    }

    public DiaryEditController(Bundle args) {
        mDiaryId = args.getString(DIARY_ID);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View root = inflater.inflate(R.layout.controller_diary_edit, container, false);
        mTitle = root.findViewById(R.id.edit_title);
        mDescription = root.findViewById(R.id.edit_description);
        ImageView confirmBtn = getActivity().findViewById(R.id.control_diaries);
        confirmBtn.setOnClickListener(v -> done());
        setActive(true);
        setToolbarTitle(TextUtils.isEmpty(mDiaryId));
        setToolbarImage(TextUtils.isEmpty(mDiaryId));
        return root;
    }

    private void setToolbarTitle(boolean isAdd) {
        Activity activity = getActivity();
        TextView titleView = activity.findViewById(R.id.toolbar_title);
        if (isAdd) {
            titleView.setText(R.string.add_diary);
        } else {
            titleView.setText(R.string.edit_diary);
        }
    }

    private void setToolbarImage(boolean isAdd) {
        Activity activity = getActivity();
        ImageView imageView = activity.findViewById(R.id.control_diaries);
        if (isAdd) {
            imageView.setImageResource(android.R.drawable.ic_input_add);
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_edit);
        }
    }

    @Override
    public void setPresenter(DiaryEditContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDiariesList() {
        getRouter().popController(this);
    }

    /**
     * 完成日记的修改。
     */
    public void done() {
        mPresenter.saveDiary(mTitle.getText().toString(), mDescription.getText().toString());
    }
}
