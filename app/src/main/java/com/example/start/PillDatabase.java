package com.example.start;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pill.class}, version = 1, exportSchema = false)
public abstract class PillDatabase extends RoomDatabase {
    private static PillDatabase instance;

    public abstract PillDao pillDao();

    public static synchronized PillDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PillDatabase.class, "pill_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}