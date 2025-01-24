package com.example.start;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PillDao {
    @Insert
    void insert(Pill pill);

    @Delete
    void delete(Pill pill);

    @Query("SELECT * FROM pill_table")
    LiveData<List<Pill>> getAllPills();
}