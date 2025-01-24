package com.example.start;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PillViewModel extends AndroidViewModel {

    private final PillRepository repository;
    private final LiveData<List<Pill>> allPills;

    public PillViewModel(@NonNull Application application) {
        super(application);
        repository = new PillRepository(application);
        allPills = repository.getAllPills();
    }

    public LiveData<List<Pill>> getAllPills() {
        return allPills;
    }

    public void insert(Pill pill) {
        repository.insert(pill);
    }
}