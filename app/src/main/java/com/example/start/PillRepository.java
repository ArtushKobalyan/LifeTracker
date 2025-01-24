package com.example.start;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PillRepository {

    private final PillDao pillDao;
    private final LiveData<List<Pill>> allPills;
    private final ExecutorService executorService;

    public PillRepository(Application application) {
        PillDatabase database = PillDatabase.getInstance(application);
        pillDao = database.pillDao();
        allPills = pillDao.getAllPills();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void deletePill(Pill pill) {
        new Thread(() -> pillDao.delete(pill)).start();}

    public LiveData<List<Pill>> getAllPills() {
        return allPills;
    }

    public void insert(Pill pill) {
        executorService.execute(() -> pillDao.insert(pill));
    }
}