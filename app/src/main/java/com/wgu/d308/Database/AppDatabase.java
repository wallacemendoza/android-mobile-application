package com.wgu.d308.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wgu.d308.dao.ExcursionDAO;
import com.wgu.d308.dao.VacationDAO;
import com.wgu.d308.entities.Excursion;
import com.wgu.d308.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version= 6,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE==null){
            synchronized (AppDatabase.class){
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}