package com.wgu.d308.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.d308.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursion_table ORDER BY id ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursion_table WHERE vacationID=:prod ORDER BY id ASC")
    List<Excursion> getAssociatedExcursions(int prod);

    @Query("SELECT * FROM excursion_table WHERE id = :excursionId LIMIT 1")
    Excursion getExcursionById(int excursionId);

}