package com.lihao.arcdemo.db;

import com.lihao.arcdemo.MApplication;
import com.lihao.arcdemo.db.daos.DiaryDao;
import com.lihao.arcdemo.models.Diary;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Diary.class}, version = 1, exportSchema = false)
public abstract class DBManager extends RoomDatabase {

    private static volatile DBManager mInstance;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE diary ADD COLUMN test1 INTEGER");
        }
    };

    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = getDatabase();
                }
            }
        }
        return mInstance;
    }

    public abstract DiaryDao diaryDao();

    @NonNull
    private static DBManager getDatabase() {
        return Room.databaseBuilder(MApplication.getInstance(), DBManager.class, "enDiary.db")
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}
