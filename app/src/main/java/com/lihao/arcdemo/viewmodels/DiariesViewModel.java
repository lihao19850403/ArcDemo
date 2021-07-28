package com.lihao.arcdemo.viewmodels;

import android.app.Activity;
import android.app.Application;

import com.lihao.arcdemo.MApplication;
import com.lihao.arcdemo.R;
import com.lihao.arcdemo.livedatas.ToastInfo;
import com.lihao.arcdemo.models.DataCallback;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;
import com.lihao.arcdemo.views.DiariesFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.AndroidViewModel;

/**
 * 日记类ViewModel。
 */
public class DiariesViewModel extends AndroidViewModel {

    /** 日记列表视图。 */
    private DiariesFragment mView;

    /** 日记信息。 */
    private final DiariesRepository mDiariesRepository;

    /** 要操作的日记信息。 */
    private Diary mDiary;

    /** Toast监听。 */
    private final ToastInfo mToastInfo = new ToastInfo();

    /** 日记列表。 */
    public final ObservableList<Diary> data = new ObservableArrayList<>();

    /** 日记Adapter。 */
    public final ObservableField<DiariesAdapter> listAdapter = new ObservableField<>();

    public DiariesViewModel(Application context) {
        super(context);
        mDiariesRepository = DiariesRepository.getInstance();
    }

    public void setView(DiariesFragment view) {
        mView = view;
    }

    public void start() {
        initAdapter();
        loadDiaries();
    }

    public void loadDiaries() {
        mDiariesRepository.getAll(new DataCallback<List<Diary>>() {
            @Override
            public void onSuccess(List<Diary> diaries) {
                updateDiaries(diaries);
            }

            @Override
            public void onError() {
                mToastInfo.setValue(MApplication.getInstance().getString(R.string.error));
            }
        });
    }

    public void addDiary() {
        mView.gotoWriteDiary();
    }

    public void updateDiary(@NonNull Diary diary) {
        mDiary = diary;
        mView.gotoUpdateDiary(diary.getId());
    }

    public void onInputDialogClick(String desc) {
        mDiary.setDescription(desc);
        mDiariesRepository.update(mDiary);
        loadDiaries();
    }

    public void onResult(int requestCode, int resultCode) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        mView.showSuccess();
    }

    public ToastInfo getToastInfo() {
        return mToastInfo;
    }

    private void initAdapter() {
        DiariesAdapter diariesAdapter = new DiariesAdapter(new ArrayList<Diary>());
        diariesAdapter.setOnLongClickListener((v, data) -> {
            updateDiary(data); // 更新日记。
            return false;
        });
        listAdapter.set(diariesAdapter);
    }

    private void updateDiaries(List<Diary> diaries) {
        data.clear();
        data.addAll(diaries);
    }
}
