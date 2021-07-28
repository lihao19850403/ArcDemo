package com.lihao.arcdemo.db.daos;

import com.lihao.arcdemo.models.Diary;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DiaryDao {

    @Query("SELECT * FROM diary")
    List<Diary> getAll();

    @Query(("SELECT * FROM diary WHERE diaryId = :id"))
    Diary get(String id);

    @Update
    int update(Diary diary);

    @Query("DELETE FROM diary")
    void deleteAll();

    @Query("DELETE FROM diary WHERE diaryId = :id")
    int delete(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Diary diary);
}
